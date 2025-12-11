package com.trx.habitmeta.receivers

import android.content.Intent
import com.google.android.gms.wearable.MessageEvent
import com.google.android.gms.wearable.Wearable
import com.google.android.gms.wearable.WearableListenerService
import com.trx.habitmeta.ui.activities.OnboardingActivity
import com.trx.habitmeta.ui.activities.MainActivity
import com.trx.habitmeta.ui.activities.TaskFormActivity
import com.trx.habitmeta.common.api.HostConfig
import com.trx.habitmeta.common.helpers.DeviceCommunication
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class DeviceCommunicationService : WearableListenerService() {
    @Inject
    lateinit var hostConfig: HostConfig

    private val messageClient by lazy { Wearable.getMessageClient(this) }

    override fun onMessageReceived(event: MessageEvent) {
        super.onMessageReceived(event)
        when (event.path) {
            DeviceCommunication.REQUEST_AUTH -> processAuthRequest(event)
            DeviceCommunication.SHOW_REGISTER -> openActivity(event, OnboardingActivity::class.java)
            DeviceCommunication.SHOW_LOGIN -> openActivity(event, OnboardingActivity::class.java)
            DeviceCommunication.SHOW_RYA -> openActivity(event, MainActivity::class.java)
            DeviceCommunication.SHOW_TASK_EDIT -> openTaskForm(event)
        }
    }

    private fun openActivity(
        event: MessageEvent,
        activityClass: Class<*>
    ) {
        val intent = Intent(this, activityClass)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
        messageClient.sendMessage(event.sourceNodeId, "/action_completed", null)
    }

    private fun openTaskForm(event: MessageEvent) {
        val taskID = String(event.data)
        val startIntent =
            Intent(this, TaskFormActivity::class.java).apply {
                putExtra(TaskFormActivity.TASK_ID_KEY, taskID)
            }
        startIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(startIntent)
        messageClient.sendMessage(event.sourceNodeId, "/action_completed", null)
    }

    private fun processAuthRequest(event: MessageEvent) {
        messageClient.sendMessage(
            event.sourceNodeId,
            "/auth",
            "${hostConfig.userID}:${hostConfig.apiKey}".toByteArray()
        )
    }
}
