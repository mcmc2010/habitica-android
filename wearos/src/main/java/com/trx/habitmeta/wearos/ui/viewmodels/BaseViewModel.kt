package com.trx.habitmeta.wearos.ui.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.trx.habitmeta.wearos.data.repositories.TaskRepository
import com.trx.habitmeta.wearos.data.repositories.UserRepository
import com.trx.habitmeta.wearos.managers.AppStateManager
import com.trx.habitmeta.wearos.models.DisplayedError
import com.trx.habitmeta.wearos.util.ErrorPresenter
import com.trx.habitmeta.wearos.util.ExceptionHandlerBuilder
import kotlinx.coroutines.launch

open class BaseViewModel(
    val userRepository: UserRepository,
    val taskRepository: TaskRepository,
    val exceptionBuilder: ExceptionHandlerBuilder,
    val appStateManager: AppStateManager
) : ViewModel(), ErrorPresenter {
    override val errorValues = MutableLiveData<DisplayedError>()

    fun retrieveFullUserData() {
        viewModelScope.launch(exceptionBuilder.userFacing(this)) {
            appStateManager.startLoading()
            val user = userRepository.retrieveUser(true)
            taskRepository.retrieveTasks(user?.tasksOrder, true)
            appStateManager.endLoading()
        }
    }
}
