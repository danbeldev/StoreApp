package com.example.core_common.worker

/**
 * @param mimeType HTTP MIME types.
 * MIME types enable browsers to recognize the filetype of a file
 * which has been sent via HTTP by the webserver.
 * @param extension Filename extension
 * */
enum class DownloadType(
    val mimeType:String,
    val extension:String
){
    IMAGE_JPG("image/jpeg",".jpg"),
    VIDEO_MP4("video/mp4",".mp4"),
    APK("application/vnd.android.package-archive", ".apk"),
    AAB("application/vnd.android.package-archive", ".aab")
}