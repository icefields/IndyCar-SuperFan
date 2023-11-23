package org.hungrytessy.indycarsuperfan

import android.os.Bundle
import androidx.appcompat.content.res.AppCompatResources
import androidx.fragment.app.Fragment
import org.hungrytessy.indycarsuperfan.data.remote.dto.Driver

abstract class IndyFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (requireActivity() is MainActivity) {
            (requireActivity() as MainActivity).supportActionBar?.setHomeAsUpIndicator(AppCompatResources.getDrawable(requireContext(), R.drawable.ic_back_arrow));
        }
    }


    open fun goToWeekendRaceSchedule(raceId: String) {
        if (activity is MainActivityNav) {
            (activity as MainActivityNav).goToWeekendRaceSchedule(raceId)
        }
    }

    open fun goToWeekendRaceResults(raceId: String) {
        if (activity is MainActivityNav) {
            (activity as MainActivityNav).goToWeekendRaceResult(raceId)
        }
    }

    open fun onDriverClick(driver: Driver?) {
        if (activity is MainActivityNav) {
            driver?.competitor?.id?.let { (activity as MainActivityNav).goToDriverProfile(it) }
        }
    }
}
