package com.trx.habitmeta.ui.views.dialogs

import android.content.Context
import com.trx.habitmeta.extensions.addCloseButton
import com.trx.habitmeta.models.inventory.QuestContent
import com.trx.habitmeta.ui.views.shops.PurchaseDialogQuestContent

class DetailDialog(context: Context) : HabiticaAlertDialog(context) {
    var quest: QuestContent? = null
        set(value) {
            field = value
            if (value == null) return

            val contentView = PurchaseDialogQuestContent(context)
            contentView.setQuestContentItem(value)
            setAdditionalContentView(contentView)
        }

    init {
        addCloseButton()
    }
}
