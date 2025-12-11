package com.trx.habitmeta.data

import com.trx.habitmeta.models.Achievement
import com.trx.habitmeta.models.QuestAchievement
import com.trx.habitmeta.models.Skill
import com.trx.habitmeta.models.TeamPlan
import com.trx.habitmeta.models.inventory.Customization
import com.trx.habitmeta.models.inventory.Equipment
import com.trx.habitmeta.models.responses.SkillResponse
import com.trx.habitmeta.models.responses.UnlockResponse
import com.trx.habitmeta.models.social.Group
import com.trx.habitmeta.models.tasks.Task
import com.trx.habitmeta.models.user.Stats
import com.trx.habitmeta.models.user.User
import com.trx.habitmeta.models.user.UserQuestStatus
import com.trx.habitmeta.common.models.Notification
import com.trx.habitmeta.common.models.auth.UserAuthResponse
import com.trx.habitmeta.shared.models.responses.VerifyUsernameResponse
import com.trx.habitmeta.shared.models.tasks.Attribute
import kotlinx.coroutines.flow.Flow

interface UserRepository : BaseRepository {
    fun getUser(): Flow<User?>

    fun getUser(userID: String): Flow<User?>

    suspend fun updateUser(updateData: Map<String, Any?>): User?

    suspend fun updateUser(
        key: String,
        value: Any?
    ): User?

    suspend fun retrieveUser(
        withTasks: Boolean = false,
        forced: Boolean = false,
        overrideExisting: Boolean = false
    ): User?

    suspend fun revive(): Equipment?

    suspend fun resetTutorial(): User?

    suspend fun sleep(user: User): User?

    fun getSkills(user: User): Flow<List<Skill>>

    fun getSpecialItems(user: User): Flow<List<Skill>>

    suspend fun useSkill(
        key: String,
        target: String?,
        taskId: String
    ): SkillResponse?

    suspend fun useSkill(
        key: String,
        target: String?
    ): SkillResponse?

    suspend fun disableClasses(): User?

    suspend fun changeClass(selectedClass: String? = null): User?

    suspend fun unlockPath(
        path: String,
        price: Int
    ): UnlockResponse?

    suspend fun unlockPath(customization: Customization): UnlockResponse?

    suspend fun runCron(tasks: MutableList<Task>)

    suspend fun runCron()

    suspend fun getNews(): List<Any>?

    suspend fun getNewsNotification(): Notification?

    suspend fun readNotification(id: String): List<Any>?

    suspend fun readNotifications(notificationIds: Map<String, List<String>>): List<Any>?

    suspend fun seeNotifications(notificationIds: Map<String, List<String>>): List<Any>?

    suspend fun changeCustomDayStart(dayStartTime: Int): User?

    suspend fun updateLanguage(languageCode: String): User?

    suspend fun resetAccount(password: String): Boolean?

    suspend fun deleteAccount(password: String): Void?

    suspend fun sendPasswordResetEmail(email: String): Void?

    suspend fun updateLoginName(
        newLoginName: String,
        password: String? = null
    ): User?

    suspend fun updateEmail(
        newEmail: String,
        password: String
    ): Void?

    suspend fun updatePassword(
        oldPassword: String,
        newPassword: String,
        newPasswordConfirmation: String
    ): UserAuthResponse?

    suspend fun verifyUsername(username: String): VerifyUsernameResponse?

    suspend fun allocatePoint(stat: Attribute): Stats?

    suspend fun bulkAllocatePoints(
        strength: Int,
        intelligence: Int,
        constitution: Int,
        perception: Int
    ): Stats?

    suspend fun useCustomization(
        type: String,
        category: String?,
        identifier: String
    ): User?

    suspend fun retrieveAchievements(): List<Achievement>?

    fun getAchievements(): Flow<List<Achievement>>

    fun getQuestAchievements(): Flow<List<QuestAchievement>>

    fun getUserQuestStatus(): Flow<UserQuestStatus>

    suspend fun reroll(): User?

    suspend fun retrieveTeamPlans(): List<TeamPlan>?

    fun getTeamPlans(): Flow<List<TeamPlan>>

    suspend fun retrieveTeamPlan(teamID: String): Group?

    fun getTeamPlan(teamID: String): Flow<Group?>

    suspend fun syncUserStats(): User?
}
