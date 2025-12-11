package com.trx.habitmeta.modules

import android.content.Context
import com.trx.habitmeta.data.ApiClient
import com.trx.habitmeta.data.ContentRepository
import com.trx.habitmeta.data.implementation.ContentRepositoryImpl
import com.trx.habitmeta.data.local.ContentLocalRepository
import com.trx.habitmeta.data.local.implementation.RealmContentLocalRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.realm.Realm

@InstallIn(SingletonComponent::class)
@Module
open class RepositoryModule {
    @Provides
    open fun providesRealm(): Realm {
        return Realm.getDefaultInstance()
    }

    @Provides
    fun providesContentLocalRepository(realm: Realm): ContentLocalRepository {
        return RealmContentLocalRepository(realm)
    }

    @Provides
    fun providesContentRepository(
        contentLocalRepository: ContentLocalRepository,
        apiClient: ApiClient,
        @ApplicationContext context: Context,
        authenticationHandler: AuthenticationHandler
    ): ContentRepository {
        return ContentRepositoryImpl(
            contentLocalRepository,
            apiClient,
            context,
            authenticationHandler
        )
    }
}
