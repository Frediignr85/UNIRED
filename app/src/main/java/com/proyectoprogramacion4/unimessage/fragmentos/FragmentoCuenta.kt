package com.proyectoprogramacion4.unimessage.fragmentos


import android.app.Activity
import android.app.SearchManager
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import com.firebase.ui.auth.AuthUI
import com.proyectoprogramacion4.unimessage.Glide.GlideApp

import com.proyectoprogramacion4.unimessage.R
import com.proyectoprogramacion4.unimessage.R.id.editText_name
import com.proyectoprogramacion4.unimessage.R.id.imageView_profile_picture
import com.proyectoprogramacion4.unimessage.Registro
import com.proyectoprogramacion4.unimessage.util.FirestoreUtil
import com.proyectoprogramacion4.unimessage.util.StorageUtil
import kotlinx.android.synthetic.main.fragment_fragmento_cuenta.*
import kotlinx.android.synthetic.main.fragment_fragmento_cuenta.view.*
import org.jetbrains.anko.applyRecursively
import org.jetbrains.anko.clearTask
import org.jetbrains.anko.newTask
import org.jetbrains.anko.support.v4.intentFor
import org.jetbrains.anko.support.v4.toast
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream

class FragmentoCuenta : Fragment() {

    private val RC_SELECT_IMAGE = 2
    private lateinit var selectedImageByte: ByteArray
    private var pictureJustChange = false

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view:View =  inflater.inflate(R.layout.fragment_fragmento_cuenta, container, false)


        view.apply {
            imageView_profile_picture.setOnClickListener {
                val intent = Intent().apply{
                    type = "image/*"
                    action = Intent.ACTION_GET_CONTENT
                    putExtra( Intent.EXTRA_MIME_TYPES, arrayOf("image/jpeg", "image/png"))
                }
                startActivityForResult(Intent.createChooser(intent, "Imagen Seleccionada"),RC_SELECT_IMAGE)
            }

            btn_save.setOnClickListener {
                if (::selectedImageByte.isInitialized)
                {
                    StorageUtil.uploadProfilePhoto(selectedImageByte)
                    {
                        imagePath ->  FirestoreUtil.updateCurrentUser(editText_name.text.toString(), editText_bio.text.toString(), imagePath)
                    }
                }
                else
                {
                    FirestoreUtil.updateCurrentUser(editText_name.text.toString(), editText_bio.text.toString(), null)
                    toast("Guardado")
                }
            }
            btn_sign_out.setOnClickListener{
                AuthUI.getInstance()
                        .signOut(this@FragmentoCuenta.context!!)
                        .addOnCompleteListener{
                            startActivity(intentFor<Registro>().newTask().clearTask())
                        }
            }
        }
        return view
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == RC_SELECT_IMAGE && resultCode == Activity.RESULT_OK && data != null &&  data.data != null)
        {
           val selectedImagePath = data.data
            val selectedImagenBmp = MediaStore.Images.Media.
                    getBitmap(activity?.contentResolver, selectedImagePath)
            val outputStream = ByteArrayOutputStream()
            selectedImagenBmp.compress(Bitmap.CompressFormat.JPEG, 90, outputStream)
            selectedImageByte = outputStream.toByteArray()

            GlideApp.with(this)
                    .load(selectedImageByte)
                    .into(imageView_profile_picture)
            pictureJustChange = true
        }
    }

    override fun onStart() {
        super.onStart()
        FirestoreUtil.getCurrentUser { usuario ->
            if (this@FragmentoCuenta.isVisible)
            {
                editText_name.setText(usuario.nombre)
                editText_bio.setText(usuario.estado)
                if (!pictureJustChange && usuario.fotoDePerfil != null)
                {
                   GlideApp.with(this)
                           .load(StorageUtil.pathToReference(usuario.fotoDePerfil))
                           .placeholder(R.drawable.abc_btn_borderless_material)
                           .into(imageView_profile_picture)
                }
            }
        }
    }

}
