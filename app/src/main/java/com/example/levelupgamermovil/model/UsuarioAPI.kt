package com.example.levelupgamermovil.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "usuarios")
data class UsuarioAPI(

    @PrimaryKey
    @SerializedName("id")
    val id: Long,

    @SerializedName("nombre")
    val nombre: String,

    @SerializedName("snombre")
    val snombre: String,

    @SerializedName("apellidopat")
    val apellidopat: String,

    @SerializedName("apellidomat")
    val apellidomat: String,

    @SerializedName("contrasena")
    val contrasena: String,

    @SerializedName("correo")
    val correo: String,
)