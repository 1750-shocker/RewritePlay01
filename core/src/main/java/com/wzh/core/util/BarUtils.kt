package com.wzh.core.util

import android.content.Context

class BarUtils {
}
fun Context?.getStatusBarHeight(): Int {
    var result = 60
    if (this == null) return result
    val resId = resources.getIdentifier("status_bar_height", "dimen", "android")
    if (resId > 0) {
        result = resources.getDimensionPixelOffset(resId)
    }
    return result
}