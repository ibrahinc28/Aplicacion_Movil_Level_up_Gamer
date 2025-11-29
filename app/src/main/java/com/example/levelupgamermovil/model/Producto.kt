package com.example.levelupgamermovil.model
import android.content.Context
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
    fun getDrawableId(context: Context): Int {
        if (imagenPath.isNullOrEmpty()) return 0 // O un R.drawable.placeholder


        val nombreLimpio = imagenPath
            .replace("/images/", "")
            .replace(".png", "")
            .replace(".jpg", "")
            .lowercase()


        return context.resources.getIdentifier(
            nombreLimpio,
            "drawable",
            context.packageName
        )
    }
}


