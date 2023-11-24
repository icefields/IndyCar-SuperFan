package org.hungrytessy.indycarsuperfan.presentation

import androidx.appcompat.app.AppCompatActivity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import org.hungrytessy.indycarsuperfan.data.IndyDataStore
import org.hungrytessy.indycarsuperfan.extensions.isDarkModeOn

const val FORCE_NIGHT_MODE = true

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
                IndyDataStore.generate(applicationContext)
                //Handler(Looper.getMainLooper()).postDelayed(this@SplashActivity::goToMain, 50)
                goToMain()
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
