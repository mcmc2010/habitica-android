package com.trx.habitmeta.wearos.ui.viewHolders

import android.view.View
import com.trx.habitmeta.databinding.RowFooterBinding

class FooterViewHolder(itemView: View) : BindableViewHolder<String>(itemView) {
    private val binding = RowFooterBinding.bind(itemView)

    override fun bind(data: String) {
        binding.textView.text = data
    }
}
