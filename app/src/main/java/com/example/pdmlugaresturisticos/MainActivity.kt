package com.example.pdmlugaresturisticos

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import com.example.pdmlugaresturisticos.helper.DataBaseHelper
import com.example.pdmlugaresturisticos.models.DestinoTuristico
import com.example.pdmlugaresturisticos.models.Usuario

class MainActivity : AppCompatActivity() {
    private lateinit var dbHelper: DataBaseHelper
    private lateinit var sharedPref: SharedPreferences
    private lateinit var imageButtonShowPassword: ImageButton
    private lateinit var txtConstraseña: EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val dbHelper = DataBaseHelper(this)
        sharedPref = getSharedPreferences("miapp", Context.MODE_PRIVATE)

        val btnIniciarSesion: Button = findViewById(R.id.btnIniciarSesion)
        val txtNombreUsuario: EditText = findViewById(R.id.txtNombreUsuario)
        val txtContrasena: EditText = findViewById(R.id.txtConstraseña)
        txtConstraseña = txtContrasena
        imageButtonShowPassword = findViewById(R.id.imageButton7)
        imageButtonShowPassword.setOnClickListener {
            togglePasswordVisibility()
        }
        //Haciendo llamado desde los botones a las otras pantallas
        btnIniciarSesion.setOnClickListener {
            val username = txtNombreUsuario.text.toString().trim()
            val password = txtContrasena.text.toString().trim()

            // Validar campos vacíos
            if (username.isEmpty() || password.isEmpty()) {
                txtNombreUsuario.error = "Usuario requerido"
                txtContrasena.error = "Contraseña requerida"
                return@setOnClickListener
            }

            // Verificar credenciales
            val (valid, idUsuario) = dbHelper.verificarUsuario(username, password)
            if (valid) {
                // Guardar idUsuario y idRol en SharedPreferences
                val idRol = dbHelper.obtenerRolUsuario(idUsuario!!)
                with (sharedPref.edit()) {
                    putInt("idUsuario", idUsuario)
                    putInt("idRol", idRol!!)
                    apply()
                }
                // Iniciar actividad PaginaInicio
                val intent = Intent(this, PaginaInicio::class.java)
                startActivity(intent)
                finish()
            } else {
                txtNombreUsuario.error = "Usuario o contraseña incorrectos"
                txtContrasena.error = "Usuario o contraseña incorrectos"
            }
        }
        val btnRegistrarseInicio: Button = findViewById(R.id.btnRegistrarseInicio)
        btnRegistrarseInicio.setOnClickListener {

            val intent: Intent = Intent(this, Registro::class.java)
            startActivity(intent)
        }
    }
    private fun togglePasswordVisibility() {
        if (txtConstraseña.transformationMethod == PasswordTransformationMethod.getInstance()) {
            // Mostrar contraseña
            txtConstraseña.transformationMethod = HideReturnsTransformationMethod.getInstance()
            imageButtonShowPassword.setImageResource(R.drawable.ojocontra)
        } else {
            // Ocultar contraseña
            txtConstraseña.transformationMethod = PasswordTransformationMethod.getInstance()
            imageButtonShowPassword.setImageResource(R.drawable.ojocontra)
        }
        // Mover el cursor al final después de cambiar el tipo de texto
        txtConstraseña.setSelection(txtConstraseña.text.length)
    }
}