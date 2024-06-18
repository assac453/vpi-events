package com.assac453.vpievents.data.repository

import android.util.Log
import com.assac453.vpievents.data.entity.Event
import com.assac453.vpievents.data.entity.EventType
import com.assac453.vpievents.logic.Constants
import com.assac453.vpievents.network.service.EventService

interface EventRepository {
    suspend fun getEvents(eventType: String): List<Event>
}

class NetworkEventRepository(
    private val eventService: EventService
) : EventRepository {
    override suspend fun getEvents(eventType: String): List<Event> {
        return eventService.events(eventType).map { item ->
            item!!.name?.let { Log.d("DOWNLOAD", it) }
            Event(
                name = item.name,
                address = item.address,
                longitude = item.longitude,
                latitude = item.latitude,
                description = item.description,
                points = item.points,
                startDate = item.begin,
                endDate = item.end,
                image = "http://${Constants.SERVER_ADDRESS}:${Constants.PORT}/images/${item.image}",
                eventType = EventType(item.eventType?.name),
            )
        }
        /*return MockEvents().getEvents(eventType)*/
    }
}
/*

class MockEvents() : EventRepository {
    override suspend fun getEvents(type: String): List<Event> {
        val events = mutableListOf(
            Event(
                name = "День открытых дверей",
                address = "ул. Ленина 72",
                longitude = 44.11,
                latitude = 44.11,
                description = "Приходите на день открытых дверей ВПИ, Вас ждёт увлекательное мероприятие",
                points = 1,
                startDate = "2024-05-04T18:00:00+03:00",
                endDate = "2024-05-04T19:00:00+03:00",
                image = "https://volpi.ru/files/about/vpi_logo_rgb_ou.jpg",
                eventType = EventType("День открытых дверей"),
            ),
            Event(
                name = "День открытых дверей",
                address = "ул. Ленина 72",
                longitude = 44.11,
                latitude = 44.11,
                description = "Приходите на день открытых дверей ВПИ, Вас ждёт увлекательное мероприятие",
                points = 1,
                startDate = "2024-05-04T18:00:00+03:00",
                endDate = "2024-05-04T19:00:00+03:00",
                image = "https://volpi.ru/files/about/vpi_logo_rgb_ou.jpg",
                eventType = EventType("День открытых дверей"),
            ),
            Event(
                name = "День открытых дверей",
                address = "ул. Ленина 72",
                longitude = 44.11,
                latitude = 44.11,
                description = "Приходите на день открытых дверей ВПИ, Вас ждёт увлекательное мероприятие",
                points = 1,
                startDate = "2024-05-04T18:00:00+03:00",
                endDate = "2024-05-04T19:00:00+03:00",
                image = "https://volpi.ru/files/about/vpi_logo_rgb_ou.jpg",
                eventType = EventType("День открытых дверей"),
            ),
            Event(
                name = "День открытых дверей",
                address = "ул. Ленина 72",
                longitude = 44.11,
                latitude = 44.11,
                description = "Приходите на день открытых дверей ВПИ, Вас ждёт увлекательное мероприятие",
                points = 1,
                startDate = "2024-05-04T18:00:00+03:00",
                endDate = "2024-05-04T19:00:00+03:00",
                image = "https://volpi.ru/files/about/vpi_logo_rgb_ou.jpg",
                eventType = EventType("День открытых дверей"),
            ),
            Event(
                name = "День открытых дверей",
                address = "ул. Ленина 72",
                longitude = 44.11,
                latitude = 44.11,
                description = "Приходите на день открытых дверей ВПИ, Вас ждёт увлекательное мероприятие",
                points = 1,
                startDate = "2024-05-04T18:00:00+03:00",
                endDate = "2024-05-04T19:00:00+03:00",
                image = "https://volpi.ru/files/about/vpi_logo_rgb_ou.jpg",
                eventType = EventType("День открытых дверей"),
            ),
        )
        return events
    }
}
*/
