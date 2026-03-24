package com.nexus.app.domain.repository

import com.nexus.app.domain.model.Publisher
import com.nexus.app.domain.model.StoryArc

interface EventRepository {
    suspend fun getAllEvents(): List<StoryArc>
    suspend fun getEventById(id: String): StoryArc?
    suspend fun getEventsForPublisher(publisher: Publisher): List<StoryArc>
}
