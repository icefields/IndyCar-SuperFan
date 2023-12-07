package org.hungrytessy.indycarsuperfan.presentation.schedule

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.hungrytessy.indycarsuperfan.data.remote.dto.Stage
import org.hungrytessy.indycarsuperfan.domain.repository.IndyRepository
import javax.inject.Inject

@HiltViewModel
class FutureRacesViewModel @Inject constructor(
    private val indyRepository: IndyRepository
): ViewModel() {
    private val _futureRaces = MutableLiveData<List<Stage>>()
    val futureRaces: LiveData<List<Stage>> = _futureRaces

    init {
        viewModelScope.launch {
            _futureRaces.value  = indyRepository.getUpcomingRaces()
        }
    }
}
