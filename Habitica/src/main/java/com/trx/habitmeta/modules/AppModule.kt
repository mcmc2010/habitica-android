package com.trx.habitmeta.modules

import android.content.Context
import android.content.SharedPreferences
import android.content.res.Resources
import androidx.preference.PreferenceManager
import com.trx.habitmeta.BuildConfig
import com.trx.habitmeta.data.ApiClient
import com.trx.habitmeta.data.ContentRepository
import com.trx.habitmeta.helpers.AppConfigManager
import com.trx.habitmeta.helpers.ReviewManager
import com.trx.habitmeta.helpers.SoundFileLoader
import com.trx.habitmeta.helpers.notifications.PushNotificationManager
import com.trx.habitmeta.common.helpers.KeyHelper
import com.trx.habitmeta.common.helpers.KeyHelper.Companion.getInstance
import com.trx.habitmeta.shared.HLogger
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import java.io.IOException
import java.security.KeyStore
import java.security.KeyStoreException
import java.security.NoSuchAlgorithmException
import java.security.cert.CertificateException
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class AppModule {
    @Provides
    @Singleton
    fun provideSharedPreferences(
        @ApplicationContext context: Context
    ): SharedPreferences {
        return PreferenceManager.getDefaultSharedPreferences(context)
    }

    @Provides
    fun provideKeyStore(): KeyStore? {
        try {
            val keyStore = KeyStore.getInstance("AndroidKeyStore")
            keyStore.load(null)
            return keyStore
        } catch (e: KeyStoreException) {
            HLogger.logException("KeyHelper", "Error initializing", e)
        } catch (e: CertificateException) {
            HLogger.logException("KeyHelper", "Error initializing", e)
        } catch (e: NoSuchAlgorithmException) {
            HLogger.logException("KeyHelper", "Error initializing", e)
        } catch (e: IOException) {
            HLogger.logException("KeyHelper", "Error initializing", e)
        }
        return null
    }

    @Provides
    fun provideKeyHelper(
        @ApplicationContext context: Context,
        sharedPreferences: SharedPreferences,
        keyStore: KeyStore?
    ): KeyHelper? {
        return if (keyStore == null) {
            null
        } else {
            getInstance(context, sharedPreferences, keyStore)
        }
    }

    @Provides
    @Singleton
    fun providesAuthenticationHandler(sharedPreferences: SharedPreferences): AuthenticationHandler {
        return if (BuildConfig.DEBUG && BuildConfig.TEST_USER_ID.isNotEmpty()) {
            AuthenticationHandler(BuildConfig.TEST_USER_ID)
        } else {
            AuthenticationHandler(sharedPreferences)
        }
    }

    @Provides
    fun providesResources(
        @ApplicationContext context: Context
    ): Resources {
        return context.resources
    }

    @Provides
    fun providesSoundFileLoader(
        @ApplicationContext context: Context
    ): SoundFileLoader {
        return SoundFileLoader(context)
    }

    @Provides
    @Singleton
    fun pushNotificationManager(
        apiClient: ApiClient,
        sharedPreferences: SharedPreferences,
        @ApplicationContext context: Context
    ): PushNotificationManager {
        return PushNotificationManager(apiClient, sharedPreferences, context)
    }

    @Provides
    @Singleton
    fun providesRemoteConfigManager(contentRepository: ContentRepository): AppConfigManager {
        return AppConfigManager(contentRepository)
    }

    @Provides
    fun providesReviewManager(
        @ApplicationContext context: Context,
        configManager: AppConfigManager
    ): ReviewManager {
        return ReviewManager(context, configManager)
    }
}
