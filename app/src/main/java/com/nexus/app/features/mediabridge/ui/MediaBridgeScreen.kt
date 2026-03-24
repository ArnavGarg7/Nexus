package com.nexus.app.features.mediabridge.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.AutoStories
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.nexus.app.features.mediabridge.viewmodel.MediaBridgeViewModel
import com.nexus.app.presentation.common.components.*
import com.nexus.app.presentation.navigation.Screen
import com.nexus.app.presentation.theme.NexusMuted
import com.nexus.app.presentation.theme.NexusRed

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MediaBridgeScreen(
    navController: NavController,
    mediaId: String,
    viewModel: MediaBridgeViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()

    Scaffold(topBar = {
        TopAppBar(
            title = { Text(state.media?.title ?: "Media Bridge", fontWeight = FontWeight.Bold) },
            navigationIcon = {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(Icons.Default.ArrowBack, "Back")
                }
            }
        )
    }) { padding ->
        if (state.isLoading) { NexusLoadingIndicator(); return@Scaffold }

        state.media?.let { media ->
            LazyColumn(
                modifier = Modifier.fillMaxSize().padding(padding),
                contentPadding = PaddingValues(bottom = 24.dp)
            ) {
                // Hero poster
                item {
                    AsyncImage(
                        model = media.posterUrl,
                        contentDescription = media.title,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxWidth().height(280.dp)
                    )
                }

                // Metadata
                item {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(media.title, fontSize = 26.sp, fontWeight = FontWeight.Bold)
                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            SpeechBubbleLabel(media.type.name.replace("_", " "))
                            Text("${media.studio} · ${media.releaseYear}", fontSize = 13.sp, color = NexusMuted,
                                modifier = Modifier.align(Alignment.CenterVertically))
                        }
                        Spacer(Modifier.height(12.dp))
                        Text(media.synopsis, fontSize = 14.sp, lineHeight = 22.sp)
                        Spacer(Modifier.height(12.dp))
                        LazyRow(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                            items(media.tags) { TagChip(it) }
                        }
                    }
                }

                // Comics That Inspired This
                item {
                    Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                        Text("Comics That Inspired This", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                        Spacer(Modifier.height(4.dp))
                        Text("Read the source material behind the adaptation", fontSize = 13.sp, color = NexusMuted)
                    }
                }

                if (state.relatedArcs.isNotEmpty()) {
                    items(state.relatedArcs) { arc ->
                        ComicPanelCard(
                            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                            onClick = { navController.navigate(Screen.EventDetail.createRoute(arc.id)) }
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(Icons.Default.AutoStories, contentDescription = null, tint = NexusRed)
                                Spacer(Modifier.width(8.dp))
                                Column {
                                    Text(arc.title, fontWeight = FontWeight.SemiBold)
                                    Text("${arc.writer} · ${arc.startYear}", fontSize = 12.sp, color = NexusMuted)
                                    Text(arc.synopsis, fontSize = 12.sp, color = NexusMuted, maxLines = 2, lineHeight = 18.sp)
                                }
                            }
                        }
                        Spacer(Modifier.height(8.dp))
                    }
                } else {
                    item {
                        Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                            media.relatedComicIds.forEach { comicId ->
                                ComicPanelCard(modifier = Modifier.fillMaxWidth()) {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Icon(Icons.Default.AutoStories, contentDescription = null, tint = NexusRed)
                                        Spacer(Modifier.width(8.dp))
                                        Text(comicId, fontWeight = FontWeight.SemiBold)
                                    }
                                }
                                Spacer(Modifier.height(8.dp))
                            }
                        }
                    }
                }

                // Character Links
                if (media.characterIds.isNotEmpty()) {
                    item {
                        Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                            Spacer(Modifier.height(8.dp))
                            Text("Characters in This Media", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                            Spacer(Modifier.height(8.dp))
                            LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                items(media.characterIds) { charId ->
                                    NexusPrimaryButton(
                                        text = charId.removePrefix("char_").replaceFirstChar { it.uppercase() },
                                        onClick = { navController.navigate(Screen.Character.createRoute(charId)) },
                                        modifier = Modifier.width(140.dp)
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
