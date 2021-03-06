package com.proyectoprogramacion4.unimessage

import android.app.Activity
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.support.v4.app.NotificationCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ListenerRegistration
import com.proyectoprogramacion4.unimessage.R.id.imageView_send
import com.proyectoprogramacion4.unimessage.R.id.recycler_view_messages
import com.proyectoprogramacion4.unimessage.modelo.*
import com.proyectoprogramacion4.unimessage.util.FirestoreUtil
import com.proyectoprogramacion4.unimessage.util.StorageUtil
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.Section
import com.xwray.groupie.kotlinandroidextensions.ViewHolder
import kotlinx.android.synthetic.main.activity_chat.*
import org.jetbrains.anko.toast
import java.io.ByteArrayOutputStream
import java.util.*

private const val RC_SELECT_IMAGE = 2

class ChatActivity : AppCompatActivity() {

    private lateinit var messagesListenerRegistration: ListenerRegistration
    private var shouldInitRecyclerView = true
    private lateinit var messagesSection: Section
    private lateinit var currentChannelId: String
    private lateinit var currentUser: usuario
    private lateinit var otherUserId:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = intent.getStringExtra(ConstantesAplicacion.NOMBRE_USUARIO)
        FirestoreUtil.getCurrentUser {
            currentUser = it
        }



        otherUserId = intent.getStringExtra(ConstantesAplicacion.ID_USUARIO)
        FirestoreUtil.getOrCreateChatChannel(otherUserId) { channelId ->
            currentChannelId = channelId
            messagesListenerRegistration =
                    FirestoreUtil.addChatMessagesListener(channelId, this, this::updateRecyclerView)



            imageView_send.setOnClickListener {
                val messageToSend =
                        MensajeDeTexto(editText_message.text.toString(), Calendar.getInstance().time,
                                FirebaseAuth.getInstance().currentUser!!.uid, otherUserId, currentUser.nombre)

                editText_message.setText("")
                FirestoreUtil.enviarMensaje(messageToSend, channelId)


            }

            fab_send_image.setOnClickListener {
                val intent = Intent().apply {
                    type = "image/*"
                    action = Intent.ACTION_GET_CONTENT
                    putExtra(Intent.EXTRA_MIME_TYPES, arrayOf("image/jpeg", "image/png", "image/apk"))
                }
                startActivityForResult(Intent.createChooser(intent, "Select Image"), RC_SELECT_IMAGE)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == RC_SELECT_IMAGE && resultCode == Activity.RESULT_OK &&
                data != null && data.data != null) {
            val selectedImagePath = data.data

            val selectedImageBmp = MediaStore.Images.Media.getBitmap(contentResolver, selectedImagePath)

            val outputStream = ByteArrayOutputStream()

            selectedImageBmp.compress(Bitmap.CompressFormat.JPEG, 90, outputStream)
            val selectedImageBytes = outputStream.toByteArray()

            StorageUtil.uploadMessageImage(selectedImageBytes) { imagePath ->
                val messageToSend =
                        MensajeDeImagen(imagePath, Calendar.getInstance().time,
                                FirebaseAuth.getInstance().currentUser!!.uid, otherUserId, currentUser.nombre)
                FirestoreUtil.enviarMensaje(messageToSend, currentChannelId)
            }
        }
    }

    private fun updateRecyclerView(messages: List<com.xwray.groupie.kotlinandroidextensions.Item>) {
        fun init() {
            recycler_view_messages.apply {
                layoutManager = LinearLayoutManager(this@ChatActivity)
                adapter = GroupAdapter<ViewHolder>().apply {
                    messagesSection = Section(messages)
                    this.add(messagesSection)
                }
            }
            shouldInitRecyclerView = false
        }

        fun updateItems() = messagesSection.update(messages)

        if (shouldInitRecyclerView)
            init()
        else
            updateItems()

        recycler_view_messages.scrollToPosition(recycler_view_messages.adapter.itemCount - 1)
    }
}
