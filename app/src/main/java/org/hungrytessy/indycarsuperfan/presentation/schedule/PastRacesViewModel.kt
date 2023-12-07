package org.hungrytessy.indycarsuperfan.presentation.schedule

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.hungrytessy.indycarsuperfan.domain.model.RaceWeekend
import org.hungrytessy.indycarsuperfan.domain.repository.IndyRepository
import javax.inject.Inject


@HiltViewModel
class PastRacesViewModel @Inject constructor(
    private val indyRepository: IndyRepository
): ViewModel() {
    private val _pastRaces = MutableLiveData<List<RaceWeekend>>()
    val pastRaces: LiveData<List<RaceWeekend>> = _pastRaces

    init {
        viewModelScope.launch {
            val list = indyRepository.getPastRaceWeekends()
            _pastRaces.value = list
        }
    }
}
