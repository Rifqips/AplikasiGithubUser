package com.rifqipadisiliwangi.aplikasigithubuser.viewmodel.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.rifqipadisiliwangi.aplikasigithubuser.uitls.preferences.DataStorePreferences
import kotlinx.coroutines.launch

class ThemeViewModel(private val pref: DataStorePreferences) : ViewModel() {
    fun getThemeSettings(): LiveData<Boolean> {
        return pref.getThemeSetting().asLiveData()
    }

    fun saveThemeSetting(isDarkModeActive: Boolean) {
        viewModelScope.launch {
            pref.saveThemeSetting(isDarkModeActive)
        }
    }
}