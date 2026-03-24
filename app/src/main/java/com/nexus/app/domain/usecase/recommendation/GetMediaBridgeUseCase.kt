package com.nexus.app.domain.usecase.recommendation

import com.nexus.app.domain.model.MediaItem
import com.nexus.app.domain.repository.MediaRepository
import javax.inject.Inject

class GetMediaBridgeUseCase @Inject constructor(
    private val repository: MediaRepository
) {
    suspend operator fun invoke(comicId: String): List<MediaItem> =
        repository.getMediaForComic(comicId)
}
