package com.nexus.app.domain.repository

import com.nexus.app.domain.model.MediaItem
import com.nexus.app.domain.model.MediaType

interface MediaRepository {
    suspend fun getMediaById(id: String): MediaItem?
    suspend fun getMediaByType(type: MediaType): List<MediaItem>
    suspend fun getComicsForMedia(mediaId: String): List<String>
    suspend fun getMediaForComic(comicId: String): List<MediaItem>
}
