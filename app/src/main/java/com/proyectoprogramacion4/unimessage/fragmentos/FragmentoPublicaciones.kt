package com.proyectoprogramacion4.unimessage.fragmentos


import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.support.annotation.RequiresApi
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.proyectoprogramacion4.unimessage.InformacionPublicacion
import com.proyectoprogramacion4.unimessage.Publicacion
import com.proyectoprogramacion4.unimessage.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.agregar_nueva_publicacion.view.*
import kotlinx.android.synthetic.main.formato_publicaciones.view.*
import kotlinx.android.synthetic.main.formato_publicaciones_sin_imagen.view.*
import kotlinx.android.synthetic.main.fragment_fragmento_publicaciones.*
import org.jetbrains.anko.support.v4.toast
import java.io.ByteArrayOutputStream
import java.text.SimpleDateFormat
import java.util.*


class FragmentoPublicaciones : Fragment() {


    private val storageInstance: FirebaseStorage by lazy { FirebaseStorage.getInstance() }
    private val currentUserRef: StorageReference
        get() = storageInstance.reference
                .child(FirebaseAuth.getInstance().currentUser?.uid
                        ?: throw NullPointerException("UID esta vacio."))


    var db = FirebaseFirestore.getInstance()


    private val firstoreInstance: FirebaseFirestore by lazy { FirebaseFirestore.getInstance() }
    private val currentUserRef1: DocumentReference
        get() = firstoreInstance.document("users/${FirebaseAuth.getInstance().currentUser?.uid
                ?: throw NullPointerException("UID esta vacio")}")


