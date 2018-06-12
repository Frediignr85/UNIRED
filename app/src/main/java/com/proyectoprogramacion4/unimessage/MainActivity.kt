package com.proyectoprogramacion4.unimessage

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import com.proyectoprogramacion4.unimessage.fragmentos.FragmentoContactos
import com.proyectoprogramacion4.unimessage.fragmentos.FragmentoCuenta
import com.proyectoprogramacion4.unimessage.fragmentos.FragmentoPublicaciones
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {


    var pagerAdapter:adaptadorFragmentos?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)




        pagerAdapter = adaptadorFragmentos(supportFragmentManager)
        pagerAdapter!!.addFragments(FragmentoContactos(),"contactos")
        pagerAdapter!!.addFragments(FragmentoPublicaciones(),"posts")
        pagerAdapter!!.addFragments(FragmentoCuenta(),"cuenta")
        customViewPager.adapter = pagerAdapter
        customTabLayout.setupWithViewPager(customViewPager)

    }

}
