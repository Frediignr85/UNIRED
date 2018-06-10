package com.proyectoprogramacion4.unimessage.vista_reciclabe.items

import android.content.Context
import com.proyectoprogramacion4.unimessage.Glide.GlideApp
import com.proyectoprogramacion4.unimessage.R
import com.proyectoprogramacion4.unimessage.modelo.publicacion
import com.proyectoprogramacion4.unimessage.modelo.usuario
import com.proyectoprogramacion4.unimessage.util.StorageUtil
import com.xwray.groupie.kotlinandroidextensions.Item
import com.xwray.groupie.kotlinandroidextensions.ViewHolder

class PublicacionesDatosPersona(val persona: usuario,
                                val idPerson: String,
                                private val context: Context)
    :Item(){
    override fun bind(viewHolder: ViewHolder, position: Int) {

    }

    override fun getLayout() = R.layout.formato_publicaciones

}