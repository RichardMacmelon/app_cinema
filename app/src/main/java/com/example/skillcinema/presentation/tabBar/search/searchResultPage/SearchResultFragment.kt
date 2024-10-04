package com.example.skillcinema.presentation.tabBar.search.searchResultPage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.skillcinema.R
import com.example.skillcinema.data.dto.EntityItemsMoviesForFiltersDto
import com.example.skillcinema.databinding.FragmentSearchResultBinding
import com.example.skillcinema.presentation.tabBar.homepage.HomePageFragment
import com.example.skillcinema.presentation.tabBar.search.searchFilterPage.SearchFilterViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class SearchResultFragment : Fragment() {

    private var _binding: FragmentSearchResultBinding? = null
    private val binding get() = _binding!!

    private val viewModel: SearchFilterViewModel by activityViewModels<SearchFilterViewModel>()
    private val adapter = MyResultPagingAdapter { item -> onClick(item) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSearchResultBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)

        binding.view.setOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }

        binding.recyclerView.adapter = adapter
        viewModel.getFilerMovie().onEach {
            adapter.submitData(it)
        }.launchIn(viewLifecycleOwner.lifecycleScope)

        viewLifecycleOwner.lifecycleScope.launch {
            adapter.loadStateFlow.collect {
                val itemCount = adapter.itemCount
                println("itemCount = $itemCount")
                if (itemCount == 0) {
                    binding.recyclerView.visibility = View.INVISIBLE
                    binding.textViewError.visibility = View.VISIBLE
                } else {
                    binding.recyclerView.visibility = View.VISIBLE
                    binding.textViewError.visibility = View.INVISIBLE
                }
            }
        }


    }

    private fun onClick(item: EntityItemsMoviesForFiltersDto) {
        val argument = bundleOf(HomePageFragment.ARGUMENT_FILM_KEY to item.kinopoiskId)
        findNavController().navigate(R.id.action_global_filmPageFragment, argument)
    }

}