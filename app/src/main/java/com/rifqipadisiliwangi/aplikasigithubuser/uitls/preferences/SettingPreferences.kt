package com.rifqipadisiliwangi.aplikasigithubuser.uitls.preferences

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences

class SettingPreferences private constructor(private val dataStore: DataStore<Preferences>) {

    companion object {
        @Volatile
        private var INSTANCE: SettingPreferences? = null

        fun getInstance(dataStore: DataStore<Preferences>): SettingPreferences {
            return INSTANCE ?: synchronized(this) {
                val instance = SettingPreferences(dataStore)
                INSTANCE = instance
                instance
            }
        }
    }
}