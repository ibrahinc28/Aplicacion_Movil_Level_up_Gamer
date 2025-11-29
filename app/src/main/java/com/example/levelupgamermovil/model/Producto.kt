package com.example.levelupgamermovil.model
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName= "productos")
data class Producto(
    @PrimaryKey
    @SerializedName("id")
    val id: Long,

    @SerializedName("codigo")
    val codigo: String,

    @SerializedName("nombre")
    val nombre: String,

    @SerializedName("descripcion")
    val description: String,

    @SerializedName("categoria")
    val categoria: String,

    @SerializedName("precio")
    val price: Double,

    @SerializedName("imagen")
    val imagenPath: String?

){

    fun getFullImageUrl(): String {
        return if (imagenPath != null && !imagenPath.startsWith("http")) {
            "http://192.168.1.89:8080$imagenPath"
        } else {
            imagenPath ?: ""
        }
    }
}
