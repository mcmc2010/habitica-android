package com.trx.habitmeta.wearos.ui.viewHolders.tasks

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.trx.habitmeta.databinding.RowDailyBinding
import com.trx.habitmeta.wearos.ui.views.TaskTextView

class DailyViewHolder(itemView: View) : CheckedTaskViewHolder(itemView) {
    private val binding = RowDailyBinding.bind(itemView)
    override val titleView: TaskTextView
        get() = binding.title
    override val checkbox: ImageView
        get() = binding.checkbox
    override val checkboxWrapper: ViewGroup
        get() = binding.checkboxWrapper
}
