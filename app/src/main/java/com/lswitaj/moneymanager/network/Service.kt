package com.lswitaj.portfelmanager.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

private const val BASE_URL_FINNHUB = "https://finnhub.io/api/v1/"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

//TODO(change all queries to Deferred)
//TODO(extract apiKey to the variable - private const val)
interface FinnhubService {
    @GET("stock/symbol?exchange=US&token=bv2tbon48v6ru7sfdof0")
    suspend fun getSymbolsFromExchange(): List<Symbol>
    //TODO(accept country parameter)
//    @GET("stock/symbol?")
//    suspend fun getSymbolsFromExchange(@Query("exchange") exchange: String?): Quote

    @GET("stock/candle?resolution=D&token=bv2tbon48v6ru7sfdof0")
    suspend fun getCandles(
        @Query("symbol") symbol: String?,
        @Query("from") timestampFrom: String?,
        @Query("to") timestampTo: String?
    ): Candles
}

object FinnhubApi {
    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL_FINNHUB)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        //.addCallAdapterFactory(CoroutineCallAdapterFactory())
        .build()

    val finnhub: FinnhubService by lazy {retrofit.create(FinnhubService::class.java)}
}