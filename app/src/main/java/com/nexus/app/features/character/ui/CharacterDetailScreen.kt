package com.nexus.app.features.character.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.AutoStories
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.nexus.app.features.character.viewmodel.CharacterViewModel
import com.nexus.app.presentation.common.components.*
import com.nexus.app.presentation.navigation.Screen
import com.nexus.app.presentation.theme.NexusMuted
import com.nexus.app.presentation.theme.NexusRed

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CharacterDetailScreen(
    navController: NavController,
    characterId: String,
    viewModel: CharacterViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(state.character?.name ?: "") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, "Back")
                    }
                }
            )
        }
    ) { padding ->
        if (state.isLoading) { NexusLoadingIndicator(); return@Scaffold }

        state.character?.let { character ->
            LazyColumn(
                modifier = Modifier.fillMaxSize().padding(padding),
                contentPadding = PaddingValues(bottom = 24.dp)
            ) {
                // Hero cover image
                item {
                    AsyncImage(
                        model = character.coverUrl,
                        contentDescription = character.name,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxWidth().height(280.dp)
                    )
                }

                // Character header
                item {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(character.name, fontSize = 28.sp, fontWeight = FontWeight.Bold)
                        Text(character.publisher.name, fontSize = 14.sp, color = NexusRed)
                        Text("First appeared: ${character.firstAppearance}", fontSize = 13.sp, color = NexusMuted)
                        Spacer(Modifier.height(8.dp))
                        Text(character.synopsis, fontSize = 14.sp, lineHeight = 22.sp)
                        Spacer(Modifier.height(12.dp))
                        LazyRow(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                            items(character.tags) { TagChip(it) }
                        }
                    }
                }

                // Beginner reading path CTA
                state.beginnerPath?.let { path ->
                    item {
                        Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                            Text("Beginner Reading Path", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                            Spacer(Modifier.height(8.dp))
                            ComicPanelCard(
                                modifier = Modifier.fillMaxWidth(),
                                onClick = {
                                    navController.navigate(Screen.ReadingOrder.createRoute(characterId, "character"))
                                }
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(Icons.Default.AutoStories, contentDescription = null, tint = NexusRed)
                                    Spacer(Modifier.width(8.dp))
                                    Column {
                                        Text(path.title, fontWeight = FontWeight.SemiBold)
                                        Text("${path.issues.size} issues  ·  ~${path.estimatedHours}h", fontSize = 13.sp, color = NexusMuted)
                                    }
                                }
                            }
                        }
                    }
                }

                // Lore Explorer CTA
                item {
                    Spacer(Modifier.height(8.dp))
                    Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                        ComicPanelCard(
                            modifier = Modifier.fillMaxWidth(),
                            onClick = { navController.navigate(Screen.LoreExplorer.createRoute(characterId)) }
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(Icons.Default.EmojiEvents, contentDescription = null, tint = NexusRed)
                                Spacer(Modifier.width(8.dp))
                                Column {
                                    Text("Explore Lore & Trivia", fontWeight = FontWeight.SemiBold)
                                    Text("Origins, Easter eggs, creator insights", fontSize = 13.sp, color = NexusMuted)
                                }
                            }
                        }
                    }
                }

                // Similar characters
                if (state.similarCharacters.isNotEmpty()) {
                    item {
                        Spacer(Modifier.height(16.dp))
                        Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                            Text("You Might Also Like", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                            Spacer(Modifier.height(8.dp))
                            LazyRow(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                                items(state.similarCharacters) { similar ->
                                    CharacterCard(character = similar) {
                                        navController.navigate(Screen.Character.createRoute(similar.id))
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
