package com.nexus.app.core.di

import android.content.Context
import androidx.room.Room
import com.nexus.app.BuildConfig
import com.nexus.app.core.data.local.NexusDatabase
import com.nexus.app.core.data.local.dao.CharacterDao
import com.nexus.app.core.data.local.dao.QuizDao
import com.nexus.app.core.data.local.dao.ReadingListDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides @Singleton
    fun provideNexusDatabase(@ApplicationContext context: Context): NexusDatabase =
        Room.databaseBuilder(context, NexusDatabase::class.java, NexusDatabase.DATABASE_NAME)
            .fallbackToDestructiveMigration()
            .build()

    @Provides fun provideCharacterDao(db: NexusDatabase): CharacterDao = db.characterDao()
    @Provides fun provideQuizDao(db: NexusDatabase): QuizDao = db.quizDao()
    @Provides fun provideReadingListDao(db: NexusDatabase): ReadingListDao = db.readingListDao()
}
