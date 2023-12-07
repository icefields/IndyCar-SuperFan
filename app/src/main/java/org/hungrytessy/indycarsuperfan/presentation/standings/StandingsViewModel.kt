package org.hungrytessy.indycarsuperfan.presentation.standings

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.hungrytessy.indycarsuperfan.data.remote.dto.CompetitorEventSummaryDto
import org.hungrytessy.indycarsuperfan.domain.model.CompetitorEventSummary
import org.hungrytessy.indycarsuperfan.domain.repository.IndyRepository
import javax.inject.Inject

@HiltViewModel
class StandingsViewModel @Inject constructor(
    private val indyRepository: IndyRepository
): ViewModel() {
    private val _currentStandings = MutableLiveData<List<CompetitorEventSummary>>()
    val currentStandings: LiveData<List<CompetitorEventSummary>> = _currentStandings

    init {
        viewModelScope.launch {
            _currentStandings.value = indyRepository.getCurrentStanding()
        }
    }
}
