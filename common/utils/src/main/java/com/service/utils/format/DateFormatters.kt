package com.service.utils.format

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.Locale

private val IsoLocalDateTime: DateTimeFormatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME
private val IsoLocalDate: DateTimeFormatter = DateTimeFormatter.ISO_LOCAL_DATE
private val HourFmt: DateTimeFormatter = DateTimeFormatter.ofPattern("ha", Locale.ENGLISH)
private val MonthShort: DateTimeFormatter = DateTimeFormatter.ofPattern("MMM d", Locale.ENGLISH)
private val MonthDay: DateTimeFormatter = DateTimeFormatter.ofPattern("MMM d", Locale.ENGLISH)

fun parseHour(iso: String): LocalDateTime? = runCatching {
    LocalDateTime.parse(iso, IsoLocalDateTime)
}.getOrNull()

fun parseDate(iso: String): LocalDate? = runCatching {
    LocalDate.parse(iso, IsoLocalDate)
}.getOrNull()

fun formatHourLabel(iso: String, isNow: Boolean = false): String {
    if (isNow) return "Now"
    val dt = parseHour(iso) ?: return ""
    return HourFmt.format(dt).uppercase(Locale.ENGLISH)
}

fun formatDayShort(iso: String): String {
    val d = parseDate(iso) ?: return ""
    return d.dayOfWeek.getDisplayName(TextStyle.FULL, Locale.ENGLISH)
}

fun formatDayDate(iso: String): String {
    val d = parseDate(iso) ?: return ""
    return MonthDay.format(d).uppercase(Locale.ENGLISH)
}

fun formatRange(startIso: String?, endIso: String?): String {
    val s = startIso?.let(::parseDate) ?: return ""
    val e = endIso?.let(::parseDate) ?: return MonthShort.format(s)
    return "${MonthShort.format(s)} - ${MonthShort.format(e)}"
}
