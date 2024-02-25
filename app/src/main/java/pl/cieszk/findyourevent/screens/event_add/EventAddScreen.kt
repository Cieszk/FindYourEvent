package pl.cieszk.findyourevent.screens.event_add

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.maxkeppeker.sheets.core.models.base.rememberUseCaseState
import com.maxkeppeler.sheets.calendar.CalendarDialog
import com.maxkeppeler.sheets.calendar.models.CalendarConfig
import com.maxkeppeler.sheets.calendar.models.CalendarSelection
import pl.cieszk.findyourevent.data.model.Event
import java.time.LocalDate
import java.time.ZoneId
import java.util.Date

@RequiresApi(Build.VERSION_CODES.O)
fun LocalDate.toDate(): Date {
    val instant = this.atStartOfDay(ZoneId.systemDefault()).toInstant()
    return Date.from(instant)
}

@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("RememberReturnType")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EventAddScreen(
    restartApp: (String) -> Unit,
    openScreen: (String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: EventAddViewModel = hiltViewModel()
) {
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var city by remember { mutableStateOf("") }
    var country by remember { mutableStateOf("") }
    var eventDateDisplay by remember { mutableStateOf("") }
    var eventDate by remember { mutableStateOf(Date()) }
    val creationDate = Date()
    val calendarState = rememberUseCaseState()

    CalendarDialog(state = calendarState, config = CalendarConfig(
        monthSelection = true, yearSelection = true
    ), selection = CalendarSelection.Date { date ->
        eventDate = date.toDate()
        eventDateDisplay = date.toString()
    })


    Column(modifier = Modifier.padding(PaddingValues(16.dp))) {
        OutlinedTextField(value = title, onValueChange = { title = it }, label = { Text("Title") })
        OutlinedTextField(value = description,
            onValueChange = { description = it },
            label = { Text("Description") })
        OutlinedTextField(value = city, onValueChange = { city = it }, label = { Text("City") })
        OutlinedTextField(value = country,
            onValueChange = { country = it },
            label = { Text("Country") })
        OutlinedTextField(value = eventDateDisplay,
            onValueChange = { eventDateDisplay = it },
            label = { Text("Event Date") }, readOnly = true)
        Button(onClick = {
            calendarState.show()
        }) {
            Text("Pick date")
        }

        Button(onClick = {
            val newEvent = Event(
                eventDate = eventDate,
                creationDate = creationDate,
                title = title,
                description = description,
                city = city,
                country = country
            )
            viewModel.createEvent(openScreen, newEvent)
        }) {
            Text("Add Event")
        }
    }
}

