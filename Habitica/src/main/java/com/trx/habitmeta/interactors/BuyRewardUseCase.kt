package com.trx.habitmeta.interactors

import com.trx.habitmeta.data.TaskRepository
import com.trx.habitmeta.helpers.SoundManager
import com.trx.habitmeta.models.tasks.Task
import com.trx.habitmeta.models.user.User
import com.trx.habitmeta.shared.models.responses.TaskScoringResult
import javax.inject.Inject

class BuyRewardUseCase
@Inject
constructor(
    private val taskRepository: TaskRepository,
    private val soundManager: SoundManager
) : UseCase<BuyRewardUseCase.RequestValues, TaskScoringResult?>() {
    override suspend fun run(requestValues: RequestValues): TaskScoringResult? {
        val response =
            taskRepository.taskChecked(
                requestValues.user,
                requestValues.task,
                false,
                false,
                requestValues.notifyFunc
            )
        soundManager.loadAndPlayAudio(SoundManager.SOUND_REWARD)
        return response
    }

    class RequestValues(
        internal val user: User?,
        val task: Task,
        val notifyFunc: (TaskScoringResult) -> Unit
    ) : UseCase.RequestValues
}
