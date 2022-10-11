package com.rne1223.bitfit

import android.app.Application

class BitFitApplication: Application() {
        val db by lazy { AppDatabase.getInstance(this) }
}