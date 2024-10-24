package com.example.skillcinema.presentation.tabBar.photoPage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.example.skillcinema.databinding.FragmentPhotoPageBinding
import com.example.skillcinema.presentation.tabBar.filmpage.FilmPageFragment
import com.example.skillcinema.presentation.tabBar.homepage.HomePageFragment
import com.example.skillcinema.presentation.tabBar.profile.profilePage.ProfileViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class PhotoPageFragment : Fragment() {

    private var _binding: FragmentPhotoPageBinding? = null
    private val binding get() = _binding!!
    private val viewModel: PhotoViewModel by viewModels()

    private val photoAdapter = ViewPagerPhotoAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPhotoPageBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val movieId = arguments?.getInt(HomePageFragment.ARGUMENT_FILM_KEY)!!
        val key = arguments?.getInt(FilmPageFragment.KEY_PEOPLE_ALL)!!
        val position = arguments?.getInt(FilmPageFragment.KEY_POSITION)!!
        println("$position $movieId $key")

        binding.buttonBack.setOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }

        setupViewPager(position)
        getPhoto(key, movieId)
    }

    private fun setupViewPager(position: Int) {
        binding.viewPager.adapter = photoAdapter

        // Ждём загрузки данных и устанавливаем нужную позицию
        photoAdapter.registerAdapterDataObserver(object : RecyclerView.AdapterDataObserver() {
            override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
                super.onItemRangeInserted(positionStart, itemCount)
                if (photoAdapter.itemCount > position) {
                    binding.viewPager.setCurrentItem(position, false) // Устанавливаем позицию
                }
            }
        })
    }

    private fun getPhoto(key: Int, movieId: Int?) {
        viewModel.loadGalleryMovie(key, movieId).onEach {
            photoAdapter.submitData(it)
        }.launchIn(viewLifecycleOwner.lifecycleScope)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

