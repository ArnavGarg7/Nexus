package com.nexus.app.core.data.repository

import com.nexus.app.core.data.local.MockDataProvider
import com.nexus.app.core.data.remote.api.NexusApiService
import com.nexus.app.core.data.remote.dto.toDomain
import com.nexus.app.domain.model.MediaItem
import com.nexus.app.domain.model.MediaType
import com.nexus.app.domain.repository.MediaRepository
import javax.inject.Inject

class MediaRepositoryImpl @Inject constructor(
    private val api: NexusApiService,
    private val mockData: MockDataProvider
) : MediaRepository {

    override suspend fun getMediaById(id: String): MediaItem? {
        return try {
            api.getMediaById(id).body()?.toDomain()
        } catch (_: Exception) {
            mockData.getMediaById(id)
        }
    }

    override suspend fun getMediaByType(type: MediaType): List<MediaItem> =
        mockData.mediaItems.filter { it.type == type }

    override suspend fun getComicsForMedia(mediaId: String): List<String> {
        return try {
            api.getComicsForMedia(mediaId).body() ?: mockData.getMediaById(mediaId)?.relatedComicIds ?: emptyList()
        } catch (_: Exception) {
            mockData.getMediaById(mediaId)?.relatedComicIds ?: emptyList()
        }
    }

    override suspend fun getMediaForComic(comicId: String): List<MediaItem> {
        return try {
            api.getMediaForComic(comicId).body()?.map { it.toDomain() } ?: mockData.mediaItems.filter { comicId in it.relatedComicIds }
        } catch (_: Exception) {
            mockData.mediaItems.filter { comicId in it.relatedComicIds }
        }
    }
}
