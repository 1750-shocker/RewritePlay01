package com.wzh.rewriteplay01

import android.app.Application
import com.wzh.core.Play

class App:Application() {
    override fun onCreate(){
        super.onCreate()
        Play.initialize(applicationContext)
    }

}