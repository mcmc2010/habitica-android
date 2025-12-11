package com.trx.habitmeta.data.local

import com.trx.habitmeta.models.Tag
import kotlinx.coroutines.flow.Flow

interface TagLocalRepository : BaseLocalRepository {
    fun getTags(userId: String): Flow<List<Tag>>

    fun deleteTag(tagID: String)
}
