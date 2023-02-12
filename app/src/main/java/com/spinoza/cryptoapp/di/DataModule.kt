package com.spinoza.cryptoapp.di

import android.app.Application
import com.spinoza.cryptoapp.data.database.AppDataBase
import com.spinoza.cryptoapp.data.database.CoinInfoDao
import com.spinoza.cryptoapp.data.repository.CoinRepositoryImpl
import com.spinoza.cryptoapp.domain.CoinRepository
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
interface DataModule {
    @Binds
    fun bindCoinRepository(impl: CoinRepositoryImpl): CoinRepository

    companion object {
        @Provides
        fun provideCoinInfoDao(application: Application): CoinInfoDao =
            AppDataBase.getInstance(application).coinInfoDao()
    }
}