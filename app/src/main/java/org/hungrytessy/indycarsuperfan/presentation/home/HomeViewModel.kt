package org.hungrytessy.indycarsuperfan.presentation.home

import android.os.CountDownTimer
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import org.hungrytessy.indycarsuperfan.data.remote.dto.CompetitorEventSummary
import org.hungrytessy.indycarsuperfan.data.remote.dto.Stage
import org.hungrytessy.indycarsuperfan.domain.repository.IndyRepository
import org.hungrytessy.indycarsuperfan.extensions.addZeroToSingleDigit
import java.time.Duration
import java.time.LocalDateTime
import javax.inject.Inject
import kotlin.math.abs

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val indyRepository: IndyRepository
): ViewModel() {
    private val _nextRace = MutableLiveData<Stage>()
    val nextRace: LiveData<Stage> = _nextRace

    private val _currentStandings = MutableLiveData<List<CompetitorEventSummary>>()
    val currentStandings: LiveData<List<CompetitorEventSummary>> = _currentStandings

    private val _countdownString = MutableLiveData<String>()
    val countdownString: LiveData<String> = _countdownString

    private lateinit var timer: CountDownTimer

    fun fetchNextRace() {
        indyRepository.getNextRace()?.let { race ->
            _nextRace.value = race
            initTimer(race)
        }
    }

    fun fetchCurrentStandings() {
        _currentStandings.value = indyRepository.getCurrentStanding()
    }

    private fun initTimer(race: Stage) {
        val durationTotal: Duration = Duration.between(LocalDateTime.now(), race.getScheduled())
        timer = object: CountDownTimer(abs(durationTotal.toMillis()), 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val duration: Duration = Duration.between(LocalDateTime.now(), race.getScheduled()).abs()
                _countdownString.value = "${duration.toDaysPart()}d ${duration.toHoursPart()}:${duration.toMinutesPart().addZeroToSingleDigit()}:${duration.toSecondsPart().addZeroToSingleDigit()}"
                //_binding?.countdownText?.text = countdownStr
            }

            override fun onFinish() {}
        }
        timer.start()
    }

    fun cleanup() {
        timer.cancel()
    }
}
