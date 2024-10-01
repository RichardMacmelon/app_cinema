package com.example.skillcinema.presentation.tabBar.galleryPage

import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.skillcinema.databinding.FragmentGalleryPageBinding
import com.example.skillcinema.presentation.tabBar.filmpage.FilmPageFragment
import com.example.skillcinema.presentation.tabBar.filmpage.MyPhotoMovieAdapter
import com.example.skillcinema.presentation.tabBar.homepage.HomePageFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject


@AndroidEntryPoint
class GalleryPageFragment : Fragment() {

    private var _binding: FragmentGalleryPageBinding? = null
    private val binding get() = _binding!!

    private val photoAdapter = MyGalleryPagePagingAdapter()

    @Inject
    lateinit var galleryPageViewModelFactory: GalleryPageViewModelFactory
    private val viewModel: GalleryPageViewModel by viewModels<GalleryPageViewModel> { galleryPageViewModelFactory }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentGalleryPageBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.view.setOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }

        binding.titleTextView.let {
            it.text = arguments?.getString(FilmPageFragment.KEY_ACTOR_TITLE)
            it.setTypeface(null, Typeface.BOLD)
        }

        val movieId = arguments?.getInt(HomePageFragment.ARGUMENT_FILM_KEY)

        getPhoto(1, movieId)
        updateButtonState(1)

        binding.button1.setOnClickListener {
            updateButtonState(1)
            getPhoto(1, movieId)
        }

        binding.button2.setOnClickListener {
            updateButtonState(2)
            getPhoto(2, movieId)
        }

        binding.button3.setOnClickListener {
            updateButtonState(3)
            getPhoto(3, movieId)
        }

    }
    private fun updateButtonState(selectedButtonId: Int) {
        binding.let {
            updateButtonStyle(it.button1, selectedButtonId == 1)
            updateButtonStyle(it.button2, selectedButtonId == 2)
            updateButtonStyle(it.button3, selectedButtonId == 3)
        }
    }

    private fun updateButtonStyle(button: Button, isSelected: Boolean) {
        if (isSelected) {
            button.setBackgroundColor(Color.parseColor("#3D3BFF"))
            button.setTextColor(Color.WHITE)
        } else {

            button.setBackgroundColor(Color.WHITE)
            button.setTextColor(Color.BLACK)
        }
    }

    private fun getPhoto(key: Int, movieId: Int?) {
        viewModel.loadGalleryMovie(key, movieId).onEach {
            binding.recyclerViewPhoto.adapter = photoAdapter
            photoAdapter.submitData(it)
        }.launchIn(viewLifecycleOwner.lifecycleScope)
    }
}