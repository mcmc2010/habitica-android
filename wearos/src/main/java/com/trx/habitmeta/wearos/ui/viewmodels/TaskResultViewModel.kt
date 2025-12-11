package com.trx.habitmeta.wearos.ui.viewmodels

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.asLiveData
import com.trx.habitmeta.shared.models.responses.TaskScoringResult
import com.trx.habitmeta.wearos.data.repositories.TaskRepository
import com.trx.habitmeta.wearos.data.repositories.UserRepository
import com.trx.habitmeta.wearos.managers.AppStateManager
import com.trx.habitmeta.wearos.util.ExceptionHandlerBuilder
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TaskResultViewModel
@Inject
constructor(
    savedStateHandle: SavedStateHandle,
    taskRepository: TaskRepository,
    userRepository: UserRepository,
    exceptionBuilder: ExceptionHandlerBuilder,
    appStateManager: AppStateManager
) : BaseViewModel(userRepository, taskRepository, exceptionBuilder, appStateManager) {
    val user = userRepository.getUser().asLiveData()
    val hasLeveledUp: Boolean
        get() = result?.hasLeveledUp == true
    val hasDied: Boolean
        get() = result?.hasDied == true
    val hasDrop: Boolean
        get() {
            return result?.drop?.key?.isNotBlank() == true // || (result?.questItemsFound ?: 0) > 0
        }
    val result = savedStateHandle.get<TaskScoringResult>("result")
}
