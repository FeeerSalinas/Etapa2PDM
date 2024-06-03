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


            // Obtener el nombre de usuario del SharedPreferences
            val nombreUsuario = username

            // Obtener el correo del SharedPreferences
            val correoUsuario = "correo@example.com" // Debes obtener el correo del usuario de alguna fuente, como la base de datos

            // Pasar el nombre de usuario y correo a la actividad PerfilActivity
            val intent = Intent(this, Perfil::class.java)
            intent.putExtra("nombreUsuario", nombreUsuario)
            intent.putExtra("correoUsuario", correoUsuario)
            startActivity(intent)
            finish()


        }
        val btnRegistrarseInicio: Button = findViewById(R.id.btnRegistrarseInicio)
        btnRegistrarseInicio.setOnClickListener {

            val intent: Intent = Intent(this, Registro::class.java)
            startActivity(intent)
        }


        val destino1 = DestinoTuristico(
            nombre = "Lago de Coatepeque",
            descripcion = " Un hermoso lago rodeado de volcanes y pueblos pintorescos.",
            imagen = "https://olaspermanentes.surf/wp-content/uploads/2023/08/Lago-de-Coatepeque-1.jpg",
        )
        val destino2 = DestinoTuristico(
            nombre = "Playa El Tunco",
            descripcion = " Una playa famosa entre surfistas con aguas cristalinas y olas perfectas.",
            imagen = " https://ipanelsalvador.wordpress.com/wp-content/uploads/2012/09/eltiunco.jpg"
        )

        val destino3 = DestinoTuristico(
            nombre = " Ruta de las Flores",
            descripcion = " Una ruta escénica que pasa por pintorescos pueblos, cafetales y cascadas.",
            imagen = " https://diarioelsalvador.com/wp-content/uploads/2021/04/WhatsApp-Image-2021-04-02-at-10.51.23-AM-3.jpeg"
        )

        val destino4 = DestinoTuristico(
            nombre = "Cerro Verde",
            descripcion = "Un volcán inactivo con senderos para hacer senderismo y disfrutar de vistas panorámicas.",
            imagen = " https://pateperro.app/wp-content/uploads/2022/02/puzzlefactory-768x539.jpg"
        )

        val destino5 = DestinoTuristico(
            nombre = "Parque Nacional El Imposible",
            descripcion = "Un área protegida con una gran diversidad de flora y fauna, perfecta para hacer senderismo.",
            imagen = " https://elsalvador.travel/system/wp-content/uploads/2021/04/10012021-El-Imposible-APPEX-5.jpg"
        )

        val destino6 = DestinoTuristico(
            nombre = "Puerta del Diablo",
            descripcion = "Un mirador natural con impresionantes vistas del valle de San Salvador y sus alrededores.",
            imagen = " https://elsalvadoresbello.com/wp-content/uploads/2019/01/Puerta-del-Diablo-El-Salvador-es-Bello-1275x850.jpg"
        )
        val destino7 = DestinoTuristico(
            nombre = "Tazumal",
            descripcion = "Un importante sitio arqueológico que muestra la historia de la civilización maya en la región.",
            imagen = " https://www.cultura.gob.sv/wp-content/uploads/2023/05/TAZUMAL.jpg"
        )

        val destino8 = DestinoTuristico(
            nombre = "El Boquerón",
            descripcion = "Un cráter volcánico que alberga un bosque nuboso y ofrece vistas espectaculares.",
            imagen = " https://d2t54f3e471ia1.cloudfront.net/ricardosiman/multimedia/galerias/fotos/1903684_514.jpg"
        )
// Insertar los destinos en la base de datos
        dbHelper.insertDestinoTuristico(destino1)
        dbHelper.insertDestinoTuristico(destino2)
        dbHelper.insertDestinoTuristico(destino3)
        dbHelper.insertDestinoTuristico(destino4)
        dbHelper.insertDestinoTuristico(destino5)
        dbHelper.insertDestinoTuristico(destino6)
        dbHelper.insertDestinoTuristico(destino7)
        dbHelper.insertDestinoTuristico(destino8)


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