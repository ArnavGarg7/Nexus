package com.nexus.app.features.reading.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.nexus.app.features.reading.viewmodel.ReadingOrderViewModel
import com.nexus.app.presentation.common.components.NexusLoadingIndicator
import com.nexus.app.presentation.theme.NexusMuted
import com.nexus.app.presentation.theme.NexusRed

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReadingOrderScreen(
    navController: NavController,
    entityId: String,
    entityType: String,
    viewModel: ReadingOrderViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()

    Scaffold(topBar = {
        TopAppBar(
            title = { Text("Reading Order", fontWeight = FontWeight.Bold) },
            navigationIcon = { IconButton(onClick = { navController.popBackStack() }) { Icon(Icons.Default.ArrowBack, "Back") } }
        )
    }) { padding ->
        Column(modifier = Modifier.fillMaxSize().padding(padding)) {
            // Mode toggle
            Row(modifier = Modifier.fillMaxWidth().padding(16.dp), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                FilterChip(
                    selected = state.isEssentialMode,
                    onClick = { viewModel.toggleMode(true) },
                    label = { Text("Essential Path") },
                    colors = FilterChipDefaults.filterChipColors(selectedContainerColor = NexusRed, selectedLabelColor = com.nexus.app.presentation.theme.NexusWhite)
                )
                FilterChip(
                    selected = !state.isEssentialMode,
                    onClick = { viewModel.toggleMode(false) },
                    label = { Text("Full Experience") },
                    colors = FilterChipDefaults.filterChipColors(selectedContainerColor = NexusRed, selectedLabelColor = com.nexus.app.presentation.theme.NexusWhite)
                )
            }

            if (state.isLoading) { NexusLoadingIndicator() }
            else state.path?.let { path ->
                LazyColumn(contentPadding = PaddingValues(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    item {
                        Text(path.title, fontSize = 20.sp, fontWeight = FontWeight.Bold)
                        Text("${path.issues.size} issues  ·  ~${path.estimatedHours}h", fontSize = 13.sp, color = NexusMuted)
                    }
                    itemsIndexed(path.issues) { i, item ->
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            Box(
                                modifier = Modifier.size(32.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Text("${i + 1}", fontWeight = FontWeight.Bold, color = NexusRed, fontSize = 16.sp)
                            }
                            Column(modifier = Modifier.weight(1f)) {
                                Text(item.comicIssueId, fontWeight = FontWeight.SemiBold, fontSize = 14.sp)
                                Text(item.reason, fontSize = 12.sp, color = NexusMuted)
                            }
                            if (item.isEssential) {
                                com.nexus.app.presentation.common.components.SpeechBubbleLabel("KEY")
                            }
                        }
                    }
                }
            }
        }
    }
}
