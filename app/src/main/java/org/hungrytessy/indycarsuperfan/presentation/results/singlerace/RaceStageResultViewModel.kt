package org.hungrytessy.indycarsuperfan.presentation.results.singlerace

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.hungrytessy.indycarsuperfan.data.IndyDataStore
import org.hungrytessy.indycarsuperfan.domain.model.SingleRaceStage

class RaceStageResultViewModel : ViewModel() {

    private val _stageResult = MutableLiveData<SingleRaceStage>()
    val stageResult: LiveData<SingleRaceStage> = _stageResult

    fun initialize(raceId: String, stageId: String) {
        val results = IndyDataStore.getRaceResults(raceId)
        if (results?.race?.id == stageId) {
            _stageResult.value = results.race
            return
        } else {
            results?.qualification?.qualificationStages?.let {
                for (qualiStage in it) {
                    if (qualiStage.id == stageId) {
                        _stageResult.value = qualiStage
                        return
                    }
                }
            }

        }
    }
}