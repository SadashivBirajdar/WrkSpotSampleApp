package com.wrkspot.sampleapp.utils

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.TimeZone

object Utility {

    /**
     * convert java Date to TimeZone format
     */
    fun formatDateToString(
        date: Date?, format: String?,
        timeZone: String?
    ): String? {
        var tz = timeZone
        if (date == null) return null
        // create SimpleDateFormat object with input format
        val sdf = SimpleDateFormat(format)
        // default system timezone if passed null or empty
        if (tz == null || "".equals(tz.trim { it <= ' ' }, ignoreCase = true)) {
            tz = Calendar.getInstance().timeZone.id
        }
        // set timezone to SimpleDateFormat
        sdf.timeZone = TimeZone.getTimeZone(tz)
        // return Date in required format with timezone as String
        return sdf.format(date)
    }
}