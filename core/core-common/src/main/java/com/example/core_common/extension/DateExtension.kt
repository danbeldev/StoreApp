package com.example.core_common.extension

import java.text.SimpleDateFormat
import java.util.*

/**
 * Get user date device
 * */
fun getDate():String {
    val time = Calendar.getInstance().time
    val formatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
    return formatter.format(time)
}
