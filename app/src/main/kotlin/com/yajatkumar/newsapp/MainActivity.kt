package com.yajatkumar.newsapp

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.yajatkumar.newsapp.databinding.ActivityMainBinding

import androidx.fragment.app.Fragment
import com.yajatkumar.newsapp.fragment.CategoriesFragment
import com.yajatkumar.newsapp.fragment.ChannelFragment
import com.yajatkumar.newsapp.fragment.HomeFragment
import com.yajatkumar.newsapp.fragment.SearchFragment
import android.view.Menu
import android.view.MenuItem
import com.yajatkumar.newsapp.setting.SettingsManager
import com.yajatkumar.newsapp.util.ActivityUtil.Companion.launchSettings


/**
 * The main activity for this app
 */
class MainActivity : AppCompatActivity(), SharedPreferences.OnSharedPreferenceChangeListener {

    private lateinit var binding: ActivityMainBinding

    // The number of current fragment
    private var fragmentNumber = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        // Set home fragment by default
        setFragment()

        // Set the bottom navigation click listeners
        binding.bottomNavigation.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.page_1 -> {
                    // Set home fragment
                    fragmentNumber = 1
                    setFragment()
                    return@setOnItemSelectedListener true
                }
                R.id.page_2 -> {
                    // Set categories fragment
                    fragmentNumber = 2
                    setFragment()
                    return@setOnItemSelectedListener true
                }
                R.id.page_3 -> {
                    // Set channel fragment
                    fragmentNumber = 3
                    setFragment()
                    return@setOnItemSelectedListener true
                }
                R.id.page_4 -> {
                    // Set search fragment
                    fragmentNumber = 4
                    setFragment()
                    return@setOnItemSelectedListener true
                }
            }

            false
        }

        // Set the custom toolbar
        setSupportActionBar(binding.customToolbar.toolbarCentered)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        binding.customToolbar.toolbarTitle.text = resources.getString(R.string.app_name)

        // Register preference changes
        SettingsManager.get(this).registerOnSharedPreferenceChangeListener(this)
    }

    /**
     * Set the fragment into fragment_container
     */
    private fun setFragment() {
        when (fragmentNumber) {
            1 -> setFragment(HomeFragment())
            2 -> setFragment(CategoriesFragment())
            3 -> setFragment(ChannelFragment())
            4 -> setFragment(SearchFragment())
        }
    }

    /**
     * Set the fragment into fragment_container
     * @param fragment - The fragment to display in container
     */
    private fun setFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commitAllowingStateLoss()
    }

    /**
     * Inflate the settings menu into actionBar
     */
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate menu
        menuInflater.inflate(R.menu.settings_menu, menu)
        return true
    }

    /**
     * Set the option selected action for actionBar
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id: Int = item.itemId
        return if (id == R.id.action_settings) {
            launchSettings(this, findViewById(R.id.action_settings))
            true
        } else super.onOptionsItemSelected(item)
    }


    /**
     * Reload the fragment when shake to swap preference is changed
     */
    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences?, key: String?) {
        if (key?.equals("shake_to_swap") == true) {
            setFragment()
        }
    }

    /**
     * Unregister preference changes on destroy
     */
    override fun onDestroy() {
        SettingsManager.get(this).unregisterOnSharedPreferenceChangeListener(this)

        // Delete cache directory
        cacheDir.deleteRecursively()

        super.onDestroy()
    }

}