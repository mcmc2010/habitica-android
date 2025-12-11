package com.trx.habitmeta.wearos.ui.viewmodels

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.asLiveData
import com.trx.habitmeta.wearos.data.repositories.TaskRepository
import com.trx.habitmeta.wearos.data.repositories.UserRepository
import com.trx.habitmeta.wearos.managers.AppStateManager
import com.trx.habitmeta.wearos.util.ExceptionHandlerBuilder
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TaskDetailViewModel
@Inject
constructor(
    savedStateHandle: SavedStateHandle,
    taskRepository: TaskRepository,
    userRepository: UserRepository,
    exceptionBuilder: ExceptionHandlerBuilder,
    appStateManager: AppStateManager
) : BaseViewModel(userRepository, taskRepository, exceptionBuilder, appStateManager) {
    val taskID = savedStateHandle.get<String>("task_id")
    val task = taskRepository.getTask(taskID).asLiveData()
}
