package com.example.skillcinema.presentation.tabBar.AllMoviePage

import android.graphics.Typeface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.skillcinema.R
import com.example.skillcinema.data.dto.EntityItemsDto
import com.example.skillcinema.databinding.FragmentAllMovieInCollectionBinding
import com.example.skillcinema.presentation.tabBar.homepage.HomePageFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@AndroidEntryPoint
class AllMovieInCollectionFragment : Fragment() {

    private var _binding: FragmentAllMovieInCollectionBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var mainViewModelFactory: AllMovieViewModelFactory
    private val viewModel: AllMovieViewModel by viewModels<AllMovieViewModel> { mainViewModelFactory }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAllMovieInCollectionBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.titleTextView.let {
            it.text = arguments?.getString(HomePageFragment.ARGUMENT_KEY)
            it.setTypeface(null, Typeface.BOLD)
        }

        binding.view.setOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }
        val adapter = MyCollectionsPaginAdapter { id ->  onItemClick(id) }

        binding.recyclerView.adapter = adapter

        viewModel.loadPagedMovie(arguments?.getInt(HomePageFragment.ARGUMENT_DOWNLOAD_KEY)).onEach {
            adapter.submitData(it)
        }.launchIn(viewLifecycleOwner.lifecycleScope)

    }

    private fun onItemClick(item: EntityItemsDto) {
        val argument = bundleOf(HomePageFragment.ARGUMENT_FILM_KEY to item.kinopoiskId)
        findNavController().navigate(R.id.action_global_filmPageFragment, argument)
    }

}