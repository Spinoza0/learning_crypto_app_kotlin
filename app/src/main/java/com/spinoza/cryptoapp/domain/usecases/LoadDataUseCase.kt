package com.spinoza.cryptoapp.domain.usecases

import com.spinoza.cryptoapp.domain.CoinRepository

class LoadDataUseCase(private val repository: CoinRepository) {
    suspend operator fun invoke() = repository.loadData()
}