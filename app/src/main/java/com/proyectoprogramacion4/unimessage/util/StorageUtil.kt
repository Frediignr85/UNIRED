package com.proyectoprogramacion4.unimessage.util

import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import org.jetbrains.anko.support.v4.toast
import java.util.*

object StorageUtil {
    private val storageInstance: FirebaseStorage by lazy { FirebaseStorage.getInstance() }

    private val currentUserRef: StorageReference
        get() = storageInstance.reference
                .child(FirebaseAuth.getInstance().currentUser?.uid ?: throw NullPointerException("UID is null."))

    fun uploadProfilePhoto(imageBytes: ByteArray,
                           onSuccess: (imagePath: String) -> Unit) {
        val ref = currentUserRef.child("profilePictures/${UUID.nameUUIDFromBytes(imageBytes)}")
        ref.putBytes(imageBytes)
                .addOnSuccessListener {
                    onSuccess(ref.path)
                }
    }
    fun uploadMessageImage(imageBytes: ByteArray,
                           onSuccess: (imagePath: String) -> Unit) {
        val ref = currentUserRef.child("mensajes/${UUID.nameUUIDFromBytes(imageBytes)}")
        ref.putBytes(imageBytes)
                .addOnSuccessListener {
                    onSuccess(ref.path)
                }
    }
    var DescargarURL: String? = null

    fun uploadPublicacionesImagenes (imageBytes: ByteArray,
                           onSuccess: (imagePath: String) -> Unit) {
        val ref = currentUserRef.child("publicacionesImagenes/${UUID.nameUUIDFromBytes(imageBytes)}")
        ref.putBytes(imageBytes)
                .addOnSuccessListener {taskSnapshot ->
                    DescargarURL = taskSnapshot.downloadUrl!!.toString()
                }
    }
    fun pathToReference(path: String) = storageInstance.getReference(path)
}