package com.nexus.app.features.onboarding.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nexus.app.core.data.local.UserPreferencesDataStore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class OnboardingState(
    val currentStep: Int = 0,
    val totalSteps: Int = 5,
    val selectedPublishers: List<String> = emptyList(),
    val selectedMood: String = "",
    val entryPoint: String = "",
    val selectedCharacter: String = "",
    val isComplete: Boolean = false
)

@HiltViewModel
class OnboardingViewModel @Inject constructor(
    private val prefs: UserPreferencesDataStore
) : ViewModel() {
    private val _state = MutableStateFlow(OnboardingState())
    val state: StateFlow<OnboardingState> = _state

    fun selectPublishers(publishers: List<String>) {
        _state.value = _state.value.copy(selectedPublishers = publishers)
    }
    fun selectMood(mood: String) {
        _state.value = _state.value.copy(selectedMood = mood)
    }
    fun selectEntryPoint(entry: String) {
        _state.value = _state.value.copy(entryPoint = entry)
    }
    fun selectCharacter(character: String) {
        _state.value = _state.value.copy(selectedCharacter = character)
    }
    fun nextStep() {
        val next = _state.value.currentStep + 1
        if (next >= _state.value.totalSteps) {
            // Save preferences and mark complete
            viewModelScope.launch {
                prefs.saveOnboardingComplete(
                    publishers = _state.value.selectedPublishers,
                    mood = _state.value.selectedMood,
                    entryPoint = _state.value.entryPoint,
                    character = _state.value.selectedCharacter
                )
                _state.value = _state.value.copy(currentStep = next, isComplete = true)
            }
        } else {
            _state.value = _state.value.copy(currentStep = next)
        }
    }
    fun prevStep() {
        if (_state.value.currentStep > 0)
            _state.value = _state.value.copy(currentStep = _state.value.currentStep - 1)
    }
}
