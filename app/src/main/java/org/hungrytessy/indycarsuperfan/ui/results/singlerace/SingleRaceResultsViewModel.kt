package org.hungrytessy.indycarsuperfan.ui.results.singlerace

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.hungrytessy.indycarsuperfan.data.IndyDataStore
import org.hungrytessy.indycarsuperfan.data.models.Driver
import org.hungrytessy.indycarsuperfan.data.models.Venue
import org.hungrytessy.indycarsuperfan.data.models.indy.RaceWeekend

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
