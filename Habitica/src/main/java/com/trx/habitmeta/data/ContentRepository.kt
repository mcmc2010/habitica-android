package com.trx.habitmeta.data

import com.trx.habitmeta.models.ContentResult
import com.trx.habitmeta.models.WorldState
import kotlinx.coroutines.flow.Flow

interface ContentRepository : BaseRepository {
    suspend fun retrieveContent(forced: Boolean = false): ContentResult?

    suspend fun retrieveWorldState(forced: Boolean = false): WorldState?

    fun getWorldState(): Flow<WorldState>
}
