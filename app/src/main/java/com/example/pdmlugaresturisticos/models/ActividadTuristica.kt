package com.example.pdmlugaresturisticos.models

data class ActividadTuristica(
    val id: Int = 0,
    val nombre: String,
    val descripcion: String,
    val imagen: String,
    val fecha: String,
    val costo: Double,
    val idDestinoTuristico: Int
)