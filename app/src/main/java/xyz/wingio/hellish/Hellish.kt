package xyz.wingio.hellish

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.dsl.koinApplication
import xyz.wingio.hellish.di.HttpModule
import xyz.wingio.hellish.di.LoggerModule

class Hellish: Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@Hellish)

            modules(
                LoggerModule,
                HttpModule
            )
        }
    }

}