package com.yajatkumar.newsapp

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
import com.yajatkumar.newsapp.util.ActivityUtil.Companion.launchSettings


/**
 * The main activity for this app
 */
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        // Set home fragment by default
        setFragment(HomeFragment())

        // Set the bottom navigation click listeners
        binding.bottomNavigation.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.page_1 -> {
                    // Set home fragment
                    setFragment(HomeFragment())
                    return@setOnItemSelectedListener true
                }
                R.id.page_2 -> {
                    // Set categories fragment
                    setFragment(CategoriesFragment())
                    return@setOnItemSelectedListener true
                }
                R.id.page_3 -> {
                    // Set channel fragment
                    setFragment(ChannelFragment())
                    return@setOnItemSelectedListener true
                }
                R.id.page_4 -> {
                    // Set search fragment
                    setFragment(SearchFragment())
                    return@setOnItemSelectedListener true
                }
            }

            false
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
}