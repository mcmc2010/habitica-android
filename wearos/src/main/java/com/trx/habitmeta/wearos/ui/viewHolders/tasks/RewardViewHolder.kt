package com.trx.habitmeta.wearos.ui.viewHolders.tasks

import android.view.View
import com.trx.habitmeta.databinding.RowRewardBinding
import com.trx.habitmeta.wearos.ui.views.TaskTextView

class RewardViewHolder(itemView: View) : TaskViewHolder(itemView) {
    private val binding = RowRewardBinding.bind(itemView)
    override val titleView: TaskTextView
        get() = binding.title
}
