package com.trx.habitmeta.wearos.ui.viewmodels

import androidx.lifecycle.asLiveData
import com.trx.habitmeta.wearos.data.repositories.TaskRepository
import com.trx.habitmeta.wearos.data.repositories.UserRepository
import com.trx.habitmeta.wearos.managers.AppStateManager
import com.trx.habitmeta.wearos.util.ExceptionHandlerBuilder
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.Date
import javax.inject.Inject
import kotlin.time.DurationUnit
import kotlin.time.toDuration

@HiltViewModel
class MainViewModel
@Inject
constructor(
    userRepository: UserRepository,
    taskRepository: TaskRepository,
    exceptionBuilder: ExceptionHandlerBuilder,
    appStateManager: AppStateManager
) : BaseViewModel(userRepository, taskRepository, exceptionBuilder, appStateManager) {
    private var lastUserFetch = 0L

    fun periodicUserRefresh() {
        val now = Date().time
        if ((now - lastUserFetch) > 5.toDuration(DurationUnit.MINUTES).inWholeMilliseconds) {
            retrieveFullUserData()
            lastUserFetch = now
        }
    }

    val taskCounts = taskRepository.getActiveTaskCounts().asLiveData()
    val user = userRepository.getUser()
}
