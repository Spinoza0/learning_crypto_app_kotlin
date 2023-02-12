package com.spinoza.cryptoapp.di

import androidx.lifecycle.ViewModel
import com.spinoza.cryptoapp.presentation.model.CoinViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(CoinViewModel::class)
    fun bindViewModel(viewModel: CoinViewModel): ViewModel
}