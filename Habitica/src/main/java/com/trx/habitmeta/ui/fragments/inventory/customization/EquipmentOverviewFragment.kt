package com.trx.habitmeta.ui.fragments.inventory.customization

import android.os.Bundle
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EquipmentOverviewFragment : AvatarOverviewFragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        showCustomization = false
        super.onCreate(savedInstanceState)
    }
}
