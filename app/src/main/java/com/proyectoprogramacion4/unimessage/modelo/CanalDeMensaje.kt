package com.proyectoprogramacion4.unimessage.modelo

import java.io.Serializable

data class CanalDeMensaje(val userIds: MutableList<String>) {
    constructor() : this(mutableListOf())
}