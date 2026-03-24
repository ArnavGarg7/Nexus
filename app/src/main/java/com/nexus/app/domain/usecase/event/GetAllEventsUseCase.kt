package com.nexus.app.domain.usecase.event

import com.nexus.app.domain.model.StoryArc
import com.nexus.app.domain.repository.EventRepository
import javax.inject.Inject

class GetAllEventsUseCase @Inject constructor(
    private val repository: EventRepository
) {
    suspend operator fun invoke(): List<StoryArc> = repository.getAllEvents()
}
