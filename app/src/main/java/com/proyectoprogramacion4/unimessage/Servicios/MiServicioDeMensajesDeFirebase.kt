package com.proyectoprogramacion4.unimessage.Servicios

import android.content.Context
import android.os.Build
import android.os.Vibrator
import android.support.annotation.RequiresApi
import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage


class MiServicioDeMensajesDeFirebase : FirebaseMessagingService() {

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onMessageReceived(mensajeRemoto: RemoteMessage) {
        if (mensajeRemoto.notification != null) {
            Log.d("FCM", mensajeRemoto.data.toString())
        }
    }

    fun vibrar() {
        var vibrator: Vibrator? = null
        vibrator = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        val time = 1
        if (vibrator.hasVibrator()) {
            vibrator.vibrate(time.toLong())
            Log.d("PELLODEBUG", "Vibrating $time milliseconds")
            val pattern = longArrayOf(10,10,10)
            vibrator.vibrate(pattern, 1)
            vibrator.cancel()
        }
    }

    override fun onCreate() {
        super.onCreate()
        vibrar()
    }

}
