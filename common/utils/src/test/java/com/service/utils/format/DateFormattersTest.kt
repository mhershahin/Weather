package com.service.utils.format

import org.junit.Assert.assertEquals
import org.junit.Test

class DateFormattersTest {

    @Test
    fun `formatHourLabel returns Now when isNow flag set`() {
        assertEquals("Now", formatHourLabel(iso = "2024-06-03T15:00", isNow = true))
    }

    @Test
    fun `formatHourLabel formats 3pm as 3PM`() {
        assertEquals("3PM", formatHourLabel(iso = "2024-06-03T15:00"))
    }

    @Test
    fun `formatHourLabel returns empty for unparseable input`() {
        assertEquals("", formatHourLabel(iso = "not-a-date"))
    }

    @Test
    fun `formatDayShort returns full English weekday`() {
        assertEquals("Monday", formatDayShort(iso = "2024-06-03"))
    }

    @Test
    fun `formatDayShort returns empty for bad input`() {
        assertEquals("", formatDayShort(iso = "nope"))
    }

    @Test
    fun `formatRange joins two valid dates`() {
        assertEquals("JUN 3 - JUN 9", formatRange(startIso = "2024-06-03", endIso = "2024-06-09").uppercase())
    }

    @Test
    fun `formatRange returns single date when end is null`() {
        assertEquals("JUN 3", formatRange(startIso = "2024-06-03", endIso = null).uppercase())
    }

    @Test
    fun `formatRange returns empty when start null`() {
        assertEquals("", formatRange(startIso = null, endIso = "2024-06-09"))
    }
}
