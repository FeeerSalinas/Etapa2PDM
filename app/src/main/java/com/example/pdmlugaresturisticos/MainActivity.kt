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
import com.example.pdmlugaresturisticos.models.ActividadTuristica
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

        val actividad1 = ActividadTuristica(
            nombre = "Escalada en Puerta del Diablo",
            descripcion = "Disfruta de una escalada desafiante con vistas impresionantes.",
            imagen = "https://istu.gob.sv/wp-content/uploads/2024/02/WhatsApp-Image-2024-02-08-at-10.46.10-AM.jpeg",
            fecha = "2024-06-01",
            costo = 20.00,
            idDestinoTuristico = 1
        )
        dbHelper.insertActividadTuristica(actividad1)

        val actividad2 = ActividadTuristica(
            nombre = "Tour guiado en Tazumal",
            descripcion = "Explora las ruinas mayas con un guía experto.",
            imagen = "https://elsalvador.travel/system/wp-content/uploads/2022/08/SantaAna.jpg",
            fecha = "2024-06-02",
            costo = 25.00,
            idDestinoTuristico = 2
        )
        dbHelper.insertActividadTuristica(actividad2)

        val actividad3 = ActividadTuristica(
            nombre = "Senderismo en El Boquerón",
            descripcion = "Recorre los senderos del volcán con vistas espectaculares.",
            imagen = "https://www.contrapunto.com.sv/wp-content/uploads/2022/08/el-boqueron-2.png",
            fecha = "2024-06-03",
            costo = 10.00,
            idDestinoTuristico = 3
        )
        dbHelper.insertActividadTuristica(actividad3)

        val actividad4 = ActividadTuristica(
            nombre = "Paseo en bote en Lago de Coatepeque",
            descripcion = "Navega por las aguas cristalinas del lago de cráter.",
            imagen = "https://cdn-pro.elsalvador.com/wp-content/uploads/2019/06/Lago-turquesa5.jpg",
            fecha = "2024-06-04",
            costo = 30.00,
            idDestinoTuristico = 4
        )
        dbHelper.insertActividadTuristica(actividad4)

        val actividad5 = ActividadTuristica(
            nombre = "Surf en Playa El Tunco",
            descripcion = "Aprende a surfear en una de las playas más famosas.",
            imagen = "https://media.tacdn.com/media/attractions-splice-spp-674x446/0e/bb/53/46.jpg",
            fecha = "2024-06-05",
            costo = 15.00,
            idDestinoTuristico = 5
        )
        dbHelper.insertActividadTuristica(actividad5)

        val actividad6 = ActividadTuristica(
            nombre = "Tour de flores en Ruta de las Flores",
            descripcion = "Visita los coloridos pueblos y disfruta de las flores.",
            imagen = "https://turismo.sv/wp-content/uploads/2019/06/ruta-de-las-flores-1.jpg",
            fecha = "2024-06-06",
            costo = 40.00,
            idDestinoTuristico = 6
        )
        dbHelper.insertActividadTuristica(actividad6)

        val actividad7 = ActividadTuristica(
            nombre = "Excursión en Cerro Verde",
            descripcion = "Descubre la naturaleza y disfruta de vistas panorámicas.",
            imagen = "https://pbs.twimg.com/media/E-y4bJjXoAIy50Z.jpg:large",
            fecha = "2024-06-07",
            costo = 18.00,
            idDestinoTuristico = 7
        )
        dbHelper.insertActividadTuristica(actividad7)

        val actividad8 = ActividadTuristica(
            nombre = "Exploración en Parque Nacional El Imposible",
            descripcion = "Aventura a través de la biodiversidad del parque.",
            imagen = "https://elsalvador.travel/system/wp-content/uploads/2021/04/10012021-El-Imposible-APPEX-5.jpg",
            fecha = "2024-06-08",
            costo = 22.00,
            idDestinoTuristico = 8
        )
        dbHelper.insertActividadTuristica(actividad8)

        val actividad9 = ActividadTuristica(
            nombre = "Escalada en Puerta del Diablo",
            descripcion = "Disfruta de una escalada desafiante con vistas impresionantes.",
            imagen = "https://www.laprensagrafica.com/__export/1505083221154/sites/prensagrafica/img/2017/03/03/c2c604e1-07f2-4840-8ff6-4d67e50c6139.jpg_1758632411.jpg",
            fecha = "2024-06-09",
            costo = 20.00,
            idDestinoTuristico = 9
        )
        dbHelper.insertActividadTuristica(actividad9)


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