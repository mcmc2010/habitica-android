package com.trx.habitmeta.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.trx.habitmeta.models.Achievement;
import com.trx.habitmeta.models.ContentResult;
import com.trx.habitmeta.models.FAQArticle;
import com.trx.habitmeta.models.Skill;
import com.trx.habitmeta.models.Tag;
import com.trx.habitmeta.models.TutorialStep;
import com.trx.habitmeta.models.WorldState;
import com.trx.habitmeta.models.inventory.Customization;
import com.trx.habitmeta.models.inventory.Equipment;
import com.trx.habitmeta.models.inventory.Quest;
import com.trx.habitmeta.models.inventory.QuestCollect;
import com.trx.habitmeta.models.inventory.QuestDropItem;
import com.trx.habitmeta.models.invitations.InviteResponse;
import com.trx.habitmeta.models.members.Member;
import com.trx.habitmeta.models.social.Challenge;
import com.trx.habitmeta.models.social.ChatMessage;
import com.trx.habitmeta.models.social.FindUsernameResult;
import com.trx.habitmeta.models.social.Group;
import com.trx.habitmeta.models.tasks.GroupAssignedDetails;
import com.trx.habitmeta.models.tasks.Task;
import com.trx.habitmeta.models.tasks.TaskList;
import com.trx.habitmeta.models.user.OwnedItem;
import com.trx.habitmeta.models.user.OwnedMount;
import com.trx.habitmeta.models.user.OwnedPet;
import com.trx.habitmeta.models.user.Purchases;
import com.trx.habitmeta.models.user.User;
import com.trx.habitmeta.models.user.auth.SocialAuthentication;
import com.trx.habitmeta.utils.AchievementListDeserializer;
import com.trx.habitmeta.utils.AssignedDetailsDeserializer;
import com.trx.habitmeta.utils.BooleanAsIntAdapter;
import com.trx.habitmeta.utils.ChallengeDeserializer;
import com.trx.habitmeta.utils.ChallengeListDeserializer;
import com.trx.habitmeta.utils.ChatMessageDeserializer;
import com.trx.habitmeta.utils.ContentDeserializer;
import com.trx.habitmeta.utils.CustomizationDeserializer;
import com.trx.habitmeta.utils.DateDeserializer;
import com.trx.habitmeta.utils.EquipmentListDeserializer;
import com.trx.habitmeta.utils.FAQArticleListDeserilializer;
import com.trx.habitmeta.utils.FeedResponseDeserializer;
import com.trx.habitmeta.utils.FindUsernameResultDeserializer;
import com.trx.habitmeta.utils.GroupSerialization;
import com.trx.habitmeta.utils.InviteResponseDeserializer;
import com.trx.habitmeta.utils.MemberSerialization;
import com.trx.habitmeta.utils.NotificationDeserializer;
import com.trx.habitmeta.utils.OwnedItemListDeserializer;
import com.trx.habitmeta.utils.OwnedMountListDeserializer;
import com.trx.habitmeta.utils.OwnedPetListDeserializer;
import com.trx.habitmeta.utils.PurchasedDeserializer;
import com.trx.habitmeta.utils.QuestCollectDeserializer;
import com.trx.habitmeta.utils.QuestDeserializer;
import com.trx.habitmeta.utils.QuestDropItemsListSerialization;
import com.trx.habitmeta.utils.SkillDeserializer;
import com.trx.habitmeta.utils.SocialAuthenticationDeserializer;
import com.trx.habitmeta.utils.TaskListDeserializer;
import com.trx.habitmeta.utils.TaskSerializer;
import com.trx.habitmeta.utils.TaskTagDeserializer;
import com.trx.habitmeta.utils.TutorialStepListDeserializer;
import com.trx.habitmeta.utils.UserDeserializer;
import com.trx.habitmeta.utils.WorldStateSerialization;
import com.trx.habitmeta.common.models.Notification;
import com.trx.habitmeta.shared.models.responses.FeedResponse;

import java.lang.reflect.Type;
import java.util.Date;
import java.util.List;

import io.realm.RealmList;
import retrofit2.converter.gson.GsonConverterFactory;

public class GSonFactoryCreator {

