package com.rifqipadisiliwangi.aplikasigithubuser.view.splash

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatDelegate
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.rifqipadisiliwangi.aplikasigithubuser.databinding.ActivitySplashBinding
import com.rifqipadisiliwangi.aplikasigithubuser.uitls.preferences.DataStorePreferences
import com.rifqipadisiliwangi.aplikasigithubuser.view.home.HomeActivity
import com.rifqipadisiliwangi.aplikasigithubuser.viewmodel.home.ThemeViewModel
import com.rifqipadisiliwangi.aplikasigithubuser.viewmodel.home.ViewModelFactory

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
class SplashActivity : AppCompatActivity() {

    private lateinit var binding : ActivitySplashBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        Handler(Looper.myLooper()!!).postDelayed({
            startActivity(Intent(this, HomeActivity::class.java))
            finish()
        }, 3000)
        setContentView(binding.root)
        supportActionBar?.hide()

        val pref = DataStorePreferences.getInstance(dataStore)
        val prefsViewMdel = ViewModelProvider(this, ViewModelFactory(pref)).get(
            ThemeViewModel::class.java
        )
        prefsViewMdel.getThemeSettings().observe(this) { isDarkModeActive: Boolean ->
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }
    }
}