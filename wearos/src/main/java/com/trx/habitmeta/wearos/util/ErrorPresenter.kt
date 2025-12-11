package com.trx.habitmeta.wearos.util

import androidx.lifecycle.MutableLiveData
import com.trx.habitmeta.wearos.models.DisplayedError

interface ErrorPresenter {
    val errorValues: MutableLiveData<DisplayedError>
}
