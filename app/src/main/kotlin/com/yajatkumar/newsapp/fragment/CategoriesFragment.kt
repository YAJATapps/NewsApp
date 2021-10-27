package com.yajatkumar.newsapp.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.yajatkumar.newsapp.R
import com.yajatkumar.newsapp.adapter.CategoryAdapter
import com.yajatkumar.newsapp.data.Category
import com.yajatkumar.newsapp.databinding.RecyclerViewBinding
import com.yajatkumar.newsapp.model.CategoryViewModel
import com.yajatkumar.newsapp.model.CategoryViewModelFactory


class CategoriesFragment : Fragment() {

    private lateinit var binding: RecyclerViewBinding

    private lateinit var mainRecycler: RecyclerView

    private lateinit var categoryAdapter: CategoryAdapter

    private val categoryViewModel: CategoryViewModel by viewModels {
        CategoryViewModelFactory()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = RecyclerViewBinding.inflate(
            inflater, container, false
        )
        return binding.root
    }


    override fun onViewCreated(rootView: View, savedInstanceState: Bundle?) {
        super.onViewCreated(rootView, savedInstanceState)

        mainRecycler = binding.mainRecycler

        categoryAdapter = CategoryAdapter(requireContext())
        mainRecycler.adapter = categoryAdapter

        mainRecycler.layoutManager = LinearLayoutManager(requireContext())

        // Add an observer on the LiveData returned by allCategories.
        // The onChanged() method fires when the observed data changes and the activity is in the foreground.
        categoryViewModel.allCategories.observe(viewLifecycleOwner) { categories ->
            // Update the cached copy of the categories in the adapter.
            categoryAdapter.setCategories(categories)
        }

        loadCategories()
    }

    private fun loadCategories() {
        val categories = mutableListOf<Category>()

        val sourceName = listOf(
            R.string.business,
            R.string.entertainment,
            R.string.general,
            R.string.health,
            R.string.science,
            R.string.sports,
            R.string.technology
        )
        val sourceValue = listOf(
            "business",
            "entertainment",
            "general",
            "health",
            "science",
            "sports",
            "technology"
        )

        for (i in sourceName.indices) {
            categories.add(Category(resources.getString(sourceName[i]), sourceValue[i]))
        }

        categoryViewModel.setCategories(categories)
    }

}