package com.example.recipesearch

import android.content.res.Resources
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.recipesearch.databinding.RecipeLayoutBinding

class RecipeAdapter( var onClick:(Hit) -> Unit): ListAdapter<Hit, RecipeAdapter.RecipeAdapter>(diffCallback) {
    inner class RecipeAdapter(var binding: RecipeLayoutBinding):RecyclerView.ViewHolder(binding.root) {
        fun bind(recipe: Hit){

            binding.recipeName.text = recipe.recipe.label
            binding.recipeIngredients.text = itemView.context.getString(R.string.ingredients,recipe.recipe.ingredients.size.toString())
            binding.recipeCalories.text = itemView.context.getString(R.string.calories,recipe.recipe.calories.toInt().toString())
            binding.recipeName.text = recipe.recipe.label
            Glide.with(binding.root).load(recipe.recipe.image).into(binding.imageView)
            binding.layout.setOnClickListener {
                onClick(recipe)
            }
        }
    }

    companion object{
        private val diffCallback = object : DiffUtil.ItemCallback<Hit>(){
            override fun areItemsTheSame(oldItem: Hit, newItem: Hit) = oldItem._links == newItem._links

            override fun areContentsTheSame(oldItem: Hit, newItem: Hit) = oldItem == newItem

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeAdapter {
        return RecipeAdapter(RecipeLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: RecipeAdapter, position: Int) {
        holder.bind(getItem(position))
    }
}