package com.example.core_common.extension

fun String.replaceRange(count:Int):String{
    if (count < 0 ) return this

    if (this.isEmpty()) return this

    return if (this.length < count)
        this
    else
        this.replaceRange(
            count until this.length,
            "..."
        )
}