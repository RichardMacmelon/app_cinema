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
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.skillcinema.R
import com.example.skillcinema.data.dto.EntityItemsPhotoDto
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
    private val photoAdapter = MyGalleryPagePagingAdapter { position -> viewBigPhoto(position) }
    private var movieId: Int = 0
    private var key: Int = 1

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

        movieId = arguments?.getInt(HomePageFragment.ARGUMENT_FILM_KEY)!!

        getPhoto(1, movieId)
        updateButtonState(1)

        binding.button1.setOnClickListener {
            key = 1
            updateButtonState(key)
            getPhoto(key, movieId)
        }

        binding.button2.setOnClickListener {
            key = 2
            updateButtonState(key)
            getPhoto(key, movieId)
        }

        binding.button3.setOnClickListener {
            key = 3
            updateButtonState(key)
            getPhoto(key, movieId)
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

    private fun viewBigPhoto(position: Int) {
        println("kekStart $position $movieId $key")
        val argument = Bundle().apply {
            putInt(FilmPageFragment.KEY_POSITION, position)
            putInt(HomePageFragment.ARGUMENT_FILM_KEY, movieId)
            putInt(FilmPageFragment.KEY_PEOPLE_ALL, key)
            println("kek $position $movieId $key")
        }
        findNavController().navigate(
            R.id.action_galleryPageFragment_to_viewPagerPhotoFragment,
            argument
        )
    }
}