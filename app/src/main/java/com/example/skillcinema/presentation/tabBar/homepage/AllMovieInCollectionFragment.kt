package com.example.skillcinema.presentation.tabBar.homepage

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.skillcinema.R
import com.example.skillcinema.databinding.FragmentAllMovieInCollectionBinding
import com.example.skillcinema.databinding.FragmentHomePageBinding

class AllMovieInCollectionFragment : Fragment() {

    private var _binding: FragmentAllMovieInCollectionBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAllMovieInCollectionBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.view.setOnClickListener {
            findNavController().navigate(R.id.action_allMovieInCollectionFragment_to_homePageFragment)
        }
    }

}