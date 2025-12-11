package com.trx.habitmeta.modules

import android.content.Context
import android.content.SharedPreferences
import com.trx.habitmeta.api.MaintenanceApiService
import com.trx.habitmeta.data.ApiClient
import com.trx.habitmeta.data.implementation.ApiClientImpl
import com.trx.habitmeta.data.implementation.ApiClientImpl.Companion.createGsonFactory
import com.trx.habitmeta.helpers.MainNotificationsManager
import com.trx.habitmeta.helpers.NotificationsManager
import com.trx.habitmeta.common.api.HostConfig
import com.trx.habitmeta.common.helpers.KeyHelper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.ref.WeakReference
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
open class ApiModule {
    @Provides
    @Singleton
    fun providesHostConfig(
        sharedPreferences: SharedPreferences,
        keyHelper: KeyHelper?,
        @ApplicationContext context: Context
    ): HostConfig {
        return HostConfig(sharedPreferences, keyHelper, context)
    }

    @Provides
    fun providesGsonConverterFactory(): GsonConverterFactory {
        return createGsonFactory()
    }

    @Provides
    @Singleton
    fun providesPopupNotificationsManager(): NotificationsManager {
        return MainNotificationsManager()
    }

    @Provides
    @Singleton
    fun providesApiHelper(
        gsonConverter: GsonConverterFactory,
        hostConfig: HostConfig,
        notificationsManager: NotificationsManager,
        @ApplicationContext context: Context
    ): ApiClient {
        val apiClient =
            ApiClientImpl(
                gsonConverter,
                hostConfig,
                notificationsManager,
                context
            )
        notificationsManager.apiClient = WeakReference(apiClient)
        return apiClient
    }

    @Provides
    fun providesMaintenanceApiService(gsonConverter: GsonConverterFactory): MaintenanceApiService {
        val adapter =
            Retrofit.Builder()
                .baseUrl("https://habitica-assets.s3.amazonaws.com/mobileApp/endpoint/")
                .addConverterFactory(gsonConverter)
                .build()
        return adapter.create(MaintenanceApiService::class.java)
    }
}
