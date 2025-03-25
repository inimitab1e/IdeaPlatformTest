package com.inimitable.ideaplatformtest.core

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object DateFormatter {
    fun getDate(timestamp: Int): String {
        val date = Date(timestamp.toLong() * 1000)
        val format = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
        return  format.format(date)
    }
}