package com.spinoza.cryptoapp.data.repository

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.work.ExistingWorkPolicy
import androidx.work.WorkManager
import com.spinoza.cryptoapp.data.database.AppDataBase
import com.spinoza.cryptoapp.data.mapper.CoinMapper
import com.spinoza.cryptoapp.data.workers.RefreshDataWorker
import com.spinoza.cryptoapp.domain.CoinInfo
import com.spinoza.cryptoapp.domain.CoinRepository

class CoinRepositoryImpl(private val application: Application) : CoinRepository {
    private val coinInfoDao = AppDataBase.getInstance(application).coinInfoDao()
    private val mapper = CoinMapper()

    override fun getCoinInfoList(): LiveData<List<CoinInfo>> {
        return Transformations.map(coinInfoDao.getPriceList()) { list ->
            list.map { mapper.mapDbModelToEntity(it) }
        }
    }

    override fun getCoinInfo(fromSymbol: String): LiveData<CoinInfo> {
        return Transformations.map(coinInfoDao.getPriceInfoAboutCoin(fromSymbol)) {
            mapper.mapDbModelToEntity(it)
        }
    }

    override fun loadData() {
        val workManager = WorkManager.getInstance(application)
        workManager.enqueueUniqueWork(
            RefreshDataWorker.NAME,
            ExistingWorkPolicy.REPLACE,
            RefreshDataWorker.makeRequest()
        )
    }
}