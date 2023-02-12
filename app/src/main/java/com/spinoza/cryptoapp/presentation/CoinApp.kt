package com.spinoza.cryptoapp.presentation

import android.app.Application
import androidx.work.Configuration
import com.spinoza.cryptoapp.data.database.AppDataBase
import com.spinoza.cryptoapp.data.mapper.CoinMapper
import com.spinoza.cryptoapp.data.network.ApiFactory
import com.spinoza.cryptoapp.data.workers.RefreshDataWorkerFactory
import com.spinoza.cryptoapp.di.DaggerApplicationComponent

class CoinApp : Application(), Configuration.Provider {
    val component by lazy {
        DaggerApplicationComponent.factory().create(this)
    }

    override fun getWorkManagerConfiguration(): Configuration = Configuration.Builder()
        .setWorkerFactory(
            RefreshDataWorkerFactory(
                AppDataBase.getInstance(this).coinInfoDao(),
                ApiFactory.apiService,
                CoinMapper()
            )
        )
        .build()
}