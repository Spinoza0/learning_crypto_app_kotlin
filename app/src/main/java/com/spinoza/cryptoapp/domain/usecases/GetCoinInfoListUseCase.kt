package com.spinoza.cryptoapp.domain.usecases

import com.spinoza.cryptoapp.domain.CoinRepository

class GetCoinInfoListUseCase(private val repository: CoinRepository) {
    operator fun invoke() = repository.getCoinInfoList()
}