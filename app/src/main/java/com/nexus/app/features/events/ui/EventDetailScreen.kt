package com.nexus.app.features.events.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.AutoStories
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.nexus.app.features.events.viewmodel.EventDetailViewModel
import com.nexus.app.presentation.common.components.*
import com.nexus.app.presentation.navigation.Screen
import com.nexus.app.presentation.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EventDetailScreen(
    navController: NavController,
    eventId: String,
    viewModel: EventDetailViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()

    Scaffold(topBar = {
        TopAppBar(
            title = { Text(state.event?.title ?: "Event Navigator", fontWeight = FontWeight.Bold) },
            navigationIcon = {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(Icons.Default.ArrowBack, "Back")
                }
            }
        )
    }) { padding ->
        if (state.isLoading) { NexusLoadingIndicator(); return@Scaffold }

        state.event?.let { event ->
            LazyColumn(
                modifier = Modifier.fillMaxSize().padding(padding),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Event Header
                item {
                    Column {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(event.title, fontSize = 26.sp, fontWeight = FontWeight.Bold)
                                Text("${event.publisher.name} · ${event.startYear}" +
                                        if (event.endYear != null && event.endYear != event.startYear) " – ${event.endYear}" else "",
                                    fontSize = 14.sp, color = NexusRed)
                                Text("Written by ${event.writer}", fontSize = 13.sp, color = NexusMuted)
                            }
                        }
                        Spacer(Modifier.height(12.dp))
                        Text(event.synopsis, fontSize = 14.sp, lineHeight = 22.sp)
                        Spacer(Modifier.height(8.dp))
                        LazyRow(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                            items(event.tags) { TagChip(it) }
                        }
                    }
                }

                // Reading Order CTA
                item {
                    ComicPanelCard(
                        modifier = Modifier.fillMaxWidth(),
                        onClick = { navController.navigate(Screen.ReadingOrder.createRoute(eventId, "event")) }
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.AutoStories, contentDescription = null, tint = NexusRed)
                            Spacer(Modifier.width(8.dp))
                            Column {
                                Text("View Reading Order", fontWeight = FontWeight.SemiBold)
                                Text("${event.issueIds.size} total issues · ${event.essentialIssueIds.size} essential",
                                    fontSize = 13.sp, color = NexusMuted)
                            }
                        }
                    }
                }

                // Phase 1: Pre-Event Context
                if (event.preEventArcIds.isNotEmpty()) {
                    item {
                        PhaseHeader("Phase 1", "Pre-Event Context", NexusGold)
                    }
                    items(event.preEventArcIds) { arcId ->
                        IssueCard(arcId, "Sets the stage for ${event.title}", false)
                    }
                }

                // Phase 2: Core Event Issues
                item {
                    PhaseHeader("Phase 2", "Core Event Issues", NexusRed)
                }
                itemsIndexed(event.essentialIssueIds) { index, issueId ->
                    IssueCard(issueId, "Essential issue ${index + 1}", true)
                }

                // Phase 3: Post-Event Consequences
                if (event.postEventArcIds.isNotEmpty()) {
                    item {
                        PhaseHeader("Phase 3", "Post-Event Consequences", NexusNavyLight)
                    }
                    items(event.postEventArcIds) { arcId ->
                        IssueCard(arcId, "Follows up on ${event.title}", false)
                    }
                }

                // Lore Cards
                if (state.loreCards.isNotEmpty()) {
                    item {
                        Spacer(Modifier.height(8.dp))
                        Text("Event Lore & Trivia", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                    }
                    items(state.loreCards) { card ->
                        ComicPanelCard(modifier = Modifier.fillMaxWidth()) {
                            Text(card.category.name.replace("_", " "), fontSize = 11.sp, color = NexusRed, fontWeight = FontWeight.Bold)
                            Spacer(Modifier.height(4.dp))
                            Text(card.title, fontWeight = FontWeight.SemiBold, fontSize = 15.sp)
                            Spacer(Modifier.height(4.dp))
                            Text(card.content, fontSize = 13.sp, color = NexusMuted, lineHeight = 20.sp)
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun PhaseHeader(phase: String, title: String, accentColor: androidx.compose.ui.graphics.Color) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Box(
            modifier = Modifier
                .background(accentColor, RoundedCornerShape(4.dp))
                .padding(horizontal = 8.dp, vertical = 4.dp)
        ) {
            Text(phase, fontSize = 11.sp, fontWeight = FontWeight.Bold, color = NexusWhite)
        }
        Text(title, fontSize = 18.sp, fontWeight = FontWeight.SemiBold)
    }
}

@Composable
private fun IssueCard(issueId: String, description: String, isEssential: Boolean) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, NexusDivider, RoundedCornerShape(8.dp))
            .background(MaterialTheme.colorScheme.surface, RoundedCornerShape(8.dp))
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(issueId, fontWeight = FontWeight.SemiBold, fontSize = 14.sp)
            Text(description, fontSize = 12.sp, color = NexusMuted)
        }
        if (isEssential) {
            SpeechBubbleLabel("KEY")
        }
    }
}
