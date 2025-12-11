package com.trx.habitmeta.data.local

import com.trx.habitmeta.models.Achievement
import com.trx.habitmeta.models.QuestAchievement
import com.trx.habitmeta.models.Skill
import com.trx.habitmeta.models.TeamPlan
import com.trx.habitmeta.models.TutorialStep
import com.trx.habitmeta.models.social.ChatMessage
import com.trx.habitmeta.models.social.Group
import com.trx.habitmeta.models.user.User
import com.trx.habitmeta.models.user.UserQuestStatus
import io.realm.RealmResults
import kotlinx.coroutines.flow.Flow

interface UserLocalRepository : BaseLocalRepository {
    suspend fun getTutorialSteps(): Flow<RealmResults<TutorialStep>>

    fun getUser(userID: String): Flow<User?>

    fun saveUser(
        user: User,
        overrideExisting: Boolean = true
    )

    fun saveMessages(messages: List<ChatMessage>)

    fun getSkills(user: User): Flow<List<Skill>>

    fun getSpecialItems(user: User): Flow<List<Skill>>

    fun getAchievements(): Flow<List<Achievement>>

    fun getQuestAchievements(userID: String): Flow<List<QuestAchievement>>

    fun getUserQuestStatus(userID: String): Flow<UserQuestStatus>

    fun getTeamPlans(userID: String): Flow<List<TeamPlan>>

    fun getTeamPlan(teamID: String): Flow<Group?>
}
