package com.nexus.app.features.home.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.nexus.app.domain.model.MediaItem
import com.nexus.app.domain.model.Quiz
import com.nexus.app.domain.model.StoryArc
import com.nexus.app.features.home.viewmodel.HomeViewModel
import com.nexus.app.presentation.common.components.*
import com.nexus.app.presentation.navigation.Screen
import com.nexus.app.presentation.theme.NexusMuted
import com.nexus.app.presentation.theme.NexusRed

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("NEXUS", fontSize = 28.sp, fontWeight = FontWeight.Bold, color = NexusRed)
                },
                actions = {
                    IconButton(onClick = { navController.navigate(Screen.Search.route) }) {
                        Icon(Icons.Default.Search, contentDescription = "Search")
                    }
                }
            )
        }
    ) { padding ->
        if (state.isLoading) {
            NexusLoadingIndicator()
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize().padding(padding),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                item {
                    Text("Your Universe Awaits", fontSize = 22.sp, fontWeight = FontWeight.Bold)
                    Text("Pick up where you left off", fontSize = 14.sp, color = NexusMuted)
                }

                // Featured Characters
                if (state.featuredCharacters.isNotEmpty()) {
                    item {
                        Text("Featured Characters", fontSize = 18.sp, fontWeight = FontWeight.SemiBold)
                        Spacer(Modifier.height(8.dp))
                        LazyRow(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                            items(state.featuredCharacters) { character ->
                                CharacterCard(character = character) {
                                    navController.navigate(Screen.Character.createRoute(character.id))
                                }
                            }
                        }
                    }
                }

                // Recommended Quizzes
                if (state.recommendedQuizzes.isNotEmpty()) {
                    item {
                        Text("Recommended Quizzes", fontSize = 18.sp, fontWeight = FontWeight.SemiBold)
                        Spacer(Modifier.height(8.dp))
                        LazyRow(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                            items(state.recommendedQuizzes) { quiz ->
                                QuizSummaryCard(quiz = quiz) {
                                    navController.navigate(Screen.Quiz.createRoute(quiz.id))
                                }
                            }
                        }
                    }
                }

                // Event Highlights
                if (state.featuredEvents.isNotEmpty()) {
                    item {
                        Text("Event Highlights", fontSize = 18.sp, fontWeight = FontWeight.SemiBold)
                        Spacer(Modifier.height(8.dp))
                        LazyRow(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                            items(state.featuredEvents) { event ->
                                EventHighlightCard(event = event) {
                                    navController.navigate(Screen.EventDetail.createRoute(event.id))
                                }
                            }
                        }
                    }
                }

                // Media Bridge
                if (state.featuredMedia.isNotEmpty()) {
                    item {
                        Text("From Screen to Page", fontSize = 18.sp, fontWeight = FontWeight.SemiBold)
                        Spacer(Modifier.height(8.dp))
                        LazyRow(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                            items(state.featuredMedia) { media ->
                                MediaCard(media = media) {
                                    navController.navigate(Screen.MediaBridge.createRoute(media.id))
                                }
                            }
                        }
                    }
                }

                // Navigation shortcuts
                item {
                    NexusSectionCard(
                        title = "Pathfinder Engine",
                        subtitle = "Find your perfect starting point",
                        onClick = { navController.navigate(Screen.PathFinder.route) }
                    )
                }

                item {
                    NexusSectionCard(
                        title = "Event Navigator",
                        subtitle = "Explore major crossover events",
                        onClick = { navController.navigate(Screen.EventList.route) }
                    )
                }

                item {
                    NexusSectionCard(
                        title = "Quiz Hub",
                        subtitle = "Test your comic knowledge",
                        onClick = { navController.navigate(Screen.QuizHub.route) }
                    )
                }
            }
        }
    }
}

@Composable
private fun QuizSummaryCard(quiz: Quiz, onClick: () -> Unit) {
    ComicPanelCard(
        modifier = Modifier.width(200.dp),
        onClick = onClick
    ) {
        Text(quiz.title, fontWeight = FontWeight.Bold, fontSize = 14.sp, maxLines = 2)
        Spacer(Modifier.height(4.dp))
        Text(quiz.difficulty.name.replace("_", " "), fontSize = 12.sp, color = NexusMuted)
        Text("~${quiz.estimatedMinutes} min  ·  ${quiz.xpReward} XP", fontSize = 11.sp, color = NexusRed)
    }
}

@Composable
private fun EventHighlightCard(event: StoryArc, onClick: () -> Unit) {
    ComicPanelCard(
        modifier = Modifier.width(200.dp),
        onClick = onClick
    ) {
        Text(event.title, fontWeight = FontWeight.Bold, fontSize = 14.sp, maxLines = 2)
        Spacer(Modifier.height(4.dp))
        Text(event.publisher.name, fontSize = 12.sp, color = NexusRed)
        Text("${event.startYear} · ${event.issueIds.size} issues", fontSize = 11.sp, color = NexusMuted)
    }
}

@Composable
private fun MediaCard(media: MediaItem, onClick: () -> Unit) {
    ComicPanelCard(
        modifier = Modifier.width(200.dp),
        onClick = onClick
    ) {
        Text(media.title, fontWeight = FontWeight.Bold, fontSize = 14.sp, maxLines = 2)
        Spacer(Modifier.height(4.dp))
        Text(media.type.name.replace("_", " "), fontSize = 12.sp, color = NexusRed)
        Text("${media.studio} · ${media.releaseYear}", fontSize = 11.sp, color = NexusMuted)
    }
}

@Composable
private fun NexusSectionCard(title: String, subtitle: String, onClick: () -> Unit) {
    ComicPanelCard(
        modifier = Modifier.fillMaxWidth(),
        onClick = onClick
    ) {
        Text(title, fontWeight = FontWeight.Bold, fontSize = 16.sp)
        Text(subtitle, fontSize = 13.sp, color = NexusMuted)
    }
}
