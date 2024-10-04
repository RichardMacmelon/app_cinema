package com.example.skillcinema.presentation.tabBar.search.searchFilterGenrePage

import android.graphics.Typeface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.example.skillcinema.data.dto.EntityIdGenresDto
import com.example.skillcinema.databinding.FragmentSearchFilterGenreBinding
import com.example.skillcinema.presentation.tabBar.search.searchFilterPage.SearchFilterViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class SearchFilterGenreFragment : Fragment() {

    private var _binding: FragmentSearchFilterGenreBinding? = null
    private val binding get() = _binding!!

    private val viewModel: SearchFilterViewModel by activityViewModels<SearchFilterViewModel>()

    private val adapter = MySimpleGenreAdapter { item -> onClick(item) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSearchFilterGenreBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonBack.setOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }

        binding.titleTextView.setTypeface(null, Typeface.BOLD)

        binding.recyclerView.adapter = adapter
        viewModel.idAndGenre.onEach {
            val debugList = it.filter {
                it.genre.isNotEmpty()
            }
            adapter.setData(debugList)
        }.launchIn(viewLifecycleOwner.lifecycleScope)

        binding.search.doOnTextChanged { text, _, _, _ ->
            viewModel.idAndGenre.onEach {
                delay(300)
                val oldList = it

                val newList = oldList.filter { country ->
                    country.genre.contains(text.toString(), ignoreCase = true)
                }
                adapter.setData(newList)

            }.launchIn(viewLifecycleOwner.lifecycleScope)
        }

    }

    private fun onClick(item: EntityIdGenresDto) {
        viewModel.saveGenre(item.genre, item.id)
        println("item.genre = ${item.genre}")
        println("item.id = ${item.id}")
        requireActivity().onBackPressedDispatcher.onBackPressed()
    }

}