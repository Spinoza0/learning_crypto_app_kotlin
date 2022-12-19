package com.spinoza.cryptoapp.utils

import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.util.*

fun convertTimeStampToTime(timeStamp: Int?): String {
    val simpleDateFormat = SimpleDateFormat("HH:mm:ss", Locale.getDefault())
    simpleDateFormat.timeZone = TimeZone.getDefault()
    var result = ""
    timeStamp?.let {
        result = simpleDateFormat.format(Date(Timestamp(timeStamp * 1000L).time))
    }
    return result
}