package com.dev.tvmania.util

fun String.removeHtmlTags(): String{
    return Regex(pattern = "<\\w+>|<\\w+>").replace(this, "")
}