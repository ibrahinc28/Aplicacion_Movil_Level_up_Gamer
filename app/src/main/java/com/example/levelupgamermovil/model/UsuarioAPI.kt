package com.example.levelupgamermovil.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Required

@Entity(tableName = "usuarios")
data class UsuarioAPI(

    @PrimaryKey
    @SerializedName("id")
    val id: Long,

    @SerializedName("nombre")
    @Required
    val nombre: String,

    @SerializedName("snombre")
    val snombre: String,

    @SerializedName("apellidopat")
    @Required
    val apellidopat: String,

    @SerializedName("apellidomat")
    @Required
    val apellidomat: String,

    @SerializedName("contrasena")
    @Required
    val contrasena: String,

    @SerializedName("correo")
    @Required
    val correo: String,
)