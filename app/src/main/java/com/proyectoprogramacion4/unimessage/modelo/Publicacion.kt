package com.proyectoprogramacion4.unimessage.modelo

import java.util.*

object TipoDePublicacion {
    const val SOLOTEXTO = "TEXTO"
    const val IMAGENYTEXTO = "IMAGENYTEXTO"
}

interface Publicacion {
    val time: Date
    val senderId: String
    val type: String
}