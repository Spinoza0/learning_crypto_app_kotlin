package com.spinoza.cryptoapp.presentation.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.spinoza.cryptoapp.domain.CoinRepository

class CoinViewModelFactory(private val repository: CoinRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return modelClass
            .getConstructor(CoinRepository::class.java)
            .newInstance(repository)
    }
}