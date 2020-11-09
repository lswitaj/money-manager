package com.lswitaj.portfelmanager.network

import androidx.lifecycle.GeneratedAdapter
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.util.*

@JsonClass(generateAdapter = true)
data class Quote(
    @Json(name = "Global Quote") val quoteProperty: QuoteProperty
)

@JsonClass(generateAdapter = true)
data class QuoteProperty(
    @Json(name = "01. symbol") val symbol: String,
    @Json(name = "02. open") val open: Double,
    @Json(name = "03. high") val high: Double,
    @Json(name = "04. low") val low: Double,
    //TODO(change to double)
    //val price: Double,
    @Json(name = "05. price") val price: String,
    @Json(name = "06. volume") val volume: Int,
    //TODO(change to date)
    //@Json(name = "latest trading day") val latestTradingDay: Date,
    @Json(name = "07. latest trading day") val latestTradingDay: String,
    //TODO(change to date)
    //@Json(name = "previous close") val previousClose: Double,
    @Json(name = "08. previous close") val previousClose: String,
    @Json(name = "09. change") val change: Double,
    //TODO(change the string to a proper Double without a percent char)
    @Json(name = "10. change percent") val changePercent: String
)