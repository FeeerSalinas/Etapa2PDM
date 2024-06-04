
package com.example.pdmlugaresturisticos

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import com.example.pdmlugaresturisticos.helper.DataBaseHelper
import com.example.pdmlugaresturisticos.models.Usuario

class Perfil : AppCompatActivity() {
    private lateinit var dbHelper: DataBaseHelper
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var txtEditName: TextView
    private lateinit var txtEditCorreo: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_perfil)

        dbHelper = DataBaseHelper(this)
        sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE)
        txtEditName = findViewById(R.id.txtEditName)
        txtEditCorreo = findViewById(R.id.txtEditCorreo)

        // Obtén el ID del usuario desde el Intent
        val idUsuario = intent.getIntExtra("idUsuario", -1)
        if (idUsuario != -1) {
            val usuario = dbHelper.getUsuarioById(idUsuario)
            if (usuario != null) {
                mostrarPerfilUsuario(usuario)
            }
        }

        val btnHomePerfil: ImageButton = findViewById(R.id.btnHomePerfil)
        btnHomePerfil.setOnClickListener {
            val intent: Intent = Intent(this, PaginaInicio::class.java)
            startActivity(intent)
        }

        // Obtener los datos del Intent
        val nombreUsuario = intent.getStringExtra("nombreUsuario")
        // Recuperar correo desde SharedPreferences
        val correoUsuario = sharedPreferences.getString("correo", "Correo no disponible")

        // Establecer los datos en las cajas de texto
        txtEditName.text = nombreUsuario
        txtEditCorreo.text = correoUsuario
    }

    private fun mostrarPerfilUsuario(usuario: Usuario) {
        txtEditName.text = usuario.nombre
        txtEditCorreo.text = usuario.correo
    }
}
