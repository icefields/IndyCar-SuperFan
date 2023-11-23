package org.hungrytessy.indycarsuperfan.ui.schedule

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.hungrytessy.indycarsuperfan.data.IndyDataStore
import org.hungrytessy.indycarsuperfan.data.remote.dto.Stage

class FutureRacesViewModel : ViewModel() {

    private val _futureRaces = MutableLiveData<List<Stage>>()
    val futureRaces: LiveData<List<Stage>> = _futureRaces

    fun fetchFutureRaces() {
        _futureRaces.value = IndyDataStore.getUpcomingRaces()
    }
}
