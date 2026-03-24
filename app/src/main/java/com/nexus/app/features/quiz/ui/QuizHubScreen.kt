package com.nexus.app.features.quiz.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.nexus.app.features.quiz.viewmodel.QuizHubViewModel
import com.nexus.app.presentation.common.components.ComicPanelCard
import com.nexus.app.presentation.common.components.NexusLoadingIndicator
import com.nexus.app.presentation.navigation.Screen
import com.nexus.app.presentation.theme.NexusMuted
import com.nexus.app.presentation.theme.NexusRed

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuizHubScreen(
    navController: NavController,
    viewModel: QuizHubViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()

    Scaffold(topBar = {
        TopAppBar(title = { Text("Quiz Hub", fontWeight = FontWeight.Bold) })
    }) { padding ->
        if (state.isLoading) { NexusLoadingIndicator(); return@Scaffold }

        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(padding),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            item {
                Text("Test Your Knowledge", fontSize = 22.sp, fontWeight = FontWeight.Bold)
                Text("Quizzes matched to your reading history", fontSize = 14.sp, color = NexusMuted)
            }

            if (state.recommended.isNotEmpty()) {
                item {
                    Text("Recommended For You", fontSize = 18.sp, fontWeight = FontWeight.SemiBold)
                    Spacer(Modifier.height(8.dp))
                    LazyRow(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                        items(state.recommended) { quiz ->
                            ComicPanelCard(
                                modifier = Modifier.width(200.dp),
                                onClick = { navController.navigate(Screen.Quiz.createRoute(quiz.id)) }
                            ) {
                                Text(quiz.title, fontWeight = FontWeight.Bold, fontSize = 14.sp, maxLines = 2)
                                Spacer(Modifier.height(4.dp))
                                Text(quiz.category.name.replace("_", " "), fontSize = 12.sp, color = NexusMuted)
                                Text("${quiz.difficulty.name.replace("_", " ")}  ·  ${quiz.xpReward} XP",
                                    fontSize = 11.sp, color = NexusRed)
                            }
                        }
                    }
                }
            }

            // Category grid
            item {
                Text("Browse by Category", fontSize = 18.sp, fontWeight = FontWeight.SemiBold)
                Spacer(Modifier.height(8.dp))
                val categories = listOf(
                    "Character Quizzes" to "How well do you know your heroes?",
                    "Storyline Quizzes" to "Event arcs & major crossovers",
                    "Universe Quizzes" to "Marvel, DC, Image & more",
                    "Movie & TV Quizzes" to "Silver screen & streaming",
                    "Mixed Lore Challenge" to "The ultimate continuity gauntlet"
                )
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    categories.forEach { (title, sub) ->
                        ComicPanelCard(modifier = Modifier.fillMaxWidth()) {
                            Text(title, fontWeight = FontWeight.SemiBold)
                            Text(sub, fontSize = 13.sp, color = NexusMuted)
                        }
                    }
                }
            }
        }
    }
}
