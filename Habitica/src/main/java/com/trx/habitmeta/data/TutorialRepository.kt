package com.trx.habitmeta.data

import com.trx.habitmeta.models.TutorialStep
import kotlinx.coroutines.flow.Flow

interface TutorialRepository : BaseRepository {
    fun getTutorialStep(key: String): Flow<TutorialStep>

    fun getTutorialSteps(keys: List<String>): Flow<out List<TutorialStep>>
}
