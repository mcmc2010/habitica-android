package com.trx.habitmeta.widget

import android.content.Context
import android.content.Intent
import com.trx.habitmeta.R
import com.trx.habitmeta.data.TaskRepository
import com.trx.habitmeta.data.UserRepository
import com.trx.habitmeta.shared.models.tasks.TaskType

class TodoListFactory(
    context: Context,
    intent: Intent,
    taskRepository: TaskRepository,
    userRepository: UserRepository
) : TaskListFactory(
    context,
    intent,
    TaskType.TODO,
    R.layout.widget_todo_list_row,
    R.id.todo_text,
    taskRepository,
    userRepository
)
