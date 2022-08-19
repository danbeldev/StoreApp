package com.example.core_common.extension

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import java.io.ByteArrayOutputStream
import java.io.OutputStream

/**
 * Converter from int to Bitmap
 * */
fun Int.decodeResourceBitmap(context: Context):Bitmap{
    return BitmapFactory.decodeResource(context.resources,this)
}

/**
 * Converter from ByteArray to Bitmap
 * */
fun ByteArray.decodeByteArrayBitmap():Bitmap{
    return BitmapFactory.decodeByteArray(this, 0, this.size)
}

/**
 * Converter Bitmap int to ByteArray
 * */
fun Bitmap.toByteArray():ByteArray{
    val outputStream = ByteArrayOutputStream()
    this.compress(Bitmap.CompressFormat.JPEG, 40, outputStream)
    return outputStream.toByteArray()
}