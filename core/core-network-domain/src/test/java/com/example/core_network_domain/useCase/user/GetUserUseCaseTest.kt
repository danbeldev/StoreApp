package com.example.core_network_domain.useCase.user

import com.example.core_model.data.api.user.User
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
class GetUserUseCaseTest {

    @Test
    fun `GetUserUseCaseTest method invoke`() = runTest {
        launch(Dispatchers.IO){
            // arrange

            // create user repository
            val repository = createRepository()

            // create user use case
            val useCase = createUseCase(repository)

            // create test data user info
            val user = User(
                username = "Danila",
                email = "dan.bel@",
                photo = null,
                role = UserRole.BaseUser
            )

            // create impl method repository.getUser()
            coEvery { repository.getUser() } returns Response.success(user)

            // act
            val response = useCase.invoke()

            // assert
            response.collect { result ->
                // method repository.getUser() must be done once
                coVerify {
                    repository.getUser()
                }
                confirmVerified(repository)
                // result message null
                Assert.assertNull(result.message)
                // result data and user equal
                Assert.assertEquals(result.data,user)
            }
        }
    }

    // create user repository
    private fun createRepository():UserRepository = mockk()

    // create get user useCase
    private fun createUseCase(
        repository: UserRepository = createRepository()
    ):GetUserUseCase = GetUserUseCase(
        userRepository = repository
    )
}