package org.hungrytessy.indycarsuperfan.ui.drivers

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.hungrytessy.indycarsuperfan.data.IndyDataStore
import org.hungrytessy.indycarsuperfan.data.models.Driver


class DriverListViewModel : ViewModel() {
    private val _driverList = MutableLiveData<List<Driver>>()
    val driverList: LiveData<List<Driver>> = _driverList

    fun fetchDrivers() {
        val list = ArrayList<Driver>()
        for(driverId in IndyDataStore.drivers.keys) {
            IndyDataStore.drivers[driverId]?.let {
                list.add(it)
            }
        }
        list.sortWith(compareBy { if (it.getTeamsString().isNotBlank()) {it.getTeamsString().lowercase()} else { "zzz${it.competitor?.name?.lowercase()}" }})
        _driverList.value = (list)
    }
}
