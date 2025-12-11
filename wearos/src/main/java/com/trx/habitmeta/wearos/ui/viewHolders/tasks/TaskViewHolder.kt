package com.trx.habitmeta.wearos.ui.viewHolders.tasks

import android.view.View
import com.trx.habitmeta.wearos.models.tasks.Task
import com.trx.habitmeta.wearos.ui.viewHolders.BindableViewHolder
import com.trx.habitmeta.wearos.ui.views.TaskTextView

abstract class TaskViewHolder(itemView: View) : BindableViewHolder<Task>(itemView) {
    var onTaskScore: (() -> Unit)? = null
    abstract val titleView: TaskTextView

    override fun bind(data: Task) {
        titleView.text = data.text
    }
}
