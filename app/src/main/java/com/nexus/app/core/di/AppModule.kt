package com.nexus.app.core.di

import android.content.Context
import androidx.room.Room
import com.nexus.app.BuildConfig
import com.nexus.app.core.data.local.NexusDatabase
import com.nexus.app.core.data.local.dao.CharacterDao
import com.nexus.app.core.data.local.dao.QuizDao
import com.nexus.app.core.data.local.dao.ReadingListDao
import com.nexus.app.core.data.remote.api.NexusApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        val logging = HttpLoggingInterceptor().apply {
            level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY
                    else HttpLoggingInterceptor.Level.NONE
        }
        return OkHttpClient.Builder()
            .addInterceptor(logging)
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .build()
    }

    @Provides @Singleton
    fun provideRetrofit(client: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Provides @Singleton
    fun provideNexusApiService(retrofit: Retrofit): NexusApiService =
        retrofit.create(NexusApiService::class.java)

    @Provides @Singleton
    fun provideNexusDatabase(@ApplicationContext context: Context): NexusDatabase =
        Room.databaseBuilder(context, NexusDatabase::class.java, NexusDatabase.DATABASE_NAME)
            .fallbackToDestructiveMigration()
            .build()

    @Provides fun provideCharacterDao(db: NexusDatabase): CharacterDao = db.characterDao()
    @Provides fun provideQuizDao(db: NexusDatabase): QuizDao = db.quizDao()
    @Provides fun provideReadingListDao(db: NexusDatabase): ReadingListDao = db.readingListDao()
}
