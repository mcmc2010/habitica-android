package com.trx.habitmeta.ui.fragments.social.guilds

import android.app.Activity
import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.trx.habitmeta.MainNavDirections
import com.trx.habitmeta.R
import com.trx.habitmeta.data.ChallengeRepository
import com.trx.habitmeta.data.UserRepository
import com.trx.habitmeta.databinding.FragmentGuildDetailBinding
import com.trx.habitmeta.helpers.AppConfigManager
import com.trx.habitmeta.models.members.Member
import com.trx.habitmeta.models.social.Challenge
import com.trx.habitmeta.models.social.Group
import com.trx.habitmeta.ui.activities.GroupInviteActivity
import com.trx.habitmeta.ui.activities.MainActivity
import com.trx.habitmeta.ui.fragments.BaseFragment
import com.trx.habitmeta.ui.viewmodels.GroupViewModel
import com.trx.habitmeta.ui.views.HabiticaIconsHelper
import com.trx.habitmeta.ui.views.SnackbarActivity
import com.trx.habitmeta.ui.views.dialogs.HabiticaAlertDialog
import com.trx.habitmeta.common.helpers.MainNavigationController
import com.trx.habitmeta.common.helpers.launchCatching
import com.trx.habitmeta.common.helpers.setMarkdown
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class GuildDetailFragment : BaseFragment<FragmentGuildDetailBinding>() {
    @Inject
    lateinit var configManager: AppConfigManager

    override var binding: FragmentGuildDetailBinding? = null

    @Inject
    lateinit var challengeRepository: ChallengeRepository

    @Inject
    lateinit var userRepository: UserRepository

    override fun createBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentGuildDetailBinding {
        return FragmentGuildDetailBinding.inflate(inflater, container, false)
    }

    val viewModel: GroupViewModel by viewModels(
        ownerProducer = { parentFragment as Fragment }
    )

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)

        binding?.refreshLayout?.setOnRefreshListener { this.refresh() }

        viewModel.getGroupData().observe(viewLifecycleOwner) { updateGuild(it) }
        viewModel.getLeaderData().observe(viewLifecycleOwner) { setLeader(it) }
        viewModel.getIsMemberData().observe(viewLifecycleOwner) { updateMembership(it) }

        binding?.guildDescription?.movementMethod = LinkMovementMethod.getInstance()
        binding?.guildSummary?.movementMethod = LinkMovementMethod.getInstance()
        binding?.guildBankIcon?.setImageBitmap(HabiticaIconsHelper.imageOfGem())
        binding?.leaveButton?.setOnClickListener {
            leaveGuild()
        }
        binding?.joinButton?.setOnClickListener {
            viewModel.joinGroup {
                (this.activity as? SnackbarActivity)?.showSnackbar(title = getString(R.string.joined_guild))
            }
        }
        binding?.leaderWrapper?.setOnClickListener {
            viewModel.leaderID?.let { leaderID ->
                val profileDirections = MainNavDirections.openProfileActivity(leaderID)
                MainNavigationController.navigate(profileDirections)
            }
        }
    }

    private fun setLeader(leader: Member?) {
        if (leader == null) {
            return
        }
        binding?.leaderAvatarView?.setAvatar(leader)
        binding?.leaderProfileName?.username = leader.displayName
        binding?.leaderProfileName?.tier = leader.contributor?.level ?: 0
        binding?.leaderUsername?.text = leader.formattedUsername
    }

    private fun updateMembership(isMember: Boolean?) {
        binding?.joinButton?.visibility = if (isMember == true) View.GONE else View.VISIBLE
        binding?.leaveButton?.visibility = if (isMember == true) View.VISIBLE else View.GONE
    }

    private val sendInvitesResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == Activity.RESULT_OK) {
                val inviteData = HashMap<String, Any>()
                inviteData["inviter"] = viewModel.user.value?.profile?.name ?: ""
                val emails = it.data?.getStringArrayExtra(GroupInviteActivity.EMAILS_KEY)
                if (!emails.isNullOrEmpty()) {
                    val invites = ArrayList<HashMap<String, String>>()
                    emails.forEach { email ->
                        val invite = HashMap<String, String>()
                        invite["name"] = ""
                        invite["email"] = email
                        invites.add(invite)
                    }
                    inviteData["emails"] = invites
                }
                val userIDs = it.data?.getStringArrayExtra(GroupInviteActivity.USER_IDS_KEY)
                if (!userIDs.isNullOrEmpty()) {
                    val invites = ArrayList<String>()
                    userIDs.forEach { invites.add(it) }
                    inviteData["usernames"] = invites
                }
                viewModel.inviteToGroup(inviteData)
            }
        }

    private fun refresh() {
        viewModel.retrieveGroup {
            binding?.refreshLayout?.isRefreshing = false
        }
    }

    private fun getGroupChallenges(): List<Challenge> {
        val groupChallenges = mutableListOf<Challenge>()
        lifecycleScope.launchCatching {
            userRepository.getUser().collect {
                it?.challenges?.forEach { membership ->
                    val challenge =
                        challengeRepository.getChallenge(membership.challengeID).firstOrNull()
                    if (challenge != null && challenge.groupId == viewModel.groupID) {
                        groupChallenges.add(challenge)
                    }
                }
            }
        }
        return groupChallenges
    }

    internal fun leaveGuild() {
        val context = context
        if (context != null) {
            val groupChallenges = getGroupChallenges()
            lifecycleScope.launch(Dispatchers.Main) {
                delay(500)
                if (groupChallenges.isNotEmpty()) {
                    val alert = HabiticaAlertDialog(context)
                    alert.setTitle(R.string.guild_challenges)
                    alert.setMessage(R.string.leave_guild_challenges_confirmation)
                    alert.addButton(R.string.keep_challenges, true) { _, _ ->
                        viewModel.leaveGroup(false, false, groupChallenges, true) { showLeaveSnackbar() }
                    }
                    alert.addButton(
                        R.string.leave_challenges_delete_tasks,
                        isPrimary = false,
                        isDestructive = true
                    ) { _, _ ->
                        viewModel.leaveGroup(false, false, groupChallenges, false) { showLeaveSnackbar() }
                    }
                    alert.setExtraCloseButtonVisibility(View.VISIBLE)
                    alert.show()
                } else {
                    val alert = HabiticaAlertDialog(context)
                    alert.setTitle(R.string.leave_guild_confirmation)
                    alert.setMessage(R.string.rejoin_guild)
                    alert.addButton(
                        R.string.leave,
                        isPrimary = true,
                        isDestructive = true
                    ) { _, _ ->
                        viewModel.leaveGroup(false, false, groupChallenges, false) {
                            showLeaveSnackbar()
                        }
                    }
                    alert.setExtraCloseButtonVisibility(View.VISIBLE)
                    alert.show()
                }
            }
        }
    }

    private fun showLeaveSnackbar() {
        (this.activity as? MainActivity)?.showSnackbar(title = getString(R.string.left_guild))
    }

    private fun updateGuild(guild: Group?) {
        binding?.titleView?.text = guild?.name
        binding?.guildMembersIcon?.setImageBitmap(
            HabiticaIconsHelper.imageOfGuildCrestMedium(
                (guild?.memberCount ?: 0).toFloat()
            )
        )
        binding?.guildMembersText?.text = guild?.memberCount.toString()
        binding?.guildBankText?.text = guild?.gemCount.toString()
        binding?.guildSummary?.setMarkdown(guild?.summary)
        binding?.guildDescription?.setMarkdown(guild?.description)
    }

    companion object {
        fun newInstance(): GuildDetailFragment {
            val args = Bundle()

            val fragment = GuildDetailFragment()
            fragment.arguments = args
            return fragment
        }
    }
}
