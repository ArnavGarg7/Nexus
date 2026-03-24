package com.nexus.app.features.onboarding.ui

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.nexus.app.features.onboarding.viewmodel.OnboardingViewModel
import com.nexus.app.presentation.common.components.*
import com.nexus.app.presentation.navigation.Screen
import com.nexus.app.presentation.theme.*

@Composable
fun OnboardingScreen(
    navController: NavController,
    viewModel: OnboardingViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()

    LaunchedEffect(state.isComplete) {
        if (state.isComplete) navController.navigate(Screen.Auth.route) {
            popUpTo(Screen.Onboarding.route) { inclusive = true }
        }
    }

    // ── Aged Paper Background with Halftone ──
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(ComicAgedPaper)
    ) {
        // Halftone dot overlay across entire screen
        HalftoneOverlay(dotColor = HalftoneBase, dotRadius = 2f, spacing = 12f)

        // Subtle speed lines behind everything
        SpeedLines(
            modifier = Modifier.fillMaxSize(),
            lineColor = SpeedLineColor.copy(alpha = 0.04f),
            lineCount = 20,
            animated = false
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp, vertical = 12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // ── Comic Lightning Bolt Progress Bar ──
            val progressScale by animatedPanelScale(delayMs = 0)
            Box(modifier = Modifier.scale(progressScale)) {
                ComicProgressBolt(
                    progress = (state.currentStep + 1f) / state.totalSteps,
                    modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
                )
            }

            Spacer(Modifier.height(16.dp))

            // ── NEXUS Title Panel (tilted with starburst) ──
            val titleScale by animatedPanelScale(delayMs = 100)
            Box(
                modifier = Modifier
                    .scale(titleScale)
                    .fillMaxWidth()
                    .height(110.dp),
                contentAlignment = Alignment.Center
            ) {
                // Starburst behind title
                StarburstBackground(
                    modifier = Modifier.size(180.dp),
                    fillColor = ComicYellow,
                    outlineColor = ComicBlack,
                    points = 16
                )
                // Tilted panel
                ComicBookPanel(
                    modifier = Modifier
                        .wrapContentSize()
                        .shadow(6.dp, RoundedCornerShape(4.dp)),
                    rotationDeg = -1.5f,
                    borderWidth = 4.dp,
                    backgroundColor = NexusRed
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            "NEXUS",
                            fontFamily = NexusDisplayFont,
                            fontSize = 52.sp,
                            fontWeight = FontWeight.Bold,
                            color = ComicYellow,
                            letterSpacing = 4.sp
                        )
                        Text(
                            "CONTINUITY & DISCOVERY",
                            fontFamily = ComicHandFont,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = NexusWhite,
                            letterSpacing = 2.sp
                        )
                    }
                }
            }

            Spacer(Modifier.height(16.dp))

            // ── Step Content (animated swap) ──
            AnimatedContent(
                targetState = state.currentStep,
                label = "step",
                transitionSpec = {
                    (fadeIn(animationSpec = tween(300)) +
                            scaleIn(initialScale = 0.85f, animationSpec = tween(300)))
                        .togetherWith(
                            fadeOut(animationSpec = tween(200)) +
                                    scaleOut(targetScale = 0.85f, animationSpec = tween(200))
                        )
                }
            ) { step ->
                when (step) {
                    0 -> ComicWelcomeStep()
                    1 -> ComicPublisherStep(viewModel)
                    2 -> ComicMoodStep(viewModel)
                    3 -> ComicEntryPointStep(viewModel)
                    4 -> ComicCharacterSparkStep(viewModel)
                }
            }

            Spacer(Modifier.weight(1f, fill = true))
            Spacer(Modifier.height(20.dp))

            // ── Navigation Buttons (comic styled) ──
            val btnScale by animatedPanelScale(delayMs = 400)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .scale(btnScale),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                if (state.currentStep > 0) {
                    // Back button — subdued comic panel
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .height(54.dp)
                            .clip(RoundedCornerShape(6.dp))
                            .border(3.dp, PanelBorder, RoundedCornerShape(6.dp))
                            .background(ComicPaperDark)
                            .clickable { viewModel.prevStep() },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            "BACK",
                            fontFamily = NexusDisplayFont,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = ComicBlack
                        )
                    }
                }

                // Continue / Enter the Nexus — explosive style
                val isFinal = state.currentStep == state.totalSteps - 1
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .height(54.dp),
                    contentAlignment = Alignment.Center
                ) {
                    // Starburst behind final button
                    if (isFinal) {
                        StarburstBackground(
                            modifier = Modifier.size(120.dp),
                            fillColor = ComicYellow.copy(alpha = 0.4f),
                            outlineColor = Color.Transparent,
                            points = 10
                        )
                    }
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(RoundedCornerShape(6.dp))
                            .border(3.dp, ComicBlack, RoundedCornerShape(6.dp))
                            .background(
                                Brush.horizontalGradient(
                                    listOf(NexusRed, NexusRedDark)
                                )
                            )
                            .clickable { viewModel.nextStep() },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            if (isFinal) "ENTER THE NEXUS!" else "CONTINUE →",
                            fontFamily = NexusDisplayFont,
                            fontSize = if (isFinal) 20.sp else 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = ComicYellow,
                            letterSpacing = 1.sp
                        )
                    }
                }
            }

            Spacer(Modifier.height(16.dp))
        }
    }
}

