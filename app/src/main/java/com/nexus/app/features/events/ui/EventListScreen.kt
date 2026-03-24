package com.nexus.app.features.events.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.nexus.app.features.events.viewmodel.EventListViewModel
import com.nexus.app.presentation.common.components.ComicPanelCard
import com.nexus.app.presentation.common.components.NexusLoadingIndicator
import com.nexus.app.presentation.common.components.SpeechBubbleLabel
import com.nexus.app.presentation.navigation.Screen
import com.nexus.app.presentation.theme.NexusMuted
import com.nexus.app.presentation.theme.NexusRed

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EventListScreen(
    navController: NavController,
    viewModel: EventListViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()

    Scaffold(topBar = {
        TopAppBar(
            title = { Text("Event Navigator", fontWeight = FontWeight.Bold) },
            navigationIcon = {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(Icons.Default.ArrowBack, "Back")
                }
            }
        )
    }) { padding ->
        if (state.isLoading) {
            NexusLoadingIndicator(); return@Scaffold
        }

        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(padding),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Text("Crossover Events & Story Arcs", fontSize = 22.sp, fontWeight = FontWeight.Bold)
                Text("Navigate the biggest moments in comic history", fontSize = 14.sp, color = NexusMuted)
            }

            // Events
            val events = state.events.filter { it.isEvent }
            if (events.isNotEmpty()) {
                item {
                    Text("Major Events", fontSize = 18.sp, fontWeight = FontWeight.SemiBold)
                }
                items(events) { event ->
                    ComicPanelCard(
                        modifier = Modifier.fillMaxWidth(),
                        onClick = { navController.navigate(Screen.EventDetail.createRoute(event.id)) }
                    ) {
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Text(event.title, fontWeight = FontWeight.Bold, fontSize = 16.sp, modifier = Modifier.weight(1f))
                            SpeechBubbleLabel(event.publisher.name)
                        }
                        Spacer(Modifier.height(4.dp))
                        Text("${event.startYear}" + if (event.endYear != null && event.endYear != event.startYear) " – ${event.endYear}" else "", fontSize = 12.sp, color = NexusRed)
                        Text("Written by ${event.writer}", fontSize = 12.sp, color = NexusMuted)
                        Spacer(Modifier.height(4.dp))
                        Text(event.synopsis, fontSize = 13.sp, color = NexusMuted, maxLines = 3, lineHeight = 20.sp)
                        Spacer(Modifier.height(4.dp))
                        Text("${event.issueIds.size} issues  ·  ${event.essentialIssueIds.size} essential", fontSize = 11.sp, color = NexusRed)
                    }
                }
            }

            // Story Arcs (non-events)
            val arcs = state.events.filter { !it.isEvent }
            if (arcs.isNotEmpty()) {
                item {
                    Spacer(Modifier.height(8.dp))
                    Text("Iconic Story Arcs", fontSize = 18.sp, fontWeight = FontWeight.SemiBold)
                }
                items(arcs) { arc ->
                    ComicPanelCard(
                        modifier = Modifier.fillMaxWidth(),
                        onClick = { navController.navigate(Screen.EventDetail.createRoute(arc.id)) }
                    ) {
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Text(arc.title, fontWeight = FontWeight.Bold, fontSize = 16.sp, modifier = Modifier.weight(1f))
                            SpeechBubbleLabel(arc.publisher.name)
                        }
                        Text("${arc.startYear} · ${arc.writer}", fontSize = 12.sp, color = NexusMuted)
                        Spacer(Modifier.height(4.dp))
                        Text(arc.synopsis, fontSize = 13.sp, color = NexusMuted, maxLines = 2, lineHeight = 20.sp)
                    }
                }
            }
        }
    }
}
