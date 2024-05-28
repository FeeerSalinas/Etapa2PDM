package com.example.pdmlugaresturisticos.helper

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.pdmlugaresturisticos.models.ActividadTuristica
import com.example.pdmlugaresturisticos.models.DestinoTuristico

class DataBaseHelper (context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

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

        db?.execSQL(CREATE_DESTINOS_TABLE)
        db?.execSQL(CREATE_ACTIVIDADES_TABLE)
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



    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_DESTINOS_NAME")
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_ACTIVIDADES_NAME")
        onCreate(db)
    }
}