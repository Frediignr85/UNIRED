package com.proyectoprogramacion4.unimessage.Servicios

import android.content.Context
import android.os.Build
import android.os.VibrationEffect
import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import android.os.Vibrator
import android.support.annotation.RequiresApi
import org.jetbrains.anko.toast


class MiServicioDeMensajesDeFirebase: FirebaseMessagingService() {

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onMessageReceived(mensajeRemoto: RemoteMessage) {
        if(mensajeRemoto.notification != null)
        {
           Log.d("FCM", mensajeRemoto.data.toString())
        }
    }
}