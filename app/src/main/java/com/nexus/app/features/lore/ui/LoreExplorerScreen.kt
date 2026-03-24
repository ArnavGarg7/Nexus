package com.nexus.app.features.lore.ui

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
import com.nexus.app.features.lore.viewmodel.LoreExplorerViewModel
import com.nexus.app.presentation.common.components.ComicPanelCard
import com.nexus.app.presentation.common.components.NexusLoadingIndicator
import com.nexus.app.presentation.theme.NexusMuted
import com.nexus.app.presentation.theme.NexusRed

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoreExplorerScreen(
    navController: NavController,
    characterId: String,
    viewModel: LoreExplorerViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()

    Scaffold(topBar = {
        TopAppBar(
            title = { Text("Lore & Trivia", fontWeight = FontWeight.Bold) },
            navigationIcon = { IconButton(onClick = { navController.popBackStack() }) { Icon(Icons.Default.ArrowBack, "Back") } }
        )
    }) { padding ->
        if (state.isLoading) { NexusLoadingIndicator(); return@Scaffold }
        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(padding),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item { Text("Deep Lore", fontSize = 22.sp, fontWeight = FontWeight.Bold) }
            items(state.cards) { card ->
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
