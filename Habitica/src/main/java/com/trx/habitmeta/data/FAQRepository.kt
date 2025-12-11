package com.trx.habitmeta.data

import com.trx.habitmeta.models.FAQArticle
import kotlinx.coroutines.flow.Flow

interface FAQRepository : BaseRepository {
    fun getArticles(): Flow<List<FAQArticle>>

    fun getArticle(position: Int): Flow<FAQArticle>
}
