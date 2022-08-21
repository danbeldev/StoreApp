package com.example.core_common.extension

import kotlinx.serialization.Serializable
import org.junit.Assert
import org.junit.Test

class SerializationExtensionTest {

    @Serializable
    data class TestData(
        val name:String,
        val age:Int?
    )

    @Test
    fun `encode to string and decode from string converter TestData`(){
        // arrange
        // create test data
        val testData = TestData(
            name = "test_1",
            age = 1
        )

        // act
        // test data TestData converter json string
        val testDataStringResult = testData.encodeToString()
        // json string converter test data TestData
        val testDataResult = testDataStringResult.decodeFromString<TestData>()

        // assert
        // testData and testDataResult must equal
        Assert.assertEquals(testData, testDataResult)
    }

    @Test
    fun `encode to string and decode from string converter list TestData`(){
        // arrange
        // create test data
        val testData = listOf(
            TestData(
                name = "test_1",
                age = 1
            ),
            TestData(
                name = "test_2",
                age = 2
            )
        )

        // act
        // test data List<TestData> converter json string
        val testDataStringResult = testData.encodeToString()
        // json string converter test data List<TestData>
        val testDataResult = testDataStringResult.decodeFromString<List<TestData>>()

        // assert
        // testData and testDataResult must equal
        Assert.assertEquals(testData, testDataResult)
    }

    @Test
    fun `encode to string and decode from string converter TestData age equal null`(){
        // arrange
        // create test data
        val testData = TestData(
            name = "test_1",
            age = null
        )

        // act
        // test data TestData converter json string
        val testDataStringResult = testData.encodeToString()
        // json string converter test data TestData
        val testDataResult = testDataStringResult.decodeFromString<TestData>()

        // assert
        // testData and testDataResult must equal
        Assert.assertEquals(testData, testDataResult)
    }
}