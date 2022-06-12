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


enum class FileExtension{
    IMAGE,
    FILE
}

@SuppressLint("ComposableNaming", "NewApi")
@Composable
fun FileManagerActivityResult(
    fileExtension: FileExtension = FileExtension.IMAGE,
    openFileManager: MutableState<Boolean>,
    onByteArray: ((ByteArray?) -> Unit?)? = null,
    onUri: ((Uri?) -> Unit)? = null
): ManagedActivityResultLauncher<String, Uri?> {
    val context = LocalContext.current
    return rememberLauncherForActivityResult(ActivityResultContracts.GetContent()){ uri ->
        if (uri != null){
            if (fileExtension == FileExtension.IMAGE){
                val sourse = ImageDecoder.createSource(context.applicationContext.contentResolver, uri)
                val bitmap = ImageDecoder.decodeBitmap(sourse)
                val outputStream = ByteArrayOutputStream()
                bitmap.compress(Bitmap.CompressFormat.JPEG, 40, outputStream)
                onByteArray?.let { it(outputStream.toByteArray()) }
            }else {
                val iStream = context.contentResolver.openInputStream(uri)
                val byteArray = iStream?.readBytes()
                onByteArray?.let { it(byteArray) }
            }
            onUri?.let { it(uri) }
            openFileManager.value = false
        }
    }
}