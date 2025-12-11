package com.trx.habitmeta.interactors

import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.trx.habitmeta.R
import com.trx.habitmeta.helpers.SoundManager
import com.trx.habitmeta.ui.views.HabiticaSnackbar
import com.trx.habitmeta.shared.models.responses.TaskScoringResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

class DisplayItemDropUseCase
@Inject
constructor(private val soundManager: SoundManager) :
    UseCase<DisplayItemDropUseCase.RequestValues, Unit>() {
    override suspend fun run(requestValues: RequestValues) {
        val data = requestValues.data
        val snackbarText = StringBuilder(data?.drop?.dialog ?: "")

        if ((data?.questItemsFound ?: 0) > 0 && requestValues.showQuestItems) {
            if (snackbarText.isNotEmpty()) {
                snackbarText.append('\n')
            }
            snackbarText.append(
                requestValues.context.getString(
                    R.string.quest_items_found,
                    data!!.questItemsFound
                )
            )
        }

        if (snackbarText.isNotEmpty()) {
            MainScope().launch(context = Dispatchers.Main) {
                delay(3000L)
                HabiticaSnackbar.showSnackbar(
                    requestValues.snackbarTargetView,
                    snackbarText,
                    HabiticaSnackbar.SnackbarDisplayType.DROP,
                    true
                )
                soundManager.loadAndPlayAudio(SoundManager.SOUND_ITEM_DROP)
            }
        }
        return
    }

    class RequestValues(
        val data: TaskScoringResult?,
        val context: AppCompatActivity,
        val snackbarTargetView: ViewGroup,
        val showQuestItems: Boolean
    ) : UseCase.RequestValues
}
