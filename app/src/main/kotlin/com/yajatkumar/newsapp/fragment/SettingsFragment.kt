package com.yajatkumar.newsapp.fragment

import android.app.AlertDialog
import android.os.Bundle
import android.view.View
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.get
import com.yajatkumar.newsapp.R
import com.yajatkumar.newsapp.util.ActivityUtil.Companion.launchSetup
import android.text.SpannableString
import android.text.method.LinkMovementMethod
import android.text.util.Linkify
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.view.setPadding


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
            "contact_us" -> {
                // Add a contact us box for compliance with google news app rules
                val  message =  TextView(context)
                val  s = SpannableString(context?.getText(R.string.contact_us_summary))
                message.text = s
                message.movementMethod = LinkMovementMethod.getInstance()


                val  message2 =  TextView(context)
                val  s1 = SpannableString(this.getText(R.string.contact_us_summary2))
                Linkify.addLinks(s1, Linkify.WEB_URLS)
                message2.text = s1
                message2.movementMethod = LinkMovementMethod.getInstance()

                val linearLayout = LinearLayout(context)
                linearLayout.orientation = LinearLayout.VERTICAL
                linearLayout.setPadding(24)
                linearLayout.addView(message)
                linearLayout.addView(message2)

                AlertDialog.Builder(context)
                    .setTitle(R.string.contact_us)
                    .setView(linearLayout)
                    .setPositiveButton(android.R.string.ok
                    ) { _, _ ->
                    }
                    .setIcon(R.mipmap.ic_launcher_round)
                    .show()
            }
        }

        return super.onPreferenceTreeClick(preferenceScreen)
    }

}