package com.yajatkumar.newsapp.fragment

import android.os.Bundle
import android.view.View
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.get
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
            "api_key_preference" -> {
                // Get the view for the preference and launch activity from it
                for (i in 0 until preferenceScreen.preferenceCount) {
                    if (preference.key.equals(preferenceScreen[i].key)) {
                        val v: View? = listView.layoutManager?.findViewByPosition(i)
                        if (v != null)
                            launchSetup(requireContext(), v)
                    }
                }
            }
        }

        return super.onPreferenceTreeClick(preferenceScreen)
    }

}