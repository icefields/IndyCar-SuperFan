package org.hungrytessy.indycarsuperfan.presentation.schedule.singlerace

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.hungrytessy.indycarsuperfan.data.IndyDataStore
import org.hungrytessy.indycarsuperfan.data.remote.dto.Driver
import org.hungrytessy.indycarsuperfan.data.remote.dto.Venue
import org.hungrytessy.indycarsuperfan.domain.model.RaceWeekend
import org.hungrytessy.indycarsuperfan.domain.repository.IndyRepository
import javax.inject.Inject

@HiltViewModel
class SingleRaceScheduleViewModel @Inject constructor(
    private val indyRepository: IndyRepository
): ViewModel() {
    private val _venue = MutableLiveData<Venue>()
    val venue: LiveData<Venue> = _venue

    private val _raceWeekend = MutableLiveData<RaceWeekend>()
    val raceWeekend: LiveData<RaceWeekend> = _raceWeekend

    private val _pastWinners = MutableLiveData<Map<String, Driver>>()
    val pastWinners: LiveData<Map<String, Driver>> = _pastWinners

    fun initialize(raceId: String) {
        viewModelScope.launch {
            val raceWeekend = indyRepository.getRaceResults(raceId)
            _raceWeekend.value = raceWeekend
            raceWeekend?.venue?.let {
                _venue.value = it
                _pastWinners.value = IndyDataStore.getPastWinners(it)
            }
        }
    }
}
