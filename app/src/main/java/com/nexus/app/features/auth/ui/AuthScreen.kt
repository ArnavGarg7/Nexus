package com.nexus.app.features.auth.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.nexus.app.features.auth.viewmodel.AuthViewModel
import com.nexus.app.presentation.common.components.*
import com.nexus.app.presentation.navigation.Screen
import com.nexus.app.presentation.theme.*

@Composable
fun AuthScreen(
    navController: NavController,
    viewModel: AuthViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()

    // Navigate to Home when authenticated
    LaunchedEffect(state.isAuthenticated) {
        if (state.isAuthenticated) {
            navController.navigate(Screen.Home.route) {
                popUpTo(Screen.Auth.route) { inclusive = true }
            }
        }
    }

    var passwordVisible by remember { mutableStateOf(false) }

    // ── Aged Paper Background ──
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(ComicAgedPaper)
    ) {
        HalftoneOverlay(dotColor = HalftoneBase, dotRadius = 2f, spacing = 12f)

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(Modifier.height(40.dp))

            // ── NEXUS Title with Starburst ──
            val titleScale by animatedPanelScale(delayMs = 100)
            Box(
                modifier = Modifier
                    .scale(titleScale)
                    .size(160.dp),
                contentAlignment = Alignment.Center
            ) {
                StarburstBackground(
                    modifier = Modifier.fillMaxSize(),
                    fillColor = ComicYellow,
                    outlineColor = ComicBlack,
                    points = 14
                )
                Text(
                    "NEXUS",
                    fontFamily = NexusDisplayFont,
                    fontSize = 42.sp,
                    fontWeight = FontWeight.Bold,
                    color = NexusRed,
                    letterSpacing = 3.sp
                )
            }

            Spacer(Modifier.height(20.dp))

            // ── Auth Form Panel ──
            val formScale by animatedPanelScale(delayMs = 200)
            ComicBookPanel(
                modifier = Modifier
                    .scale(formScale)
                    .fillMaxWidth()
                    .shadow(6.dp, RoundedCornerShape(4.dp)),
                rotationDeg = 0f,
                borderWidth = 3.dp,
                backgroundColor = Color.White.copy(alpha = 0.95f)
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    // Title
                    Text(
                        if (state.isSignUp) "JOIN THE NEXUS!" else "ENTER THE NEXUS!",
                        fontFamily = NexusDisplayFont,
                        fontSize = 26.sp,
                        fontWeight = FontWeight.Bold,
                        color = ComicBlack,
                        letterSpacing = 1.5.sp,
                        textAlign = TextAlign.Center
                    )

                    Text(
                        if (state.isSignUp)
                            "Create your explorer account"
                        else
                            "Welcome back, explorer",
                        fontFamily = ComicHandFont,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color = NexusMuted,
                        textAlign = TextAlign.Center
                    )

                    // Email field
                    OutlinedTextField(
                        value = state.email,
                        onValueChange = { viewModel.onEmailChange(it) },
                        label = {
                            Text(
                                "EMAIL",
                                fontFamily = ComicHandFont,
                                fontWeight = FontWeight.Bold,
                                fontSize = 13.sp
                            )
                        },
                        leadingIcon = {
                            Icon(Icons.Default.Email, contentDescription = "Email")
                        },
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = NexusRed,
                            unfocusedBorderColor = PanelBorder.copy(alpha = 0.4f),
                            focusedLabelColor = NexusRed
                        ),
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier.fillMaxWidth()
                    )

                    // Password field
                    OutlinedTextField(
                        value = state.password,
                        onValueChange = { viewModel.onPasswordChange(it) },
                        label = {
                            Text(
                                "PASSWORD",
                                fontFamily = ComicHandFont,
                                fontWeight = FontWeight.Bold,
                                fontSize = 13.sp
                            )
                        },
                        leadingIcon = {
                            Icon(Icons.Default.Lock, contentDescription = "Password")
                        },
                        trailingIcon = {
                            IconButton(onClick = { passwordVisible = !passwordVisible }) {
                                Icon(
                                    if (passwordVisible) Icons.Default.Visibility
                                    else Icons.Default.VisibilityOff,
                                    contentDescription = "Toggle password"
                                )
                            }
                        },
                        singleLine = true,
                        visualTransformation = if (passwordVisible) VisualTransformation.None
                        else PasswordVisualTransformation(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = NexusRed,
                            unfocusedBorderColor = PanelBorder.copy(alpha = 0.4f),
                            focusedLabelColor = NexusRed
                        ),
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier.fillMaxWidth()
                    )

                    // Error message
                    AnimatedVisibility(visible = state.errorMessage != null) {
                        state.errorMessage?.let { msg ->
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .rotate(-0.5f)
                                    .background(NexusRed.copy(alpha = 0.1f), RoundedCornerShape(6.dp))
                                    .border(1.5.dp, NexusRed, RoundedCornerShape(6.dp))
                                    .padding(10.dp)
                            ) {
                                Text(
                                    msg,
                                    fontFamily = ComicHandFont,
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = NexusRed
                                )
                            }
                        }
                    }

                    // Submit button
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(52.dp)
                            .clip(RoundedCornerShape(6.dp))
                            .border(3.dp, ComicBlack, RoundedCornerShape(6.dp))
                            .background(
                                Brush.horizontalGradient(listOf(NexusRed, NexusRedDark))
                            )
                            .clickable(enabled = !state.isLoading) { viewModel.submit() },
                        contentAlignment = Alignment.Center
                    ) {
                        if (state.isLoading) {
                            CircularProgressIndicator(
                                color = ComicYellow,
                                modifier = Modifier.size(24.dp),
                                strokeWidth = 3.dp
                            )
                        } else {
                            Text(
                                if (state.isSignUp) "CREATE ACCOUNT!" else "SIGN IN!",
                                fontFamily = NexusDisplayFont,
                                fontSize = 19.sp,
                                fontWeight = FontWeight.Bold,
                                color = ComicYellow,
                                letterSpacing = 1.sp
                            )
                        }
                    }
                }
            }

            Spacer(Modifier.height(20.dp))

            // ── Toggle sign-up / sign-in ──
            val toggleScale by animatedPanelScale(delayMs = 350)
            Box(
                modifier = Modifier
                    .scale(toggleScale)
                    .rotate(0.8f)
                    .background(ComicYellowPale, RoundedCornerShape(6.dp))
                    .border(2.dp, ComicBlack, RoundedCornerShape(6.dp))
                    .clickable { viewModel.toggleMode() }
                    .padding(horizontal = 20.dp, vertical = 10.dp)
            ) {
                Text(
                    if (state.isSignUp)
                        "Already an explorer? SIGN IN →"
                    else
                        "New explorer? SIGN UP →",
                    fontFamily = ComicHandFont,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = ComicBlack
                )
            }

            Spacer(Modifier.height(30.dp))

            // ── Skip auth for now (optional) ──
            TextButton(
                onClick = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Auth.route) { inclusive = true }
                    }
                }
            ) {
                Text(
                    "Skip for now →",
                    fontFamily = ComicHandFont,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    color = NexusMuted
                )
            }
        }
    }
}
