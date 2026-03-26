package com.nexus.app.core.data.repository

import com.nexus.app.core.data.local.MockDataProvider
import com.nexus.app.domain.model.MediaItem
import com.nexus.app.domain.model.MediaType
import com.nexus.app.domain.repository.MediaRepository
import javax.inject.Inject

class MediaRepositoryImpl @Inject constructor(
    private val mockData: MockDataProvider
) : MediaRepository {

    override suspend fun getMediaById(id: String): MediaItem? = mockData.getMediaById(id)

    override suspend fun getMediaByType(type: MediaType): List<MediaItem> =
        mockData.mediaItems.filter { it.type == type }

    override suspend fun getComicsForMedia(mediaId: String): List<String> =
        mockData.getMediaById(mediaId)?.relatedComicIds ?: emptyList()

    override suspend fun getMediaForComic(comicId: String): List<MediaItem> =
        mockData.mediaItems.filter { comicId in it.relatedComicIds }
}