    public static Gson createGson() {
        Type skillListType = new TypeToken<List<Skill>>() {
        }.getType();
        Type taskTagClassListType = new TypeToken<RealmList<Tag>>() {
        }.getType();
        Type customizationListType = new TypeToken<RealmList<Customization>>() {
        }.getType();
        Type tutorialStepListType = new TypeToken<RealmList<TutorialStep>>() {
        }.getType();
        Type faqArticleListType = new TypeToken<RealmList<FAQArticle>>() {
        }.getType();
        Type itemDataListType = new TypeToken<RealmList<Equipment>>() {
        }.getType();
        Type questCollectListType = new TypeToken<RealmList<QuestCollect>>() {
        }.getType();
        Type chatMessageListType = new TypeToken<RealmList<ChatMessage>>() {
        }.getType();
        Type challengeListType = new TypeToken<List<Challenge>>() {
        }.getType();
        Type challengeRealmListType = new TypeToken<RealmList<Challenge>>() {
        }.getType();
        Type questDropItemListType = new TypeToken<RealmList<QuestDropItem>>() {
        }.getType();
        Type ownedItemListType = new TypeToken<RealmList<OwnedItem>>() {
        }.getType();
        Type ownedPetListType = new TypeToken<RealmList<OwnedPet>>() {
        }.getType();
        Type ownedMountListType = new TypeToken<RealmList<OwnedMount>>() {
        }.getType();
        Type achievementsListType = new TypeToken<List<Achievement>>() {
        }.getType();
        Type assignedDetailsListType = new TypeToken<RealmList<GroupAssignedDetails>>() {
        }.getType();

        return new GsonBuilder()
                .registerTypeAdapter(taskTagClassListType, new TaskTagDeserializer())
                .registerTypeAdapter(Boolean.class, new BooleanAsIntAdapter())
                .registerTypeAdapter(boolean.class, new BooleanAsIntAdapter())
                .registerTypeAdapter(skillListType, new SkillDeserializer())
                .registerTypeAdapter(TaskList.class, new TaskListDeserializer())
                .registerTypeAdapter(Purchases.class, new PurchasedDeserializer())
                .registerTypeAdapter(customizationListType, new CustomizationDeserializer())
                .registerTypeAdapter(tutorialStepListType, new TutorialStepListDeserializer())
                .registerTypeAdapter(faqArticleListType, new FAQArticleListDeserilializer())
                .registerTypeAdapter(Group.class, new GroupSerialization())
                .registerTypeAdapter(Date.class, new DateDeserializer())
                .registerTypeAdapter(itemDataListType, new EquipmentListDeserializer())
                .registerTypeAdapter(ChatMessage.class, new ChatMessageDeserializer())
                .registerTypeAdapter(Task.class, new TaskSerializer())
                .registerTypeAdapter(ContentResult.class, new ContentDeserializer())
                .registerTypeAdapter(FeedResponse.class, new FeedResponseDeserializer())
                .registerTypeAdapter(Challenge.class, new ChallengeDeserializer())
                .registerTypeAdapter(User.class, new UserDeserializer())
                .registerTypeAdapter(questCollectListType, new QuestCollectDeserializer())
                .registerTypeAdapter(challengeListType, new ChallengeListDeserializer())
                .registerTypeAdapter(challengeRealmListType, new ChallengeListDeserializer())
                .registerTypeAdapter(questDropItemListType, new QuestDropItemsListSerialization())
                .registerTypeAdapter(ownedItemListType, new OwnedItemListDeserializer())
                .registerTypeAdapter(ownedPetListType, new OwnedPetListDeserializer())
                .registerTypeAdapter(ownedMountListType, new OwnedMountListDeserializer())
                .registerTypeAdapter(achievementsListType, new AchievementListDeserializer())
                .registerTypeAdapter(assignedDetailsListType, new AssignedDetailsDeserializer())
                .registerTypeAdapter(Quest.class, new QuestDeserializer())
                .registerTypeAdapter(Member.class, new MemberSerialization())
                .registerTypeAdapter(InviteResponse.class, new InviteResponseDeserializer())
                .registerTypeAdapter(WorldState.class, new WorldStateSerialization())
                .registerTypeAdapter(FindUsernameResult.class, new FindUsernameResultDeserializer())
                .registerTypeAdapter(Notification.class, new NotificationDeserializer())
                .registerTypeAdapter(SocialAuthentication.class, new SocialAuthenticationDeserializer())
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
                .serializeNulls()
                .setLenient()
                .create();
    }

    public static GsonConverterFactory create() {
        return GsonConverterFactory.create(createGson());
    }
}
