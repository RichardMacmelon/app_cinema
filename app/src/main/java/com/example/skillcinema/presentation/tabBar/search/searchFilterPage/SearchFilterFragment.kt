package com.example.skillcinema.presentation.tabBar.search.searchFilterPage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.example.skillcinema.R
import com.example.skillcinema.databinding.FragmentSearchFilterBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
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

        binding.slider.setCustomThumbDrawable(R.drawable.ellipse_slider_2)

        binding.slider.addOnChangeListener { slider, _, _ ->
            viewModel.getRating(slider.values[0].toInt(), slider.values[1].toInt())
        }

        viewModel.rating.onEach {
            binding.textViewRating.text = it
        }.launchIn(viewLifecycleOwner.lifecycleScope)

        var buttonGenre1Check = true
        var buttonGenre2Check = false
        var buttonGenre3Check = false

        if (buttonGenre1Check) {
            binding.buttonAll.setTextColor(resources.getColor(R.color.white))
            binding.buttonAll.setBackgroundColor(resources.getColor(R.color.blue))
        }

        binding.buttonAll.setOnClickListener {
            buttonGenre1Check = true
            buttonGenre2Check = false
            buttonGenre3Check = false

            if (buttonGenre1Check) {
                binding.buttonAll.setTextColor(resources.getColor(R.color.white))
                binding.buttonAll.setBackgroundColor(resources.getColor(R.color.blue))

                binding.buttonFilms.setTextColor(resources.getColor(R.color.black))
                binding.buttonFilms.setBackgroundColor(resources.getColor(R.color.white))

                binding.buttonSeries.setTextColor(resources.getColor(R.color.black))
                binding.buttonSeries.setBackgroundColor(resources.getColor(R.color.white))

            }
        }

        binding.buttonFilms.setOnClickListener {
            buttonGenre1Check = false
            buttonGenre2Check = true
            buttonGenre3Check = false

            if (buttonGenre2Check) {
                binding.buttonAll.setTextColor(resources.getColor(R.color.black))
                binding.buttonAll.setBackgroundColor(resources.getColor(R.color.white))

                binding.buttonFilms.setTextColor(resources.getColor(R.color.white))
                binding.buttonFilms.setBackgroundColor(resources.getColor(R.color.blue))

                binding.buttonSeries.setTextColor(resources.getColor(R.color.black))
                binding.buttonSeries.setBackgroundColor(resources.getColor(R.color.white))

            }
        }

        binding.buttonSeries.setOnClickListener {
            buttonGenre1Check = false
            buttonGenre2Check = false
            buttonGenre3Check = true

            if (buttonGenre3Check) {
                binding.buttonAll.setTextColor(resources.getColor(R.color.black))
                binding.buttonAll.setBackgroundColor(resources.getColor(R.color.white))

                binding.buttonFilms.setTextColor(resources.getColor(R.color.black))
                binding.buttonFilms.setBackgroundColor(resources.getColor(R.color.white))

                binding.buttonSeries.setTextColor(resources.getColor(R.color.white))
                binding.buttonSeries.setBackgroundColor(resources.getColor(R.color.blue))

            }
        }

        var buttonSort1Check = true
        var buttonSort2Check = false
        var buttonSort3Check = false

        if (buttonSort1Check) {
            binding.buttonData.setTextColor(resources.getColor(R.color.white))
            binding.buttonData.setBackgroundColor(resources.getColor(R.color.blue))
        }

        binding.buttonData.setOnClickListener {
            buttonSort1Check = true
            buttonSort2Check = false
            buttonSort3Check = false

            if (buttonSort1Check) {
                binding.buttonData.setTextColor(resources.getColor(R.color.white))
                binding.buttonData.setBackgroundColor(resources.getColor(R.color.blue))

                binding.buttonPopular.setTextColor(resources.getColor(R.color.black))
                binding.buttonPopular.setBackgroundColor(resources.getColor(R.color.white))

                binding.buttonRating.setTextColor(resources.getColor(R.color.black))
                binding.buttonRating.setBackgroundColor(resources.getColor(R.color.white))

            }
        }

        binding.buttonPopular.setOnClickListener {
            buttonSort1Check = false
            buttonSort2Check = true
            buttonSort3Check = false

            if (buttonSort2Check) {
                binding.buttonData.setTextColor(resources.getColor(R.color.black))
                binding.buttonData.setBackgroundColor(resources.getColor(R.color.white))

                binding.buttonPopular.setTextColor(resources.getColor(R.color.white))
                binding.buttonPopular.setBackgroundColor(resources.getColor(R.color.blue))

                binding.buttonRating.setTextColor(resources.getColor(R.color.black))
                binding.buttonRating.setBackgroundColor(resources.getColor(R.color.white))

            }
        }

        binding.buttonRating.setOnClickListener {
            buttonSort1Check = false
            buttonSort2Check = false
            buttonSort3Check = true

            if (buttonSort3Check) {
                binding.buttonData.setTextColor(resources.getColor(R.color.black))
                binding.buttonData.setBackgroundColor(resources.getColor(R.color.white))

                binding.buttonPopular.setTextColor(resources.getColor(R.color.black))
                binding.buttonPopular.setBackgroundColor(resources.getColor(R.color.white))

                binding.buttonRating.setTextColor(resources.getColor(R.color.white))
                binding.buttonRating.setBackgroundColor(resources.getColor(R.color.blue))

            }
        }
        var isCheck = false

        binding.buttonNotSee.setOnClickListener {
            if (!isCheck) {
                binding.imageView10.background.setTint(resources.getColor(R.color.blue))
                binding.textViewNotSee.setTextColor(resources.getColor(R.color.blue))
                isCheck = true
            } else {
                binding.imageView10.background.setTint(resources.getColor(R.color.black))
                binding.textViewNotSee.setTextColor(resources.getColor(R.color.black))
                isCheck = false
            }
        }

    }

}