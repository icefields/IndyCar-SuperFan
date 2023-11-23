package org.hungrytessy.indycarsuperfan.ui.schedule

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.hungrytessy.indycarsuperfan.data.IndyDataStore
import org.hungrytessy.indycarsuperfan.data.remote.dto.indy.RaceWeekend

class PastRacesViewModel : ViewModel() {

    private val _pastRaces = MutableLiveData<List<RaceWeekend>>()
    val pastRaces: LiveData<List<RaceWeekend>> = _pastRaces

    fun fetchPastRaces() {
        val list = IndyDataStore.getPastRaceWeekends()
//        for (stage in list) {
//            //stage.stageSummary?.competitors = IndyDataStore.raceWeekends[stage.id]?.race?.result
//        }
        _pastRaces.value = list
    }
}
