package com.work.activitytracker.core.data.di

import com.work.activitytracker.core.data.repository.SportRecordRepositoryImpl
import com.work.activitytracker.core.domain.repository.SportRecordRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindSportRecordRepository(impl: SportRecordRepositoryImpl): SportRecordRepository
}
