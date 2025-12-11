package com.trx.habitmeta.wearos.modules

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import com.trx.habitmeta.BuildConfig
import com.trx.habitmeta.common.api.HostConfig
import com.trx.habitmeta.common.helpers.AppTestingLevel
import com.trx.habitmeta.common.helpers.KeyHelper
import com.trx.habitmeta.shared.HLogger
import com.trx.habitmeta.wearos.data.ApiClient
import com.trx.habitmeta.wearos.managers.AppStateManager
import com.trx.habitmeta.wearos.models.tasks.WrappedTasklistAdapter
import com.trx.habitmeta.wearos.util.AttributeAdapter
import com.trx.habitmeta.wearos.util.FrequencyAdapter
import com.trx.habitmeta.wearos.util.TaskTypeAdapter
import com.trx.habitmeta.wearos.util.customDateAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Converter
import retrofit2.converter.moshi.MoshiConverterFactory
import java.io.IOException
import java.security.KeyStore
import java.security.KeyStoreException
import java.security.NoSuchAlgorithmException
import java.security.cert.CertificateException
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
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
    fun providesConverterFactory(moshi: Moshi): Converter.Factory {
        return MoshiConverterFactory.create(
            moshi
        ).asLenient()
    }

    @Provides
    fun providesMoshi(): Moshi {
        return Moshi.Builder()
            .add(WrappedTasklistAdapter())
            .add(customDateAdapter)
            .add(FrequencyAdapter())
            .add(TaskTypeAdapter())
            .add(AttributeAdapter())
            .addLast(KotlinJsonAdapterFactory())
            .build()
    }

    @Provides
    @Singleton
    fun providesApiHClient(
        hostConfig: HostConfig,
        @ApplicationContext context: Context,
        converter: Converter.Factory,
        appStateManager: AppStateManager
    ): ApiClient {
        return ApiClient(
            converter,
            hostConfig,
            appStateManager,
            context
        )
    }

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
            KeyHelper.getInstance(context, sharedPreferences, keyStore)
        }
    }

    @Provides
    @Singleton
    fun provideTestingLevel(): AppTestingLevel {
        return AppTestingLevel.valueOf(BuildConfig.TESTING_LEVEL.uppercase())
    }
}
