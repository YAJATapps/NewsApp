package com.yajatkumar.newsapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.yajatkumar.newsapp.databinding.ActivityMainBinding

import androidx.fragment.app.Fragment
import com.yajatkumar.newsapp.fragment.HomeFragment
import com.yajatkumar.newsapp.fragment.SearchFragment


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        // Set home fragment by default
        setFragment(HomeFragment())

        binding.bottomNavigation.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.page_1 -> {
                    setFragment(HomeFragment())
                    return@setOnItemSelectedListener true
                }
                R.id.page_2 -> {
                    //setFragment(CategoriesFragment())
                    return@setOnItemSelectedListener true
                }
                R.id.page_3 -> {
                    //setFragment(ChannelFragment())
                    return@setOnItemSelectedListener true
                }
                R.id.page_4 -> {
                    setFragment(SearchFragment())
                    return@setOnItemSelectedListener true
                }
            }

            false
        }
    }

    private fun setFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commitAllowingStateLoss()
    }

}