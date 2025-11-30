package com.example.levelupgamermovil.viewmodel

import com.example.levelupgamermovil.model.UsuarioAPI
import io.kotest.matchers.collections.shouldContainExactly
import io.kotest.core.spec.style.StringSpec
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest

@OptIn(ExperimentalCoroutinesApi::class)
class UserViewModelTest : StringSpec(
    {
        val usuariosPrueba = listOf(
            UsuarioAPI(1, "Alberto", "Ignacio", "Carrera", "Pérez", "clave12345", "correoalberto@mail.com") ,
            UsuarioAPI(2, "Pedro", "", "Reyes", "Pérez", "clave12345", "correoalberto@mail.com")
        )

        val testViewModel = object : UsuarioViewModel() {
            override fun fetchUsers() {
                _userList.value = usuariosPrueba
            }
        }
        runTest {
            testViewModel.fetchUsers()
            testViewModel.userList.value shouldContainExactly usuariosPrueba
        }

    }
)