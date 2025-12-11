package com.trx.habitmeta.extensions

import com.trx.habitmeta.common.helpers.LanguageHelper
import java.util.Locale

fun Locale.getSystemDefault(): Locale {
    return LanguageHelper.systemLocale
}
