package com.proyectoprogramacion4.unimessage.fragmentos


import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.firebase.ui.auth.AuthUI
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ListenerRegistration
import com.proyectoprogramacion4.unimessage.ChatActivity
import com.proyectoprogramacion4.unimessage.ConstantesAplicacion
import com.proyectoprogramacion4.unimessage.R
import com.proyectoprogramacion4.unimessage.Registro
import com.proyectoprogramacion4.unimessage.modelo.MensajeDeTexto
import com.proyectoprogramacion4.unimessage.modelo.MessageType
import com.proyectoprogramacion4.unimessage.modelo.PublicacionSoloTexto
import com.proyectoprogramacion4.unimessage.modelo.TipoDePublicacion
import com.proyectoprogramacion4.unimessage.util.FirestoreUtil
import com.proyectoprogramacion4.unimessage.util.StorageUtil
import com.proyectoprogramacion4.unimessage.vista_reciclabe.items.Contacto
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.OnItemClickListener
import com.xwray.groupie.Section
import com.xwray.groupie.kotlinandroidextensions.ViewHolder
import kotlinx.android.synthetic.main.activity_chat.*
import kotlinx.android.synthetic.main.fragment_fragmento_cuenta.view.*
import kotlinx.android.synthetic.main.fragment_fragmento_publicaciones.*
import org.jetbrains.anko.clearTask
import org.jetbrains.anko.newTask
import org.jetbrains.anko.support.v4.intentFor
import org.jetbrains.anko.support.v4.startActivity
import org.jetbrains.anko.support.v4.toast
import java.util.*

class FragmentoPublicaciones : Fragment() {




}
