package com.example.core_network_data.api

import com.example.core_network_data.retrofit.RetrofitInst
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Test

@ExperimentalCoroutinesApi
class EventApiTest {

    // Create retrofit
    private val retrofit = RetrofitInst.createRetrofit()

    // Create Event Api
    private val eventApi = retrofit.create(EventApi::class.java)

    @Test
    fun `Get events`() = runTest {
        launch(Dispatchers.IO){

            // arrange
            val pageSize = 20
            val pageNumber = 1

            // act
            // Request API get Events
            val response = eventApi.getEvent(
                pageSize = pageSize,
                pageNumber = pageNumber
            )

            // assert
            assertTrue(response.isSuccessful)
            assertNotNull(response.body())
        }
    }
}