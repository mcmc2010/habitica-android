package com.trx.habitmeta.wearos.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.trx.habitmeta.wearos.data.repositories.TaskRepository
import com.trx.habitmeta.wearos.data.repositories.UserRepository
import com.trx.habitmeta.wearos.managers.AppStateManager
import com.trx.habitmeta.wearos.models.user.User
import com.trx.habitmeta.wearos.util.ExceptionHandlerBuilder
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StatsViewModel
@Inject
constructor(
    userRepository: UserRepository,
    taskRepository: TaskRepository,
    exceptionBuilder: ExceptionHandlerBuilder,
    appStateManager: AppStateManager
) : BaseViewModel(userRepository, taskRepository, exceptionBuilder, appStateManager) {
    fun retrieveUser() {
        viewModelScope.launch(exceptionBuilder.silent()) {
            userRepository.retrieveUser(true)
        }
    }

    var user: LiveData<User> =
        userRepository.getUser()
            .filterNotNull()
            .distinctUntilChanged { old, new ->
                val oldStats = old.stats ?: return@distinctUntilChanged false
                val newStats = new.stats ?: return@distinctUntilChanged false
                return@distinctUntilChanged (oldStats.hp ?: 0.0) + (oldStats.exp ?: 0.0) + (oldStats.exp ?: 0.0) ==
                    (newStats.hp ?: 0.0) + (newStats.exp ?: 0.0) + (newStats.exp ?: 0.0)
            }
            .asLiveData()
}
