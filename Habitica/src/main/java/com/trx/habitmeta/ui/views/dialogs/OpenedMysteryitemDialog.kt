package com.trx.habitmeta.ui.views.dialogs

import android.content.Context
import com.trx.habitmeta.databinding.DialogOpenMysteryitemBinding
import com.trx.habitmeta.common.extensions.dpToPx
import com.trx.habitmeta.common.extensions.layoutInflater

class OpenedMysteryitemDialog(context: Context) : HabiticaAlertDialog(context) {
    val binding = DialogOpenMysteryitemBinding.inflate(context.layoutInflater)

    init {
        setAdditionalContentView(binding.root)
        dialogWidth = 302.dpToPx(context)
    }
}
