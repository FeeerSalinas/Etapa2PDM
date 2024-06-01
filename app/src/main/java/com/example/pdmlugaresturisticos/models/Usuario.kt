package com.example.pdmlugaresturisticos.models

data class Usuario(
    val id: Int,
    val nombre: String,
    val contrasena: String,
    val rolId: Int
)
