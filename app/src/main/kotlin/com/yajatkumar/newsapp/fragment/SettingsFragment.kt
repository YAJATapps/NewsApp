package com.yajatkumar.newsapp.fragment

import android.os.Bundle
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.yajatkumar.newsapp.R
import com.yajatkumar.newsapp.util.ActivityUtil.Companion.launchSetup


/**
 * The fragment that displays the settings for the app
 */
class SettingsFragment : PreferenceFragmentCompat() {
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preferences, rootKey)
    }

    // Click listeners for preference click
    override fun onPreferenceTreeClick(
        preference: Preference
    ): Boolean {

        when (preference.key) {
            "api_key_preference" -> launchSetup(requireContext())
        }

        return super.onPreferenceTreeClick(preferenceScreen)
    }

}