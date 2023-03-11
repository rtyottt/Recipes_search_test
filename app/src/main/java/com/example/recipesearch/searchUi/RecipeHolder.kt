package com.example.recipesearch

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.recipesearch.databinding.RecipeLayoutBinding

class RecipeAdapter( var onClick:(Hit) -> Unit): ListAdapter<Hit, RecipeAdapter.RecipeHolder>(diffCallback) {
    private lateinit var myListener:OnItemClickListener
    inner class RecipeHolder(var binding: RecipeLayoutBinding):RecyclerView.ViewHolder(binding.root) {

        fun bind(recipe: Hit,listener: OnItemClickListener){
            binding.recipeName.text = recipe.recipe.label
            binding.recipeIngredients.text = itemView.context.getString(R.string.ingredients,recipe.recipe.ingredients.size.toString())
            binding.recipeCalories.text = itemView.context.getString(R.string.calories,recipe.recipe.calories.toString())
            Glide.with(binding.root).load(recipe.recipe.image).into(binding.imageView)
            binding.layout.setOnClickListener {
                onClick(recipe)
            }
            binding.imageButton.setOnClickListener {
                listener.onItemClick(recipe)
            }
        }
    }

    companion object{
        private val diffCallback = object : DiffUtil.ItemCallback<Hit>(){
            override fun areItemsTheSame(oldItem: Hit, newItem: Hit) = oldItem._links == newItem._links

            override fun areContentsTheSame(oldItem: Hit, newItem: Hit) = oldItem == newItem

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeHolder {
        return RecipeHolder(RecipeLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: RecipeHolder, position: Int) {
        holder.bind(getItem(position),myListener)
    }
    interface OnItemClickListener {
        fun onItemClick(recipe: Hit)
    }
    fun setOnSaveClickListener(listener: OnItemClickListener){
        myListener = listener
    }
}