package com.trx.habitmeta.ui.views.social

import android.content.Context
import android.text.method.LinkMovementMethod
import android.util.AttributeSet
import android.widget.LinearLayout
import androidx.lifecycle.findViewTreeLifecycleOwner
import androidx.lifecycle.lifecycleScope
import com.trx.habitmeta.MainNavDirections
import com.trx.habitmeta.R
import com.trx.habitmeta.databinding.ViewInvitationBinding
import com.trx.habitmeta.helpers.HapticFeedbackManager
import com.trx.habitmeta.models.invitations.GenericInvitation
import com.trx.habitmeta.models.members.Member
import com.trx.habitmeta.common.extensions.flash
import com.trx.habitmeta.common.extensions.layoutInflater
import com.trx.habitmeta.common.helpers.MainNavigationController
import com.trx.habitmeta.common.helpers.setMarkdown
import kotlinx.coroutines.launch

class InvitationsView
@JvmOverloads
constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {
    var acceptCall: ((String) -> Unit)? = null
    var rejectCall: ((String) -> Unit)? = null
    var getLeader: (suspend (String) -> Member?)? = null

    init {
        orientation = VERTICAL
    }

    fun setInvitations(invitations: List<GenericInvitation>) {
        removeAllViews()
        for (invitation in invitations) {
            val leaderID = invitation.inviter
            val binding = ViewInvitationBinding.inflate(context.layoutInflater, this, true)
            binding.groupleaderTextView.movementMethod = LinkMovementMethod.getInstance()

            findViewTreeLifecycleOwner()?.lifecycleScope?.launch {
                leaderID?.let {
                    val leader = getLeader?.invoke(it) ?: return@launch
                    binding.groupleaderAvatarView.setAvatar(leader)
                    binding.groupleaderTextView.setMarkdown(
                        context.getString(
                            R.string.invitation_title,
                            "[${leader.formattedUsername}](https://habitica.com/profile/$leaderID)",
                            invitation.name
                        )
                    )
                }
            }

            binding.root.setOnClickListener {
                leaderID?.let { id ->
                    it.flash()
                    HapticFeedbackManager.tap(it)
                    val profileDirections = MainNavDirections.openProfileActivity(id)
                    MainNavigationController.navigate(profileDirections)
                }
            }

            binding.acceptButton.setOnClickListener {
                binding.root.flash()
                HapticFeedbackManager.tap(it)
                invitation.id?.let { it1 -> acceptCall?.invoke(it1) }
            }
            binding.rejectButton.setOnClickListener {
                binding.root.flash()
                HapticFeedbackManager.tap(it)
                invitation.id?.let { it1 -> rejectCall?.invoke(it1) }
            }
        }
    }
}
