package com.example.levelupgamermovil.model

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface UsuarioDAO {

    @Query("SELECT * FROM usuarios")
    suspend fun getAll(): List<UsuarioAPI>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(usuario: UsuarioAPI)

}