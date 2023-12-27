package org.hungrytessy.indycarsuperfan.presentation

import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import dagger.hilt.android.AndroidEntryPoint
import org.hungrytessy.indycarsuperfan.MainNavDirections
import org.hungrytessy.indycarsuperfan.R
import org.hungrytessy.indycarsuperfan.databinding.ActivityMainBinding
import org.hungrytessy.indycarsuperfan.common.navController

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), MainActivityNav {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_activity_main)

        // Passing each menu ID as a set of Ids because each menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home,
                R.id.navigation_news,
                R.id.navigation_schedule,
                R.id.navigation_results,
                R.id.navigation_standings,
                R.id.navigation_drivers,
            )
        )

        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    override fun onSupportNavigateUp(): Boolean = navController?.navigateUp() == true || super.onSupportNavigateUp()

    override fun goToDriverProfile(driverId: String) {
        val action = MainNavDirections.actionGlobalDriverFragment(driverId = driverId)
        navController?.navigate(action)
    }

    override fun goToWeekendRaceResult(raceId: String) {
        val action = MainNavDirections.actionGlobalRaceWeekendFragment(raceId = raceId)
        navController?.navigate(action)
    }

    override fun goToWeekendRaceSchedule(raceId: String) {
        val action = MainNavDirections.actionGlobalRaceWeekendScheduleFragment(raceId = raceId)
        navController?.navigate(action)
    }

    override fun switchToResults() {
        binding.navView.selectedItemId = R.id.navigation_standings
    }
}
