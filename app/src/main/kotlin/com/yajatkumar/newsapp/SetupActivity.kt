package com.yajatkumar.newsapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.yajatkumar.newsapp.databinding.ActivitySetupBinding
import com.yajatkumar.newsapp.setting.SettingsApp


/**
 * The setup activity used to set the API key
 */
class SetupActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySetupBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySetupBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        // Display the back button in actionBar
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // Set the API key from the preferences
        binding.keyEditText.setText(SettingsApp.getAPIkey(this))

        // Add the value of API key to the preferences and close activity
        binding.submitButton.setOnClickListener {
            SettingsApp.setAPIkey(this, binding.keyEditText.text.toString())
            finish()
        }
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