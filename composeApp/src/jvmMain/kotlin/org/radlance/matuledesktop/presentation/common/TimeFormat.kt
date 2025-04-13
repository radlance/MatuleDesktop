package org.radlance.matuledesktop.presentation.common

import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.toJavaLocalDateTime
import java.time.ZoneId
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

abstract class TimeFormat {

    protected fun formatUtcTimestampToLocal(timestamp: LocalDateTime, zoneId: ZoneId): String {
        val utcZoned = timestamp.toJavaLocalDateTime().atZone(ZoneOffset.UTC)
        val localZoned = utcZoned.withZoneSameInstant(zoneId)
        val formatter = DateTimeFormatter.ofPattern("d MMMM yyyy, HH:mm")
        return localZoned.format(formatter)
    }
}