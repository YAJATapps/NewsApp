package com.yajatkumar.newsapp

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.signature.ObjectKey
import com.yajatkumar.newsapp.databinding.ActivityReaderBinding


class ReaderActivity : AppCompatActivity() {

    private lateinit var binding: ActivityReaderBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityReaderBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val id = intent.getLongExtra("id", -1)
        if (id == (-1).toLong()) {
            // Exit activity if id was not set
            finish()
        }

        val imageUrl = intent.getStringExtra("imageUrl")
        val description = intent.getStringExtra("description")
        val content = intent.getStringExtra("content")

        binding.newsDescription.text = description
        binding.newsContent.text = content

        try {
            Glide.with(this)
                .load(imageUrl)
                .centerCrop()
                .placeholder(R.drawable.ic_photo)
                .error(R.drawable.ic_error)
                .signature(ObjectKey(id))
                .transition(DrawableTransitionOptions.withCrossFade()).into(binding.newsImage)
        } catch (e: Exception) {
        }
    }

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