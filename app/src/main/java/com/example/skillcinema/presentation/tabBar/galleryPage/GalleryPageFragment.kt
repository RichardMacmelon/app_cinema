package com.example.skillcinema.presentation.tabBar.galleryPage

import android.graphics.Typeface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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

        binding.titleTextView.let {
            it.text = arguments?.getString(FilmPageFragment.KEY_ACTOR_TITLE)
            it.setTypeface(null, Typeface.BOLD)
        }

        val movieId = arguments?.getInt(HomePageFragment.ARGUMENT_FILM_KEY)

        getPhoto(1, movieId)

        binding.view.setOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }

        binding.button4.setOnClickListener {
            buttonVisible(1)
            getPhoto(1, movieId)
        }

        binding.button5.setOnClickListener {
            buttonVisible(2)
            getPhoto(2, movieId)
        }

        binding.button6.setOnClickListener {
            buttonVisible(3)
            getPhoto(3, movieId)
        }

    }

    private fun buttonVisible(id: Int) {
        when (id) {
            1 -> {
                binding.let {
                    it.button1.visibility = View.VISIBLE
                    it.button2.visibility = View.INVISIBLE
                    it.button3.visibility = View.INVISIBLE
                    it.button4.visibility = View.INVISIBLE
                    it.button5.visibility = View.VISIBLE
                    it.button6.visibility = View.VISIBLE
                }
            }

            2 -> {
                binding.let {
                    it.button1.visibility = View.INVISIBLE
                    it.button3.visibility = View.INVISIBLE
                    it.button2.visibility = View.VISIBLE
                    it.button5.visibility = View.INVISIBLE
                    it.button4.visibility = View.VISIBLE
                    it.button6.visibility = View.VISIBLE
                }
            }

            3 -> {
                binding.let {
                    it.button1.visibility = View.INVISIBLE
                    it.button2.visibility = View.INVISIBLE
                    it.button3.visibility = View.VISIBLE
                    it.button6.visibility = View.INVISIBLE
                    it.button4.visibility = View.VISIBLE
                    it.button5.visibility = View.VISIBLE
                }
            }
        }
    }

    private fun getPhoto(key: Int, movieId: Int?) {
        viewModel.loadGalleryMovie(key, movieId).onEach {
            binding.recyclerViewPhoto.adapter = photoAdapter
            photoAdapter.submitData(it)
        }.launchIn(viewLifecycleOwner.lifecycleScope)
    }
}