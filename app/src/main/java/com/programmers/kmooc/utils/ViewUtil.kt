package com.programmers.kmooc.utils

import android.view.View
import androidx.appcompat.widget.AppCompatImageView
import androidx.databinding.BindingAdapter
import com.programmers.kmooc.network.ImageLoader

fun Boolean.toVisibility(): Int {
    return if (this) View.VISIBLE else View.GONE
}