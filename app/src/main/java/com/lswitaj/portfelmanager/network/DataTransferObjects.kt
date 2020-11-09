package com.lswitaj.portfelmanager.network

import androidx.lifecycle.GeneratedAdapter
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.util.*

////TODO(remove temp toString() method)
@JsonClass(generateAdapter = true)
class Quote<QuoteProperty>()

@JsonClass(generateAdapter = true)
data class QuoteProperty(
    val symbol: String,
    val open: Double,
    val high: Double,
    val low: Double,
    val price: Double,
    val volume: Int,
    @Json(name = "latest trading day") val latestTradingDay: Date,
    @Json(name = "previous close") val previousClose: Double,
    val change: Double,
    //TODO(change the string to a proper Double without a percent char)
    @Json(name = "change percent") val changePercent: String
)