package com.example.levelupgamermovil.model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [CarritoItemEntity::class, Producto::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun carritoDao(): CarritoDao
    abstract fun productoDao(): ProductoDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDataBase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "carrito_db"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}