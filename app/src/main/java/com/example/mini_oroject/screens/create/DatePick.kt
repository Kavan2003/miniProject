package com.example.mini_oroject.screens.create

import android.app.DatePickerDialog
import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import java.text.SimpleDateFormat
import java.util.Calendar

private var dateFormat = "yyyy-MM-dd"
var dateOfBirth by mutableStateOf("")
fun showDatePickerDialog(context: Context, onDatePicked: (String) -> Unit) {
    val calendar = getCalendar()

    DatePickerDialog(
        context, { _, year, month, day ->
            dateOfBirth = getPickedDateAsString(year, month, day)
            onDatePicked(getPickedDateAsString(year, month, day))
            
        },
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    )
        .show()

}

private fun getCalendar(): Calendar {
    return if (dateOfBirth.isEmpty())
        Calendar.getInstance()
    else
        getLastPickedDateCalendar()
}


private fun getLastPickedDateCalendar(): Calendar {
    val dateFormat = SimpleDateFormat(dateFormat)
    val calendar = Calendar.getInstance()

    calendar.time = dateFormat.parse(dateOfBirth)
    return calendar
}

private fun getPickedDateAsString(year: Int, month: Int, day: Int): String {
    val calendar = Calendar.getInstance()
    calendar.set(year, month, day)
    val dateFormat = SimpleDateFormat(dateFormat)
    return dateFormat.format(calendar.time)
}