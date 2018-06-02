package com.proyectoprogramacion4.unimessage

class InformacionPublicacion {
    var userUID:String? = null
    var texto:String? = null
    var imagenDelPost:String? = null

    constructor(UserUID: String, Texto:String, ImagenDelPost: String)
    {
        this.userUID = UserUID
        this.texto = Texto
        this.imagenDelPost = ImagenDelPost
    }
}