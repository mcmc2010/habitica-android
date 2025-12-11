package com.trx.habitmeta.ui.fragments.inventory.shops

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import com.trx.habitmeta.models.shops.Shop
import com.trx.habitmeta.common.helpers.launchCatching
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class QuestShopFragment : ShopFragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        shopIdentifier = Shop.QUEST_SHOP
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launchCatching {
            userRepository.getQuestAchievements().collect {
                adapter?.completedQuests = it.map { it.questKey }
            }
        }
    }
}
