package com.trx.habitmeta.ui.viewmodels.inventory.equipment

import androidx.lifecycle.viewModelScope
import com.trx.habitmeta.data.InventoryRepository
import com.trx.habitmeta.data.UserRepository
import com.trx.habitmeta.models.inventory.Equipment
import com.trx.habitmeta.ui.viewmodels.BaseViewModel
import com.trx.habitmeta.ui.viewmodels.MainUserViewModel
import com.trx.habitmeta.common.helpers.launchCatching
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class EquipmentOverviewViewModel
@Inject
constructor(
    userRepository: UserRepository,
    userViewModel: MainUserViewModel,
    val inventoryRepository: InventoryRepository
) : BaseViewModel(userRepository, userViewModel) {
    val usesAutoEquip: Boolean
        get() = user.value?.preferences?.autoEquip == true
    val usesCostume: Boolean
        get() = user.value?.preferences?.costume == true

    fun getGear(
        key: String,
        onSuccess: (Equipment) -> Unit
    ) {
        viewModelScope.launchCatching {
            inventoryRepository.getEquipment(key).collect {
                onSuccess(it)
            }
        }
    }
}
