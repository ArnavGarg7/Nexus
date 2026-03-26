package com.nexus.app.core.data.repository

import com.nexus.app.core.data.local.MockDataProvider
import com.nexus.app.domain.model.Publisher
import com.nexus.app.domain.model.StoryArc
import com.nexus.app.domain.repository.EventRepository
import javax.inject.Inject

class EventRepositoryImpl @Inject constructor(
    private val mockData: MockDataProvider
) : EventRepository {

    override suspend fun getAllEvents(): List<StoryArc> = mockData.events

    override suspend fun getEventById(id: String): StoryArc? = mockData.getEventById(id)

    override suspend fun getEventsForPublisher(publisher: Publisher): List<StoryArc> =
        mockData.getEventsForPublisher(publisher)
}
