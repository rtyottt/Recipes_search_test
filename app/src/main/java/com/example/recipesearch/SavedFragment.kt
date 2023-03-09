package com.example.recipesearch

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
import com.example.recipesearch.databinding.FragmentSavedBinding
import kotlinx.coroutines.flow.collectLatest


class SavedFragment : Fragment() {
    lateinit var binding: FragmentSavedBinding
    private val viewModel: SaveViewModel by activityViewModels()
    lateinit var adapter: RecipeAdapter
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
        adapter = RecipeAdapter{ hit->
            val direction = SavedFragmentDirections.actionSavedFragment2ToWebFragment2(hit.recipe.url)
            findNavController().navigate(direction)
        }
        binding.savedRecycler.layoutManager = LinearLayoutManager(activity)
        binding.savedRecycler.adapter = adapter
        lifecycleScope.launchWhenStarted {
            viewModel.savedRecipe.collectLatest {
                adapter.submitList(it)
            }
        }
        adapter.setOnSaveClickListener(object :RecipeAdapter.OnItemClickListener{
            override fun onItemClick(recipe: Hit) {
                viewModel.deleteRecipe(recipe)
            }

        })
    }

}