package com.trx.habitmeta.data.implementation

import com.trx.habitmeta.data.ApiClient
import com.trx.habitmeta.data.TutorialRepository
import com.trx.habitmeta.data.local.TutorialLocalRepository
import com.trx.habitmeta.models.TutorialStep
import com.trx.habitmeta.modules.AuthenticationHandler
import kotlinx.coroutines.flow.Flow

class TutorialRepositoryImpl(
    localRepository: TutorialLocalRepository,
    apiClient: ApiClient,
    authenticationHandler: AuthenticationHandler
) : BaseRepositoryImpl<TutorialLocalRepository>(localRepository, apiClient, authenticationHandler),
    TutorialRepository {
    override fun getTutorialStep(key: String): Flow<TutorialStep> =
        localRepository.getTutorialStep(key)

    override fun getTutorialSteps(keys: List<String>): Flow<List<TutorialStep>> =
        localRepository.getTutorialSteps(keys)
}