    private var database = FirebaseDatabase.getInstance()
    private var myReferencia = database.reference
    var ListaPublicaciones = ArrayList<Publicacion>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_fragmento_publicaciones, container, false)

    }

    var adaptador: MiAdaptadorDePublicaciones? = null




    override fun onStart() {
        super.onStart()
        adaptador = MiAdaptadorDePublicaciones(ListaPublicaciones)
        lista_publicaciones.adapter = adaptador
        cargarPost()
    }

    var terminado = true


    inner class MiAdaptadorDePublicaciones : BaseAdapter {


        var listaDeAdaptadorDeNotas = ArrayList<Publicacion>()

        constructor(listaDeAdaptadorDeNotas: ArrayList<Publicacion>) : super() {
            this.listaDeAdaptadorDeNotas = listaDeAdaptadorDeNotas
        }


        @RequiresApi(Build.VERSION_CODES.O)
        override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {

            var miPublicacion = listaDeAdaptadorDeNotas[p0]

            if (miPublicacion.IdPersonaPublicacion.equals("add")) {

                var miVista = layoutInflater.inflate(R.layout.agregar_nueva_publicacion, null)


                miVista.ImagenPublicarImagen.setOnClickListener(View.OnClickListener {
                    val intent = Intent().apply {
                        type = "image/*"
                        action = Intent.ACTION_GET_CONTENT
                        putExtra(Intent.EXTRA_MIME_TYPES, arrayOf("image/jpeg", "image/png"))
                    }
                    startActivityForResult(Intent.createChooser(intent, "Imagen Seleccionada"), RC_SELECT_IMAGE)
                })
                miVista.CompartirPublicacion.setOnClickListener(View.OnClickListener {


                    val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                    val date = Date()
                    val fecha = dateFormat.format(date)
                    if (::selectedImageByte.isInitialized) {
                        var Nombre: String? = null
                        myReferencia.child("Post").push().setValue(InformacionPublicacion(FirebaseAuth.getInstance().currentUser!!.uid,
                                FirebaseAuth.getInstance().currentUser!!.photoUrl.toString(),
                                FirebaseAuth.getInstance().currentUser!!.email.toString(),
                                miVista.TextoPublicacion.text.toString(), DescargarURL.toString(), fecha.toString()))
                    } else {
                        if (miVista.TextoPublicacion.text.toString() == "")
                        {
                            toast("Publicacion invalida")
                        }
                        else
                        {
                            myReferencia.child("Post").push().setValue(InformacionPublicacion(FirebaseAuth.getInstance().currentUser!!.uid
                                    , FirebaseAuth.getInstance().currentUser!!.photoUrl.toString(),
                                    FirebaseAuth.getInstance().currentUser!!.email.toString(),
                                    miVista.TextoPublicacion.text.toString(), "Null", fecha.toString()))
                            toast("Publicacion Realizada")
                        }

                    }
                })
                return miVista
            }
            if (miPublicacion.IdPersonaPublicacion.equals("Cargando")) {
                val Holaa = layoutInflater.inflate(R.layout.cargando_imagen, null)
                return Holaa
            }
            else {

                if(miPublicacion.URLImagenPublicacion != "Null")
                {
                    var miVista2 = layoutInflater.inflate(R.layout.formato_publicaciones, null)
                    miVista2.txtContenidoPublicacion.setText(miPublicacion.TextoPublicacion)
                    Picasso.with(context).load(miPublicacion.URLImagenPublicacion).into(miVista2.imagenPublicacion)
                    Picasso.with(context).load(miPublicacion.URLFotoDePerfilPersona).into(miVista2.imagenPath)
                    miVista2.txt_NombreUsuario_Publicacion.setText(miPublicacion.NombrePersona)
                    miVista2.txtFecha_publicacion.setText(miPublicacion.Fecha)

                    return miVista2


                }
                else
                {
                    var sinImagen = layoutInflater.inflate(R.layout.formato_publicaciones_sin_imagen, null)
                    sinImagen.TextoDeLaPublicacionn.setText(miPublicacion.TextoPublicacion)
                    Picasso.with(context).load(miPublicacion.URLFotoDePerfilPersona).into(sinImagen.imagenPerfil)
                    sinImagen.NombreUsuario.setText(miPublicacion.NombrePersona)
                    sinImagen.FechaPublicacion.setText(miPublicacion.Fecha)

                    return sinImagen
                }

            }
        }

        override fun getItem(p0: Int): Any {
            return listaDeAdaptadorDeNotas[p0]
        }

        override fun getItemId(p0: Int): Long {
            return p0.toLong()
        }

        override fun getCount(): Int {
            return listaDeAdaptadorDeNotas.size
        }

    }


    val PICK_IMAGE_CODE = 123

    fun CargarImagen() {
        var intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, PICK_IMAGE_CODE)
    }

    private lateinit var selectedImageByte: ByteArray
    val RC_SELECT_IMAGE = 2
    private var pictureJustChange = false

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SELECT_IMAGE && resultCode == Activity.RESULT_OK && data != null && data.data != null) {

            ListaPublicaciones.add(1, Publicacion("0", "", "", "Hola a todo el mundo", "URL", "Cargando", ""))
            adaptador!!.notifyDataSetChanged()
            val selectedImagePath = data.data
            val selectedImagenBmp = MediaStore.Images.Media.getBitmap(activity?.contentResolver, selectedImagePath)
            val outputStream = ByteArrayOutputStream()
            selectedImagenBmp.compress(Bitmap.CompressFormat.JPEG, 90, outputStream)
            selectedImageByte = outputStream.toByteArray()
            pictureJustChange = true
            val ref = currentUserRef.child("publicacionesImagenes/${UUID.nameUUIDFromBytes(selectedImageByte)}")
            terminado = false
            ref.putBytes(selectedImageByte)
                    .addOnFailureListener {
                        toast("Error al subir la imagen")
                    }.addOnSuccessListener { taskSnapshot ->

                        DescargarURL = taskSnapshot.downloadUrl!!.toString()
                        terminado = true
                        toast("Ya puede publicar")
                        ListaPublicaciones.removeAt(1)
                        adaptador!!.notifyDataSetChanged()
                    }
        }

    }

    var DescargarURL: String? = null

    fun DividirCadena(email: String): String {
        val split = email.split("@")
        return split[0]
    }

    fun cargarPost() {
        myReferencia.child("Post")
                .addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(dataSnapshot: DataSnapshot?) {

                        try {

                            ListaPublicaciones.clear()
                            ListaPublicaciones.add(Publicacion("0", "", "", "Hola a todo el mundo", "URL", "add", ""))

                            var td = dataSnapshot!!.value as HashMap<String, Any>
                            for (llave in td.keys) {

                                var post = td[llave] as HashMap<String, Any>
                                ListaPublicaciones.add(Publicacion(llave,
                                        post["urlfotoDePerfilPersona"] as String,
                                        post["nombrePersona"] as String,
                                        post["texto"] as String,
                                        post["imagenDelPost"] as String,
                                        post["userUID"] as String,
                                        post["fecha"] as String))

                            }

                            adaptador!!.notifyDataSetChanged()
                        } catch (ex: Exception) {
                        }
                    }

                    override fun onCancelled(p0: DatabaseError?) {
                        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                    }
                })
    }


}
