package com.trx.habitmeta.data

import com.trx.habitmeta.models.BaseObject

interface BaseRepository {
    val isClosed: Boolean

    fun close()

    fun <T : BaseObject> getUnmanagedCopy(obj: T): T

    fun <T : BaseObject> getUnmanagedCopy(list: List<T>): List<T>
}
