package com.proyectoprogramacion4.unimessage.Servicios

import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MiServicioDeMensajesDeFirebase: FirebaseMessagingService() {

    override fun onMessageReceived(mensajeRemoto: RemoteMessage) {
        if(mensajeRemoto.notification != null)
        {
           Log.d("FCM", mensajeRemoto.data.toString())
        }
    }
}