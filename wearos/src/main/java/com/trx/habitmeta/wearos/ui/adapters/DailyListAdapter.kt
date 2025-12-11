package com.trx.habitmeta.wearos.ui.adapters

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.trx.habitmeta.databinding.RowDailyBinding
import com.trx.habitmeta.common.extensions.layoutInflater
import com.trx.habitmeta.wearos.ui.viewHolders.tasks.DailyViewHolder

class DailyListAdapter : TaskListAdapter() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {
        return if (viewType == 1) {
            return DailyViewHolder(RowDailyBinding.inflate(parent.context.layoutInflater, parent, false).root)
        } else {
            super.onCreateViewHolder(parent, viewType)
        }
    }
}
