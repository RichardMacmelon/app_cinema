package com.example.skillcinema.presentation.tabBar.similarMoviePage

import android.graphics.Typeface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.view.isGone
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.skillcinema.R
import com.example.skillcinema.data.EntityItemsSimilarsFilmsDto
import com.example.skillcinema.databinding.FragmentHomePageBinding
import com.example.skillcinema.databinding.FragmentSimilarMovieBinding
import com.example.skillcinema.presentation.tabBar.filmpage.FilmPageFragment
import com.example.skillcinema.presentation.tabBar.filmpage.MySimilarMovieAdapter
import com.example.skillcinema.presentation.tabBar.homepage.HomePageFragment
import com.example.skillcinema.presentation.tabBar.homepage.HomeViewModel
import com.example.skillcinema.presentation.tabBar.homepage.MainViewModelFactory
import com.example.skillcinema.presentation.tabBar.homepage.MyPremieresAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class SimilarMovieFragment : Fragment() {

    private var _binding: FragmentSimilarMovieBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var similarMovieViewModelFactory: SimilarMovieViewModelFactory
    private val viewModel: SimilarMovieViewModel by viewModels { similarMovieViewModelFactory }

    private val similarMovieAdapter = MySimilarMovieAdapter { id -> viewSimilarFilm(id) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSimilarMovieBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val movieId = arguments?.getInt(HomePageFragment.ARGUMENT_FILM_KEY)
        println("movieId: $movieId")

        viewModel.loadSimilarsMovie(movieId!!)

        binding.titleTextView.let {
            it.text = arguments?.getString(FilmPageFragment.KEY_ACTOR_TITLE)
            it.setTypeface(null, Typeface.BOLD)
        }

        binding.view.setOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.similarsMovie.collect { values ->
                similarMovieAdapter.setData(values.items)
                binding.recyclerView.adapter = similarMovieAdapter
            }
        }

    }

    private fun viewSimilarFilm(item: EntityItemsSimilarsFilmsDto) {
        val argument = bundleOf(HomePageFragment.ARGUMENT_FILM_KEY to item.filmId)
        findNavController().navigate(R.id.action_similarMovieFragment_to_filmPageFragment, argument)
    }
}