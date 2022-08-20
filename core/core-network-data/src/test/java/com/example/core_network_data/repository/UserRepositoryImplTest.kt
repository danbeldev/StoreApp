package com.example.core_network_data.repository

import com.example.core_model.data.api.user.*
import com.example.core_model.data.enums.user.UserRole
import com.example.core_network_data.api.UserApi
import com.example.core_network_domain.repository.UserRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Test
import retrofit2.Response

@ExperimentalCoroutinesApi
class UserRepositoryImplTest {

    @Test
    fun authorization() = runTest {
        launch(Dispatchers.IO){
            // arrange
            // create test authorization date
            val authorization = Authorization(
                email = "email",
                password = "password"
            )

            // create test authorization response date
            val authorizationResponse = AuthorizationResponse(
                access_token = "token",
                role = UserRole.BaseUser
            )

            // create user api
            val api = creteApi()

            // create user repository
            val userRepository = createRepository(api)

            // create impl method authorization
            coEvery { api.authorization(any()) } returns Response.success(authorizationResponse)

            // act
            val response = userRepository.authorization(
                authorization = authorization
            )

            /**
             * assert
             * method [api.getCompany()] must be done once
             * response code [200..300]
             * response body not null
             * test authorizationResponse and response body must equal
             * */
            coVerify(exactly = 1) {
                api.authorization(any())
            }
            confirmVerified(api)

            assertTrue(response.isSuccessful)
            assertNotNull(response.body())
            assertEquals(response.body(), authorizationResponse)
        }
    }

    @Test
    fun registration() = runTest {
        launch(Dispatchers.IO){
            // arrange

            // create test date registration
            val registration = Registration (
                email = "email",
                password = "password",
                username = "username"
            )

            // create test date registration response
            val registrationResponse = RegistrationResult(
                error = "null"
            )

            // create user api
            val api = creteApi()

            // create user repository
            val repository = createRepository(api)

            // create impl method api.registration(any())
            coEvery { api.registration(any()) } returns Response.success(registrationResponse)

            // act
            val response = repository.registration(
                registration = registration
            )

            // assert
            // method [api.registration()] must be done once
            coVerify(exactly = 1) {
                api.registration(any())
            }
            confirmVerified(api)
            // response code [200..300]
            assertTrue(response.isSuccessful)
            // response body not null
            assertNotNull(response.body())
            // test registrationResponse and response body must equal
            assertEquals(response.body(), registrationResponse)
        }
    }

    @Test
    fun `get user`() = runTest {
        launch(Dispatchers.IO){
            // arrange
            // create test date user info
            val userInfo = User(
                username = "username",
                email = "email",
                photo = null,
                role = UserRole.BaseUser
            )

            // create user api
            val api = creteApi()

            // create user repository
            val repository = createRepository(
                api = api
            )

            // create impl method api.getUser(any())
            coEvery { api.getUser(any()) } returns Response.success(userInfo)

            // act
            val response = repository.getUser()

            // assert
            // method [api.getUser(any())] must be done once
            coVerify(exactly = 1) {
                api.getUser(any())
            }
            confirmVerified(api)
            // response code [200..300]
            assertTrue(response.isSuccessful)
            // response body not null
            assertNotNull(response.body())
            // test userInfo and response body must equal
            assertEquals(response.body(), userInfo)
        }
    }

    // Create User Api
    private fun creteApi():UserApi = mockk(relaxed = true)

    // Create User Repository
    private fun createRepository(
        api:UserApi = creteApi()
    ):UserRepository = UserRepositoryImpl(
        userApi = api
    )
}