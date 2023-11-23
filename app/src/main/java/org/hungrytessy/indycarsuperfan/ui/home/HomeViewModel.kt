package org.hungrytessy.indycarsuperfan.ui.home

import android.os.CountDownTimer
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.hungrytessy.indycarsuperfan.data.IndyDataStore
import org.hungrytessy.indycarsuperfan.data.models.CompetitorEventSummary
import org.hungrytessy.indycarsuperfan.data.models.Stage
import org.hungrytessy.indycarsuperfan.extensions.addZeroToSingleDigit
import java.time.Duration
import java.time.LocalDateTime
import kotlin.math.abs

class HomeViewModel(private val indyDataStore: IndyDataStore) : ViewModel() {

    private val _nextRace = MutableLiveData<Stage>()
    val nextRace: LiveData<Stage> = _nextRace

    private val _currentStandings = MutableLiveData<List<CompetitorEventSummary>>()
    val currentStandings: LiveData<List<CompetitorEventSummary>> = _currentStandings

    private val _countdownString = MutableLiveData<String>()
    val countdownString: LiveData<String> = _countdownString

    private lateinit var timer: CountDownTimer

    fun fetchNextRace() {
        indyDataStore.getNextRace()?.let { race ->
            _nextRace.value = race
            initTimer(race)
        }
    }

    fun fetchCurrentStandings() {
        _currentStandings.value = indyDataStore.getCurrentStanding()
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
