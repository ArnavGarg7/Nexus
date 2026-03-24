package com.nexus.app.domain.usecase.pathfinder

import com.nexus.app.domain.model.ReadingPath
import com.nexus.app.domain.repository.ReadingPathRepository
import javax.inject.Inject

class GetPathfinderResultsUseCase @Inject constructor(
    private val repository: ReadingPathRepository
) {
    suspend operator fun invoke(tags: List<String>): List<ReadingPath> =
        repository.getPathfinderPaths(tags)
}
