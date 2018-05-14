package com.proyectoprogramacion4.unimessage.util

import android.content.Context
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import com.proyectoprogramacion4.unimessage.modelo.*
import com.proyectoprogramacion4.unimessage.vista_reciclabe.items.Contacto
import com.proyectoprogramacion4.unimessage.vista_reciclabe.items.ItemsMensajeDeImagen
import com.proyectoprogramacion4.unimessage.vista_reciclabe.items.ItemsMensajeDeTexto
import com.xwray.groupie.kotlinandroidextensions.Item


object FirestoreUtil {
    private val firstoreInstance: FirebaseFirestore by lazy { FirebaseFirestore.getInstance() }
    private val currentUserRef: DocumentReference
        get() = firstoreInstance.document("users/${FirebaseAuth.getInstance().currentUser?.uid
                ?: throw NullPointerException("UID esta vacio")}")

    fun initCurrentUserIfFirstTime(onComplete: () -> Unit) {
        currentUserRef.get().addOnSuccessListener { documentSnapshot ->
            if (!documentSnapshot.exists()) {
                val nuevoUsuario = usuario(FirebaseAuth.getInstance().currentUser?.displayName
                        ?: "",
                        "", null, mutableListOf())
                currentUserRef.set(nuevoUsuario).addOnSuccessListener { onComplete() }
            } else {
                onComplete()
            }
        }
    }

    fun addChatMessagesListener(channelId: String, context: Context,
                                onListen: (List<Item>) -> Unit): ListenerRegistration {
        return chatChannelsCollectionRef.document(channelId).collection("messages")
                .orderBy("time")
                .addSnapshotListener { querySnapshot, firebaseFirestoreException ->
                    if (firebaseFirestoreException != null) {
                        Log.e("FIRESTORE", "ChatMessagesListener error.", firebaseFirestoreException)
                        return@addSnapshotListener
                    }

                    val items = mutableListOf<Item>()
                    querySnapshot!!.documents.forEach {
                        if (it["type"] == MessageType.TEXT) {
                            items.add(ItemsMensajeDeTexto(it.toObject(MensajeDeTexto::class.java)!!, context))

                        } else
                        {
                            items.add(ItemsMensajeDeImagen(it.toObject(MensajeDeImagen::class.java)!!, context))
                        }

                        return@forEach
                    }
                    onListen(items)
                }
    }


    fun enviarMensaje(message: Mensaje, channelId: String) {
        chatChannelsCollectionRef.document(channelId)
                .collection("messages")
                .add(message)
    }



    private val chatChannelsCollectionRef = firstoreInstance.collection("Canal de Mensajes")

    private val canalPublicacionesColeccion = firstoreInstance.collection("Canal de Publicaciones")

    fun getOrCreateChatChannel(otherUserId: String,
                               onComplete: (channelId: String) -> Unit) {
        currentUserRef.collection("engagedChatChannels")
                .document(otherUserId).get().addOnSuccessListener {
                    if (it.exists()) {
                        onComplete(it["channelId"] as String)
                        return@addOnSuccessListener
                    }

                    val currentUserId = FirebaseAuth.getInstance().currentUser!!.uid

                    val newChannel = chatChannelsCollectionRef.document()
                    newChannel.set(CanalDeMensaje(mutableListOf(currentUserId, otherUserId)))

                    currentUserRef
                            .collection("engagedChatChannels")
                            .document(otherUserId)
                            .set(mapOf("channelId" to newChannel.id))

                    firstoreInstance.collection("users").document(otherUserId)
                            .collection("engagedChatChannels")
                            .document(currentUserId)
                            .set(mapOf("channelId" to newChannel.id))

                    onComplete(newChannel.id)
                }
    }





    fun addUsersListener(context: Context, onListen: (List<Item>) -> Unit): ListenerRegistration {
        return firstoreInstance.collection("users")
                .addSnapshotListener { querySnapshot, firebaseFirestoreException ->
                    if (firebaseFirestoreException != null) {
                        Log.e("FIRESTORE", "Users listener error.", firebaseFirestoreException)
                        return@addSnapshotListener
                    }

                    val items = mutableListOf<Item>()
                    if (querySnapshot != null) {
                        querySnapshot.documents.forEach {
                            if (it.id != FirebaseAuth.getInstance().currentUser?.uid)
                                items.add(Contacto(it.toObject(usuario::class.java)!!, it.id, context))
                        }
                    }
                    onListen(items)
                }
    }


    fun updateCurrentUser(nombre: String = "", estado: String = "", fotoDePerfil: String? = null) {
        val userFieldMap = mutableMapOf<String, Any>()
        if (nombre.isNotBlank()) {
            userFieldMap["nombre"] = nombre
        }
        if (estado.isNotBlank()) {
            userFieldMap["estado"] = estado
        }
        if (fotoDePerfil != null) {
            userFieldMap["fotoDePerfil"] = fotoDePerfil
        }
        currentUserRef.update(userFieldMap)
    }

    fun getCurrentUser(onComplete: (usuario) -> Unit) {
        currentUserRef.get().addOnSuccessListener { onComplete(it.toObject(usuario::class.java)!!) }
    }


    fun removerlistener(registration: ListenerRegistration) = registration.remove()

    //RegionFMC

    fun getFCMRegistrationToken(onComplete: (tokens: MutableList<String>) -> Unit)
    {

        currentUserRef.get().addOnSuccessListener {
            var usuario = it.toObject(usuario::class.java)!!
            onComplete(usuario.registroDeTokens)
        }
    }
    fun setFCMRegistrationToken(registrationToken: MutableList<String>)
    {
        currentUserRef.update(mapOf("registroDeTokens" to registrationToken))
    }



    //endRegionFMC





}