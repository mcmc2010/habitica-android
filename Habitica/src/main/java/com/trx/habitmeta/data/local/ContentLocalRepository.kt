package com.trx.habitmeta.data.local

import com.trx.habitmeta.models.ContentResult
import com.trx.habitmeta.models.WorldState
import kotlinx.coroutines.flow.Flow

interface ContentLocalRepository : BaseLocalRepository {
    fun saveContent(contentResult: ContentResult)

    fun saveWorldState(worldState: WorldState)

    fun getWorldState(): Flow<WorldState>
}
