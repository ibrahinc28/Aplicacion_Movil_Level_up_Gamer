package com.example.levelupgamermovil.repository

import com.example.levelupgamermovil.model.UsuarioAPI
import com.example.levelupgamermovil.network.UsuarioAPIService
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.collections.shouldContainExactly
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest

class TestableUserRepository(private val testApi: UsuarioAPIService): UsuarioRepository() {
    override suspend fun getUsuarios(): List<UsuarioAPI> {
        return testApi.getUsuarios()
    }
}
class UserRepositoryTest: StringSpec({
    val usuariosPrueba = listOf(
        UsuarioAPI(1, "Alberto", "Ignacio", "Carrera", "Pérez", "clave12345", "correoalberto@mail.com") ,
        UsuarioAPI(2, "Pedro", "", "Reyes", "Pérez", "clave12345", "correoalberto@mail.com")
    )
    val mockApi = mockk<UsuarioAPIService>()
    coEvery { mockApi.getUsuarios() } returns usuariosPrueba

    val repo = TestableUserRepository(mockApi)

    runTest {
        val result = repo.getUsuarios()
        result shouldContainExactly usuariosPrueba
    }
})