package com.trx.habitmeta.utils

import com.google.firebase.perf.FirebasePerformance
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonParseException
import com.google.gson.reflect.TypeToken
import com.trx.habitmeta.extensions.getAsString
import com.trx.habitmeta.models.PushDevice
import com.trx.habitmeta.models.QuestAchievement
import com.trx.habitmeta.models.Tag
import com.trx.habitmeta.models.inventory.Quest
import com.trx.habitmeta.models.invitations.Invitations
import com.trx.habitmeta.models.social.ChallengeMembership
import com.trx.habitmeta.models.social.UserParty
import com.trx.habitmeta.models.user.ABTest
import com.trx.habitmeta.models.user.Authentication
import com.trx.habitmeta.models.user.Backer
import com.trx.habitmeta.models.user.ContributorInfo
import com.trx.habitmeta.models.user.Flags
import com.trx.habitmeta.models.user.Inbox
import com.trx.habitmeta.models.user.Items
import com.trx.habitmeta.models.user.OwnedItem
import com.trx.habitmeta.models.user.Permissions
import com.trx.habitmeta.models.user.Preferences
import com.trx.habitmeta.models.user.Profile
import com.trx.habitmeta.models.user.Purchases
import com.trx.habitmeta.models.user.Stats
import com.trx.habitmeta.models.user.User
import com.trx.habitmeta.models.user.UserAchievement
import com.trx.habitmeta.shared.models.tasks.TasksOrder
import io.realm.Realm
import io.realm.RealmList
import java.lang.reflect.Type
import java.util.Date

