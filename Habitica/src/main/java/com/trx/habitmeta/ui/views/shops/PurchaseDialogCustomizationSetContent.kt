package com.trx.habitmeta.ui.views.shops

import android.content.Context
import android.widget.TextView
import com.google.android.flexbox.FlexboxLayout
import com.trx.habitmeta.R
import com.trx.habitmeta.databinding.DialogPurchaseCustomizationsetBinding
import com.trx.habitmeta.models.shops.ShopItem
import com.trx.habitmeta.common.extensions.dpToPx
import com.trx.habitmeta.common.extensions.layoutInflater
import com.trx.habitmeta.common.extensions.loadImage
import com.trx.habitmeta.common.views.PixelArtView

class PurchaseDialogCustomizationSetContent(context: Context) : PurchaseDialogContent(context) {
    val binding = DialogPurchaseCustomizationsetBinding.inflate(context.layoutInflater, this)
    override val imageView: PixelArtView
        get() = PixelArtView(context)
    override val titleTextView: TextView
        get() = binding.titleTextView

    override fun setItem(item: ShopItem) {
        titleTextView.text = item.text
        binding.imageViewWrapper.removeAllViews()
        item.setImageNames.forEach {
            val imageView = PixelArtView(context)
            imageView.setBackgroundResource(R.drawable.layout_rounded_bg_window)
            imageView.loadImage(it)
            imageView.layoutParams =
                FlexboxLayout.LayoutParams(76.dpToPx(context), 76.dpToPx(context))
            binding.imageViewWrapper.addView(imageView)
        }
        if (item.key == "facialHair") {
            binding.notesTextView.text = context.getString(R.string.facial_hair_notes)
        } else {
            binding.notesTextView.text = item.notes
        }
    }
}
