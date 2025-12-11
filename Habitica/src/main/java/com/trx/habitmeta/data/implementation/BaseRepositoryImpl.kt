package com.trx.habitmeta.data.implementation

import com.trx.habitmeta.data.ApiClient
import com.trx.habitmeta.data.BaseRepository
import com.trx.habitmeta.data.local.BaseLocalRepository
import com.trx.habitmeta.models.BaseObject
import com.trx.habitmeta.modules.AuthenticationHandler

abstract class BaseRepositoryImpl<T : BaseLocalRepository>(
    protected val localRepository: T,
    protected val apiClient: ApiClient,
    protected val authenticationHandler: AuthenticationHandler
) : BaseRepository {
    val currentUserID: String
        get() = authenticationHandler.currentUserID ?: ""

    override fun close() {
        this.localRepository.close()
    }

    override fun <T : BaseObject> getUnmanagedCopy(list: List<T>): List<T> {
        return localRepository.getUnmanagedCopy(list)
    }

    override val isClosed: Boolean
        get() = localRepository.isClosed

    override fun <T : BaseObject> getUnmanagedCopy(obj: T): T {
        return localRepository.getUnmanagedCopy(obj)
    }
}