// ═══════════════════════════════════════════════════════════════
// Step 0: Welcome — hero intro panel
// ═══════════════════════════════════════════════════════════════

@Composable
private fun ComicWelcomeStep() {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // ── "Welcome, Explorer" in action cloud ──
        val scale1 by animatedPanelScale(delayMs = 150)
        Box(
            modifier = Modifier
                .scale(scale1)
                .fillMaxWidth()
                .height(140.dp),
            contentAlignment = Alignment.Center
        ) {
            // Speed lines behind
            SpeedLines(
                modifier = Modifier.fillMaxSize(),
                lineColor = NexusRed.copy(alpha = 0.08f),
                lineCount = 24,
                animated = false
            )
            // Action cloud
            ActionCloud(
                modifier = Modifier.fillMaxSize(),
                cloudColor = Color.White,
                outlineColor = ComicBlack
            )
            // Title text
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    "WELCOME,",
                    fontFamily = NexusDisplayFont,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = ComicBlack,
                    letterSpacing = 2.sp
                )
                Text(
                    "EXPLORER!",
                    fontFamily = NexusDisplayFont,
                    fontSize = 36.sp,
                    fontWeight = FontWeight.Bold,
                    color = NexusRed,
                    letterSpacing = 3.sp
                )
            }
        }

        // ── Description panel (tilted, hand-lettered) ──
        val scale2 by animatedPanelScale(delayMs = 300)
        ComicBookPanel(
            modifier = Modifier
                .scale(scale2)
                .fillMaxWidth()
                .shadow(4.dp, RoundedCornerShape(4.dp)),
            rotationDeg = 1.2f,
            borderWidth = 3.dp,
            backgroundColor = ComicYellowPale
        ) {
            Column(
                modifier = Modifier.padding(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    "THE COMIC UNIVERSE IS VAST.",
                    fontFamily = ComicHandFont,
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Bold,
                    color = ComicBlack,
                    textAlign = TextAlign.Center
                )
                Spacer(Modifier.height(6.dp))
                Text(
                    "NEXUS IS YOUR GUIDE.",
                    fontFamily = NexusDisplayFont,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = NexusRed,
                    textAlign = TextAlign.Center
                )
                Spacer(Modifier.height(6.dp))
                Text(
                    "We'll build your personal reading\npath in just 60 seconds!",
                    fontFamily = ComicHandFont,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    color = NexusNavyMid,
                    textAlign = TextAlign.Center,
                    lineHeight = 20.sp
                )
            }
        }

        // ── Small caption panel ──
        val scale3 by animatedPanelScale(delayMs = 450)
        Box(
            modifier = Modifier
                .scale(scale3)
                .rotate(-0.8f)
                .background(NexusRed, RoundedCornerShape(4.dp))
                .border(2.dp, ComicBlack, RoundedCornerShape(4.dp))
                .padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            Text(
                "★ YOUR STORY BEGINS HERE ★",
                fontFamily = ComicHandFont,
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                color = ComicYellow,
                letterSpacing = 1.sp
            )
        }
    }
}

