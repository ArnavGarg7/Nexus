package com.nexus.app.presentation.navigation

sealed class Screen(val route: String) {
    object Splash      : Screen("splash")
    object Onboarding  : Screen("onboarding")
    object Auth        : Screen("auth")
    object Home        : Screen("home")
    object Search      : Screen("search")
    object Character   : Screen("character/{characterId}") {
        fun createRoute(id: String) = "character/$id"
    }
    object PathFinder  : Screen("pathfinder")
    object ReadingOrder: Screen("reading_order/{entityId}/{entityType}") {
        fun createRoute(id: String, type: String) = "reading_order/$id/$type"
    }
    object EventList   : Screen("events")
    object EventDetail : Screen("event/{eventId}") {
        fun createRoute(id: String) = "event/$id"
    }
    object MediaBridge : Screen("media_bridge/{mediaId}") {
        fun createRoute(id: String) = "media_bridge/$id"
    }
    object LoreExplorer: Screen("lore/{characterId}") {
        fun createRoute(id: String) = "lore/$id"
    }
    object Quiz        : Screen("quiz/{quizId}") {
        fun createRoute(id: String) = "quiz/$id"
    }
    object QuizHub     : Screen("quiz_hub")
    object Profile     : Screen("profile")
    object Settings    : Screen("settings")
}
