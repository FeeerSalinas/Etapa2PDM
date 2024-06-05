package com.example.pdmlugaresturisticos

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.ImageButton
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import kotlin.math.abs

class PaginaInicio : AppCompatActivity() {

    private lateinit var viewPager2: ViewPager2
    private lateinit var handler: Handler
    private lateinit var imageList: ArrayList<Int>
    private lateinit var adapter: imageAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pagina_inicio)


        init()
        setUpTransformer()

        viewPager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                handler.removeCallbacks(runnable)
                handler.postDelayed(runnable, 2000)
            }
        })

        //movilidad con los botones
        val btnPerfilUsuario: ImageButton = findViewById(R.id.btnPerfilUsuario)
        btnPerfilUsuario.setOnClickListener{

            val intent: Intent = Intent(this, Perfil::class.java)
            startActivity(intent)
        }

        val btnAnadirSitio: ImageButton = findViewById(R.id.btnAnadirSitio)
        btnAnadirSitio.setOnClickListener{

            val intent: Intent = Intent(this, agregarSitio::class.java)
            startActivity(intent)

        }

        val btnDestacados: ImageButton = findViewById(R.id.btnDestacados)
        btnDestacados.setOnClickListener{

            val intent: Intent = Intent(this, ViewActividadesTuristicas::class.java)
            startActivity(intent)

        }
        val btnInfo: ImageButton = findViewById(R.id.btnInfo)
        btnInfo.setOnClickListener {

            val intent: Intent = Intent(this, Activity_contacts::class.java)
            startActivity(intent)
        }

        val btnNotificaciones: ImageButton = findViewById(R.id.btnNotificacion)
        btnNotificaciones.setOnClickListener {
            val intent = Intent(this, Notificaciones::class.java)
            startActivity(intent)
        }

    }

    override fun onPause() {
        super.onPause()
        handler.removeCallbacks(runnable)
    }

    override fun onResume() {
        super.onResume()
        handler.postDelayed(runnable, 2000)
    }

    private val runnable = Runnable {
        viewPager2.currentItem = viewPager2.currentItem + 1
    }

    private fun setUpTransformer(){
        val transformer = CompositePageTransformer()
        transformer.addTransformer(MarginPageTransformer(40))
        transformer.addTransformer{ page, position ->
            val r = 1 - abs(position)
            page.scaleY = 0.85f + r * 0.14f
        }

        viewPager2.setPageTransformer(transformer)
    }

    private fun init(){
        viewPager2 = findViewById(R.id.vpSitios)
        handler = Handler(Looper.myLooper()!!)
        imageList = ArrayList()

        //agregando las imagenes
        imageList.add(R.drawable.lago_de_ilopango)
        imageList.add(R.drawable.volcan_de_izalco)
        imageList.add(R.drawable.lagocoatepeque)
        imageList.add(R.drawable.puerta_del_diablo)
        imageList.add(R.drawable.cerro_verde)
        imageList.add(R.drawable.playa_el_tunco)
        imageList.add(R.drawable.surf_city)
        imageList.add(R.drawable.el_pital)
        imageList.add(R.drawable.el_majahual)
        imageList.add(R.drawable.playa_los_cobanos)
        imageList.add(R.drawable.atecozol)


        adapter = imageAdapter(imageList, viewPager2)

        viewPager2.adapter = adapter
        viewPager2.offscreenPageLimit = 3
        viewPager2.clipToPadding  = false
        viewPager2.clipChildren = false
        viewPager2.getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER

    }

}
