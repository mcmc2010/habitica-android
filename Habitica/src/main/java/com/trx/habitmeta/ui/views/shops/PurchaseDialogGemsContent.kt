package com.trx.habitmeta.ui.views.shops

import android.content.Context
import android.widget.TextView
import com.trx.habitmeta.databinding.DialogPurchaseGemsBinding
import com.trx.habitmeta.extensions.asDrawable
import com.trx.habitmeta.models.shops.ShopItem
import com.trx.habitmeta.ui.views.HabiticaIconsHelper
import com.trx.habitmeta.common.extensions.layoutInflater
import com.trx.habitmeta.common.views.PixelArtView

internal class PurchaseDialogGemsContent(context: Context) : PurchaseDialogContent(context) {
    internal val binding = DialogPurchaseGemsBinding.inflate(context.layoutInflater, this)
    override val imageView: PixelArtView
        get() = binding.imageView
    override val titleTextView: TextView
        get() = binding.titleTextView

    init {
        binding.stepperView.iconDrawable =
            HabiticaIconsHelper.imageOfGem().asDrawable(context.resources)
    }

    override fun setItem(item: ShopItem) {
        super.setItem(item)
        binding.notesTextView.text = item.notes
    }
}
