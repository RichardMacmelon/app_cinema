package com.example.skillcinema.presentation.tabBar.search.searchFilterCountryPage

import android.graphics.Typeface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.example.skillcinema.data.dto.EntityIdCountriesDto
import com.example.skillcinema.databinding.FragmentSearchFilterCountryBinding
import com.example.skillcinema.presentation.tabBar.search.searchFilterPage.SearchFilterViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class SearchFilterCountryFragment : Fragment() {

    private var _binding: FragmentSearchFilterCountryBinding? = null
    private val binding get() = _binding!!

    private val viewModel: SearchFilterViewModel by activityViewModels<SearchFilterViewModel>()

    private val adapter = MySimpleCountryAdapter { item -> onClick(item) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSearchFilterCountryBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonBack.setOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }

        binding.titleTextView.setTypeface(null, Typeface.BOLD)

        binding.recyclerView.adapter = adapter
        viewModel.idAndCountry.onEach {
            adapter.setData(it)
        }.launchIn(viewLifecycleOwner.lifecycleScope)

        binding.search.doOnTextChanged { text, _, _, _ ->
            viewModel.idAndCountry.onEach {
                delay(300)
                val oldList = it

                val newList = oldList.filter { country ->
                    country.country.contains(text.toString(), ignoreCase = true)
                }
                adapter.setData(newList)

            }.launchIn(viewLifecycleOwner.lifecycleScope)
        }

    }

    private fun onClick(item: EntityIdCountriesDto) {
        viewModel.saveCountry(item.country, item.id)
        println("item.country = ${item.country}")
        println("item.id = ${item.id}")
        requireActivity().onBackPressedDispatcher.onBackPressed()
    }

}