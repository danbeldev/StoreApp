package com.example.core_network_domain.useCase.user

import com.example.core_model.data.api.user.Registration
import com.example.core_model.data.api.user.RegistrationResult
import com.example.core_network_domain.repository.UserRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Test
import retrofit2.Response

@ExperimentalCoroutinesApi
class RegistrationUseCaseTest {

    @Test
    fun `RegistrationUseCaseTest method invoke`() = runTest {
        launch(Dispatchers.IO){
            // arrange

            // create test data registration
            val registration = Registration(
                email = "email",
                password = "password",
                username = "Danila"
            )

            // create test data registration response
            val registrationResponse = RegistrationResult(
                error = ""
            )

            // create user repository
            val repository = createRepository()

            // create authorization user case
            val useCase = createUseCase(repository)

            // create impl method repository.registration(any())
            coEvery { repository.registration(any()) } returns Response.success(registrationResponse)

            // act
            val response = useCase.invoke(registration)

            // assert
            response.collect { result ->
                // method repository.registration() must be done once
                coVerify {
                    repository.registration(any())
                }
                confirmVerified(repository)
                // result message null
                Assert.assertNull(result.message)
                // result data and registration response equal
                Assert.assertEquals(result.data,registrationResponse)
            }
        }
    }

    // create user repository
    private fun createRepository(): UserRepository = mockk(relaxed = true)

    // create authorization user case
    private fun createUseCase(
        repository: UserRepository
    ):RegistrationUseCase = RegistrationUseCase(
        userRepository = repository
    )
}