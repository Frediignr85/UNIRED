package com.proyectoprogramacion4.unimessage.modelo

import java.util.*

class MensajeDeImagen(val imagePath: String,
                      override val time: Date,
                      override val senderId: String,
                      override val type: String = MessageType.IMAGE)   : Mensaje {
    constructor() : this("", Date(0), "")
}