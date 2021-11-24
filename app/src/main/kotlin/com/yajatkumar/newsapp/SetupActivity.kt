package com.yajatkumar.newsapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import com.yajatkumar.newsapp.data.News
import com.yajatkumar.newsapp.databinding.ActivitySetupBinding
import com.yajatkumar.newsapp.setting.SettingsApp
import com.yajatkumar.newsapp.util.NewsAPI
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


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

        // Set the custom toolbar
        setSupportActionBar(binding.customToolbar.toolbarCentered)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        binding.customToolbar.toolbarTitle.text = resources.getString(R.string.api_key)

        // Display the back button in actionBar
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // Set the API key from the preferences
        binding.keyEditText.setText(SettingsApp.getAPIkey(this))

        // Add the value of API key to the preferences and close activity
        binding.submitButton.setOnClickListener {
            setKeyIfValid(binding.keyEditText.text.toString())
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

    /**
     *  Set the API key submitted if it is valid
     *  @param key - The key in editText
     */
    private fun setKeyIfValid(key: String) {
        val service = retrofitService() ?: return

        CoroutineScope(Dispatchers.IO).launch {
            try {
                // Response from GET request
                val response = service.newsList(key, "us") ?: return@launch
                var newsList: List<News>? = null

                withContext(Dispatchers.Main) {
                    if (response.isSuccessful) {
                        val items = response.body()
                        if (items != null) {
                            if (items.status == "error") {
                                if (items.code == "apiKeyInvalid") {
                                    // Handle the case when api key is not valid
                                    Log.e("errorInvalidAPI", response.code().toString())
                                    errorToast()
                                } else {
                                    // Other error
                                    Log.e("error", response.code().toString())
                                    errorToast()
                                }
                            } else if (items.status == "ok") {
                                newsList = items.articles
                                if (newsList != null && newsList?.size!! > 0) {
                                    setAPIkey(key)
                                } else {
                                    errorToast()
                                }
                            }
                        }
                    } else {
                        Log.e("error", response.code().toString())
                        errorToast()
                    }

                }
            } catch (e: Exception) {
                // Log the exception and show error toast
                e.message?.let { Log.e("error", it) }
                e.printStackTrace()
                withContext(Dispatchers.Main) {
                    errorToast()
                }
            }
        }
    }

    /**
     *  Set API key from editText
     *  @param key - The key in editText
     */
    private fun setAPIkey(key: String) {
        SettingsApp.setAPIkey(this, key)
        finish()
    }

    // Show API key error
    private fun errorToast() {
        Toast.makeText(this, resources.getString(R.string.incorrect_api_key), Toast.LENGTH_LONG)
            .show()
    }

    /**
     * Retrofit Service
     */
    private fun retrofitService(): NewsAPI? {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://newsapi.org/v2/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofit.create(NewsAPI::class.java)
    }

}