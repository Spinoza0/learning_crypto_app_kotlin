package com.spinoza.cryptoapp.domain.usecases

import com.spinoza.cryptoapp.domain.CoinRepository

class GetCoinInfoUseCase(private val repository: CoinRepository) {
    operator fun invoke(fromSymbol: String) = repository.getCoinInfo(fromSymbol)
}