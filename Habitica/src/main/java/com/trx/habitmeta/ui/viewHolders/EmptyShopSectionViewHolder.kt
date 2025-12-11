package com.trx.habitmeta.ui.viewHolders

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.trx.habitmeta.databinding.ShopSectionEmptyBinding
import com.trx.habitmeta.models.shops.EmptyShopCategory

class EmptyShopSectionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val binding = ShopSectionEmptyBinding.bind(itemView)

    var onClicked: (() -> Unit)? = null

    init {
        binding.root.setOnClickListener {
            onClicked?.invoke()
        }
    }

    fun bind(emptyShopCategory: EmptyShopCategory) {
        binding.titleView.text = emptyShopCategory.title
        binding.descriptionView.text = emptyShopCategory.description
    }
}
