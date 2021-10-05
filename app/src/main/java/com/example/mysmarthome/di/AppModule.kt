package com.example.mysmarthome.di

import android.content.Context
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.example.data.database.AppDatabase
import com.example.data.net.repository.DeviceDataRepository
import com.example.data.net.repository.UserDataRepository
import com.example.data.shared_preference.BaseEncryptedStorageSource
import com.example.domain.model.shared_preference.BaseCache
import com.example.domain.model.ui.UserUiModel
import com.example.mysmarthome.BuildConfig
import com.google.gson.reflect.TypeToken
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun providesDeviceDataRepository(
        @ApplicationContext context: Context,
        appDatabase: AppDatabase
    ): DeviceDataRepository {
        return DeviceDataRepository(context, appDatabase.smartDeviceDao())
    }

    @Provides
    @Singleton
    fun provideUserDataRepository(
        @ApplicationContext context: Context,
        encryptedSharedPreferences: EncryptedSharedPreferences
    ): UserDataRepository {
        return UserDataRepository(
            context = context,
            encryptedStorageSource = BaseEncryptedStorageSource(
                encryptedSharedPreferences,
                UserUiModel::class.java.toString(),
                object : TypeToken<UserUiModel>() {}.type
            )
        )
    }

    @Provides
    @Singleton
    fun provideRoomDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            AppDatabase::class.java,
            "app_database"
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideEncryptedSharedPreference(@ApplicationContext context: Context): EncryptedSharedPreferences {
        val spec = KeyGenParameterSpec.Builder(
            MasterKey.DEFAULT_MASTER_KEY_ALIAS,
            KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT
        )
            .setBlockModes(KeyProperties.BLOCK_MODE_GCM)
            .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_NONE)
            .setKeySize(256)
            .build()

        val masterKey = MasterKey.Builder(context)
            .setKeyGenParameterSpec(spec)
            .build()

        return EncryptedSharedPreferences.create(
            context,
            BuildConfig.APPLICATION_ID,
            masterKey,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        ) as EncryptedSharedPreferences
    }
}