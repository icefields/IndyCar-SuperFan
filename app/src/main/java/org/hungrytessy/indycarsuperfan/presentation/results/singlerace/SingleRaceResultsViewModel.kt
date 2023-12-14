package org.hungrytessy.indycarsuperfan.presentation.results.singlerace

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.hungrytessy.indycarsuperfan.domain.model.RaceWeekend
import org.hungrytessy.indycarsuperfan.domain.model.Venue
import org.hungrytessy.indycarsuperfan.domain.repository.IndyRepository
import javax.inject.Inject

@HiltViewModel
class SingleRaceResultsViewModel @Inject constructor(
    val repository: IndyRepository
) : ViewModel() {

    private val _venue = MutableLiveData<Venue>()
    val venue: LiveData<Venue> = _venue

    private val _raceWeekend = MutableLiveData<RaceWeekend>()
    val raceWeekend: LiveData<RaceWeekend> = _raceWeekend

    fun initialize(raceId: String) {
        viewModelScope.launch {
            val raceWeekend = repository.getRaceResults(raceId)
            _raceWeekend.value = raceWeekend
            raceWeekend?.venue?.let {
                _venue.value = it
            }
        }
    }
}
