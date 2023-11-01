package org.hungrytessy.indycarsuperfan.ui.driver

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.hungrytessy.indycarsuperfan.data.IndyDataStore
import org.hungrytessy.indycarsuperfan.data.models.CompetitorEventResult
import org.hungrytessy.indycarsuperfan.data.models.CompetitorEventSummary
import org.hungrytessy.indycarsuperfan.data.models.Driver
import org.hungrytessy.indycarsuperfan.data.models.Season

class DriverViewModel : ViewModel() {

    private val _driver = MutableLiveData<Driver>()
    val driver: LiveData<Driver> = _driver

    private val _driverResultsAll = MutableLiveData<Map<Season, CompetitorEventSummary>>()
    val driverResultsAll: LiveData<Map<Season, CompetitorEventSummary>> = _driverResultsAll

    private val _driverResultsCurrentSeasons = MutableLiveData<CompetitorEventSummary?>()
    val driverResultsCurrentSeasons: LiveData<CompetitorEventSummary?> = _driverResultsCurrentSeasons

    fun initialize(id: String) {
        _driver.value = IndyDataStore.drivers[id]
        _driverResultsAll.value = IndyDataStore.getResultsDriverAllSeasons(id)
        _driverResultsCurrentSeasons.value = IndyDataStore.getResultsDriverCurrentSeason(id)
    }
}