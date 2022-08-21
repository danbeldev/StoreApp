package com.example.core_common.extension

import com.example.core_common.test.testByteArray
import org.junit.Assert
import org.junit.Test

class BitmapExtensionTest {

    @Test
    fun fromByteArrayToBitmap(){
        // arrange
        // create test byte array
        val testByteArray = testByteArray

        // act
        // from byte array to bitmap
        val responseBitmap = testByteArray.decodeByteArrayBitmap()
        // from bitmap to byte array
        val responseByteArray = responseBitmap.toByteArray()
        
        // assert
        // testByteArray and responseByteArray must equal
        Assert.assertEquals(testByteArray, responseByteArray)
    }
}