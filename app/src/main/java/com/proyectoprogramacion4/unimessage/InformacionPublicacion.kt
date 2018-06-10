package com.proyectoprogramacion4.unimessage

class InformacionPublicacion {
    var userUID:String? = null
    var URLFotoDePerfilPersona: String? = null
    var NombrePersona: String? = null
    var texto:String? = null
    var imagenDelPost:String? = null
    var Fecha: String? = null

    constructor(UserUID: String, urlFotoDePerfil: String, nombrePersona:String, Texto:String, ImagenDelPost: String, fecha: String)
    {
        this.userUID = UserUID
        this.URLFotoDePerfilPersona = urlFotoDePerfil
        this.NombrePersona = nombrePersona
        this.texto = Texto
        this.imagenDelPost = ImagenDelPost
        this.Fecha = fecha
    }
}