package org.hungrytessy.indycarsuperfan.presentation.results.singlerace

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.hungrytessy.indycarsuperfan.data.IndyDataStore
import org.hungrytessy.indycarsuperfan.data.remote.dto.Venue
import org.hungrytessy.indycarsuperfan.domain.model.RaceWeekend

class SingleRaceResultsViewModel : ViewModel() {

    private val _venue = MutableLiveData<Venue>()
    val venue: LiveData<Venue> = _venue

    private val _raceWeekend = MutableLiveData<RaceWeekend>()
    val raceWeekend: LiveData<RaceWeekend> = _raceWeekend

    fun initialize(raceId: String) {
        val raceWeekend = IndyDataStore.getRaceResults(raceId)
        _raceWeekend.value = raceWeekend
        raceWeekend?.venue?.let {
            _venue.value = it
        }
    }
}
