package com.spinoza.cryptoapp.presentation.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.spinoza.cryptoapp.domain.CoinRepository
import com.spinoza.cryptoapp.domain.usecases.GetCoinInfoListUseCase
import com.spinoza.cryptoapp.domain.usecases.GetCoinInfoUseCase
import com.spinoza.cryptoapp.domain.usecases.LoadDataUseCase

class CoinViewModel(repository: CoinRepository) : ViewModel() {
    private val getCoinInfoListUseCase = GetCoinInfoListUseCase(repository)
    private val getCoinInfoUseCase = GetCoinInfoUseCase(repository)
    private val loadDataUseCase = LoadDataUseCase(repository)

    val coinInfoList = getCoinInfoListUseCase()

    private val _error = MutableLiveData<String>()
    val error: LiveData<String>
        get() = _error

    init {
        loadDataUseCase()
    }

    fun getDetailInfo(fSym: String) = getCoinInfoUseCase(fSym)
}