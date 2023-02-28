package com.dacodes.todolistad

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class AppController : Application() {

    companion object{
        lateinit var instance : AppController
            private set
    }
}