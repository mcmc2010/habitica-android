package com.trx.habitmeta.wearos.ui.viewmodels

import android.content.SharedPreferences
import androidx.core.content.edit
import androidx.lifecycle.viewModelScope
import com.google.android.gms.wearable.MessageClient
import com.google.android.gms.wearable.MessageEvent
import com.trx.habitmeta.common.helpers.KeyHelper
import com.trx.habitmeta.wearos.data.ApiClient
import com.trx.habitmeta.wearos.data.repositories.TaskRepository
import com.trx.habitmeta.wearos.data.repositories.UserRepository
import com.trx.habitmeta.wearos.managers.AppStateManager
import com.trx.habitmeta.wearos.util.ExceptionHandlerBuilder
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel
@Inject
constructor(
    userRepository: UserRepository,
    taskRepository: TaskRepository,
    exceptionBuilder: ExceptionHandlerBuilder,
    val apiClient: ApiClient,
    val sharedPreferences: SharedPreferences,
    val keyHelper: KeyHelper?,
    appStateManager: AppStateManager
) : BaseViewModel(userRepository, taskRepository, exceptionBuilder, appStateManager), MessageClient.OnMessageReceivedListener {
    lateinit var onLoginCompleted: (Boolean) -> Unit
    val hasAuthentication: Boolean
        get() {
            return apiClient.hasAuthentication()
        }

    override fun onMessageReceived(event: MessageEvent) {
        when (event.path) {
            "/auth" -> authDataReceived(event)
        }
    }

    private fun authDataReceived(event: MessageEvent) {
        viewModelScope.launch(
            exceptionBuilder.silent {
                onLoginCompleted(false)
            }
        ) {
            val (userID, apiKey) = String(event.data).split(":")
            try {
                if (userID.isBlank() || apiKey.isBlank()) {
                    return@launch
                }
                saveTokens(apiKey, userID)
            } catch (e: Exception) {
                onLoginCompleted(false)
                return@launch
            }
            userRepository.retrieveUser()
            onLoginCompleted(true)
        }
    }

    @Throws(Exception::class)
    private fun saveTokens(
        api: String,
        user: String
    ) {
        this.apiClient.updateAuthenticationCredentials(user, api)
        sharedPreferences.edit {
            putString("UserID", user)
            val encryptedKey =
                try {
                    keyHelper?.encrypt(api)
                } catch (e: Exception) {
                    null
                }
            if ((encryptedKey?.length ?: 0) > 5) {
                putString(user, encryptedKey)
            } else {
                // Something might have gone wrong with encryption, so fall back to this.
                putString("APIToken", api)
            }
        }
    }
}
