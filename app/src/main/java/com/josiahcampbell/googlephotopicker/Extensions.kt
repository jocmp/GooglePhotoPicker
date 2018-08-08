package com.josiahcampbell.googlephotopicker

import java.io.File
import java.io.InputStream

fun File.writeFromInputStream(inputStream: InputStream?) {
    inputStream?.use { input -> outputStream().use { input.copyTo(it) } }
}

fun CharSequence?.isPresent() = !isNullOrBlank()