package com.spinoza.cryptoapp.presentation.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.spinoza.cryptoapp.data.network.ApiService
import com.spinoza.cryptoapp.data.database.CoinInfoDao
import com.spinoza.cryptoapp.data.network.model.CoinInfoDto
import com.spinoza.cryptoapp.data.network.model.CoinInfoJsonContainerDto
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class CoinViewModel(
    private val coinInfoDao: CoinInfoDao,
    private val apiService: ApiService,
) :
    ViewModel() {
    private val compositeDisposable = CompositeDisposable()
    val priceList = coinInfoDao.getPriceList()

    private val _error = MutableLiveData<String>()
    val error: LiveData<String>
        get() = _error


    init {
        loadData()
    }

    fun getDetailInfo(fSym: String): LiveData<CoinInfoDto> =
        coinInfoDao.getPriceInfoAboutCoin(fSym)

    private fun loadData() {
        val disposable = apiService.getTopCoinsInfo()
            .map { coinInfoListData ->
                coinInfoListData.names
                    ?.map { it.coinName?.name }
                    ?.joinToString(",")
                    .toString()
            }
            .flatMap { apiService.getFullPriceList(fSyms = it) }
            .map { getPriceListFromRawData(it) }
            .delaySubscription(UPDATE_INTERVAL, TimeUnit.SECONDS)
            .repeat()
            .retry()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ coinInfoDao.insertPriceList(it) },
                { _error.value = "Failure: $it.message" })
        compositeDisposable.add(disposable)
    }

    private fun getPriceListFromRawData(
        coinInfoJsonContainer: CoinInfoJsonContainerDto,
    ): List<CoinInfoDto> {
        val jsonObject = coinInfoJsonContainer.json
        val coinPriceInfoList = ArrayList<CoinInfoDto>()
        jsonObject?.let { jsonObjectNonNull ->
            jsonObjectNonNull.keySet().forEach { coinKey ->
                val currencyJson = jsonObjectNonNull.getAsJsonObject(coinKey)
                currencyJson.keySet().forEach { currencyKey ->
                    val coinPriceInfo = Gson().fromJson(
                        currencyJson.getAsJsonObject(currencyKey),
                        CoinInfoDto::class.java
                    )
                    coinPriceInfoList.add(coinPriceInfo)
                }
            }
        }
        return coinPriceInfoList
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }

    companion object {
        private const val UPDATE_INTERVAL = 10L
    }
}