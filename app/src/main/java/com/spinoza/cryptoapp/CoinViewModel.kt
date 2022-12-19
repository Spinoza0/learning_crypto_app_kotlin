package com.spinoza.cryptoapp

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.google.gson.Gson
import com.spinoza.cryptoapp.api.ApiFactory
import com.spinoza.cryptoapp.database.AppDataBase
import com.spinoza.cryptoapp.pojo.CoinPriceInfo
import com.spinoza.cryptoapp.pojo.CoinPriceInfoRawData
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class CoinViewModel(application: Application) : AndroidViewModel(application) {
    private val db = AppDataBase.getInstance(application)
    private val compositeDisposable = CompositeDisposable()

    val priceList = db.coinPriceInfoDao().getPriceList()

    init {
        loadData()
    }

    fun getDetailInfo(fSym: String): LiveData<CoinPriceInfo> =
        db.coinPriceInfoDao().getPriceInfoAboutCoin(fSym)

    private fun loadData() {
        val disposable = ApiFactory.apiService.getTopCoinsInfo()
            .map { coinInfoListData ->
                coinInfoListData.data
                    ?.map { it.coinInfo?.name }
                    ?.joinToString(",")
                    .toString()
            }
            .flatMap { ApiFactory.apiService.getFullPriceList(fSyms = it) }
            .map { getPriceListFromRawData(it) }
            .delaySubscription(15, TimeUnit.SECONDS)
            .repeat()
            .retry()
            .subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                db.coinPriceInfoDao().insertPriceList(it)
            }, {
                Log.e("TEST_OF_LOADING_DATA", "Failure: $it.message")
            })
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
}