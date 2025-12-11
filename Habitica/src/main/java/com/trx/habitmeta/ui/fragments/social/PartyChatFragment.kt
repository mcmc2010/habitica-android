package com.trx.habitmeta.ui.fragments.social

import androidx.fragment.app.viewModels
import com.trx.habitmeta.ui.viewmodels.PartyViewModel

class PartyChatFragment : ChatFragment() {
    override val viewModel: PartyViewModel by viewModels(
        ownerProducer = { requireParentFragment() }
    )
}
