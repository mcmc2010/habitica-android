package com.trx.habitmeta.ui.adapter.social

import android.view.ViewGroup
import com.trx.habitmeta.R
import com.trx.habitmeta.models.members.Member
import com.trx.habitmeta.ui.adapter.BaseRecyclerViewAdapter
import com.trx.habitmeta.ui.viewHolders.GroupMemberViewHolder
import com.trx.habitmeta.common.extensions.inflate

class PartyMemberRecyclerViewAdapter : BaseRecyclerViewAdapter<Member, GroupMemberViewHolder>() {
    var leaderID: String? = null

    var onUserClicked: ((String) -> Unit)? = null

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): GroupMemberViewHolder {
        return GroupMemberViewHolder(parent.inflate(R.layout.party_member))
    }

    override fun onBindViewHolder(
        holder: GroupMemberViewHolder,
        position: Int
    ) {
        holder.bind(data[position], leaderID, null)
        holder.onClickEvent = {
            onUserClicked?.invoke(data[position].id)
        }
    }
}
