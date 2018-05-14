package com.proyectoprogramacion4.unimessage.modelo

import java.util.*

object MessageType {
    const val TEXT = "TEXT"
    const val IMAGE = "IMAGE"
}

interface Mensaje {
    val time: Date
    val senderId: String
    val type: String
}