// ═══════════════════════════════════════════════════════════════
// Step 1: Publisher Selection — comic panels
// ═══════════════════════════════════════════════════════════════

@Composable
private fun ComicPublisherStep(viewModel: OnboardingViewModel) {
    val state by viewModel.state.collectAsState()
    val publishers = listOf("Marvel", "DC", "Image", "Dark Horse", "Indie", "All of them!")

    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // Title in speech bubble
        val titleScale by animatedPanelScale(delayMs = 100)
        ComicSpeechTitle(
            text = "WHICH UNIVERSES CALL TO YOU?",
            modifier = Modifier.scale(titleScale)
        )

        // Publisher chips in a comic panel
        val panelScale by animatedPanelScale(delayMs = 200)
        ComicBookPanel(
            modifier = Modifier
                .scale(panelScale)
                .fillMaxWidth()
                .shadow(4.dp, RoundedCornerShape(4.dp)),
            rotationDeg = 0.5f,
            borderWidth = 3.dp,
            backgroundColor = Color.White.copy(alpha = 0.9f)
        ) {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                verticalArrangement = Arrangement.spacedBy(10.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier.height(260.dp)
            ) {
                items(publishers) { pub ->
                    ComicChip(
                        text = pub,
                        selected = pub in state.selectedPublishers,
                        onClick = {
                            val current = state.selectedPublishers.toMutableList()
                            if (pub in current) current.remove(pub) else current.add(pub)
                            viewModel.selectPublishers(current)
                        }
                    )
                }
            }
        }
    }
}

// ═══════════════════════════════════════════════════════════════
// Step 2: Mood Selection
// ═══════════════════════════════════════════════════════════════

