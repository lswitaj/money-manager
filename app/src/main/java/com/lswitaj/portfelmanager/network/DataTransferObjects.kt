package com.lswitaj.portfelmanager.network

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize

/**
 * DAO for getQuote()
 ** Request example:
 * https://www.alphavantage.co/query?function=GLOBAL_QUOTE&symbol=AAPL&apikey=L4VWY02QZAQREPRJ
 ** Response example:
 * {
 *  "Global Quote": {
 *   "01. symbol": "AAPL",
 *   "02. open": "115.5500",
 *   "03. high": "117.5900",
 *   "04. low": "114.1300",
 *   "05. price": "115.9700",
 *   "06. volume": "138023390",
 *   "07. latest trading day": "2020-11-10",
 *   "08. previous close": "116.3200",
 *   "09. change": "-0.3500",
 *   "10. change percent": "-0.3009%"
 *  }
 * }
 */
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

/**
 * DAO for getSearchableItems()
 ** Request example:
 * https://www.alphavantage.co/query?function=SYMBOL_SEARCH&keywords=opera&apikey=L4VWY02QZAQREPRJ
 ** Response example:
 * {
 *  "bestMatches": [
 *  {
 *   "1. symbol": "OPRA",
 *   "2. name": "Opera Limited",
 *   "3. type": "Equity",
 *   "4. region": "United States",
 *   "5. marketOpen": "09:30",
 *   "6. marketClose": "16:00",
 *   "7. timezone": "UTC-05",
 *   "8. currency": "USD",
 *   "9. matchScore": "0.8889"
 *  }, {...}
 *  ]
 * }
 */
@JsonClass(generateAdapter = true)
data class SearchableSymbols(
    val bestMatches: List<SymbolMatches>
)

@Parcelize
@JsonClass(generateAdapter = true)
data class SymbolMatches(
    @Json(name = "1. symbol") val symbol: String,
    @Json(name = "2. name") val name: String,
    @Json(name = "3. type") val type: String,
    @Json(name = "4. region") val region: String,
    //TODO(change to Time)
    @Json(name = "5. marketOpen") val marketOpen: String,
    //TODO(change to Time)
    @Json(name = "6. marketClose") val marketClose: String,
    //TODO(change to Timezone)
    @Json(name = "7. timezone") val timezone: String,
    //TODO(to consider adding some currency enum here)
    @Json(name = "8. currency") val currency: String,
    @Json(name = "9. matchScore") val matchScore: String
) : Parcelable
