package com.trx.habitmeta.widget

import com.trx.habitmeta.R

class DailiesWidgetProvider : TaskListWidgetProvider() {
    override val serviceClass: Class<*>
        get() = DailiesWidgetService::class.java
    override val providerClass: Class<*>
        get() = DailiesWidgetProvider::class.java
    override val titleResId: Int
        get() = R.string.dailies
}
