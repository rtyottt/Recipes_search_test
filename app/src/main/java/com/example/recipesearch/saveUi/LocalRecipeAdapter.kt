package com.example.recipesearch.saveUi

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.recipesearch.Hit
import com.example.recipesearch.R
import com.example.recipesearch.RecipeAdapter
import com.example.recipesearch.databinding.RecipeLayoutBinding
import com.example.recipesearch.room.SavedRecipe

class LocalRecipeAdapter( var onClick:(SavedRecipe) -> Unit): ListAdapter<SavedRecipe, LocalRecipeAdapter.LocalRecipeHolder>(diffCallback) {
    private lateinit var myListener:OnItemClickListenerLocal
    inner class LocalRecipeHolder(var binding: RecipeLayoutBinding): RecyclerView.ViewHolder(binding.root) {

        fun bind(recipe: SavedRecipe, listener: OnItemClickListenerLocal){
            binding.recipeName.text = recipe.label
            binding.recipeIngredients.text = itemView.context.getString(R.string.ingredients,recipe.ingredients.size.toString())
            binding.recipeCalories.text = itemView.context.getString(R.string.calories,recipe.calories.toString())
            Glide.with(binding.root).load(recipe.image).into(binding.imageView)
            binding.layout.setOnClickListener {
                onClick(recipe)
            }
            binding.imageButton.setOnClickListener {
                listener.onItemClick(recipe)
            }
        }
    }

    companion object{
        private val diffCallback = object : DiffUtil.ItemCallback<SavedRecipe>(){
            override fun areItemsTheSame(oldItem: SavedRecipe, newItem: SavedRecipe) = oldItem.localId == newItem.localId

            override fun areContentsTheSame(oldItem: SavedRecipe, newItem: SavedRecipe) = oldItem == newItem

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocalRecipeHolder {
        return LocalRecipeHolder(RecipeLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: LocalRecipeHolder, position: Int) {
        holder.bind(getItem(position),myListener)
    }
    interface OnItemClickListenerLocal {
        fun onItemClick(recipe: SavedRecipe)
    }
    fun setOnSaveClickListener(listener: OnItemClickListenerLocal){
        myListener = listener
    }
}