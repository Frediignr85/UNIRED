package com.proyectoprogramacion4.unimessage.fragmentos


import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.provider.ContactsContract
import android.provider.MediaStore
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.proyectoprogramacion4.unimessage.Publicacion
import com.proyectoprogramacion4.unimessage.R
import com.proyectoprogramacion4.unimessage.R.id.lista_publicaciones
import kotlinx.android.synthetic.main.agregar_nueva_publicacion.view.*
import kotlinx.android.synthetic.main.fragment_fragmento_publicaciones.*
import kotlinx.android.synthetic.main.item_publicacion_solo_texto.view.*
import org.jetbrains.anko.support.v4.toast
import java.io.ByteArrayOutputStream
import java.text.SimpleDateFormat
import java.util.*



class FragmentoPublicaciones : Fragment() {


    private var database = FirebaseDatabase.getInstance()
    private var myReferencia = database.reference
    var ListaPublicaciones = ArrayList<Publicacion>()
    var miEmail:String? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_fragmento_publicaciones, container, false)
    }

    var adaptador:MiAdaptadorDePublicaciones? = null


    var userUID:String? = null



    override fun onStart() {
        super.onStart()



        ListaPublicaciones.add(Publicacion("0", "Hola a todo el mundo", "URL", "add"))
        ListaPublicaciones.add(Publicacion("1", "Siempre con todo", "URL", "Holaa"))
        ListaPublicaciones.add(Publicacion("2", "Probando", "URL", "Stemen"))


        adaptador = MiAdaptadorDePublicaciones( ListaPublicaciones )
        lista_publicaciones.adapter  = adaptador
    }


    inner class MiAdaptadorDePublicaciones:BaseAdapter{

        var listaDeAdaptadorDeNotas = ArrayList<Publicacion>()

        constructor(listaDeAdaptadorDeNotas: ArrayList<Publicacion>):super()
        {
            this.listaDeAdaptadorDeNotas = listaDeAdaptadorDeNotas
        }


        override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {

            var miPublicacion = listaDeAdaptadorDeNotas[p0]

            if (miPublicacion.IdPersonaPublicacion.equals("add"))
            {

                var miVista = layoutInflater.inflate(R.layout.agregar_nueva_publicacion, null)

                miVista.ImagenPublicarImagen.setOnClickListener(View.OnClickListener {
                    CargarImagen()
                })

                miVista.ImagenEnviar.setOnClickListener(View.OnClickListener {
                    myReferencia.child("Post").push().child("userUID").setValue("ertyui")
                    myReferencia.child("Post").push().child("texto").setValue(miVista.TextoPublicacion.text.toString())
                    myReferencia.child("Post").push().child("ImagenPost").setValue("holl")
                    toast("Completado")

                })
                return miVista
            }
            else
            {
                var miVista = layoutInflater.inflate(R.layout.formato_publicaciones, null)
                return miVista

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
    fun CargarImagen()
    {
        var intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, PICK_IMAGE_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == PICK_IMAGE_CODE && data!= null && resultCode == RESULT_OK)
        {
            val selectedImagenData = data.data
            val filePathColum = arrayOf(MediaStore.Images.Media.DATA)
            val cursor = activity?.contentResolver!!.query(selectedImagenData, filePathColum, null, null, null)
            cursor.moveToFirst()
            val columnaIndex = cursor.getColumnIndex(filePathColum[0])
            val picturePath = cursor.getString(columnaIndex)
            cursor.close()
            SubirFotoPublicacion(BitmapFactory.decodeFile(picturePath))


        }

    }
    var DescargarURL:String? = null

    fun SubirFotoPublicacion(bitmap: Bitmap){
        val storage = FirebaseStorage.getInstance()
        val storageRef = storage.getReferenceFromUrl("gs://proyecto-programacion-4.appspot.com")
        val df = SimpleDateFormat("ddMMyyHHmmss")
        val dataObjet = Date()
        val imagenPath = DividirCadena(miEmail!!) + "." +df.format(dataObjet)+ ".jpg"
        val ImageRef = storageRef.child("imagenes publicaciones/"+ imagenPath)
        val baos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,baos)
        val data = baos.toByteArray()
        val uploadTask = ImageRef.putBytes(data)
        uploadTask.addOnFailureListener{
            toast("Fallo al subir imagen")

        }.addOnSuccessListener { taskSnapshot ->

            DescargarURL = taskSnapshot.downloadUrl!!.toString()
        }

    }
    fun DividirCadena(email:String):String{
        val split = email.split("@")
        return  split[0]
    }

}