@Composable
private fun ComicMoodStep(viewModel: OnboardingViewModel) {
    val state by viewModel.state.collectAsState()
    val moods = listOf("Dark & Gritty", "Hopeful & Classic", "Cosmic & Epic", "Street Level", "Satirical", "I'll explore")

    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        val titleScale by animatedPanelScale(delayMs = 100)
        ComicSpeechTitle(
            text = "WHAT'S YOUR STORY MOOD?",
            modifier = Modifier.scale(titleScale)
        )

        val panelScale by animatedPanelScale(delayMs = 200)
        ComicBookPanel(
            modifier = Modifier
                .scale(panelScale)
                .fillMaxWidth()
                .shadow(4.dp, RoundedCornerShape(4.dp)),
            rotationDeg = -0.7f,
            borderWidth = 3.dp,
            backgroundColor = Color.White.copy(alpha = 0.9f)
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                moods.forEach { mood ->
                    ComicChip(
                        text = mood,
                        selected = state.selectedMood == mood,
                        onClick = { viewModel.selectMood(mood) },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }
    }
}

// ═══════════════════════════════════════════════════════════════
// Step 3: Entry Point Selection
// ═══════════════════════════════════════════════════════════════

@Composable
private fun ComicEntryPointStep(viewModel: OnboardingViewModel) {
    val state by viewModel.state.collectAsState()
    val entries = listOf("Movies & TV", "Already a reader", "Video Games", "Fresh start — complete newbie")

    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        val titleScale by animatedPanelScale(delayMs = 100)
        ComicSpeechTitle(
            text = "HOW DID YOU DISCOVER THIS WORLD?",
            modifier = Modifier.scale(titleScale)
        )

        val panelScale by animatedPanelScale(delayMs = 200)
        ComicBookPanel(
            modifier = Modifier
                .scale(panelScale)
                .fillMaxWidth()
                .shadow(4.dp, RoundedCornerShape(4.dp)),
            rotationDeg = 0.8f,
            borderWidth = 3.dp,
            backgroundColor = Color.White.copy(alpha = 0.9f)
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                entries.forEach { entry ->
                    ComicChip(
                        text = entry,
                        selected = state.entryPoint == entry,
                        onClick = { viewModel.selectEntryPoint(entry) },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }
    }
}

// ═══════════════════════════════════════════════════════════════
// Step 4: Character Spark
// ═══════════════════════════════════════════════════════════════

@Composable
private fun ComicCharacterSparkStep(viewModel: OnboardingViewModel) {
    val characters = listOf(
        "Batman", "Spider-Man", "Superman", "Iron Man",
        "Wonder Woman", "Wolverine", "Thor", "Black Panther",
        "Invincible", "The Boys", "Daredevil", "Moon Knight"
    )

    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        val titleScale by animatedPanelScale(delayMs = 100)
        ComicSpeechTitle(
            text = "PICK A CHARACTER YOU KNOW!",
            modifier = Modifier.scale(titleScale)
        )

        // Optional subtitle
        Box(
            modifier = Modifier
                .rotate(-1f)
                .background(ComicYellowPale, RoundedCornerShape(4.dp))
                .border(1.5.dp, ComicBlack, RoundedCornerShape(4.dp))
                .padding(horizontal = 10.dp, vertical = 4.dp)
        ) {
            Text(
                "Optional — helps personalise your path",
                fontFamily = ComicHandFont,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = NexusNavyMid
            )
        }

        val panelScale by animatedPanelScale(delayMs = 200)
        ComicBookPanel(
            modifier = Modifier
                .scale(panelScale)
                .fillMaxWidth()
                .shadow(4.dp, RoundedCornerShape(4.dp)),
            rotationDeg = -0.5f,
            borderWidth = 3.dp,
            backgroundColor = Color.White.copy(alpha = 0.9f)
        ) {
            val state by viewModel.state.collectAsState()
            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.height(280.dp)
            ) {
                items(characters) { char ->
                    ComicChip(
                        text = char,
                        selected = state.selectedCharacter == char,
                        onClick = { viewModel.selectCharacter(char) }
                    )
                }
            }
        }
    }
}

// ═══════════════════════════════════════════════════════════════
// Shared Comic Components
// ═══════════════════════════════════════════════════════════════

/** Speech-bubble styled title for each step */
@Composable
private fun ComicSpeechTitle(text: String, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .background(
                NexusRed,
                RoundedCornerShape(topStart = 14.dp, topEnd = 14.dp, bottomEnd = 14.dp, bottomStart = 3.dp)
            )
            .border(
                2.5.dp,
                ComicBlack,
                RoundedCornerShape(topStart = 14.dp, topEnd = 14.dp, bottomEnd = 14.dp, bottomStart = 3.dp)
            )
            .padding(horizontal = 16.dp, vertical = 10.dp)
    ) {
        Text(
            text,
            fontFamily = NexusDisplayFont,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = ComicYellow,
            letterSpacing = 1.sp
        )
    }
}

/** Comic-styled selection chip with thick borders and starburst on selection */
@Composable
private fun ComicChip(
    text: String,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val bgColor = if (selected) NexusRed else ComicAgedPaper
    val textColor = if (selected) ComicYellow else ComicBlack
    val borderColor = if (selected) ComicBlack else PanelBorder.copy(alpha = 0.5f)
    val borderW = if (selected) 2.5.dp else 1.5.dp

    Box(
        modifier = modifier
            .height(44.dp)
            .clip(RoundedCornerShape(6.dp))
            .border(borderW, borderColor, RoundedCornerShape(6.dp))
            .background(bgColor)
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text,
            fontFamily = ComicHandFont,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            color = textColor,
            modifier = Modifier.padding(horizontal = 12.dp)
        )
    }
}
