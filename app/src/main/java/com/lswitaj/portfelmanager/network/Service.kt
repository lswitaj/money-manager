package com.lswitaj.portfelmanager.network

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

//TODO(change to not have hardcoded request)
private const val BASE_URL = "https://www.alphavantage.co/"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

//TODO(change all queries to Deferred + suspend + coroutines)
interface AlphaVentageService {
    @GET("query?function=GLOBAL_QUOTE&symbol=AAPL&apikey=L4VWY02QZAQREPRJ")
    suspend fun getQuote(): Quote

    @GET("query?function=SYMBOL_SEARCH&keywords=opera&apikey=L4VWY02QZAQREPRJ")
    suspend fun getSearchableItems(): SearchableSymbols
}

//TODO(add Coroutine adapter)

object AplhaVantageApi {
    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        //.addCallAdapterFactory(CoroutineCallAdapterFactory())
        .build()

    val aplhavantage: AlphaVentageService by lazy {retrofit.create(AlphaVentageService::class.java)}
}