package com.nexus.app.presentation.common.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.nexus.app.domain.model.Character
import com.nexus.app.presentation.theme.*

/** Comic-panel styled card used across multiple screens */
@Composable
fun ComicPanelCard(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
    content: @Composable ColumnScope.() -> Unit
) {
    Card(
        modifier = modifier
            .clickable(onClick = onClick)
            .border(1.5.dp, NexusDivider, RoundedCornerShape(8.dp)),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        content = { Column(modifier = Modifier.padding(12.dp), content = content) }
    )
}

/** Speech-bubble styled tooltip / label */
@Composable
fun SpeechBubbleLabel(text: String, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .background(NexusRed, RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp, bottomEnd = 12.dp, bottomStart = 2.dp))
            .padding(horizontal = 10.dp, vertical = 5.dp)
    ) {
        Text(text, color = NexusWhite, fontSize = 11.sp, fontWeight = FontWeight.Bold)
    }
}

/** Character portrait card */
@Composable
fun CharacterCard(character: Character, onClick: () -> Unit) {
    ComicPanelCard(modifier = Modifier.width(140.dp), onClick = onClick) {
        AsyncImage(
            model = character.coverUrl,
            contentDescription = character.name,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxWidth().height(180.dp).clip(RoundedCornerShape(4.dp))
        )
        Spacer(Modifier.height(8.dp))
        Text(character.name, style = MaterialTheme.typography.labelLarge, maxLines = 1)
        Text(character.publisher.name, style = MaterialTheme.typography.labelSmall, color = NexusMuted)
    }
}

/** Tag chip */
@Composable
fun TagChip(tag: String) {
    SuggestionChip(
        onClick = {},
        label = { Text(tag, fontSize = 11.sp) },
        colors = SuggestionChipDefaults.suggestionChipColors(
            containerColor = NexusNavyLight,
            labelColor = NexusWhite
        ),
        border = BorderStroke(1.dp, NexusDivider)
    )
}

/** Full-width primary action button with NEXUS red style */
@Composable
fun NexusPrimaryButton(text: String, onClick: () -> Unit, modifier: Modifier = Modifier) {
    Button(
        onClick = onClick,
        modifier = modifier.fillMaxWidth().height(52.dp),
        shape = RoundedCornerShape(8.dp),
        colors = ButtonDefaults.buttonColors(containerColor = NexusRed)
    ) {
        Text(text, fontWeight = FontWeight.Bold, fontSize = 16.sp, color = NexusWhite)
    }
}

/** Loading indicator with NEXUS branding */
@Composable
fun NexusLoadingIndicator(modifier: Modifier = Modifier) {
    Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        CircularProgressIndicator(color = NexusRed)
    }
}
