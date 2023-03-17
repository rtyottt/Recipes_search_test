package com.example.recipesearch.searchUi

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.recipesearch.Hit
import com.example.recipesearch.RecipeAdapter
import com.example.recipesearch.databinding.FragmentSearchBinding
import kotlinx.coroutines.flow.collectLatest


class SearchFragment : Fragment() {
    private val args: SearchFragmentArgs by navArgs()
    private val viewModel: SearchViewModel by activityViewModels()
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
                viewModel.getData()
                return true
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                return true
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
        adapter.setOnSaveClickListener(object : RecipeAdapter.OnItemClickListener {
            override fun onItemClick(recipe: Hit) {
                viewModel.saveRecipe(recipe)
            }

        })
    }

}