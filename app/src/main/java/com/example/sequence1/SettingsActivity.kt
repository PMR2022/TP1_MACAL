@file:Suppress("DEPRECATION")

package com.example.sequence1


import android.os.Bundle
import android.preference.PreferenceActivity

class SettingsActivity : PreferenceActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addPreferencesFromResource(R.xml.preferences)
    }

}