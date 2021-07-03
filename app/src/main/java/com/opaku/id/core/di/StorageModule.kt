package com.opaku.id.core.di

import android.content.Context
import com.opaku.id.core.data.source.local.session.ISessionManager
import com.opaku.id.core.data.source.local.session.SessionManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class StorageModule {
    @Singleton
    @Provides
    fun provideSessionManager(@ApplicationContext context: Context): ISessionManager =
        SessionManager(context)
}