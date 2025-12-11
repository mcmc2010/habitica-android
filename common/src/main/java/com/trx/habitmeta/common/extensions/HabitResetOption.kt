package com.trx.habitmeta.common.extensions

import com.trx.habitmeta.common.R
import com.trx.habitmeta.shared.models.tasks.HabitResetOption

val HabitResetOption.nameRes: Int
    get() =
        when (this) {
            HabitResetOption.DAILY -> R.string.daily
            HabitResetOption.WEEKLY -> R.string.weekly
            HabitResetOption.MONTHLY -> R.string.monthly
        }
