package com.example.pdmlugaresturisticos.helper

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.example.pdmlugaresturisticos.models.ActividadTuristica
import com.example.pdmlugaresturisticos.models.DestinoTuristico
import com.example.pdmlugaresturisticos.models.Reservacion
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException

class DataBaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "Turismo.db"

        // Tabla DestinosTuristicos
        private const val TABLE_DESTINOS_NAME = "DestinosTuristicos"
        private const val DESTINOS_ID_KEY = "idDestinoTuristico"
        private const val DESTINOS_NOMBRE = "nombre"
        private const val DESTINOS_DESCRIPCION = "descripcion"
        private const val DESTINOS_IMAGEN = "imagen"

        // Tabla ActividadesTuristicas
        private const val TABLE_ACTIVIDADES_NAME = "ActividadesTuristicas"
        private const val ACTIVIDADES_ID_KEY = "id"
        private const val ACTIVIDADES_NOMBRE = "nombre"
        private const val ACTIVIDADES_DESCRIPCION = "descripcion"
        private const val ACTIVIDADES_IMAGEN = "imagen"
        private const val ACTIVIDADES_FECHA = "fecha"
        private const val ACTIVIDADES_COSTO = "costo"
        private const val ACTIVIDADES_DESTINO_ID_KEY = "idDestinoTuristico"

        // Tabla Reservaciones
        private const val TABLE_RESERVACIONES_NAME = "Reservaciones"
        private const val RESERVACIONES_ID_KEY = "idReserva"
        private const val RESERVACIONES_USUARIO_ID = "idUsuario"
        private const val RESERVACIONES_ACTIVIDAD_ID = "idActividadTuristica"

        // Tabla rol
        private const val TABLE_ROL = "rol"
        private const val COLUMN_ID_ROL = "idRol"
        private const val COLUMN_NOMBRE_ROL = "nombreRol"

        // Tabla usuario
        private const val TABLE_USUARIO = "usuario"
        private const val COLUMN_ID_USUARIO = "idUsuario"
        private const val COLUMN_NOMBRE_USUARIO = "nombreUsuario"
        private const val COLUMN_CONTRASENA = "contrasena"
        private const val COLUMN_CORREO = "correo"
        private const val COLUMN_ID_ROL_FK = "idRol"

    }

    override fun onCreate(db: SQLiteDatabase?) {

        val CREATE_DESTINOS_TABLE = "CREATE TABLE $TABLE_DESTINOS_NAME (" +
                "$DESTINOS_ID_KEY INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "$DESTINOS_NOMBRE TEXT, " +
                "$DESTINOS_DESCRIPCION TEXT, " +
                "$DESTINOS_IMAGEN TEXT)"

        val CREATE_ACTIVIDADES_TABLE = "CREATE TABLE $TABLE_ACTIVIDADES_NAME (" +
                "$ACTIVIDADES_ID_KEY INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "$ACTIVIDADES_NOMBRE TEXT, " +
                "$ACTIVIDADES_DESCRIPCION TEXT, " +
                "$ACTIVIDADES_IMAGEN TEXT, " +
                "$ACTIVIDADES_FECHA TEXT, " +
                "$ACTIVIDADES_COSTO REAL, " +
                "$ACTIVIDADES_DESTINO_ID_KEY INTEGER, " +
                "FOREIGN KEY($ACTIVIDADES_DESTINO_ID_KEY) REFERENCES $TABLE_DESTINOS_NAME($DESTINOS_ID_KEY))"

        val CREATE_RESERVACIONES_TABLE = "CREATE TABLE $TABLE_RESERVACIONES_NAME (" +
                "$RESERVACIONES_ID_KEY INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "$RESERVACIONES_USUARIO_ID INTEGER, " +
                "$RESERVACIONES_ACTIVIDAD_ID INTEGER, " +
                "FOREIGN KEY($RESERVACIONES_ACTIVIDAD_ID) REFERENCES $TABLE_ACTIVIDADES_NAME($ACTIVIDADES_ID_KEY))"
        // Creación de tabla rol
        val CREATE_TABLE_ROL = "CREATE TABLE $TABLE_ROL (" +
                "$COLUMN_ID_ROL INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "$COLUMN_NOMBRE_ROL TEXT NOT NULL);"

        // Creación de tabla usuario
        val CREATE_TABLE_USUARIO = "CREATE TABLE $TABLE_USUARIO (" +
                "$COLUMN_ID_USUARIO INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "$COLUMN_NOMBRE_USUARIO TEXT NOT NULL, " +
                "$COLUMN_CONTRASENA TEXT NOT NULL, " +
                "$COLUMN_CORREO TEXT NOT NULL, " +
                "$COLUMN_ID_ROL_FK INTEGER NOT NULL, " +
                "FOREIGN KEY ($COLUMN_ID_ROL_FK) REFERENCES $TABLE_ROL($COLUMN_ID_ROL));"
        db?.execSQL(CREATE_DESTINOS_TABLE)
        db?.execSQL(CREATE_ACTIVIDADES_TABLE)
        db?.execSQL(CREATE_RESERVACIONES_TABLE)
        db?.execSQL(CREATE_TABLE_ROL)
        db?.execSQL(CREATE_TABLE_USUARIO)

        insertRol(db, "admin")
        insertRol(db, "usuario")
    }

    fun getAllDestinos(): List<DestinoTuristico> {
        val destinosList = mutableListOf<DestinoTuristico>()
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM DestinosTuristicos", null)
        if (cursor.moveToFirst()) {
            do {
                val idDestinoTuristico = cursor.getInt(cursor.getColumnIndexOrThrow("idDestinoTuristico"))
                val nombre = cursor.getString(cursor.getColumnIndexOrThrow("nombre"))
                val descripcion = cursor.getString(cursor.getColumnIndexOrThrow("descripcion"))
                val imagen = cursor.getString(cursor.getColumnIndexOrThrow("imagen"))
                destinosList.add(DestinoTuristico(idDestinoTuristico, nombre, descripcion, imagen))
            } while (cursor.moveToNext())
        }
        cursor.close()
        return destinosList
    }

    fun insertDestinoTuristico(destino: DestinoTuristico): Long {
        val db = this.writableDatabase

        val valuesDestino = ContentValues().apply {
            put(DESTINOS_NOMBRE, destino.nombre)
            put(DESTINOS_DESCRIPCION, destino.descripcion)
            put(DESTINOS_IMAGEN, destino.imagen)
        }

        val id = db.insert(TABLE_DESTINOS_NAME, null, valuesDestino)
        db.close()
        return id
    }

    fun insertActividadTuristica(actividad: ActividadTuristica): Long {
        val db = this.writableDatabase

        val valuesActividad = ContentValues().apply {
            put(ACTIVIDADES_NOMBRE, actividad.nombre)
            put(ACTIVIDADES_DESCRIPCION, actividad.descripcion)
            put(ACTIVIDADES_IMAGEN, actividad.imagen)
            put(ACTIVIDADES_FECHA, actividad.fecha)
            put(ACTIVIDADES_COSTO, actividad.costo)
            put(ACTIVIDADES_DESTINO_ID_KEY, actividad.idDestinoTuristico)
        }

        val id = db.insert(TABLE_ACTIVIDADES_NAME, null, valuesActividad)
        db.close()
        return id
    }
    fun deleteDestinoTuristico(destinoId: Long): Int {
        val db = writableDatabase

        val result = db.delete(TABLE_DESTINOS_NAME, "$DESTINOS_ID_KEY=?", arrayOf(destinoId.toString()))
        db.delete(TABLE_ACTIVIDADES_NAME, "$ACTIVIDADES_DESTINO_ID_KEY=?", arrayOf(destinoId.toString()))

        db.close()
        return result
    }

    fun deleteActividadTuristica(actividadId: Long): Int {
        val db = writableDatabase
        val result = db.delete(TABLE_ACTIVIDADES_NAME, "$ACTIVIDADES_ID_KEY=?", arrayOf(actividadId.toString()))
        db.close()
        return result
    }

    fun getAllActividades(): List<ActividadTuristica> {
        val actividadesList = mutableListOf<ActividadTuristica>()
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM ActividadesTuristicas", null)
        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(cursor.getColumnIndexOrThrow("id"))
                val nombre = cursor.getString(cursor.getColumnIndexOrThrow("nombre"))
                val descripcion = cursor.getString(cursor.getColumnIndexOrThrow("descripcion"))
                val imagen = cursor.getString(cursor.getColumnIndexOrThrow("imagen"))
                val fecha = cursor.getString(cursor.getColumnIndexOrThrow("fecha"))
                val costo = cursor.getDouble(cursor.getColumnIndexOrThrow("costo"))
                val idDestinoTuristico = cursor.getInt(cursor.getColumnIndexOrThrow("idDestinoTuristico"))
                actividadesList.add(ActividadTuristica(id, nombre, descripcion, imagen, fecha, costo, idDestinoTuristico))
            } while (cursor.moveToNext())
        }
        cursor.close()
        return actividadesList
    }
    fun insertReserva(idActividad: Long): Long {
        val db = this.writableDatabase
        val idUsuario = 1 // Definir el idUsuario como constante 1
        val values = ContentValues().apply {
            put(RESERVACIONES_USUARIO_ID, idUsuario)
            put(RESERVACIONES_ACTIVIDAD_ID, idActividad)
        }
        return try {
            val result = db.insert(TABLE_RESERVACIONES_NAME, null, values)
            Log.d("DB Insert", "Reserva insertada con ID: $result")
            result
        } catch (e: Exception) {
            Log.e("DB Insert Error", "Error al insertar la reserva: ${e.message}", e)
            -1L
        } finally {
            db.close()
        }
    }


    fun getAllReservaciones(): List<Reservacion> {
        val reservacionesList = mutableListOf<Reservacion>()
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $TABLE_RESERVACIONES_NAME", null)
        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(cursor.getColumnIndexOrThrow(RESERVACIONES_ID_KEY))
                val idUsuario = cursor.getInt(cursor.getColumnIndexOrThrow(RESERVACIONES_USUARIO_ID))
                val idActividad = cursor.getInt(cursor.getColumnIndexOrThrow(RESERVACIONES_ACTIVIDAD_ID))
                reservacionesList.add(Reservacion(id, idUsuario, idActividad))
            } while (cursor.moveToNext())
        }
        cursor.close()
        return reservacionesList
    }
    fun deleteReservacion(reservacionId: Long): Int {
        val db = writableDatabase
        val result = db.delete(TABLE_RESERVACIONES_NAME, "$RESERVACIONES_ID_KEY=?", arrayOf(reservacionId.toString()))
        db.close()
        return result
    }
    fun getActividadById(actividadId: Int): ActividadTuristica? {
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $TABLE_ACTIVIDADES_NAME WHERE $ACTIVIDADES_ID_KEY = ?", arrayOf(actividadId.toString()))
        var actividad: ActividadTuristica? = null
        if (cursor.moveToFirst()) {
            val id = cursor.getInt(cursor.getColumnIndexOrThrow(ACTIVIDADES_ID_KEY))
            val nombre = cursor.getString(cursor.getColumnIndexOrThrow(ACTIVIDADES_NOMBRE))
            val descripcion = cursor.getString(cursor.getColumnIndexOrThrow(ACTIVIDADES_DESCRIPCION))
            val imagen = cursor.getString(cursor.getColumnIndexOrThrow(ACTIVIDADES_IMAGEN))
            val fecha = cursor.getString(cursor.getColumnIndexOrThrow(ACTIVIDADES_FECHA))
            val costo = cursor.getDouble(cursor.getColumnIndexOrThrow(ACTIVIDADES_COSTO))
            val idDestinoTuristico = cursor.getInt(cursor.getColumnIndexOrThrow(ACTIVIDADES_DESTINO_ID_KEY))
            actividad = ActividadTuristica(id, nombre, descripcion, imagen, fecha, costo, idDestinoTuristico)
        }
        cursor.close()
        return actividad
    }
    // Método para insertar un usuario con contraseña encriptada
    fun insertUsuario(nombreUsuario: String, contrasena: String, correo: String, idRol: Int): Long {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_NOMBRE_USUARIO, nombreUsuario)
            put(COLUMN_CONTRASENA, encryptPassword(contrasena))
            put(COLUMN_CORREO, correo)
            put(COLUMN_ID_ROL_FK, idRol)
        }
        return db.insert(TABLE_USUARIO, null, values)
    }

    // Método para encriptar la contraseña usando SHA-256
    private fun encryptPassword(password: String): String? {
        return try {
            val digest = MessageDigest.getInstance("SHA-256")
            val hash = digest.digest(password.toByteArray())
            val hexString = StringBuilder()
            for (b in hash) {
                val hex = Integer.toHexString(0xff and b.toInt())
                if (hex.length == 1) hexString.append('0')
                hexString.append(hex)
            }
            hexString.toString()
        } catch (e: NoSuchAlgorithmException) {
            Log.e("DatabaseHelper", "Encryption Error", e)
            null
        }
    }
    // Método para verificar el inicio de sesión
    @SuppressLint("Range")
    fun verificarUsuario(nombreUsuario: String, contrasena: String): Pair<Boolean, Int?> {
        val db = this.readableDatabase
        var idUsuario: Int? = null
        val query = "SELECT $COLUMN_ID_USUARIO, $COLUMN_CONTRASENA, $COLUMN_ID_ROL_FK " +
                "FROM $TABLE_USUARIO WHERE $COLUMN_NOMBRE_USUARIO = ?"
        val cursor = db.rawQuery(query, arrayOf(nombreUsuario))

        if (cursor.moveToFirst()) {
            val storedPassword = cursor.getString(cursor.getColumnIndex(COLUMN_CONTRASENA))
            val storedIdUsuario = cursor.getInt(cursor.getColumnIndex(COLUMN_ID_USUARIO))
            val storedIdRol = cursor.getInt(cursor.getColumnIndex(COLUMN_ID_ROL_FK))

            if (storedPassword == encryptPassword(contrasena)) {
                idUsuario = storedIdUsuario
            }
        }

        cursor.close()
        db.close()

        return Pair(idUsuario != null, idUsuario)
    }

    // Método para obtener el rol de un usuario por su id
    @SuppressLint("Range")
    fun obtenerRolUsuario(idUsuario: Int): Int? {
        val db = this.readableDatabase
        var idRol: Int? = null
        val query = "SELECT $COLUMN_ID_ROL_FK FROM $TABLE_USUARIO WHERE $COLUMN_ID_USUARIO = ?"
        val cursor = db.rawQuery(query, arrayOf(idUsuario.toString()))

        if (cursor.moveToFirst()) {
            idRol = cursor.getInt(cursor.getColumnIndex(COLUMN_ID_ROL_FK))
        }

        cursor.close()
        db.close()

        return idRol
    }
    // Método para insertar un rol en la tabla rol
    private fun insertRol(db: SQLiteDatabase?, nombreRol: String): Long? {
        val values = ContentValues().apply {
            put(COLUMN_NOMBRE_ROL, nombreRol)
        }
        return db?.insert(TABLE_ROL, null, values)
    }


    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_DESTINOS_NAME")
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_ACTIVIDADES_NAME")
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_RESERVACIONES_NAME")
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_USUARIO")
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_ROL")
        onCreate(db)
    }
}
