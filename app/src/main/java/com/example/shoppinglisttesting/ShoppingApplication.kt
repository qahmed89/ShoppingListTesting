package com.example.shoppinglisttesting

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class ShoppingApplication : Application() {
    override fun onCreate() {
        super.onCreate()

    }
}