class UserDeserializer : JsonDeserializer<User> {
    @Throws(JsonParseException::class)
    override fun deserialize(
        json: JsonElement,
        typeOfT: Type,
        context: JsonDeserializationContext
    ): User {
        val deserializeTrace = FirebasePerformance.getInstance().newTrace("UserDeserialize")
        deserializeTrace.start()
        val user = User()
        val obj = json.asJsonObject

        if (obj.has("_id")) {
            user.id = obj.getAsString("_id")
        }
        if (obj.has("_v")) {
            user.versionNumber = obj.get("_v").asInt
        }

        if (obj.has("balance")) {
            user.balance = obj.get("balance").asDouble
        }
        if (obj.has("stats")) {
            user.stats = context.deserialize(obj.get("stats"), Stats::class.java)
        }
        if (obj.has("inbox")) {
            user.inbox = context.deserialize(obj.get("inbox"), Inbox::class.java)
        }
        if (obj.has("permissions")) {
            user.permissions = context.deserialize(obj.get("permissions"), Permissions::class.java)
        }
        if (obj.has("preferences")) {
            user.preferences = context.deserialize(obj.get("preferences"), Preferences::class.java)
        }
        if (obj.has("profile")) {
            user.profile = context.deserialize(obj.get("profile"), Profile::class.java)
        }
        if (obj.has("party")) {
            val partyObj = obj.getAsJsonObject("party")
            user.party = context.deserialize(partyObj, UserParty::class.java)
            if (user.party != null && user.party?.quest != null) {
                user.party?.quest?.id = user.id
                if (!partyObj.getAsJsonObject("quest").has("RSVPNeeded")) {
                    val realm = Realm.getDefaultInstance()
                    val quest = realm.where(Quest::class.java).equalTo("id", user.id).findFirst()
                    if (quest != null && quest.isValid) {
                        user.party?.quest?.rsvpNeeded = quest.rsvpNeeded
                    }
                }
                if (partyObj.getAsJsonObject("quest").has("completed")) {
                    if (!partyObj.getAsJsonObject("quest").get("completed").isJsonNull) {
                        user.party?.quest?.completed =
                            obj.getAsJsonObject("party").getAsJsonObject("quest")
                                .get("completed").asString
                    }
                }
            }
        }
        if (obj.has("purchased")) {
            user.purchased = context.deserialize(obj.get("purchased"), Purchases::class.java)
            if (obj.getAsJsonObject("purchased").has("plan")) {
                if (obj.getAsJsonObject("purchased").getAsJsonObject("plan").has("mysteryItems")) {
                    user.purchased?.plan?.mysteryItemCount =
                        obj.getAsJsonObject("purchased").getAsJsonObject("plan")
                            .getAsJsonArray("mysteryItems").size()
                }
            }
        }
        if (obj.has("items")) {
            user.items = context.deserialize(obj.get("items"), Items::class.java)
            val item = OwnedItem()
            item.itemType = "special"
            item.key = "inventory_present"
            item.userID = user.id
            item.numberOwned = user.purchased?.plan?.mysteryItemCount ?: 0
            user.items?.special?.add(item)
            user.items?.setItemTypes()
        }
        if (obj.has("auth")) {
            user.authentication = context.deserialize(obj.get("auth"), Authentication::class.java)
        }
        if (obj.has("flags")) {
            user.flags = context.deserialize(obj.get("flags"), Flags::class.java)
        }
        if (obj.has("contributor")) {
            user.contributor =
                context.deserialize(obj.get("contributor"), ContributorInfo::class.java)
        }
        if (obj.has("backer")) {
            user.backer = context.deserialize<Backer>(obj.get("backer"), Backer::class.java)
        }
        if (obj.has("invitations")) {
            user.invitations = context.deserialize(obj.get("invitations"), Invitations::class.java)
        }
        if (obj.has("tags")) {
            user.tags =
                context.deserialize(
                    obj.get("tags"),
                    object : TypeToken<RealmList<Tag>>() {
                    }.type
                )
            for (tag in user.tags) {
                tag.userId = user.id
            }
        }
        if (obj.has("achievements")) {
            val achievements = RealmList<UserAchievement>()
            for (entry in obj.getAsJsonObject("achievements").entrySet()) {
                if (!entry.value.isJsonPrimitive) {
                    continue
                }
                val achievement = UserAchievement()
                achievement.key = entry.key
                achievement.earned = entry.value.asBoolean
                achievements.add(achievement)
            }
            user.achievements = achievements
        }
        if (obj.has("tasksOrder")) {
            user.tasksOrder = context.deserialize(obj.get("tasksOrder"), TasksOrder::class.java)
        }
        if (obj.has("challenges")) {
            user.challenges = RealmList()
            obj.getAsJsonArray("challenges").forEach {
                user.challenges?.add(ChallengeMembership(user.id ?: "", it.asString))
            }
        }

        if (obj.has("pushDevices")) {
            user.pushDevices = ArrayList()
            obj.getAsJsonArray("pushDevices")
                .map { context.deserialize<PushDevice>(it, PushDevice::class.java) }
                .forEach { (user.pushDevices as? ArrayList<PushDevice>)?.add(it) }
        }

        if (obj.has("lastCron")) {
            user.lastCron = context.deserialize(obj.get("lastCron"), Date::class.java)
        }

        if (obj.has("needsCron")) {
            user.needsCron = obj.get("needsCron").asBoolean
        }

        if (obj.has("achievements")) {
            val achievements = obj.getAsJsonObject("achievements")
            if (achievements.has("streak")) {
                try {
                    user.streakCount = obj.getAsJsonObject("achievements").get("streak").asInt
                } catch (ignored: UnsupportedOperationException) {
                }
            }
            if (achievements.has("quests")) {
                val questAchievements = RealmList<QuestAchievement>()
                for (entry in achievements.getAsJsonObject("quests").entrySet()) {
                    val questAchievement = QuestAchievement()
                    questAchievement.questKey = entry.key
                    questAchievement.count = entry.value.asInt
                    questAchievements.add(questAchievement)
                }
                user.questAchievements = questAchievements
            }
            if (achievements.has("challenges")) {
                val challengeAchievements = RealmList<String>()
                for (entry in achievements.getAsJsonArray("challenges")) {
                    challengeAchievements.add(entry.asString)
                }
                user.challengeAchievements = challengeAchievements
            }
        }

        if (obj.has("_ABTests")) {
            user.abTests = RealmList()
            for (testJSON in obj.getAsJsonObject("_ABTests").entrySet()) {
                val test = ABTest()
                test.name = testJSON.key
                test.group = testJSON.value.asString
                user.abTests?.add(test)
            }
        }
        deserializeTrace.stop()
        return user
    }
}
