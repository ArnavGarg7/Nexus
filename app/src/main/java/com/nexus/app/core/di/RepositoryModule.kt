package com.nexus.app.core.di

import com.nexus.app.core.data.repository.*
import com.nexus.app.domain.repository.*
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindCharacterRepository(impl: CharacterRepositoryImpl): CharacterRepository

    @Binds
    abstract fun bindReadingPathRepository(impl: ReadingPathRepositoryImpl): ReadingPathRepository

    @Binds
    abstract fun bindQuizRepository(impl: QuizRepositoryImpl): QuizRepository

    @Binds
    abstract fun bindMediaRepository(impl: MediaRepositoryImpl): MediaRepository

    @Binds
    abstract fun bindLoreRepository(impl: LoreRepositoryImpl): LoreRepository

    @Binds
    abstract fun bindEventRepository(impl: EventRepositoryImpl): EventRepository
}
