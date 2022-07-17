package com.salman

import android.app.Application

class CorApplication : Application() {

    init {
        instance = this
    }

    companion object {
        private var instance: CorApplication? = null
        fun getInstance(): CorApplication {
            synchronized(CorApplication::class.java) {
                if (instance == null)
                    instance =
                        CorApplication()

            }
            return instance!!
        }
    }

    override fun onCreate() {
        super.onCreate()
    }

}
