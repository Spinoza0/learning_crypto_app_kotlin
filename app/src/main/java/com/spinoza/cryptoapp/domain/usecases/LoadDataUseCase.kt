package com.spinoza.cryptoapp.domain.usecases

import com.spinoza.cryptoapp.domain.CoinRepository
import javax.inject.Inject

class LoadDataUseCase @Inject constructor(private val repository: CoinRepository) {
    operator fun invoke() = repository.loadData()
}