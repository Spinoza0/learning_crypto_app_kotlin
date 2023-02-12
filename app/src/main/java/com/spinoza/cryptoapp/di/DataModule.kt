package com.spinoza.cryptoapp.di

import android.app.Application
import com.spinoza.cryptoapp.data.database.AppDataBase
import com.spinoza.cryptoapp.data.database.CoinInfoDao
import com.spinoza.cryptoapp.data.network.ApiFactory
import com.spinoza.cryptoapp.data.network.ApiService
import com.spinoza.cryptoapp.data.repository.CoinRepositoryImpl
import com.spinoza.cryptoapp.domain.CoinRepository
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
interface DataModule {
    @Binds
    @ApplicationScope
    fun bindCoinRepository(impl: CoinRepositoryImpl): CoinRepository

    companion object {
        @Provides
        @ApplicationScope
        fun provideCoinInfoDao(application: Application): CoinInfoDao =
            AppDataBase.getInstance(application).coinInfoDao()

        @Provides
        @ApplicationScope
        fun provideApiService(): ApiService = ApiFactory.apiService
    }
}