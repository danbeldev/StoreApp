package com.example.storeapp.application

import android.app.Application
import androidx.work.Configuration

class BaseApplication:Application(), Configuration.Provider {
    override fun getWorkManagerConfiguration(): Configuration {
        return Configuration.Builder()
            .setDefaultProcessName("com.example:remote")
            .build()
    }
}