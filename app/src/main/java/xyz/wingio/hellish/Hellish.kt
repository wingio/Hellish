package xyz.wingio.hellish

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.koinApplication

class Hellish: Application() {

    override fun onCreate() {
        super.onCreate()

        koinApplication {
            androidContext(this@Hellish)

            modules()
        }
    }

}