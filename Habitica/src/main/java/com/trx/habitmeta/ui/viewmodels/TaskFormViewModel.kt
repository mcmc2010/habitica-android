package com.trx.habitmeta.ui.viewmodels

import androidx.compose.runtime.mutableStateOf
import com.trx.habitmeta.data.UserRepository
import com.trx.habitmeta.shared.models.tasks.Attribute
import com.trx.habitmeta.shared.models.tasks.HabitResetOption
import com.trx.habitmeta.shared.models.tasks.TaskDifficulty
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TaskFormViewModel
@Inject
constructor(
    userRepository: UserRepository,
    userViewModel: MainUserViewModel
) : BaseViewModel(userRepository, userViewModel) {
    val taskDifficulty = mutableStateOf(TaskDifficulty.EASY)
    val selectedAttribute = mutableStateOf(Attribute.STRENGTH)
    val habitResetOption = mutableStateOf(HabitResetOption.DAILY)
    val habitScoringPositive = mutableStateOf(true)
    val habitScoringNegative = mutableStateOf(false)
}
