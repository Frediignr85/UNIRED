package com.proyectoprogramacion4.unimessage

class Publicacion {

    var IdPublicacion: String? = null
    var TextoPublicacion: String? = null
    var URLImagenPublicacion: String? = null
    var IdPersonaPublicacion: String? = null
    constructor(IdPublicacion:String, TextoPublicacion:String, URLImagenPublicacion:String,IdPersonaPublicacion:String)
    {
        this.IdPublicacion = IdPublicacion
        this.TextoPublicacion = TextoPublicacion
        this.URLImagenPublicacion = URLImagenPublicacion
        this.IdPersonaPublicacion = IdPersonaPublicacion
    }
}