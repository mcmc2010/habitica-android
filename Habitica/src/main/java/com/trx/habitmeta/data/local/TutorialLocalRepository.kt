package com.trx.habitmeta.data.local

import com.trx.habitmeta.models.TutorialStep
import kotlinx.coroutines.flow.Flow

interface TutorialLocalRepository : BaseLocalRepository {
    fun getTutorialStep(key: String): Flow<TutorialStep>

    fun getTutorialSteps(keys: List<String>): Flow<List<TutorialStep>>
}
