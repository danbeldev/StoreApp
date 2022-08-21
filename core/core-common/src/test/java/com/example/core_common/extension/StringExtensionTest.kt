package com.example.core_common.extension

import org.junit.Assert
import org.junit.Test

class StringExtensionTest {

    @Test
    fun `replace range number greater than zero`(){
        // arrange
        // create test data
        val text = "0123456789"

        // act
        val result = text.replaceRange(4)

        // assert
        // 0123... and result must equal
        Assert.assertEquals("0123...",result)
    }

    @Test
    fun `replace range number less than zero`(){
        // arrange
        // create test data
        val text = "0123456789"

        // act
        val result = text.replaceRange(-1)

        // assert
        // text and result must equal
        Assert.assertEquals(text,result)
    }

    @Test
    fun `replace range number equal to zero`(){
        // arrange
        // create test data
        val text = "0123456789"

        // act
        val result = text.replaceRange(0)

        // assert
        // ... and result must equal
        Assert.assertEquals("...",result)
    }

    @Test
    fun `replace range text empty number greater than zero`(){
        // arrange
        // create test data
        val text = ""

        // act
        val result = text.replaceRange(1)

        // assert
        // text and result must equal
        Assert.assertEquals(text,result)
    }

    @Test
    fun `replace range text empty number equal to zero`(){
        // arrange
        // create test data
        val text = ""

        // act
        val result = text.replaceRange(0)

        // assert
        // text and result must equal
        Assert.assertEquals(text,result)
    }
}