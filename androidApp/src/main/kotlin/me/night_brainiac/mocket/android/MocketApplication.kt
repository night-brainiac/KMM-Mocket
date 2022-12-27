package me.night_brainiac.mocket.android

import android.app.Application
import me.night_brainiac.mocket.android.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidFileProperties
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import timber.log.Timber

class MocketApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger() // Log Koin into Android logger(Level.INFO by default)
            androidContext(this@MocketApplication) // Reference Android context
            androidFileProperties() // Use properties from assets/koin.properties

            modules(appModule) // Load modules
        }

        if (BuildConfig.DEBUG) Timber.plant(Timber.DebugTree())
    }
}
