package com.trx.habitmeta.data.local

import com.trx.habitmeta.models.FAQArticle
import kotlinx.coroutines.flow.Flow

interface FAQLocalRepository : ContentLocalRepository {
    fun getArticle(position: Int): Flow<FAQArticle>

    val articles: Flow<List<FAQArticle>>
}
