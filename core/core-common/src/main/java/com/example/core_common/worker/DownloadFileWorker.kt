package com.example.core_common.worker

import android.app.DownloadManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Environment
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File

class DownloadFileWorker(
    private val context: Context,
    params: WorkerParameters,
): CoroutineWorker(context,params) {
    override suspend fun doWork(): Result = withContext(Dispatchers.IO) {
        return@withContext try {
            val url = inputData.getString(URL_WORKER_KEY)
            val token = inputData.getString(TOKEN_WORKER_KEY)
            val downloadTypeString = inputData.getString(DOWNLOAD_TYPE_WORKER_KEY)
            val downloadType = downloadTypeString?.let { enumValueOf<DownloadType>(it) }
            downloadType?.let { type ->
                download(
                    context = context,
                    uri = Uri.parse(url),
                    downloadType = type,
                    token = token
                )
            }
            Result.success()
        }catch (e:Exception){
            Log.e("ResponseWorker", e.message.toString())
            Result.failure()
        }
    }

    companion object{
        const val URL_WORKER_KEY = "url"
        const val TOKEN_WORKER_KEY = "token"
        const val DOWNLOAD_TYPE_WORKER_KEY = "downloadType"
    }
}

private fun download(
    uri: Uri,
    downloadType: DownloadType,
    context: Context,
    token: String? = null,
){
    val downloadManager = context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
    val request = DownloadManager.Request(uri)

//    val intent = Intent(Intent.ACTION_VIEW)
//    intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)

    request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE or DownloadManager.Request.NETWORK_WIFI)
        .setMimeType(downloadType.mimeType)
        .setAllowedOverRoaming(false)
        .setAllowedOverMetered(true)
        .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
        .setTitle(uri.lastPathSegment)
        .addRequestHeader("Authorization","Bearer $token")
        .setDestinationInExternalPublicDir(
            Environment.DIRECTORY_DOWNLOADS,
            File.separator+uri.lastPathSegment + downloadType.extension
        )
    downloadManager.enqueue(request)
//
//    intent.setDataAndType(
//        Uri.parse("" +
//                Environment.DIRECTORY_DOWNLOADS + File.separator+uri.lastPathSegment + downloadType.extension
//        ), downloadType.mimeType)
//    context.startActivity(Intent.createChooser(intent, "Open"))
}