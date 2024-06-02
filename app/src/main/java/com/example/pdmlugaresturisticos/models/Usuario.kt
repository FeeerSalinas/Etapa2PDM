package com.example.pdmlugaresturisticos.models

data class Usuario(
    val id: Int = 0,
    val nombre: String,
    val contrasena: String,
    val rolId: Int
)
