package com.spinoza.cryptoapp.data.repository

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.spinoza.cryptoapp.data.database.AppDataBase
import com.spinoza.cryptoapp.data.mapper.CoinMapper
import com.spinoza.cryptoapp.data.network.ApiFactory
import com.spinoza.cryptoapp.domain.CoinInfo
import com.spinoza.cryptoapp.domain.CoinRepository
import kotlinx.coroutines.delay

class CoinRepositoryImpl(application: Application) : CoinRepository {
    private val coinInfoDao = AppDataBase.getInstance(application).coinPriceInfoDao()
    private val apiService = ApiFactory.apiService

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

    override suspend fun loadData() {
        while (true) {
            val topCoins = apiService.getTopCoinsInfo(limit = COINS_LIST_LIMIT)
            val fromSymbols = mapper.mapNamesListToString(topCoins)
            val jsonContainerDto = apiService.getFullPriceList(fSyms = fromSymbols)
            val coinInfoDtoList = mapper.mapJsonContainerToListCoinInfo(jsonContainerDto)
            val dbModelList = coinInfoDtoList.map {
                mapper.mapDtoToDbModel(it)
            }
            coinInfoDao.insertPriceList(dbModelList)
            delay(UPDATE_INTERVAL_MILLIS)
        }
    }

    companion object {
        private const val COINS_LIST_LIMIT = 50
        private const val UPDATE_INTERVAL_MILLIS = 10_000L
    }
}