package com.example.recipesearch.saveUi

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.recipesearch.Hit
import com.example.recipesearch.RecipeAdapter
import com.example.recipesearch.databinding.FragmentSavedBinding
import com.example.recipesearch.room.SavedRecipe
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


class SavedFragment : Fragment() {
    lateinit var binding: FragmentSavedBinding
    private val viewModel: SaveViewModel by activityViewModels()
    lateinit var adapter: LocalRecipeAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentSavedBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpRecycler()
        setUpSearchWiev()

    }

    private fun setUpSearchWiev() {
        binding.savedSearchView.setOnQueryTextListener(object:
            android.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                viewModel.updateQuery(p0.orEmpty())
                return true
            }
        })
    }

    private fun setUpRecycler() {
        adapter = LocalRecipeAdapter{ recipe->
            val direction = SavedFragmentDirections.actionSavedFragment2ToWebFragment2(recipe.url)
            findNavController().navigate(direction)
        }
        binding.savedRecycler.layoutManager = LinearLayoutManager(activity)
        binding.savedRecycler.adapter = adapter
        lifecycleScope.launch {
            viewModel.savedRecipe.collect {
                adapter.submitList(it)
            }
        }
        adapter.setOnSaveClickListener(object : LocalRecipeAdapter.OnItemClickListenerLocal {
            override fun onItemClick(recipe: SavedRecipe) {
                viewModel.deleteRecipe(recipe)
            }

        })
    }

}