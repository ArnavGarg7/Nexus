package com.nexus.app.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.nexus.app.features.character.ui.CharacterDetailScreen
import com.nexus.app.features.events.ui.EventDetailScreen
import com.nexus.app.features.events.ui.EventListScreen
import com.nexus.app.features.home.ui.HomeScreen
import com.nexus.app.features.lore.ui.LoreExplorerScreen
import com.nexus.app.features.mediabridge.ui.MediaBridgeScreen
import com.nexus.app.features.auth.ui.AuthScreen
import com.nexus.app.features.onboarding.ui.OnboardingScreen
import com.nexus.app.features.pathfinder.ui.PathfinderScreen
import com.nexus.app.features.profile.ui.ProfileScreen
import com.nexus.app.features.quiz.ui.QuizHubScreen
import com.nexus.app.features.quiz.ui.QuizScreen
import com.nexus.app.features.reading.ui.ReadingOrderScreen
import com.nexus.app.features.search.ui.SearchScreen

@Composable
fun NexusNavHost(
    navController: NavHostController,
    startDestination: String = Screen.Onboarding.route
) {
    NavHost(navController = navController, startDestination = startDestination) {
        composable(Screen.Onboarding.route)  { OnboardingScreen(navController) }
        composable(Screen.Auth.route)         { AuthScreen(navController) }
        composable(Screen.Home.route)        { HomeScreen(navController) }
        composable(Screen.Search.route)      { SearchScreen(navController) }
        composable(Screen.PathFinder.route)  { PathfinderScreen(navController) }
        composable(Screen.QuizHub.route)     { QuizHubScreen(navController) }
        composable(Screen.Profile.route)     { ProfileScreen(navController) }
        composable(Screen.EventList.route)   { EventListScreen(navController) }

        composable(Screen.Character.route) { backStack ->
            val id = backStack.arguments?.getString("characterId") ?: return@composable
            CharacterDetailScreen(navController = navController, characterId = id)
        }
        composable(Screen.ReadingOrder.route) { backStack ->
            val id   = backStack.arguments?.getString("entityId") ?: return@composable
            val type = backStack.arguments?.getString("entityType") ?: return@composable
            ReadingOrderScreen(navController = navController, entityId = id, entityType = type)
        }
        composable(Screen.EventDetail.route) { backStack ->
            val id = backStack.arguments?.getString("eventId") ?: return@composable
            EventDetailScreen(navController = navController, eventId = id)
        }
        composable(Screen.MediaBridge.route) { backStack ->
            val id = backStack.arguments?.getString("mediaId") ?: return@composable
            MediaBridgeScreen(navController = navController, mediaId = id)
        }
        composable(Screen.LoreExplorer.route) { backStack ->
            val id = backStack.arguments?.getString("characterId") ?: return@composable
            LoreExplorerScreen(navController = navController, characterId = id)
        }
        composable(Screen.Quiz.route) { backStack ->
            val id = backStack.arguments?.getString("quizId") ?: return@composable
            QuizScreen(navController = navController, quizId = id)
        }
    }
}
