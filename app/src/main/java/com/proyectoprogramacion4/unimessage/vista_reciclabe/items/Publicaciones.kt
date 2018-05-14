package com.proyectoprogramacion4.unimessage.vista_reciclabe.items

import android.content.Context
import com.proyectoprogramacion4.unimessage.Glide.GlideApp
import com.proyectoprogramacion4.unimessage.R
import com.proyectoprogramacion4.unimessage.modelo.usuario
import com.proyectoprogramacion4.unimessage.util.StorageUtil
import com.xwray.groupie.kotlinandroidextensions.Item
import com.xwray.groupie.kotlinandroidextensions.ViewHolder
import kotlinx.android.synthetic.main.personas.*
import java.util.*

class Publicaciones(val persona: usuario,
                    val tiempo: Date,
                    val contenido: String,
                    val IdPersona: String,
                    private val context: Context) : Item(){

    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.textView_name.text  = persona.nombre
        viewHolder.textView_bio.text = persona.estado
        if (persona.fotoDePerfil !=  null)
        {
            GlideApp.with(context).load(StorageUtil.pathToReference(persona.fotoDePerfil))
                    .placeholder(R.drawable.ic_account_circle_black_24dp)
                    .into(viewHolder.imageView_profile_picture)
        }

    }

    override fun getLayout() = R.layout.personas

}
