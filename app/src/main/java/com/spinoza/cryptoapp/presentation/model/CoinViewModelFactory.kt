package com.spinoza.cryptoapp.presentation.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.spinoza.cryptoapp.domain.ApiService
import com.spinoza.cryptoapp.domain.CoinPriceInfoDao

class CoinViewModelFactory(
    private val coinPriceInfoDao: CoinPriceInfoDao,
    private val apiService: ApiService,
) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return modelClass
            .getConstructor(CoinPriceInfoDao::class.java, ApiService::class.java)
            .newInstance(coinPriceInfoDao, apiService)
    }
}