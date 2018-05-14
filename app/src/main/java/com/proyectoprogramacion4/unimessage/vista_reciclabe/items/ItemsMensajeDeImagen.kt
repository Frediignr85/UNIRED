package com.proyectoprogramacion4.unimessage.vista_reciclabe.items

import android.content.Context
import com.proyectoprogramacion4.unimessage.Glide.GlideApp
import com.proyectoprogramacion4.unimessage.R
import com.proyectoprogramacion4.unimessage.modelo.MensajeDeImagen
import com.proyectoprogramacion4.unimessage.util.StorageUtil
import com.xwray.groupie.kotlinandroidextensions.ViewHolder
import kotlinx.android.synthetic.main.item_mensaje_de_imagen.*

class ItemsMensajeDeImagen (val message: MensajeDeImagen,
                            val context: Context)
    : ItemMensaje(message) {

    override fun bind(viewHolder: ViewHolder, position: Int) {
        super.bind(viewHolder, position)
        GlideApp.with(context)
                .load(StorageUtil.pathToReference(message.imagePath))
                .placeholder(R.drawable.ic_account_circle_black_24dp)
                .into(viewHolder.imageView_message_image)
    }

    override fun getLayout() = R.layout.item_mensaje_de_imagen

    override fun isSameAs(other: com.xwray.groupie.Item<*>?): Boolean {
        if (other !is ItemsMensajeDeImagen)
            return false
        if (this.message != other.message)
            return false
        return true
    }

    override fun equals(other: Any?): Boolean {
        return isSameAs(other as? ItemsMensajeDeImagen)
    }

    override fun hashCode(): Int {
        var result = message.hashCode()
        result = 31 * result + context.hashCode()
        return result
    }
}