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
class ScheduleViewModel @Inject constructor(
    private val indyRepository: IndyRepository
): ViewModel() {
    private val _futureRaces = MutableLiveData<List<RaceWeekend>>()
    val futureRaces: LiveData<List<RaceWeekend>> = _futureRaces

    fun fetchFutureRaces() {
        viewModelScope.launch {
            _futureRaces.value = indyRepository.getUpcomingRaces()
        }
    }
}
