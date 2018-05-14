package com.proyectoprogramacion4.unimessage.fragmentos


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SectionIndexer
import com.google.firebase.firestore.ListenerRegistration
import com.proyectoprogramacion4.unimessage.ChatActivity
import com.proyectoprogramacion4.unimessage.ConstantesAplicacion

import com.proyectoprogramacion4.unimessage.R
import com.proyectoprogramacion4.unimessage.R.id.VistaReciclableLayout
import com.proyectoprogramacion4.unimessage.Registro
import com.proyectoprogramacion4.unimessage.util.FirestoreUtil
import com.proyectoprogramacion4.unimessage.vista_reciclabe.items.Contacto
import com.xwray.groupie.*
import kotlinx.android.synthetic.main.fragment_fragmento_contactos.*
import org.jetbrains.anko.support.v4.startActivity


class FragmentoContactos : Fragment() {

    private lateinit var userListenerRegistro: ListenerRegistration
    private var shouldInitRecycleListView = true
    private lateinit var peopleSection :Section


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment


        userListenerRegistro =
                FirestoreUtil.addUsersListener(this.activity!!, this::updateRecycleView)

        return inflater.inflate(R.layout.fragment_fragmento_contactos, container, false)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        FirestoreUtil.removerlistener(userListenerRegistro)
        shouldInitRecycleListView = true
    }
    private fun updateRecycleView(items: List<com.xwray.groupie.kotlinandroidextensions.Item>){

        fun init()
        {
            VistaReciclableLayout.apply {
                layoutManager = LinearLayoutManager(this@FragmentoContactos.context)
                adapter = GroupAdapter<com.xwray.groupie.kotlinandroidextensions.ViewHolder>().apply {
                    peopleSection = Section(items)
                    add(peopleSection)
                    setOnItemClickListener(onItemClick)
                }
            }
            shouldInitRecycleListView = false
        }
        fun actualizarItems() = peopleSection.update(items)
        if (shouldInitRecycleListView)
        {
            init()
        }
        else
        {
            actualizarItems()
        }

    }

    private val onItemClick = OnItemClickListener { item, view ->
        if (item is Contacto) {
            startActivity<ChatActivity>(
                    ConstantesAplicacion.NOMBRE_USUARIO to item.persona.nombre,
                    ConstantesAplicacion.ID_USUARIO to item.IdPersona
            )
        }
    }
}
