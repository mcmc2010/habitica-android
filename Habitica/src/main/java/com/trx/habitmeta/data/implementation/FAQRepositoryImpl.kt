package com.trx.habitmeta.data.implementation

import com.trx.habitmeta.data.ApiClient
import com.trx.habitmeta.data.FAQRepository
import com.trx.habitmeta.data.local.FAQLocalRepository
import com.trx.habitmeta.models.FAQArticle
import com.trx.habitmeta.modules.AuthenticationHandler
import kotlinx.coroutines.flow.Flow

class FAQRepositoryImpl(
    localRepository: FAQLocalRepository,
    apiClient: ApiClient,
    authenticationHandler: AuthenticationHandler
) : BaseRepositoryImpl<FAQLocalRepository>(localRepository, apiClient, authenticationHandler),
    FAQRepository {
    override fun getArticle(position: Int): Flow<FAQArticle> {
        return localRepository.getArticle(position)
    }

    override fun getArticles(): Flow<List<FAQArticle>> {
        return localRepository.articles
    }
}
