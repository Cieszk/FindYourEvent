package pl.cieszk.findyourevent.screens.event

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import pl.cieszk.findyourevent.screens.event_add.EventAddViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EventScreen(
    eventId: String,
    popUpScreen: () -> Unit,
    restartApp: (String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: EventScreenViewModel = hiltViewModel()
) {
    LaunchedEffect(eventId) {
        viewModel.initialize(eventId, restartApp)
    }

    val event by viewModel.event.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = event.title) },
                navigationIcon = {
                    IconButton(onClick = popUpScreen) {
                        Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "Go back")
                    }
                },
                actions = {
                    if (viewModel.accountService.currentUserId == event.userId) {
                        IconButton(onClick = { viewModel.deleteEvent(popUpScreen) }) {
                            Icon(imageVector = Icons.Filled.Delete, contentDescription = "Delete Event")
                        }
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(modifier = Modifier
            .padding(innerPadding)
            .padding(16.dp)
            .fillMaxWidth()) {
            Field("Title", event.title)
            Field("Description", event.description)
            Field("Date", event.eventDate.toString())
            Field("Creation Date", event.creationDate.toString())
            Field("City", event.city)
            Field("Country", event.country)
        }
    }
}

@Composable
fun Field(label: String, value: String) {
    Column(modifier = Modifier.padding(bottom = 8.dp)) {
        Text(text = "$label:", style = MaterialTheme.typography.labelLarge)
        Text(
            text = value,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier
                .padding(start = 16.dp)
                .fillMaxWidth()
        )
    }
}