package com.trx.habitmeta.modules

import android.content.Context
import com.trx.habitmeta.data.ApiClient
import com.trx.habitmeta.data.ChallengeRepository
import com.trx.habitmeta.data.CustomizationRepository
import com.trx.habitmeta.data.FAQRepository
import com.trx.habitmeta.data.InventoryRepository
import com.trx.habitmeta.data.SetupCustomizationRepository
import com.trx.habitmeta.data.SocialRepository
import com.trx.habitmeta.data.TagRepository
import com.trx.habitmeta.data.TaskRepository
import com.trx.habitmeta.data.TutorialRepository
import com.trx.habitmeta.data.UserRepository
import com.trx.habitmeta.data.implementation.ChallengeRepositoryImpl
import com.trx.habitmeta.data.implementation.CustomizationRepositoryImpl
import com.trx.habitmeta.data.implementation.FAQRepositoryImpl
import com.trx.habitmeta.data.implementation.InventoryRepositoryImpl
import com.trx.habitmeta.data.implementation.SetupCustomizationRepositoryImpl
import com.trx.habitmeta.data.implementation.SocialRepositoryImpl
import com.trx.habitmeta.data.implementation.TagRepositoryImpl
import com.trx.habitmeta.data.implementation.TaskRepositoryImpl
import com.trx.habitmeta.data.implementation.TutorialRepositoryImpl
import com.trx.habitmeta.data.implementation.UserRepositoryImpl
import com.trx.habitmeta.data.local.ChallengeLocalRepository
import com.trx.habitmeta.data.local.CustomizationLocalRepository
import com.trx.habitmeta.data.local.FAQLocalRepository
import com.trx.habitmeta.data.local.InventoryLocalRepository
import com.trx.habitmeta.data.local.SocialLocalRepository
import com.trx.habitmeta.data.local.TagLocalRepository
import com.trx.habitmeta.data.local.TaskLocalRepository
import com.trx.habitmeta.data.local.TutorialLocalRepository
import com.trx.habitmeta.data.local.UserLocalRepository
import com.trx.habitmeta.data.local.implementation.RealmChallengeLocalRepository
import com.trx.habitmeta.data.local.implementation.RealmCustomizationLocalRepository
import com.trx.habitmeta.data.local.implementation.RealmFAQLocalRepository
import com.trx.habitmeta.data.local.implementation.RealmInventoryLocalRepository
import com.trx.habitmeta.data.local.implementation.RealmSocialLocalRepository
import com.trx.habitmeta.data.local.implementation.RealmTagLocalRepository
import com.trx.habitmeta.data.local.implementation.RealmTaskLocalRepository
import com.trx.habitmeta.data.local.implementation.RealmTutorialLocalRepository
import com.trx.habitmeta.data.local.implementation.RealmUserLocalRepository
import com.trx.habitmeta.helpers.AppConfigManager
import com.trx.habitmeta.helpers.PurchaseHandler
import com.trx.habitmeta.ui.viewmodels.MainUserViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.realm.Realm
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class UserRepositoryModule {
    @Provides
    fun providesSetupCustomizationRepository(
        @ApplicationContext context: Context
    ): SetupCustomizationRepository {
        return SetupCustomizationRepositoryImpl(context)
    }

    @Provides
    fun providesTaskLocalRepository(realm: Realm): TaskLocalRepository {
        return RealmTaskLocalRepository(realm)
    }

    @Provides
    fun providesTaskRepository(
        localRepository: TaskLocalRepository,
        apiClient: ApiClient,
        authenticationHandler: AuthenticationHandler,
        appConfigManager: AppConfigManager
    ): TaskRepository {
        return TaskRepositoryImpl(
            localRepository,
            apiClient,
            authenticationHandler,
            appConfigManager
        )
    }

    @Provides
    fun providesTagLocalRepository(realm: Realm): TagLocalRepository {
        return RealmTagLocalRepository(realm)
    }

    @Provides
    fun providesTagRepository(
        localRepository: TagLocalRepository,
        apiClient: ApiClient,
        authenticationHandler: AuthenticationHandler
    ): TagRepository {
        return TagRepositoryImpl(localRepository, apiClient, authenticationHandler)
    }

    @Provides
    fun provideChallengeLocalRepository(realm: Realm): ChallengeLocalRepository {
        return RealmChallengeLocalRepository(realm)
    }

    @Provides
    fun providesChallengeRepository(
        localRepository: ChallengeLocalRepository,
        apiClient: ApiClient,
        authenticationHandler: AuthenticationHandler
    ): ChallengeRepository {
        return ChallengeRepositoryImpl(localRepository, apiClient, authenticationHandler)
    }

    @Provides
    fun providesUserLocalRepository(realm: Realm): UserLocalRepository {
        return RealmUserLocalRepository(realm)
    }

    @Provides
    fun providesUserRepository(
        localRepository: UserLocalRepository,
        apiClient: ApiClient,
        authenticationHandler: AuthenticationHandler,
        taskRepository: TaskRepository,
        appConfigManager: AppConfigManager
    ): UserRepository {
        return UserRepositoryImpl(
            localRepository,
            apiClient,
            authenticationHandler,
            taskRepository,
            appConfigManager
        )
    }

    @Provides
    fun providesSocialLocalRepository(realm: Realm): SocialLocalRepository {
        return RealmSocialLocalRepository(realm)
    }

    @Provides
    fun providesSocialRepository(
        localRepository: SocialLocalRepository,
        apiClient: ApiClient,
        authenticationHandler: AuthenticationHandler
    ): SocialRepository {
        return SocialRepositoryImpl(localRepository, apiClient, authenticationHandler)
    }

    @Provides
    fun providesInventoryLocalRepository(
        realm: Realm
    ): InventoryLocalRepository {
        return RealmInventoryLocalRepository(realm)
    }

    @Provides
    fun providesInventoryRepository(
        localRepository: InventoryLocalRepository,
        apiClient: ApiClient,
        authenticationHandler: AuthenticationHandler,
        remoteConfig: AppConfigManager
    ): InventoryRepository {
        return InventoryRepositoryImpl(
            localRepository,
            apiClient,
            authenticationHandler,
            remoteConfig
        )
    }

    @Provides
    fun providesFAQLocalRepository(realm: Realm): FAQLocalRepository {
        return RealmFAQLocalRepository(realm)
    }

    @Provides
    fun providesFAQRepository(
        localRepository: FAQLocalRepository,
        apiClient: ApiClient,
        authenticationHandler: AuthenticationHandler
    ): FAQRepository {
        return FAQRepositoryImpl(localRepository, apiClient, authenticationHandler)
    }

    @Provides
    fun providesTutorialLocalRepository(realm: Realm): TutorialLocalRepository {
        return RealmTutorialLocalRepository(realm)
    }

    @Provides
    fun providesTutorialRepository(
        localRepository: TutorialLocalRepository,
        apiClient: ApiClient,
        authenticationHandler: AuthenticationHandler
    ): TutorialRepository {
        return TutorialRepositoryImpl(localRepository, apiClient, authenticationHandler)
    }

    @Provides
    fun providesCustomizationLocalRepository(realm: Realm): CustomizationLocalRepository {
        return RealmCustomizationLocalRepository(realm)
    }

    @Provides
    fun providesCustomizationRepository(
        localRepository: CustomizationLocalRepository,
        apiClient: ApiClient,
        authenticationHandler: AuthenticationHandler
    ): CustomizationRepository {
        return CustomizationRepositoryImpl(localRepository, apiClient, authenticationHandler)
    }

    @Provides
    @Singleton
    fun providesPurchaseHandler(
        @ApplicationContext context: Context,
        apiClient: ApiClient,
        userViewModel: MainUserViewModel,
        appConfigManager: AppConfigManager
    ): PurchaseHandler {
        return PurchaseHandler(context, apiClient, userViewModel, appConfigManager)
    }
}
