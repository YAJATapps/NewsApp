package com.yajatkumar.newsapp.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.yajatkumar.newsapp.adapter.SourceAdapter
import com.yajatkumar.newsapp.databinding.RecyclerViewBinding
import com.yajatkumar.newsapp.model.SourceViewModel
import com.yajatkumar.newsapp.model.SourceViewModelFactory


/**
 * The base fragment for Category and Channel fragment.
 *
 * Loads a recyclerview into the view with linear layout manager.
 *
 * The recyclerview uses SourceAdapter and SourceViewModel.
 */
abstract class BaseCategoryFragment : Fragment() {

    private lateinit var binding: RecyclerViewBinding

    /**
     * The main recyclerView to show sources
     */
    private lateinit var mainRecycler: RecyclerView

    /**
     * The adapter that shows sources
     */
    private lateinit var sourceAdapter: SourceAdapter

    protected val sourceViewModel: SourceViewModel by viewModels {
        SourceViewModelFactory()
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

        sourceAdapter = SourceAdapter(requireContext())
        mainRecycler.adapter = sourceAdapter

        mainRecycler.layoutManager = LinearLayoutManager(requireContext())

        // Add an observer on the LiveData returned by allCategories.
        // The onChanged() method fires when the observed data changes and the activity is in the foreground.
        sourceViewModel.allCategories.observe(viewLifecycleOwner) { categories ->
            // Update the cached copy of the categories in the adapter.
            sourceAdapter.setCategories(categories)
        }

        try {
            loadItems()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    /**
     * Abstract method for implementing items load
     */
    abstract fun loadItems()

}