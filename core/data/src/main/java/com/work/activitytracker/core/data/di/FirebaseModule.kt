package com.work.activitytracker.core.data.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestoreSettings
import com.google.firebase.firestore.memoryCacheSettings
import com.google.firebase.firestore.persistentCacheSettings
import com.work.activitytracker.core.data.session.FirebaseUserSession
import com.work.activitytracker.core.domain.session.UserSession
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal abstract class FirebaseModule {

    @Binds
    @Singleton
    abstract fun bindUserSession(impl: FirebaseUserSession): UserSession

    companion object {
        @Provides
        @Singleton
        fun provideFirebaseAuth(): FirebaseAuth = FirebaseAuth.getInstance()

        @Provides
        @Singleton
        fun provideFirestore(): FirebaseFirestore = FirebaseFirestore.getInstance().apply {
            firestoreSettings = firestoreSettings {
                setLocalCacheSettings(persistentCacheSettings {})
            }
        }
    }
}
