package com.trx.habitmeta.ui.views.shops

import android.content.Context
import android.widget.TextView
import com.trx.habitmeta.databinding.DialogPurchaseContentItemBinding
import com.trx.habitmeta.models.shops.ShopItem
import com.trx.habitmeta.common.extensions.layoutInflater
import com.trx.habitmeta.common.views.PixelArtView

class PurchaseDialogItemContent(context: Context) : PurchaseDialogContent(context) {
    private val binding = DialogPurchaseContentItemBinding.inflate(context.layoutInflater, this)
    override val imageView: PixelArtView
        get() = binding.imageView
    override val titleTextView: TextView
        get() = binding.titleTextView

    override fun setItem(item: ShopItem) {
        super.setItem(item)
        binding.notesTextView.text = item.notes
        binding.stepperView.iconDrawable = null
    }
}
