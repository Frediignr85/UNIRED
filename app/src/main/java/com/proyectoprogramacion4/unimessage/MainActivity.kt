package com.proyectoprogramacion4.unimessage

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import com.proyectoprogramacion4.unimessage.fragmentos.FragmentoContactos
import com.proyectoprogramacion4.unimessage.fragmentos.FragmentoCuenta
import com.proyectoprogramacion4.unimessage.fragmentos.FragmentoPublicaciones
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        replaceFragment(FragmentoContactos())

        navigation.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.Contactos -> {
                    replaceFragment(FragmentoContactos( ))
                    true
                }
                R.id.Perfil -> {
                    replaceFragment(FragmentoCuenta())
                    true
                }
                R.id.Publicaciones -> {
                    replaceFragment(FragmentoPublicaciones())
                    true
                }
                else -> false
            }
        }
    }
    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
                .replace(R.id.Fragmento, fragment)
                .commit()
    }
}
