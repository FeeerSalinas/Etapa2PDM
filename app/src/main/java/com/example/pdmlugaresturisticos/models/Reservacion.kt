package com.example.pdmlugaresturisticos.models

data class Reservacion(
    var id: Int = 0,
    var idUsuario: Int,
    var idActividadTuristica: Int
)