package org.hungrytessy.indycarsuperfan.ui

import androidx.appcompat.app.AppCompatActivity
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatDelegate
import org.hungrytessy.indycarsuperfan.MainActivity
import org.hungrytessy.indycarsuperfan.data.IndyDataStore

class SplashActivity : AppCompatActivity() {

    //private lateinit var binding: ActivitySplashBinding
    //private val hideHandler = Handler(Looper.myLooper()!!)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //binding = ActivitySplashBinding.inflate(layoutInflater)
        //setContentView(binding.root)

        //supportActionBar?.setDisplayHomeAsUpEnabled(false)
        //Handler(Looper.getMainLooper()).postDelayed(this::goToMain, 1000)

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        delegate.applyDayNight()

    }

    private fun goToMain() {
        startActivity(
            Intent(this, MainActivity::class.java)
        )
    }

    override fun onStart() {
        super.onStart()
        IndyDataStore.generate(applicationContext) {
            if (it) {
                Handler(Looper.getMainLooper()).postDelayed(this::goToMain, 50)
            //goToMain()
            }
        }
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)

        // Trigger the initial hide() shortly after the activity has been
        // created, to briefly hint to the user that UI controls
        // are available.
    }
}