package com.spinoza.cryptoapp.data.mapper

import com.google.gson.Gson
import com.spinoza.cryptoapp.data.database.CoinInfoDbModel
import com.spinoza.cryptoapp.data.network.model.CoinInfoDto
import com.spinoza.cryptoapp.data.network.model.CoinInfoJsonContainerDto
import com.spinoza.cryptoapp.data.network.model.CoinNamesListDto
import com.spinoza.cryptoapp.domain.CoinInfo
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class CoinMapper @Inject constructor(){
    fun mapDtoToDbModel(dto: CoinInfoDto): CoinInfoDbModel = CoinInfoDbModel(
        fromSymbol = dto.fromSymbol,
        toSymbol = dto.toSymbol,
        price = dto.price,
        lastUpdate = dto.lastUpdate,
        highDay = dto.highDay,
        lowDay = dto.lowDay,
        lastMarket = dto.lastMarket,
        imageUrl = BASE_IMAGE_URL + dto.imageUrl
    )

    fun mapJsonContainerToListCoinInfo(jsonContainer: CoinInfoJsonContainerDto): List<CoinInfoDto> {
        val result = mutableListOf<CoinInfoDto>()
        val jsonObject = jsonContainer.json
        jsonObject?.let { json ->
            json.keySet().forEach { coinKey ->
                val currencyJson = json.getAsJsonObject(coinKey)
                currencyJson.keySet().forEach { currencyKey ->
                    val priceInfo = Gson().fromJson(
                        currencyJson.getAsJsonObject(currencyKey),
                        CoinInfoDto::class.java
                    )
                    result.add(priceInfo)
                }
            }
        }
        return result
    }

    fun mapNamesListToString(namesListDto: CoinNamesListDto): String {
        return namesListDto.names?.map { it.coinName?.name }?.joinToString(",") ?: ""
    }

    fun mapDbModelToEntity(dbModel: CoinInfoDbModel): CoinInfo = CoinInfo(
        fromSymbol = dbModel.fromSymbol,
        toSymbol = dbModel.toSymbol,
        price = dbModel.price,
        lastUpdate = convertTimeStampToTime(dbModel.lastUpdate),
        highDay = dbModel.highDay,
        lowDay = dbModel.lowDay,
        lastMarket = dbModel.lastMarket,
        imageUrl = dbModel.imageUrl
    )

    private fun convertTimeStampToTime(timeStamp: Long?): String {
        val simpleDateFormat = SimpleDateFormat("HH:mm:ss", Locale.getDefault())
        simpleDateFormat.timeZone = TimeZone.getDefault()
        var result = ""
        timeStamp?.let {
            result = simpleDateFormat.format(Date(Timestamp(timeStamp * 1000).time))
        }
        return result
    }

    companion object {
        const val BASE_IMAGE_URL = "https://cryptocompare.com"
    }
}