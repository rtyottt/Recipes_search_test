package com.example.recipesearch

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.recipesearch.databinding.FragmentBrowsingBinding

class BrowsingFragment : Fragment() {
    lateinit var binding: FragmentBrowsingBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentBrowsingBinding.inflate(inflater, container, false)
        return binding.root
    }

}