package com.trx.habitmeta.wearos.ui.viewmodels

import com.trx.habitmeta.shared.models.tasks.Frequency
import com.trx.habitmeta.shared.models.tasks.TaskType
import com.trx.habitmeta.wearos.data.repositories.TaskRepository
import com.trx.habitmeta.wearos.data.repositories.UserRepository
import com.trx.habitmeta.wearos.managers.AppStateManager
import com.trx.habitmeta.wearos.models.tasks.Task
import com.trx.habitmeta.wearos.util.ExceptionHandlerBuilder
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TaskFormViewModel
@Inject
constructor(
    userRepository: UserRepository,
    taskRepository: TaskRepository,
    exceptionBuilder: ExceptionHandlerBuilder,
    appStateManager: AppStateManager
) : BaseViewModel(userRepository, taskRepository, exceptionBuilder, appStateManager) {
    suspend fun saveTask(
        text: CharSequence?,
        taskType: TaskType?
    ) {
        if (text?.isNotBlank() != true || taskType == null) {
            return
        }
        val task = Task()
        task.text = text.toString()
        task.type = taskType
        task.priority = 1.0f
        task.up = true
        task.everyX = 1
        task.frequency = Frequency.DAILY
        taskRepository.createTask(task)
    }
}
