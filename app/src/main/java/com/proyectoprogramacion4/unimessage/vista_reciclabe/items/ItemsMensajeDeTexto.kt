package com.proyectoprogramacion4.unimessage.vista_reciclabe.items

import android.content.ClipData
import android.content.Context
import android.support.design.R.id.message
import android.view.Gravity
import android.widget.FrameLayout
import com.google.firebase.auth.FirebaseAuth
import com.proyectoprogramacion4.unimessage.R
import com.proyectoprogramacion4.unimessage.modelo.MensajeDeTexto
import com.xwray.groupie.Item
import com.xwray.groupie.kotlinandroidextensions.ViewHolder
import kotlinx.android.synthetic.main.item_mensaje_de_texto.*
import org.jetbrains.anko.backgroundResource
import org.jetbrains.anko.wrapContent
import java.text.SimpleDateFormat

class ItemsMensajeDeTexto(val message: MensajeDeTexto,
                      val context: Context)
    : ItemMensaje(message) {

    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.textView_message_text.text = message.text
        super.bind(viewHolder, position)
    }




    override fun getLayout() = R.layout.item_mensaje_de_texto

    override fun isSameAs(other: com.xwray.groupie.Item<*>?): Boolean {
        if (other !is ItemsMensajeDeTexto)
            return false
        if (this.message != other.message)
            return false
        return true
    }

    override fun equals(other: Any?): Boolean {
        return isSameAs(other as? ItemsMensajeDeTexto)
    }

    override fun hashCode(): Int {
        var result = message.hashCode()
        result = 31 * result + context.hashCode()
        return result
    }



}