package com.nexus.app.core.data.repository

import com.nexus.app.core.data.local.MockDataProvider
import com.nexus.app.core.data.remote.api.NexusApiService
import com.nexus.app.core.data.remote.dto.toDomain
import com.nexus.app.domain.model.Publisher
import com.nexus.app.domain.model.StoryArc
import com.nexus.app.domain.repository.EventRepository
import javax.inject.Inject

class EventRepositoryImpl @Inject constructor(
    private val api: NexusApiService,
    private val mockData: MockDataProvider
) : EventRepository {

    override suspend fun getAllEvents(): List<StoryArc> {
        return try {
            api.getAllEvents().body()?.map { it.toDomain() } ?: mockData.events
        } catch (_: Exception) {
            mockData.events
        }
    }

    override suspend fun getEventById(id: String): StoryArc? {
        return try {
            api.getEventById(id).body()?.toDomain()
        } catch (_: Exception) {
            mockData.getEventById(id)
        }
    }

    override suspend fun getEventsForPublisher(publisher: Publisher): List<StoryArc> =
        mockData.getEventsForPublisher(publisher)
}
