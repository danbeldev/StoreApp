package com.example.core_ui.activityResult

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.platform.LocalContext
import java.io.ByteArrayOutputStream

@SuppressLint("ComposableNaming", "NewApi")
@Composable
fun FileManagerActivityResult(
    openFileManager:MutableState<Boolean>,
    byteArray:(ByteArray?) -> Unit
): ManagedActivityResultLauncher<String, Uri?> {
    val context = LocalContext.current
    return rememberLauncherForActivityResult(ActivityResultContracts.GetContent()){ uri ->
        if (uri != null){
            val sourse = ImageDecoder.createSource(context.applicationContext.contentResolver, uri)
            val bitmap = ImageDecoder.decodeBitmap(sourse)
            val outputStream = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.JPEG, 40, outputStream)
            byteArray(outputStream.toByteArray())
            openFileManager.value = false
        }else{
            byteArray(null)
        }
    }
}