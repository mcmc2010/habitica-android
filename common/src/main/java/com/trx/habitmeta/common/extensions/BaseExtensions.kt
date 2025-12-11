package com.trx.habitmeta.common.extensions

import com.trx.habitmeta.common.helpers.launchCatching
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlin.time.DurationUnit
import kotlin.time.toDuration

/**
 * Created by phillip on 01.02.18.
 */

fun runDelayed(
    interval: Long,
    timeUnit: DurationUnit,
    function: () -> Unit
) {
    MainScope().launchCatching {
        delay(interval.toDuration(timeUnit))
        function()
    }
}
