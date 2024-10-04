package com.example.skillcinema.presentation.tabBar.profile.allFilmInCollectionPage

import android.graphics.Typeface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.skillcinema.R
import com.example.skillcinema.data.tables.FilmDB
import com.example.skillcinema.databinding.FragmentAllFilmInCollectionBinding
import com.example.skillcinema.presentation.tabBar.homepage.HomePageFragment
import com.example.skillcinema.presentation.tabBar.profile.profilePage.MyFilmFromCollectionAdapter
import com.example.skillcinema.presentation.tabBar.profile.profilePage.ProfileFragment.Companion.COLLECTION_ID
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AllFilmInCollectionFragment : Fragment() {

    private var _binding: FragmentAllFilmInCollectionBinding? = null
    private val binding get() = _binding!!

    private val recyclerViewAdapter = MyFilmFromCollectionAdapter { item -> onFilmClick(item) }
    private val viewModel: AllFilmInCollectionViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAllFilmInCollectionBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.view.setOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }
        binding.recyclerView.adapter = recyclerViewAdapter

        val collectionId: Int? = arguments?.getInt(COLLECTION_ID)
        collectionId!!

        viewLifecycleOwner.lifecycleScope.launch {
            recyclerViewAdapter.setData(viewModel.loadFilm(collectionId))
        }

        viewLifecycleOwner.lifecycleScope.launch {
            binding.titleTextView.let {
             it.text = viewModel.loadCollectionName(collectionId)
                it.setTypeface(null, Typeface.BOLD)
            }
        }

    }

    private fun onFilmClick(item: FilmDB) {
        val argument = bundleOf(HomePageFragment.ARGUMENT_FILM_KEY to item.filmId)
        findNavController().navigate(R.id.action_global_filmPageFragment, argument)
    }

}