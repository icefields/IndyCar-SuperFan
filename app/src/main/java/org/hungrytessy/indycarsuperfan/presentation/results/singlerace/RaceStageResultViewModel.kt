package org.hungrytessy.indycarsuperfan.presentation.results.singlerace

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.hungrytessy.indycarsuperfan.domain.model.SingleRaceStage
import org.hungrytessy.indycarsuperfan.domain.repository.IndyRepository
import javax.inject.Inject

@HiltViewModel
class RaceStageResultViewModel @Inject constructor(
    val repository: IndyRepository
) : ViewModel() {

    private val _stageResult = MutableLiveData<SingleRaceStage>()
    val stageResult: LiveData<SingleRaceStage> = _stageResult

    fun initialize(raceId: String, stageId: String) {
        viewModelScope.launch {
            val results = repository.getRaceResults(raceId)
            if (results?.race?.id == stageId) {
                _stageResult.value = results.race
                return@launch
            } else {
                results?.qualification?.qualificationStages?.let {
                    for (qualiStage in it) {
                        if (qualiStage.id == stageId) {
                            _stageResult.value = qualiStage
                            return@launch
                        }
                    }
                }
            }
        }
    }
}
