
package com.example.pdmlugaresturisticos

import android.annotation.SuppressLint
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.pdmlugaresturisticos.helper.DataBaseHelper

class Registro : AppCompatActivity() {
    private lateinit var dbHelper: DataBaseHelper
    private lateinit var sharedPreferences: SharedPreferences

    @SuppressLint("CutPasteId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro)
        dbHelper = DataBaseHelper(this)
        sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE)

        // Obtener referencias a los elementos de la interfaz de usuario
        val btnRegistrarseRegistro: Button = findViewById(R.id.btnRegistrarseRegistro)
        val btnVolverInicioSesion: Button = findViewById(R.id.btnVolverInicioSesion)
        val txtNombreRegistro: EditText = findViewById(R.id.txtNombreRegistro)
        val txtUsuarioRegistro: EditText = findViewById(R.id.txtUsuarioRegistro)
        val txtCorreoRegistro: EditText = findViewById(R.id.txtCorreoRegistro)
        val txtContraRegistro: EditText = findViewById(R.id.txtContraRegistro)

        //Haciendo llamado desde los botones a las otras pantallas
        btnRegistrarseRegistro.setOnClickListener {

            val nombre = txtNombreRegistro.text.toString().trim()
            val usuario = txtUsuarioRegistro.text.toString().trim()
            val correo = txtCorreoRegistro.text.toString().trim()
            val contrasena = txtContraRegistro.text.toString().trim()
            val rolId = 2 // Asignar rol de Usuario por defecto

            // Validar campos vacíos
            if (usuario.isEmpty() || correo.isEmpty() || contrasena.isEmpty()) {
                Toast.makeText(applicationContext, "Todos los campos son obligatorios", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Verificar si el usuario ya existe
            val (usuarioExiste, _) = dbHelper.verificarUsuario(usuario, contrasena)
            if (usuarioExiste) {
                Toast.makeText(applicationContext, "El usuario ya existe, elige otro nombre de usuario", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Insertar usuario en la base de datos
            val idUsuario = dbHelper.insertUsuario(usuario, contrasena, correo, rolId)
            if (idUsuario != -1L) {
                // Guardar nombre y correo en SharedPreferences
                val editor = sharedPreferences.edit()
                editor.putString("nombre", nombre)
                editor.putString("correo", correo)
                editor.apply()

                Toast.makeText(applicationContext, "Usuario registrado correctamente", Toast.LENGTH_SHORT).show()
                // Ir a la pantalla de inicio de sesión
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                Toast.makeText(applicationContext, "Error al registrar el usuario", Toast.LENGTH_SHORT).show()
            }
        }

        // Listener para el botón de volver al inicio de sesión
        btnVolverInicioSesion.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}
