package com.lswitaj.portfelmanager

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.parse.Parse
import com.parse.ParseInstallation

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Parse.initialize(
            Parse.Configuration.Builder(this)
                .applicationId(getString(R.string.back4app_app_id))
                .clientKey(getString(R.string.back4app_client_key))
                .server(getString(R.string.back4app_server_url))
                .build()
        )
        //TODO(temporary - to log first event in Back4app console)
        ParseInstallation.getCurrentInstallation().saveInBackground();
        setContentView(R.layout.activity_main)
    }
}