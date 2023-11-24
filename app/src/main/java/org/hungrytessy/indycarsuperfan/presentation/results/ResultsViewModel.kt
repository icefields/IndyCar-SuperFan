package org.hungrytessy.indycarsuperfan.presentation.results

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.hungrytessy.indycarsuperfan.data.IndyDataStore
import org.hungrytessy.indycarsuperfan.data.remote.dto.Stage

class ResultsViewModel : ViewModel() {

    private val _pastRaces = MutableLiveData<List<Stage>>()
    val pastRaces: LiveData<List<Stage>> = _pastRaces

    fun fetchCurrentSeasonResults() {
        _pastRaces.value = IndyDataStore.getCurrentSeasonFinishedRaces()
    }
}