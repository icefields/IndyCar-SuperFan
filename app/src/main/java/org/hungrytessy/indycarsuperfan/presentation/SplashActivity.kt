package org.hungrytessy.indycarsuperfan.presentation

import androidx.appcompat.app.AppCompatActivity
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import org.hungrytessy.indycarsuperfan.common.isDarkModeOn

const val FORCE_NIGHT_MODE = true

@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {

    private val viewModel: SplashViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.dataReady.observe(this) { goToMain() }

        // this version only supports night mode
        if (FORCE_NIGHT_MODE) {
            forceNightMode()
        }
    }

    override fun onResume() {
        super.onResume()
        // avoid making network calls twice when refreshing the screen after forcing dark mode
        if (!FORCE_NIGHT_MODE || FORCE_NIGHT_MODE && isDarkModeOn()) {
            lifecycleScope.launch {
                viewModel.generate()
            }
        }
    }

    private fun forceNightMode() {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        delegate.applyDayNight()
    }

    private fun goToMain() {
        startActivity(Intent(this, MainActivity::class.java))
    }
}
