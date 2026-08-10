package com.championwayappsprtchapwayway

import android.util.Log
import com.onesignal.OneSignal
import com.onesignal.inAppMessages.IInAppMessageDidDismissEvent
import com.onesignal.inAppMessages.IInAppMessageDidDisplayEvent
import com.onesignal.inAppMessages.IInAppMessageLifecycleListener
import com.onesignal.inAppMessages.IInAppMessageWillDismissEvent
import com.onesignal.inAppMessages.IInAppMessageWillDisplayEvent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

object OneSignalInApp {
    private const val TAG = "OneSignalInApp"

    const val STARTUP_TRIGGER_KEY = "app_loaded"
    const val STARTUP_TRIGGER_VALUE = "true"

    private val retryDelaysMs = longArrayOf(
        0L,
        2_000L,
        5_000L,
        10_000L,
    )

    @Volatile
    private var startupMessageDisplayed = false

    @Volatile
    private var startupCompleted = false

    private var startupJob: Job? = null
    private var onDismissed: (() -> Unit)? = null
    private var onContinueWithoutPopup: (() -> Unit)? = null
    private var onFailed: (() -> Unit)? = null

    private val lifecycleListener = object : IInAppMessageLifecycleListener {
        override fun onWillDisplay(event: IInAppMessageWillDisplayEvent) {
            Log.d(TAG, "In-app will display: ${event.message.messageId}")
        }

        override fun onDidDisplay(event: IInAppMessageDidDisplayEvent) {
            startupMessageDisplayed = true
            Log.d(TAG, "In-app did display: ${event.message.messageId}")
        }

        override fun onWillDismiss(event: IInAppMessageWillDismissEvent) {
            Log.d(TAG, "In-app will dismiss: ${event.message.messageId}")
        }

        override fun onDidDismiss(event: IInAppMessageDidDismissEvent) {
            if (startupCompleted) return

            startupCompleted = true
            Log.d(TAG, "In-app did dismiss: ${event.message.messageId}")
            OneSignalPush.requestPushPermission()
            onDismissed?.invoke()
        }
    }

    fun setupLifecycleLogging() {
        OneSignal.InAppMessages.addLifecycleListener(lifecycleListener)
    }

    fun pauseUntilStartup() {
        OneSignal.InAppMessages.paused = true
    }

    fun showStartupMessage(
        hasInternet: () -> Boolean,
        onDismissed: () -> Unit,
        onContinueWithoutPopup: () -> Unit,
        onFailed: () -> Unit,
    ) {
        startupJob?.cancel()
        startupMessageDisplayed = false
        startupCompleted = false
        this.onDismissed = onDismissed
        this.onContinueWithoutPopup = onContinueWithoutPopup
        this.onFailed = onFailed

        OneSignal.InAppMessages.paused = false

        startupJob = CoroutineScope(Dispatchers.Main).launch {
            if (!hasInternet()) {
                Log.w(TAG, "No internet connection before startup popup")
                failStartup(onFailed)
                return@launch
            }

            var elapsedMs = 0L

            for (delayMs in retryDelaysMs) {
                val waitMs = delayMs - elapsedMs
                if (waitMs > 0) {
                    delay(waitMs)
                    elapsedMs += waitMs
                }

                if (startupCompleted) return@launch

                if (!hasInternet()) {
                    Log.w(TAG, "Internet lost while waiting for startup popup")
                    failStartup(onFailed)
                    return@launch
                }

                if (startupMessageDisplayed) {
                    Log.d(TAG, "Startup in-app displayed, waiting for dismiss")
                    return@launch
                }

                fireStartupTrigger()
            }

            if (!startupMessageDisplayed && !startupCompleted) {
                Log.i(
                    TAG,
                    "Startup in-app was not shown after ${retryDelaysMs.last()}ms; continuing without popup",
                )
                continueWithoutPopup(onContinueWithoutPopup)
            }
        }
    }

    private fun failStartup(onFailed: () -> Unit) {
        if (startupCompleted) return
        startupCompleted = true
        startupJob?.cancel()
        OneSignal.InAppMessages.paused = true
        onFailed()
    }

    private fun continueWithoutPopup(onContinue: () -> Unit) {
        if (startupCompleted) return
        startupCompleted = true
        startupJob?.cancel()
        OneSignal.InAppMessages.paused = true
        OneSignalPush.requestPushPermission()
        onContinue()
    }

    private fun fireStartupTrigger() {
        Log.d(TAG, "Firing trigger $STARTUP_TRIGGER_KEY=$STARTUP_TRIGGER_VALUE")
        OneSignal.InAppMessages.addTrigger(STARTUP_TRIGGER_KEY, STARTUP_TRIGGER_VALUE)
    }
}
