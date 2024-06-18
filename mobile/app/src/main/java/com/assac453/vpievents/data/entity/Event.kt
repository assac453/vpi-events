package com.assac453.vpievents.data.entity

data class Event(
    var name        : String?    = null,
    var address     : String?    = null,
    var longitude   : Double?    = null,
    var latitude    : Double?    = null,
    var description : String?    = null,
    var points      : Int?       = null,
    var startDate   : String?    = null,
    var endDate     : String?    = null,
    var image       : String?    = null,
    var eventType   : EventType? = EventType()
)
