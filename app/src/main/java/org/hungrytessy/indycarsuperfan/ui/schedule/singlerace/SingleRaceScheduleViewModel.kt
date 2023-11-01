package org.hungrytessy.indycarsuperfan.ui.schedule.singlerace

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.hungrytessy.indycarsuperfan.data.IndyDataStore
import org.hungrytessy.indycarsuperfan.data.models.Driver
import org.hungrytessy.indycarsuperfan.data.models.Venue
import org.hungrytessy.indycarsuperfan.data.models.indy.RaceWeekend

class SingleRaceScheduleViewModel : ViewModel() {

    private val _venue = MutableLiveData<Venue>()
    val venue: LiveData<Venue> = _venue

    private val _raceWeekend = MutableLiveData<RaceWeekend>()
    val raceWeekend: LiveData<RaceWeekend> = _raceWeekend

    private val _pastWinners = MutableLiveData<Map<String, Driver>>()
    val pastWinners: LiveData<Map<String, Driver>> = _pastWinners

    fun initialize(raceId: String) {
        val raceWeekend = IndyDataStore.getRaceResults(raceId)
        _raceWeekend.value = raceWeekend
        raceWeekend?.venue?.let {
            _venue.value = it
            _pastWinners.value = IndyDataStore.getPastWinners(it)
        }
    }
}