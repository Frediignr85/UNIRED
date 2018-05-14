package com.proyectoprogramacion4.unimessage.modelo

import java.util.*

data class MensajeDeTexto(val text: String,
                          override val time: Date,
                          override val senderId: String,
                          override val type: String = MessageType.TEXT)
    : Mensaje {
    constructor() : this("", Date(0), "")
}