package com.proyectoprogramacion4.unimessage.modelo

data class publicacion(val nombre: String,
                   val estado: String,
                   val fotoDePerfil: String?,
                   val registroDeTokens: MutableList<String>) {
    constructor():this("", "", null, mutableListOf())
}