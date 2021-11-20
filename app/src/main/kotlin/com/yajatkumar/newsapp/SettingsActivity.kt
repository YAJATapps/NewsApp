package com.yajatkumar.newsapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.yajatkumar.newsapp.databinding.ActivitySettingsBinding
import com.yajatkumar.newsapp.fragment.SettingsFragment


/**
 * The settings activity to set preferences for the app
 */
class SettingsActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySettingsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySettingsBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        // Set the custom toolbar
        setSupportActionBar(binding.customToolbar.toolbarCentered)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        binding.customToolbar.toolbarTitle.text = resources.getString(R.string.settings)

        // Display the back button in actionBar
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // Replace the settings_container with SettingsFragment
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.settings_container, SettingsFragment())
            .commit()
    }

    // Finish the activity when back button is clicked
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}