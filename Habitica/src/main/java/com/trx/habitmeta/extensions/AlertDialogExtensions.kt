package com.trx.habitmeta.extensions

import com.trx.habitmeta.R
import com.trx.habitmeta.ui.views.dialogs.HabiticaAlertDialog

fun HabiticaAlertDialog.addOkButton(
    isPrimary: Boolean = true,
    listener: ((HabiticaAlertDialog, Int) -> Unit)? = null
) {
    this.addButton(R.string.ok, isPrimary, false, true, listener)
}

fun HabiticaAlertDialog.addCloseButton(
    isPrimary: Boolean = false,
    listener: ((HabiticaAlertDialog, Int) -> Unit)? = null
) {
    this.addButton(R.string.close, isPrimary, false, true, listener)
}

fun HabiticaAlertDialog.addCancelButton(
    isPrimary: Boolean = false,
    listener: ((HabiticaAlertDialog, Int) -> Unit)? = null
) {
    this.addButton(R.string.cancel, isPrimary, false, true, listener)
}
