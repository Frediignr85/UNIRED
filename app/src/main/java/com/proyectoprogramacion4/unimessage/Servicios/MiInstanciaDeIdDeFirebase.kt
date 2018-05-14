package com.proyectoprogramacion4.unimessage.Servicios

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.iid.FirebaseInstanceIdService
import com.proyectoprogramacion4.unimessage.util.FirestoreUtil

class MiInstanciaDeIdDeFirebase: FirebaseInstanceIdService() {

    override fun onTokenRefresh() {
        val nuevoRegistroDeToken = FirebaseInstanceId.getInstance().token

        if (FirebaseAuth.getInstance().currentUser != null)
        {
            agregarTokenAFirestore(nuevoRegistroDeToken)
        }

    }
    companion object {
        fun agregarTokenAFirestore(nuevoRegistroDeToken:String?)
        {
            if (nuevoRegistroDeToken == null)
            {
                throw NullPointerException("FCM es nulo")
            }
            FirestoreUtil.getFCMRegistrationToken { tokens ->
                if (tokens.contains(nuevoRegistroDeToken))
                {
                    return@getFCMRegistrationToken
                }

                tokens.add(nuevoRegistroDeToken)
                FirestoreUtil.setFCMRegistrationToken(tokens)
            }
        }
    }

}