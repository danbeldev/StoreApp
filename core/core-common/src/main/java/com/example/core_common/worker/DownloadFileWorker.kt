package com.example.core_common.worker

import android.annotation.SuppressLint
import android.app.DownloadManager
import android.content.Context
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
            // download Url
            val url = inputData.getString(URL_WORKER_KEY)
            // Authorization JWT Token
            val token = inputData.getString(TOKEN_WORKER_KEY)
            /**
             * get string name [DownloadType]
             * */
            val downloadTypeString = inputData.getString(DOWNLOAD_TYPE_WORKER_KEY)
            val downloadType = downloadTypeString?.let { enumValueOf<DownloadType>(it) }

            downloadType?.let { type ->
                download(
                    context = context,
                    uri = Uri.parse(url),
                    downloadType = type,
                    token = token,
                    onProgress = {  }
                )
            }
            Result.success()
        }catch (e:Exception){
            Log.e("ResponseWorker", e.message.toString())
            Result.failure()
        }
    }

    companion object{
        // input data download Url
        const val URL_WORKER_KEY = "url"
        // input Authorization JWT Token
        const val TOKEN_WORKER_KEY = "token"
        // input string name [DownloadType]
        const val DOWNLOAD_TYPE_WORKER_KEY = "downloadType"

        // input data progress download
        const val PROGRESS_WORKER_KEY = "progress"
    }
}

/**
 * upload file on device
 * @param uri upload file url
 * @param downloadType mimeType and extension
 * @param token authorization JWT Token
 * @param onProgress file upload progress
 * */
@SuppressLint("Range", "NewApi", "Recycle")
private fun download(
    uri: Uri,
    downloadType: DownloadType,
    context: Context,
    token: String? = null,
    onProgress: (Int) -> Unit,
){
    val downloadManager = context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
    val request = DownloadManager.Request(uri)

    request.setAllowedNetworkTypes(
        DownloadManager.Request.NETWORK_MOBILE or DownloadManager.Request.NETWORK_WIFI
    )
        .setMimeType(downloadType.mimeType)
        .setAllowedOverRoaming(false)
        .setAllowedOverMetered(true)
        .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
        .setTitle(uri.lastPathSegment)
        .setDescription("The file is downloading...")
        .addRequestHeader("Authorization","Bearer $token")
        .setDestinationInExternalPublicDir(
            Environment.DIRECTORY_DOWNLOADS,
            File.separator+uri.lastPathSegment + downloadType.extension
        )

    val fileDownloadedId  = downloadManager.enqueue(request)

    var downloading = true

    while (downloading) {
        val managerQuery = DownloadManager.Query()
        val cursor = downloadManager.query(managerQuery)
        cursor.moveToFirst()

        val bytesDownloaded = cursor.getInt(cursor
            .getColumnIndex(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR))
        val bytesTotal = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_TOTAL_SIZE_BYTES))

        val progress = ((bytesDownloaded * 100) / bytesTotal )

        onProgress(progress)
        Log.e("ResponseFile", "$bytesDownloaded / $bytesTotal // $progress")

        if (
            cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS))
            == DownloadManager.STATUS_SUCCESSFUL
        ) {
            val fileUri = downloadManager.getUriForDownloadedFile(fileDownloadedId)
            val type = downloadManager.getMimeTypeForDownloadedFile(fileDownloadedId)

            downloading = false
        }

        cursor.close()
    }
}