package com.example.core_network_domain.useCase.user

import com.example.core_model.data.api.user.Authorization
import com.example.core_model.data.api.user.AuthorizationResult
import com.example.core_model.data.enums.user.UserRole
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
class AuthorizationUseCaseTest {

    @Test
    fun `AuthorizationUseCaseTest method invoke`() = runTest {
        launch(Dispatchers.IO){
            // arrange

            // create test data authorization
            val authorization = Authorization(
                email = "email",
                password = "password"
            )

            // create test data authorization response
            val authorizationResponse = AuthorizationResult(
                access_token = "token",
                role = UserRole.BaseUser
            )

            // create user repository
            val repository = createRepository()

            // create authorization user case
            val useCase = createUseCase(repository)

            // create test impl method repository.authorization(any())
            coEvery { repository.authorization(any()) } returns Response.success(authorizationResponse)

            // act
            val response = useCase.invoke(authorization)

            // assert
            response.collect { result ->
                // method repository.authorization() must be done once
                coVerify {
                    repository.authorization(any())
                }
                confirmVerified(repository)
                // result message null
                Assert.assertNull(result.message)
                // result data and authorization response equal
                Assert.assertEquals(result.data,authorizationResponse)
            }
        }
    }

    // create user repository
    private fun createRepository():UserRepository = mockk(relaxed = true)

    // create authorization user case
    private fun createUseCase(
        repository: UserRepository
    ):AuthorizationUseCase = AuthorizationUseCase(
        userRepository = repository
    )
}