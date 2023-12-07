package org.hungrytessy.indycarsuperfan.presentation.drivers

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.hungrytessy.indycarsuperfan.common.Resource
import org.hungrytessy.indycarsuperfan.domain.model.Driver
import org.hungrytessy.indycarsuperfan.domain.repository.IndyRepository
import javax.inject.Inject

@HiltViewModel
class DriverListViewModel @Inject constructor(
    val repository: IndyRepository
) : ViewModel() {
    private val _driverList = MutableLiveData<List<Driver>>()
    val driverList: LiveData<List<Driver>> = _driverList

    init {
        viewModelScope.launch {
            val list = ArrayList<Driver>()
            when(val driversResponse = repository.getDrivers()) {
                is Resource.Success -> {
                    driversResponse.data?.let { drivers ->
                        for(driverId in drivers.keys) {
                            drivers[driverId]?.let {
                                list.add(it)
                            }
                        }
                        list.sortWith(compareBy { if (it.getTeamsString().isNotBlank()) {it.getTeamsString().lowercase()} else { "zzz${it.name?.lowercase()}" }})
                        _driverList.value = (list)
                    }
                }
                is Resource.Error -> {
                    // TODO: implement error state
                }
                is Resource.Loading -> {
                    // TODO: implement loading
                }
            }

        }
    }
}
