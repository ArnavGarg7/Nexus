package com.nexus.app.features.quiz.ui

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.nexus.app.features.quiz.viewmodel.QuizViewModel
import com.nexus.app.presentation.common.components.NexusLoadingIndicator
import com.nexus.app.presentation.common.components.NexusPrimaryButton
import com.nexus.app.presentation.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuizScreen(
    navController: NavController,
    quizId: String,
    viewModel: QuizViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(state.quiz?.title ?: "Quiz") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, "Back")
                    }
                }
            )
        }
    ) { padding ->
        Box(modifier = Modifier.fillMaxSize().padding(padding)) {
            when {
                state.isLoading -> NexusLoadingIndicator()
                state.isComplete -> QuizResultScreen(state.score, state.quiz?.questions?.size ?: 0, state.quiz?.xpReward ?: 0) {
                    navController.popBackStack()
                }
                else -> state.quiz?.let { quiz ->
                    val question = quiz.questions[state.currentIndex]
                    Column(
                        modifier = Modifier.fillMaxSize().padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        // Progress bar styled as issue spine
                        LinearProgressIndicator(
                            progress = { (state.currentIndex + 1f) / quiz.questions.size },
                            modifier = Modifier.fillMaxWidth().height(6.dp).clip(RoundedCornerShape(3.dp)),
                            color = NexusRed,
                            trackColor = NexusDivider
                        )
                        Text(
                            "Question ${state.currentIndex + 1} of ${quiz.questions.size}",
                            fontSize = 13.sp, color = NexusMuted
                        )

                        // Question panel — comic-panel border
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .border(2.dp, NexusDivider, RoundedCornerShape(12.dp))
                                .background(MaterialTheme.colorScheme.surface, RoundedCornerShape(12.dp))
                                .padding(20.dp)
                        ) {
                            Text(question.question, fontSize = 18.sp, fontWeight = FontWeight.SemiBold,
                                lineHeight = 26.sp, textAlign = TextAlign.Start)
                        }

                        // Answer options as speech bubbles
                        Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                            question.options.forEachIndexed { index, option ->
                                AnswerOption(
                                    text = option,
                                    index = index,
                                    isSelected = state.selectedAnswer == index,
                                    isRevealed = state.isAnswerRevealed,
                                    isCorrect = index == question.correctAnswerIndex,
                                    onClick = { viewModel.selectAnswer(index) }
                                )
                            }
                        }

                        // Explanation after reveal
                        AnimatedVisibility(visible = state.isAnswerRevealed) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(NexusNavyLight, RoundedCornerShape(8.dp))
                                    .padding(12.dp)
                            ) {
                                Text(question.explanation, fontSize = 13.sp, color = NexusWhite, lineHeight = 20.sp)
                            }
                        }

                        Spacer(Modifier.weight(1f))

                        if (!state.isAnswerRevealed) {
                            NexusPrimaryButton("Reveal Answer", onClick = { viewModel.revealAnswer() },
                                modifier = Modifier.fillMaxWidth())
                        } else {
                            NexusPrimaryButton(
                                if (state.currentIndex < quiz.questions.size - 1) "Next Question" else "See Results",
                                onClick = { viewModel.nextQuestion() },
                                modifier = Modifier.fillMaxWidth()
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun AnswerOption(
    text: String, index: Int, isSelected: Boolean,
    isRevealed: Boolean, isCorrect: Boolean, onClick: () -> Unit
) {
    val bgColor = when {
        isRevealed && isCorrect  -> Color(0xFF1E8449)
        isRevealed && isSelected -> Color(0xFF922B21)
        isSelected               -> NexusNavyLight
        else                     -> MaterialTheme.colorScheme.surface
    }
    val borderColor = when {
        isSelected -> NexusRed
        else       -> NexusDivider
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .background(bgColor)
            .border(1.5.dp, borderColor, RoundedCornerShape(8.dp))
            .clickable(enabled = !isRevealed) { onClick() }
            .padding(14.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Box(
            modifier = Modifier.size(28.dp)
                .background(NexusRed, RoundedCornerShape(14.dp)),
            contentAlignment = Alignment.Center
        ) {
            Text("ABCD"[index].toString(), color = NexusWhite, fontWeight = FontWeight.Bold, fontSize = 13.sp)
        }
        Text(text, fontSize = 14.sp, fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Normal)
    }
}

@Composable
private fun QuizResultScreen(score: Int, total: Int, xpEarned: Int, onBack: () -> Unit) {
    val pct = if (total > 0) (score * 100) / total else 0
    Column(
        modifier = Modifier.fillMaxSize().padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Mock comic cover result
        Box(
            modifier = Modifier
                .size(200.dp)
                .border(4.dp, NexusRed, RoundedCornerShape(8.dp))
                .background(NexusNavy, RoundedCornerShape(8.dp)),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text("NEXUS", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = NexusRed)
                Text("PRESENTS", fontSize = 10.sp, color = NexusMuted)
                Spacer(Modifier.height(8.dp))
                Text("$score/$total", fontSize = 48.sp, fontWeight = FontWeight.Bold, color = NexusWhite)
                Text("$pct% Correct", fontSize = 14.sp, color = NexusGold)
            }
        }
        Spacer(Modifier.height(24.dp))
        Text(
            when {
                pct >= 90 -> "Lore Master! Legendary!"
                pct >= 70 -> "Hardcore Fan — Impressive!"
                pct >= 50 -> "Casual Fan — Not bad!"
                else      -> "Keep reading — the knowledge awaits!"
            },
            fontSize = 20.sp, fontWeight = FontWeight.Bold, textAlign = TextAlign.Center
        )
        Spacer(Modifier.height(8.dp))
        Text("+$xpEarned XP Earned", fontSize = 16.sp, color = NexusGold, fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(32.dp))
        NexusPrimaryButton("Back to Quiz Hub", onClick = onBack)
    }
}
