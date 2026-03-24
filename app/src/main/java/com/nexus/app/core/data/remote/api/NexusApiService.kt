package com.nexus.app.core.data.remote.api

import com.nexus.app.core.data.remote.dto.*
import retrofit2.Response
import retrofit2.http.*

interface NexusApiService {

    // Characters
    @GET("characters")
    suspend fun searchCharacters(@Query("q") query: String): Response<List<CharacterDto>>

    @GET("characters/{id}")
    suspend fun getCharacterById(@Path("id") id: String): Response<CharacterDto>

    @GET("characters/{id}/similar")
    suspend fun getSimilarCharacters(@Path("id") id: String): Response<List<CharacterDto>>

    @GET("characters/featured")
    suspend fun getFeaturedCharacters(): Response<List<CharacterDto>>

    // Reading Paths
    @GET("reading-paths/character/{id}")
    suspend fun getReadingPathForCharacter(
        @Path("id") characterId: String,
        @Query("type") pathType: String
    ): Response<ReadingPathDto>

    @GET("reading-paths/event/{id}")
    suspend fun getReadingPathForEvent(
        @Path("id") eventId: String,
        @Query("type") pathType: String
    ): Response<ReadingPathDto>

    @POST("reading-paths/pathfinder")
    suspend fun getPathfinderPaths(@Body tags: List<String>): Response<List<ReadingPathDto>>

    // Events
    @GET("events")
    suspend fun getAllEvents(): Response<List<StoryArcDto>>

    @GET("events/{id}")
    suspend fun getEventById(@Path("id") id: String): Response<StoryArcDto>

    // Media
    @GET("media/{id}")
    suspend fun getMediaById(@Path("id") id: String): Response<MediaItemDto>

    @GET("media/{id}/comics")
    suspend fun getComicsForMedia(@Path("id") id: String): Response<List<String>>

    @GET("comics/{id}/media")
    suspend fun getMediaForComic(@Path("id") comicId: String): Response<List<MediaItemDto>>

    // Lore
    @GET("lore/character/{id}")
    suspend fun getLoreForCharacter(@Path("id") characterId: String): Response<List<LoreCardDto>>

    // Quizzes
    @GET("quizzes/{id}")
    suspend fun getQuizById(@Path("id") id: String): Response<QuizDto>

    @GET("quizzes/category/{category}")
    suspend fun getQuizzesByCategory(@Path("category") category: String): Response<List<QuizDto>>

    @GET("quizzes/recommended")
    suspend fun getRecommendedQuizzes(@Query("userId") userId: String): Response<List<QuizDto>>

    @POST("quizzes/generate")
    suspend fun generateDynamicQuiz(@Body request: GenerateQuizRequest): Response<QuizDto>

    @POST("quizzes/{id}/result")
    suspend fun submitQuizResult(@Path("id") quizId: String, @Body result: QuizResultRequest): Response<Unit>
}
