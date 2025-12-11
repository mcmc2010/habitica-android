package com.trx.habitmeta.ui.views.dialogs

import android.content.Context
import androidx.compose.foundation.layout.size
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.dp
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.setViewTreeLifecycleOwner
import androidx.savedstate.setViewTreeSavedStateRegistryOwner
import com.trx.habitmeta.common.extensions.dpToPx
import com.trx.habitmeta.common.theme.HabiticaTheme
import com.trx.habitmeta.common.views.HabiticaCircularProgressView

class HabiticaProgressDialog(context: Context) : HabiticaAlertDialog(context) {
    companion object {
        fun show(
            context: FragmentActivity,
            titleID: Int
        ): HabiticaProgressDialog {
            return show(context, context.getString(titleID))
        }

        fun show(
            context: FragmentActivity,
            title: String?,
            dialogWidth: Int = 300
        ): HabiticaProgressDialog {
            val dialog = HabiticaProgressDialog(context)
            val composeView = ComposeView(context)
            dialog.setAdditionalContentView(composeView)
            composeView.setContent {
                HabiticaTheme {
                    HabiticaCircularProgressView(Modifier.size(60.dp))
                }
            }
            dialog.window?.let {
                dialog.additionalContentView?.setViewTreeSavedStateRegistryOwner(context)
                it.decorView.setViewTreeSavedStateRegistryOwner(context)
                dialog.additionalContentView?.setViewTreeLifecycleOwner(context)
                it.decorView.setViewTreeLifecycleOwner(context)
            }
            dialog.dialogWidth = dialogWidth.dpToPx(context)
            dialog.setTitle(title)
            dialog.enqueue()
            return dialog
        }
    }
}
