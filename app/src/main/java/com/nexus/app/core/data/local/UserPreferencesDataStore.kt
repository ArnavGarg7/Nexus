package com.nexus.app.core.data.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "nexus_prefs")

@Singleton
class UserPreferencesDataStore @Inject constructor(
    @ApplicationContext private val context: Context
) {
    companion object {
        val KEY_ONBOARDING_COMPLETE = booleanPreferencesKey("onboarding_complete")
        val KEY_PUBLISHERS = stringPreferencesKey("selected_publishers")
        val KEY_MOOD = stringPreferencesKey("selected_mood")
        val KEY_ENTRY_POINT = stringPreferencesKey("entry_point")
        val KEY_CHARACTER = stringPreferencesKey("selected_character")
        val KEY_DISPLAY_NAME = stringPreferencesKey("display_name")
        val KEY_FAN_LEVEL = intPreferencesKey("fan_level")
        val KEY_XP_POINTS = intPreferencesKey("xp_points")
        val KEY_STREAK_DAYS = intPreferencesKey("streak_days")
    }

    val isOnboardingComplete: Flow<Boolean> = context.dataStore.data.map { prefs ->
        prefs[KEY_ONBOARDING_COMPLETE] ?: false
    }

    val selectedPublishers: Flow<List<String>> = context.dataStore.data.map { prefs ->
        prefs[KEY_PUBLISHERS]?.split(",")?.filter { it.isNotBlank() } ?: emptyList()
    }

    val selectedMood: Flow<String> = context.dataStore.data.map { prefs ->
        prefs[KEY_MOOD] ?: ""
    }

    suspend fun saveOnboardingComplete(
        publishers: List<String>,
        mood: String,
        entryPoint: String,
        character: String
    ) {
        context.dataStore.edit { prefs ->
            prefs[KEY_ONBOARDING_COMPLETE] = true
            prefs[KEY_PUBLISHERS] = publishers.joinToString(",")
            prefs[KEY_MOOD] = mood
            prefs[KEY_ENTRY_POINT] = entryPoint
            prefs[KEY_CHARACTER] = character
            prefs[KEY_DISPLAY_NAME] = "Explorer"
            prefs[KEY_FAN_LEVEL] = 1
            prefs[KEY_XP_POINTS] = 0
            prefs[KEY_STREAK_DAYS] = 0
        }
    }

    suspend fun addXp(xp: Int) {
        context.dataStore.edit { prefs ->
            val current = prefs[KEY_XP_POINTS] ?: 0
            val newXp = current + xp
            prefs[KEY_XP_POINTS] = newXp
            prefs[KEY_FAN_LEVEL] = 1 + (newXp / 200)
        }
    }

    suspend fun isOnboardingDone(): Boolean =
        context.dataStore.data.first()[KEY_ONBOARDING_COMPLETE] ?: false

    suspend fun getUserProfile(): UserPrefs {
        val prefs = context.dataStore.data.first()
        return UserPrefs(
            displayName = prefs[KEY_DISPLAY_NAME] ?: "Explorer",
            fanLevel = prefs[KEY_FAN_LEVEL] ?: 1,
            xpPoints = prefs[KEY_XP_POINTS] ?: 0,
            streakDays = prefs[KEY_STREAK_DAYS] ?: 0,
            publishers = prefs[KEY_PUBLISHERS]?.split(",")?.filter { it.isNotBlank() } ?: emptyList(),
            mood = prefs[KEY_MOOD] ?: "",
            entryPoint = prefs[KEY_ENTRY_POINT] ?: "",
            character = prefs[KEY_CHARACTER] ?: ""
        )
    }
}

data class UserPrefs(
    val displayName: String,
    val fanLevel: Int,
    val xpPoints: Int,
    val streakDays: Int,
    val publishers: List<String>,
    val mood: String,
    val entryPoint: String,
    val character: String
)
