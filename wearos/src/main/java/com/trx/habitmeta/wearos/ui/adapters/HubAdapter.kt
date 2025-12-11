package com.trx.habitmeta.wearos.ui.adapters

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.trx.habitmeta.databinding.RowHubBinding
import com.trx.habitmeta.common.extensions.layoutInflater
import com.trx.habitmeta.wearos.models.user.MenuItem
import com.trx.habitmeta.wearos.ui.viewHolders.HubViewHolder

class HubAdapter : BaseAdapter<MenuItem>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {
        val inflater = parent.context.layoutInflater
        return if (viewType == 1) {
            HubViewHolder(RowHubBinding.inflate(inflater, parent, false).root)
        } else {
            super.onCreateViewHolder(parent, viewType)
        }
    }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int
    ) {
        if (holder is HubViewHolder) {
            holder.bind(getItemAt(position))
        } else {
            super.onBindViewHolder(holder, position)
        }
    }

    override fun getItemViewType(position: Int) = if (position == 0) TYPE_HEADER else 1
}
