package com.spinoza.cryptoapp.data.workers

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkerParameters
import com.spinoza.cryptoapp.data.database.AppDataBase
import com.spinoza.cryptoapp.data.mapper.CoinMapper
import com.spinoza.cryptoapp.data.network.ApiFactory
import kotlinx.coroutines.delay

class RefreshDataWorker(context: Context, workerParameters: WorkerParameters) :
    CoroutineWorker(context, workerParameters) {

    private val coinInfoDao = AppDataBase.getInstance(context).coinInfoDao()
    private val apiService = ApiFactory.apiService
    private val mapper = CoinMapper()

    override suspend fun doWork(): Result {
        while (true) {
            try {
                val topCoins =
                    apiService.getTopCoinsInfo(limit = COINS_LIST_LIMIT)
                val fromSymbols = mapper.mapNamesListToString(topCoins)
                val jsonContainerDto = apiService.getFullPriceList(fSyms = fromSymbols)
                val coinInfoDtoList = mapper.mapJsonContainerToListCoinInfo(jsonContainerDto)
                val dbModelList = coinInfoDtoList.map {
                    mapper.mapDtoToDbModel(it)
                }
                coinInfoDao.insertPriceList(dbModelList)
            } catch (e: Exception) {
            }
            delay(UPDATE_INTERVAL_MILLIS)
        }
    }

    companion object {
        private const val COINS_LIST_LIMIT = 50
        private const val UPDATE_INTERVAL_MILLIS = 10_000L
        const val NAME = "RefreshDataWorker"

        fun makeRequest() = OneTimeWorkRequestBuilder<RefreshDataWorker>().build()
    }
}