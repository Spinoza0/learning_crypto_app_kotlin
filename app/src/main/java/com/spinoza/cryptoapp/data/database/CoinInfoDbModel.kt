package com.spinoza.cryptoapp.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "full_price_list")
data class CoinInfoDbModel(
    @PrimaryKey
    @SerializedName("FROMSYMBOL")
    val fromSymbol: String,
    @SerializedName("TOSYMBOL")
    val toSymbol: String?,
    @SerializedName("PRICE")
    val price: Double?,
    @SerializedName("LASTUPDATE")
    val lastUpdate: Int?,
    @SerializedName("HIGHDAY")
    val highDay: Double?,
    @SerializedName("LOWDAY")
    val lowDay: Double?,
    @SerializedName("LASTMARKET")
    val lastMarket: String?,
    @SerializedName("IMAGEURL")
    val imageUrl: String?,
)