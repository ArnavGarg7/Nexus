package com.nexus.app.features.pathfinder.ui

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
import com.nexus.app.features.pathfinder.viewmodel.PathfinderViewModel
import com.nexus.app.presentation.common.components.*
import com.nexus.app.presentation.navigation.Screen
import com.nexus.app.presentation.theme.NexusMuted

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun PathfinderScreen(
    navController: NavController,
    viewModel: PathfinderViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
    val toneOptions    = listOf("Dark", "Hopeful", "Cosmic", "Street Level", "Political", "Satirical")
    val themeOptions   = listOf("Identity", "Redemption", "Legacy", "Family", "Power", "War")
    val formatOptions  = listOf("Single Arc", "Ongoing", "Event", "Mini-Series")

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Pathfinder Engine") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, "Back")
                    }
                }
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(padding),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            item {
                Text("Build Your Path", fontSize = 22.sp, fontWeight = FontWeight.Bold)
                Text("Select your preferences — we'll generate your personal entry point", fontSize = 14.sp, color = NexusMuted)
            }

            item {
                TagSelector("Tone", toneOptions, state.selectedTags) { viewModel.toggleTag(it) }
            }
            item {
                TagSelector("Theme", themeOptions, state.selectedTags) { viewModel.toggleTag(it) }
            }
            item {
                TagSelector("Format", formatOptions, state.selectedTags) { viewModel.toggleTag(it) }
            }

            item {
                NexusPrimaryButton("Generate My Path", onClick = { viewModel.generatePaths() })
            }

            if (state.isLoading) {
                item { NexusLoadingIndicator(modifier = Modifier.height(80.dp)) }
            }

            if (state.hasResults) {
                items(state.results) { path ->
                    ComicPanelCard(
                        modifier = Modifier.fillMaxWidth(),
                        onClick = { navController.navigate(Screen.ReadingOrder.createRoute(path.id, "path")) }
                    ) {
                        Text(path.title, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                        Text(path.description, fontSize = 13.sp, color = NexusMuted)
                        Text("${path.issues.size} issues  ·  ~${path.estimatedHours}h", fontSize = 12.sp, color = NexusMuted)
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun TagSelector(label: String, tags: List<String>, selected: List<String>, onToggle: (String) -> Unit) {
    Column {
        Text(label, fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
        Spacer(Modifier.height(8.dp))
        FlowRow(horizontalArrangement = Arrangement.spacedBy(8.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            tags.forEach { tag ->
                FilterChip(
                    selected = tag in selected,
                    onClick = { onToggle(tag) },
                    label = { Text(tag) }
                )
            }
        }
    }
}
