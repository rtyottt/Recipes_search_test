package com.example.recipesearch

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.activity.viewModels
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.recipesearch.databinding.ActivityMainBinding
import com.example.recipesearch.databinding.FragmentSearchBinding
import kotlinx.coroutines.flow.collectLatest


class SearchFragment : Fragment() {
    private val args: SearchFragmentArgs by navArgs()
    private val viewModel: RecipeViewModel by activityViewModels()
    lateinit var adapter: RecipeAdapter
    lateinit var binding: FragmentSearchBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getData()
        setUpRecycler()
        binding.searchView.setOnQueryTextListener(object:
            android.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                viewModel.updateQuery(p0!!)
                return false
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                return false
            }
        })
    }

    private fun setUpRecycler() {
        adapter = RecipeAdapter{ hit->
            val direction = SearchFragmentDirections.actionSearchFragmentToWebFragment(hit.recipe.url)
            findNavController().navigate(direction)
        }
        binding.recycler.layoutManager = LinearLayoutManager(activity)
        binding.recycler.adapter = adapter
        lifecycleScope.launchWhenStarted {
            viewModel.searchResult.collectLatest {
                adapter.submitList(it.hits)
            }
        }
    }

}