package com.trx.habitmeta.receivers

import android.app.AlarmManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import com.trx.habitmeta.helpers.TaskAlarmManager
import com.trx.habitmeta.common.helpers.ExceptionHandler
import com.trx.habitmeta.shared.HLogger
import com.trx.habitmeta.shared.LogLevel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class TaskAlarmBootReceiver : BroadcastReceiver() {
    @Inject
    lateinit var taskAlarmManager: TaskAlarmManager

    @Inject
    lateinit var sharedPreferences: SharedPreferences

    override fun onReceive(
        context: Context,
        intent: Intent
    ) {
        if (intent.action != Intent.ACTION_BOOT_COMPLETED &&
            intent.action != AlarmManager.ACTION_SCHEDULE_EXACT_ALARM_PERMISSION_STATE_CHANGED
        ) {
            return
        }
        MainScope().launch(ExceptionHandler.coroutine()) {
            taskAlarmManager.scheduleAllSavedAlarms(
                sharedPreferences.getBoolean(
                    "preventDailyReminder",
                    false
                )
            )
        }
        HLogger.log(LogLevel.INFO, this::javaClass.name, "onReceive")
    }
}
