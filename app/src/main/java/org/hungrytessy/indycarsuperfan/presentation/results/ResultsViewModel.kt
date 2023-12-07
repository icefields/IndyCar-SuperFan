package org.hungrytessy.indycarsuperfan.presentation.results

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import org.hungrytessy.indycarsuperfan.data.remote.dto.Stage
import org.hungrytessy.indycarsuperfan.domain.repository.IndyRepository
import javax.inject.Inject

@HiltViewModel
class ResultsViewModel @Inject constructor(
    private val indyRepository: IndyRepository
) : ViewModel() {

    private val _pastRaces = MutableLiveData<List<Stage>>()
    val pastRaces: LiveData<List<Stage>> = _pastRaces

    init {
        _pastRaces.value = indyRepository.getCurrentSeasonFinishedRaces()
    }
}
