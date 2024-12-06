package com.practicum.buyinglist

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class BuyingApplication : Application() {
    override fun onCreate() {
        super.onCreate()
    }
}
