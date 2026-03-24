package com.nexus.app.features.profile.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.nexus.app.features.profile.viewmodel.ProfileViewModel
import com.nexus.app.presentation.common.components.ComicPanelCard
import com.nexus.app.presentation.common.components.NexusLoadingIndicator
import com.nexus.app.presentation.common.components.TagChip
import com.nexus.app.presentation.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    navController: NavController,
    viewModel: ProfileViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()

    Scaffold(topBar = {
        TopAppBar(title = { Text("Profile", fontWeight = FontWeight.Bold) })
    }) { padding ->
        if (state.isLoading) { NexusLoadingIndicator(); return@Scaffold }

        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(padding),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Avatar & Name
            item {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(
                        modifier = Modifier
                            .size(80.dp)
                            .background(NexusRed, CircleShape)
                            .border(3.dp, NexusGold, CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            state.displayName.take(2).uppercase(),
                            fontSize = 28.sp, fontWeight = FontWeight.Bold, color = NexusWhite
                        )
                    }
                    Spacer(Modifier.height(12.dp))
                    Text(state.displayName, fontSize = 22.sp, fontWeight = FontWeight.Bold)
                    Text("Fan Level ${state.fanLevel}", fontSize = 14.sp, color = NexusRed, fontWeight = FontWeight.SemiBold)
                }
            }

            // Stats Row
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    StatItem(icon = Icons.Default.Star, label = "XP", value = "${state.xpPoints}")
                    StatItem(icon = Icons.Default.LocalFireDepartment, label = "Streak", value = "${state.streakDays} days")
                    StatItem(icon = Icons.Default.EmojiEvents, label = "Level", value = "${state.fanLevel}")
                }
            }

            // Preferences
            if (state.publishers.isNotEmpty()) {
                item {
                    ComicPanelCard(modifier = Modifier.fillMaxWidth()) {
                        Text("My Publishers", fontWeight = FontWeight.SemiBold, fontSize = 16.sp)
                        Spacer(Modifier.height(8.dp))
                        LazyRow(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                            items(state.publishers) { TagChip(it) }
                        }
                    }
                }
            }

            if (state.mood.isNotBlank()) {
                item {
                    ComicPanelCard(modifier = Modifier.fillMaxWidth()) {
                        Text("Story Mood", fontWeight = FontWeight.SemiBold, fontSize = 16.sp)
                        Spacer(Modifier.height(4.dp))
                        Text(state.mood, fontSize = 14.sp, color = NexusMuted)
                    }
                }
            }

            // Badges placeholder
            item {
                ComicPanelCard(modifier = Modifier.fillMaxWidth()) {
                    Text("Badges", fontWeight = FontWeight.SemiBold, fontSize = 16.sp)
                    Spacer(Modifier.height(8.dp))
                    Text("Complete quizzes and reading paths to earn badges!", fontSize = 13.sp, color = NexusMuted)
                    Spacer(Modifier.height(8.dp))
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        BadgePlaceholder("🦇", "First Quiz")
                        BadgePlaceholder("⚡", "Speed Reader")
                        BadgePlaceholder("🌟", "Lore Hunter")
                    }
                }
            }
        }
    }
}

@Composable
private fun StatItem(icon: androidx.compose.ui.graphics.vector.ImageVector, label: String, value: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Icon(icon, contentDescription = label, tint = NexusGold, modifier = Modifier.size(28.dp))
        Spacer(Modifier.height(4.dp))
        Text(value, fontWeight = FontWeight.Bold, fontSize = 18.sp)
        Text(label, fontSize = 12.sp, color = NexusMuted)
    }
}

@Composable
private fun BadgePlaceholder(emoji: String, name: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Box(
            modifier = Modifier
                .size(48.dp)
                .background(NexusNavyLight, RoundedCornerShape(12.dp))
                .border(1.dp, NexusDivider, RoundedCornerShape(12.dp)),
            contentAlignment = Alignment.Center
        ) {
            Text(emoji, fontSize = 22.sp)
        }
        Spacer(Modifier.height(4.dp))
        Text(name, fontSize = 10.sp, color = NexusMuted, textAlign = TextAlign.Center)
    }
}
