package com.spinoza.cryptoapp.presentation.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.spinoza.cryptoapp.data.network.ApiService
import com.spinoza.cryptoapp.data.database.CoinInfoDao

class CoinViewModelFactory(
    private val coinInfoDao: CoinInfoDao,
    private val apiService: ApiService,
) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return modelClass
            .getConstructor(CoinInfoDao::class.java, ApiService::class.java)
            .newInstance(coinInfoDao, apiService)
    }
}