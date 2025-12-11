package com.trx.habitmeta.extensions

import android.content.res.Configuration
import android.content.res.Resources
import android.os.Build
import com.trx.habitmeta.helpers.CrashReporter

import com.trx.habitmeta.ui.activities.BaseActivity
import java.util.Locale

fun Resources.forceLocale(
    activity: BaseActivity,
    locale: Locale
) {
    Locale.setDefault(locale)
    val configuration = Configuration()
    configuration.setLocale(locale)
    activity.createConfigurationContext(configuration)
    updateConfiguration(configuration, displayMetrics)

    try {
        CrashReporter.setCustomKey("language", locale.toLanguageTag())
    } catch (_: IllegalStateException) {
        // issue with getting firebase
    }
}
