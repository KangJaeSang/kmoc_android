package com.programmers.kmooc.network

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import kotlinx.coroutines.*
import java.lang.Exception
import java.net.URL

object ImageLoader {
    fun loadImage(url: String, completed: (Bitmap?) -> Unit) {
        //TODO: String -> Bitmap 을 구현하세요
        CoroutineScope(Dispatchers.IO).launch {
            val bitmap = BitmapFactory.decodeStream(URL(url).openStream())
            withContext(Dispatchers.Main){
                completed(bitmap)
            }
        }
    }
}