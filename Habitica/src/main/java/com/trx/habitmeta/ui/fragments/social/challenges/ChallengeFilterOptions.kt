package com.trx.habitmeta.ui.fragments.social.challenges

import com.trx.habitmeta.models.social.Group

data class ChallengeFilterOptions(
    var showByGroups: List<Group>,
    var showOwned: Boolean = false,
    var notOwned: Boolean = false
)
