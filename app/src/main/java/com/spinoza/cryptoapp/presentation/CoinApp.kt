package com.spinoza.cryptoapp.presentation

import android.app.Application
import com.spinoza.cryptoapp.di.DaggerApplicationComponent

class CoinApp : Application() {
    val component by lazy {
        DaggerApplicationComponent.factory().create(this)
    }
}