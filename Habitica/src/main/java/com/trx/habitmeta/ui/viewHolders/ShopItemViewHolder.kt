package com.trx.habitmeta.ui.viewHolders

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.trx.habitmeta.R
import com.trx.habitmeta.databinding.RowShopitemBinding
import com.trx.habitmeta.extensions.getImpreciseRemainingString
import com.trx.habitmeta.extensions.getRemainingString
import com.trx.habitmeta.extensions.getShortRemainingString
import com.trx.habitmeta.models.shops.ShopItem
import com.trx.habitmeta.ui.views.HabiticaIconsHelper
import com.trx.habitmeta.common.extensions.dpToPx
import com.trx.habitmeta.common.extensions.loadImage

class ShopItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
    private val binding = RowShopitemBinding.bind(itemView)
    var shopIdentifier: String? = null
    private var item: ShopItem? = null
    var limitedNumberLeft: Int? = null

    var onNeedsRefresh: (() -> Unit)? = null
    var onShowPurchaseDialog: ((ShopItem, Boolean) -> Unit)? = null

    private var context: Context = itemView.context

    var purchaseCardAction: ((ShopItem) -> Unit)? = null

    var isPinned = false
        set(value) {
            field = value
            binding.pinIndicator.visibility = if (field) View.VISIBLE else View.GONE
        }

    var isCompleted = false
        set(value) {
            field = value
            binding.completedIndicator.visibility = if (field) View.VISIBLE else View.GONE
        }

    init {
        itemView.setOnClickListener(this)
        itemView.isClickable = true
        binding.pinIndicator.setImageBitmap(HabiticaIconsHelper.imageOfPinnedItem())
    }

    fun bind(
        item: ShopItem,
        canBuy: Boolean,
        numberOwned: Int
    ) {
        this.item = item
        binding.buyButton.visibility = View.VISIBLE

        var contentDescription = item.text

        binding.imageView.loadImage(item.imageName?.replace("_locked", ""))

        binding.itemDetailIndicator.text = null
        binding.itemDetailIndicator.visibility = View.GONE

        val lockedReason = item.shortLockedReason(context)
        if (!item.locked || lockedReason == null) {
            binding.priceLabel.text = item.value.toString()
            binding.priceLabel.currency = item.currency
            if (item.currency == null) {
                binding.buyButton.visibility = View.GONE
            }
            binding.priceLabel.visibility = View.VISIBLE
            binding.unlockLabel.visibility = View.GONE
            contentDescription += ", ${item.value} ${binding.priceLabel.currencyContentDescription}"
        } else {
            binding.unlockLabel.text = lockedReason
            binding.priceLabel.visibility = View.GONE
            binding.unlockLabel.visibility = View.VISIBLE
            contentDescription += ", $lockedReason"
        }
        val isLimited = item.isLimited || item.availableUntil != null
        if (numberOwned > 0) {
            binding.itemDetailIndicator.text = numberOwned.toString()
            if (isLimited) {
                binding.itemDetailIndicator.background = AppCompatResources.getDrawable(context, R.drawable.pill_bg_purple_300)
                binding.itemDetailIndicator.setTextColor(ContextCompat.getColor(context, R.color.white))
            } else {
                binding.itemDetailIndicator.background = AppCompatResources.getDrawable(context, R.drawable.pill_bg_gray)
                binding.itemDetailIndicator.setTextColor(ContextCompat.getColor(context, R.color.text_quad))
            }
            binding.itemDetailIndicator.visibility = View.VISIBLE
            contentDescription += ", ${context.getString(R.string.owned)}: $numberOwned"
        } else if (item.locked) {
            binding.itemDetailIndicator.background =
                AppCompatResources.getDrawable(context, if (isLimited) R.drawable.shop_locked_limited else R.drawable.shop_locked)
            binding.itemDetailIndicator.visibility = View.VISIBLE
            contentDescription += ", ${context.getString(R.string.locked)}"
        } else if (isLimited) {
            if (numberOwned == 0) {
                binding.itemDetailIndicator.background = AppCompatResources.getDrawable(context, R.drawable.shop_limited)
            } else {
                binding.itemDetailIndicator.background = AppCompatResources.getDrawable(context, R.drawable.pill_bg_purple_300)
            }
            binding.itemDetailIndicator.visibility = View.VISIBLE

            item.availableUntil?.let {
                contentDescription += ", ${it.getImpreciseRemainingString(context.resources)}"
            }
        }

        val limitedLeft = item.limitedNumberLeft ?: limitedNumberLeft
        if (item.key == "gem" && limitedLeft == -1) {
            binding.itemDetailIndicator.background =
                AppCompatResources.getDrawable(context, R.drawable.item_indicator_subscribe)
            binding.itemDetailIndicator.visibility = View.VISIBLE
            contentDescription += ", ${context.getString(R.string.locked)}"
        } else if (item.key == "gem") {
            binding.itemDetailIndicator.background =
                AppCompatResources.getDrawable(context, R.drawable.pill_bg_green)
            binding.itemDetailIndicator.text = "$limitedLeft"
            binding.itemDetailIndicator.setTextColor(ContextCompat.getColor(context, R.color.white))
            binding.itemDetailIndicator.visibility = View.VISIBLE
            contentDescription += ", ${context.getString(R.string.gems_left_nomax, limitedLeft)}"
        }

        if (binding.itemDetailIndicator.visibility == View.VISIBLE) {
            val layoutParams = binding.itemDetailIndicator.layoutParams
            layoutParams.width =
                if (binding.itemDetailIndicator.text.isBlank()) {
                    24.dpToPx(context)
                } else {
                    ViewGroup.LayoutParams.WRAP_CONTENT
                }
            binding.itemDetailIndicator.layoutParams = layoutParams
        }

        binding.priceLabel.isLocked = item.locked || (!canBuy && item.currency == "gold")

        binding.container.contentDescription = contentDescription
    }

    override fun onClick(view: View) {
        val item = item
        if (item != null && item.isValid) {
            onShowPurchaseDialog?.invoke(item, isPinned)
        }
    }

    fun hidePinIndicator() {
        binding.pinIndicator.visibility = View.GONE
    }
}
