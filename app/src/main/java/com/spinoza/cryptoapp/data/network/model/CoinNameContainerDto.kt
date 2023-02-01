package com.spinoza.cryptoapp.data.network.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class CoinNameContainerDto(
    @SerializedName("CoinNameDto")
    @Expose
    val coinName: CoinNameDto? = null,
)