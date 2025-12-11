package com.trx.habitmeta.wearos.ui.viewmodels

import androidx.lifecycle.SavedStateHandle
import com.trx.habitmeta.R
import com.trx.habitmeta.wearos.data.repositories.TaskRepository
import com.trx.habitmeta.wearos.data.repositories.UserRepository
import com.trx.habitmeta.wearos.managers.AppStateManager
import com.trx.habitmeta.wearos.util.ExceptionHandlerBuilder
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ConfirmactionActivityViewModel
@Inject
constructor(
    savedStateHandle: SavedStateHandle,
    userRepository: UserRepository,
    taskRepository: TaskRepository,
    exceptionBuilder: ExceptionHandlerBuilder,
    appStateManager: AppStateManager
) : BaseViewModel(userRepository, taskRepository, exceptionBuilder, appStateManager) {
    val icon: Int = savedStateHandle["icon"] ?: R.drawable.error
    val text: String? = savedStateHandle["text"]
}
