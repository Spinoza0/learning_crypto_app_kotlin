package com.spinoza.cryptoapp.presentation.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.spinoza.cryptoapp.domain.ApiService
import com.spinoza.cryptoapp.domain.CoinPriceInfoDao
import com.spinoza.cryptoapp.domain.pojo.CoinPriceInfo
import com.spinoza.cryptoapp.domain.pojo.CoinPriceInfoRawData
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class CoinViewModel(
    private val coinPriceInfoDao: CoinPriceInfoDao,
    private val apiService: ApiService,
) :
    ViewModel() {
    private val compositeDisposable = CompositeDisposable()
    val priceList = coinPriceInfoDao.getPriceList()

    private val _error = MutableLiveData<String>()
    val error: LiveData<String>
        get() = _error


    init {
        loadData()
    }

    fun getDetailInfo(fSym: String): LiveData<CoinPriceInfo> =
        coinPriceInfoDao.getPriceInfoAboutCoin(fSym)

    private fun loadData() {
        val disposable = apiService.getTopCoinsInfo()
            .map { coinInfoListData ->
                coinInfoListData.data
                    ?.map { it.coinInfo?.name }
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
            .subscribe({ coinPriceInfoDao.insertPriceList(it) },
                { _error.value = "Failure: $it.message" })
        compositeDisposable.add(disposable)
    }

    private fun getPriceListFromRawData(
        coinPriceInfoRawData: CoinPriceInfoRawData,
    ): List<CoinPriceInfo> {
        val jsonObject = coinPriceInfoRawData.coinPriceInfoJsonObject
        val coinPriceInfoList = ArrayList<CoinPriceInfo>()
        jsonObject?.let { jsonObjectNonNull ->
            jsonObjectNonNull.keySet().forEach { coinKey ->
                val currencyJson = jsonObjectNonNull.getAsJsonObject(coinKey)
                currencyJson.keySet().forEach { currencyKey ->
                    val coinPriceInfo = Gson().fromJson(
                        currencyJson.getAsJsonObject(currencyKey),
                        CoinPriceInfo::class.java
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