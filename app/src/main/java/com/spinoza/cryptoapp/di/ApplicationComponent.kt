package com.spinoza.cryptoapp.di

import android.app.Application
import com.spinoza.cryptoapp.presentation.CoinPriceListActivity
import com.spinoza.cryptoapp.presentation.fragment.CoinDetailFragment
import dagger.BindsInstance
import dagger.Component

@Component(modules = [DataModule::class, ViewModelModule::class])
interface ApplicationComponent {
    fun inject(activity: CoinPriceListActivity)
    fun inject(fragment: CoinDetailFragment)

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance application: Application): ApplicationComponent
    }
}