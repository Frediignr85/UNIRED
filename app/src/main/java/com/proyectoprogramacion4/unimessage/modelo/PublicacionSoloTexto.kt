package com.proyectoprogramacion4.unimessage.modelo

import java.util.*

class PublicacionSoloTexto (val text: String,
                            override val time: Date,
                            override val senderId: String,
                            override val type: String = MessageType.TEXT)
    : Publicacion {
    constructor() : this("", Date(0), "")
}