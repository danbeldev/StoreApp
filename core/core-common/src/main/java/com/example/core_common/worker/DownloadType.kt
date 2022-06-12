package com.example.core_common.worker

enum class DownloadType(
    val mimeType:String,
    val extension:String
){
    IMAGE_JPG("image/jpeg",".jpg"),
    VIDEO_MP4("video/mp4",".mp4"),
    APK("application/vnd.android.package-archive", ".apk"),
    AAB("application/vnd.android.package-archive", ".aab")
}