package com.trx.habitmeta.wearos.extensions

import android.view.View
import android.view.ViewTreeObserver

inline fun View.waitForLayout(crossinline f: View.() -> Unit) =
    with(viewTreeObserver) {
        addOnGlobalLayoutListener(
            object : ViewTreeObserver.OnGlobalLayoutListener {
                override fun onGlobalLayout() {
                    viewTreeObserver.removeOnGlobalLayoutListener(this)
                    f()
                }
            }
        )
    }
