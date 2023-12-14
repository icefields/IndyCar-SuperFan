package org.hungrytessy.indycarsuperfan.presentation.driver

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.hungrytessy.indycarsuperfan.common.Resource
import org.hungrytessy.indycarsuperfan.domain.model.CompetitorEventSummary
import org.hungrytessy.indycarsuperfan.domain.model.Driver
import org.hungrytessy.indycarsuperfan.domain.model.Season
import org.hungrytessy.indycarsuperfan.domain.repository.IndyRepository
import javax.inject.Inject

@HiltViewModel
class DriverViewModel @Inject constructor(
    val repository: IndyRepository
) : ViewModel() {

    private val _driver = MutableLiveData<Driver>()
    val driver: LiveData<Driver> = _driver

    private val _driverResultsAll = MutableLiveData<Map<Season, CompetitorEventSummary>>()
    val driverResultsAll: LiveData<Map<Season, CompetitorEventSummary>> = _driverResultsAll

    private val _driverResultsCurrentSeasons = MutableLiveData<CompetitorEventSummary?>()
    val driverResultsCurrentSeasons: LiveData<CompetitorEventSummary?> = _driverResultsCurrentSeasons

    fun initialize(id: String) {
        viewModelScope.launch {
            when(val driversResponse = repository.getDrivers()) {
                is Resource.Success -> {
                    driversResponse.data?.let { drivers ->
                        _driver.value = drivers[id]
                    }
                }
                is Resource.Error -> {
                    // TODO: implement error state
                }
                is Resource.Loading -> {
                    // TODO: implement loading
                }
            }

            _driverResultsAll.value = repository.getResultsDriverAllSeasons(id)
            _driverResultsCurrentSeasons.value = repository.getResultsDriverCurrentSeason(id)
        }
    }
}
