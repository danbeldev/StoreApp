package com.example.core_common.extension

fun String.replaceRange(count:Int):String{
    return if (this.length < count)
        this
    else
        this.replaceRange(
            count until this.length,
            "..."
        )
}