package com.championwayappsprtchapwayway

import android.app.Application
import com.onesignal.OneSignal
import com.onesignal.debug.LogLevel

class SportQuizzApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            OneSignal.Debug.logLevel = LogLevel.VERBOSE
        }

        OneSignal.initWithContext(this, getString(R.string.onesignal_app_id))
        OneSignalInApp.setupLifecycleLogging()
        OneSignalPush.setupObservers()
        OneSignalInApp.pauseUntilStartup()
    }
}
