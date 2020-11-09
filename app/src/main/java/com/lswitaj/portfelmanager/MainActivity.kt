package com.lswitaj.portfelmanager

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.lswitaj.portfelmanager.network.AlphaVentageService
import com.lswitaj.portfelmanager.network.QuoteProperty
import com.lswitaj.portfelmanager.summary.SummaryViewModel
import retrofit2.Callback

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}