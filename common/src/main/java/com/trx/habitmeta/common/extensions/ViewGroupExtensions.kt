@file:JvmName("ViewGroupExt")

package com.trx.habitmeta.common.extensions

import android.view.View
import android.view.ViewGroup

fun ViewGroup.inflate(
    layoutId: Int,
    attachToRoot: Boolean = false
): View =
    context.layoutInflater.inflate(layoutId, this, attachToRoot)
