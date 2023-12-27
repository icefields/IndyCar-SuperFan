package org.hungrytessy.indycarsuperfan.presentation

import android.os.Bundle
import androidx.appcompat.content.res.AppCompatResources
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import org.hungrytessy.indycarsuperfan.R
import org.hungrytessy.indycarsuperfan.domain.model.Driver
import java.lang.ref.WeakReference

abstract class IndyFragment : Fragment(), FragmentLogger by FragmentLoggerImpl() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (requireActivity() is MainActivity) {
            (requireActivity() as MainActivity).supportActionBar?.setHomeAsUpIndicator(AppCompatResources.getDrawable(requireContext(),
                R.drawable.ic_back_arrow
            ))
        }
        registerLifecycleOwner(this)
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
            driver?.id?.let { (activity as MainActivityNav).goToDriverProfile(it) }
        }
    }
}

interface FragmentLogger {
    fun registerLifecycleOwner(owner: LifecycleOwner)
}

class FragmentLoggerImpl: FragmentLogger, LifecycleEventObserver {
    private var weakOwner: WeakReference<LifecycleOwner>? = null

    override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
        when(event) {
            Lifecycle.Event.ON_CREATE -> {

            }
            Lifecycle.Event.ON_START -> {

            }
            Lifecycle.Event.ON_RESUME -> {

            }
            Lifecycle.Event.ON_PAUSE -> {

            }
            Lifecycle.Event.ON_STOP -> {

            }
            Lifecycle.Event.ON_DESTROY -> {
                weakOwner?.get()?.let {
                    it.lifecycle.removeObserver(this)
                }
            }
            Lifecycle.Event.ON_ANY -> {

            }
        }
    }

    override fun registerLifecycleOwner(owner: LifecycleOwner) {
        weakOwner = WeakReference(owner)
        owner.lifecycle.addObserver(this)
    }
}
