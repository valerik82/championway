package com.championwayappsprtchapwayway

import android.util.Log
import com.onesignal.OneSignal
import com.onesignal.notifications.INotificationLifecycleListener
import com.onesignal.notifications.INotificationWillDisplayEvent
import com.onesignal.notifications.IPermissionObserver
import com.onesignal.user.subscriptions.IPushSubscriptionObserver
import com.onesignal.user.subscriptions.PushSubscriptionChangedState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

object OneSignalPush {
    private const val TAG = "OneSignalPush"

    fun setupObservers() {
        OneSignal.Notifications.addForegroundLifecycleListener(
            object : INotificationLifecycleListener {
                override fun onWillDisplay(event: INotificationWillDisplayEvent) {
                    Log.d(TAG, "Push received in foreground: ${event.notification.title}")
                }
            },
        )

        OneSignal.Notifications.addPermissionObserver(
            object : IPermissionObserver {
                override fun onNotificationPermissionChange(permission: Boolean) {
                    Log.d(TAG, "Notification permission granted: $permission")
                }
            },
        )

        OneSignal.User.pushSubscription.addObserver(
            object : IPushSubscriptionObserver {
                override fun onPushSubscriptionChange(state: PushSubscriptionChangedState) {
                    val current = state.current
                    Log.d(
                        TAG,
                        "Push subscription id=${current.id}, token=${current.token}, optedIn=${current.optedIn}",
                    )
                }
            },
        )
    }

    fun requestPushPermission() {
        CoroutineScope(Dispatchers.Main).launch {
            Log.d(TAG, "Requesting push permission")
            val granted = OneSignal.Notifications.requestPermission(true)
            Log.d(TAG, "Push permission result: $granted")
        }
    }
}
