package com.example.skillcinema.presentation.tabBar.search.searchFilterPage

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.skillcinema.R
import com.example.skillcinema.databinding.FragmentSearchFilterBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import java.time.LocalDate
import javax.inject.Inject

@AndroidEntryPoint
class SearchFilterFragment : Fragment() {

    private var _binding: FragmentSearchFilterBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var searchFilterViewModelFactory: SearchFilterViewModelFactory
    private val viewModel: SearchFilterViewModel by activityViewModels<SearchFilterViewModel> { searchFilterViewModelFactory }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSearchFilterBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonBack.setOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }

        viewModel.getIdForCountryAndGenre()

        binding.buttonCountry.setOnClickListener {
            findNavController().navigate(R.id.action_searchFilterFragment_to_searchFilterCountryFragment)
        }

        binding.buttonGenre.setOnClickListener {
            findNavController().navigate(R.id.action_searchFilterFragment_to_searchFilterGenreFragment)
        }

        binding.buttonYear.setOnClickListener {
            findNavController().navigate(R.id.action_searchFilterFragment_to_searchFilterYearFragment)
        }

        binding.radioGroupFimsSerials.setOnCheckedChangeListener { _, buttonId ->
            when(buttonId) {
                R.id.radio_all -> viewModel.saveCheckedRadioButtonFilmsSerials("ALL")
                R.id.radio_movies -> viewModel.saveCheckedRadioButtonFilmsSerials("FILM")
                R.id.radioButtonShowTVSeries -> viewModel.saveCheckedRadioButtonFilmsSerials("TV_SERIES")
            }
        }

        binding.radioGroupFilmsData.setOnCheckedChangeListener { _, buttonId ->
            when(buttonId) {
                R.id.radio_data -> viewModel.saveCheckedRadioButtonDataPopularRating("YEAR")
                R.id.radio_popular -> viewModel.saveCheckedRadioButtonDataPopularRating("NUM_VOTE")
                R.id.radioButton_rating -> viewModel.saveCheckedRadioButtonDataPopularRating("RATING")
            }
        }

        binding.textViewCountry.text = viewModel.country.ifEmpty {
            viewModel.saveCountry("CША", 1)
            viewModel.country
        }
        binding.textViewGenre.text = viewModel.genre.ifEmpty {
            viewModel.saveGenre("триллер", 1)
            viewModel.genre
        }

        binding.textViewYear.text = viewModel.year.ifEmpty {
            viewModel.saveYear(1888, LocalDate.now().year)
            viewModel.year
        }

        binding.slider.setCustomThumbDrawable(R.drawable.ellipse_slider_2)
        binding.slider.addOnChangeListener { slider, _, _ ->
            viewModel.getRating(slider.values[0].toInt(), slider.values[1].toInt())
        }

        viewModel.rating.onEach {
            binding.textViewRating.text = it
        }.launchIn(viewLifecycleOwner.lifecycleScope)

        binding.buttonNotSee.setOnClickListener {
            viewModel.toggleColor()
        }

        viewModel.isBlue.observe(viewLifecycleOwner, Observer { isBlue ->
            if (isBlue) {
                binding.textViewNotSee.setTextColor(Color.BLUE)
                binding.imageView10.background.setTint(resources.getColor(R.color.blue))
            } else {
                binding.textViewNotSee.setTextColor(Color.BLACK)
                binding.imageView10.background.setTint(resources.getColor(R.color.black))
            }
        })

        binding.buttonResult.setOnClickListener {
            findNavController().navigate(R.id.action_searchFilterFragment_to_searchResultFragment)
        }

    }

}