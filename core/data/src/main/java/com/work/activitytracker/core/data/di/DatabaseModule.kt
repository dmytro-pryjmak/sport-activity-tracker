package com.work.activitytracker.core.data.di

import android.content.Context
import androidx.room.Room
import com.work.activitytracker.core.data.local.SportRecordDatabase
import com.work.activitytracker.core.data.local.dao.SportRecordDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): SportRecordDatabase =
        Room.databaseBuilder(
            context,
            SportRecordDatabase::class.java,
            "sport_records.db",
        ).build()

    @Provides
    fun provideSportRecordDao(db: SportRecordDatabase): SportRecordDao = db.sportRecordDao()
}
