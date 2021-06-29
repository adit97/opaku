package com.opaku.id.di

import com.opaku.id.core.domain.usecase.AppInteractor
import com.opaku.id.core.domain.usecase.AppUseCase
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
abstract class AppModule {

    @Binds
    @ViewModelScoped
    abstract fun provideTourismUseCase(appInteractor: AppInteractor): AppUseCase

}