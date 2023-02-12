package com.spinoza.cryptoapp.domain.usecases

import com.spinoza.cryptoapp.domain.CoinRepository
import javax.inject.Inject

class GetCoinInfoUseCase @Inject constructor(private val repository: CoinRepository) {
    operator fun invoke(fromSymbol: String) = repository.getCoinInfo(fromSymbol)
}