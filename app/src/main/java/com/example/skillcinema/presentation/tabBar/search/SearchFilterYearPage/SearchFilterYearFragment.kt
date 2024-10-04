package com.example.skillcinema.presentation.tabBar.search.SearchFilterYearPage

import android.graphics.Typeface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.skillcinema.R
import com.example.skillcinema.databinding.FragmentSearchFilterYearBinding
import com.example.skillcinema.presentation.tabBar.search.searchFilterPage.SearchFilterViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.time.LocalDate

@AndroidEntryPoint
class SearchFilterYearFragment : Fragment() {

    private var _binding: FragmentSearchFilterYearBinding? = null
    private val binding get() = _binding!!

    private val viewModel: SearchFilterViewModel by activityViewModels<SearchFilterViewModel>()

    private val adapterWith = MySimpleYearAdapter { item -> onStartYearClick(item) }
    private val adapterBy = MySimpleYearAdapter { item -> onEndYearClick(item) }

    private var selectedStartYear: Int? = null
    private var selectedEndYear: Int? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSearchFilterYearBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonBack.setOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }

        var listStartFrame1 = 1888
        var listEndFrame1 = listStartFrame1 + 11

        adapterWith.setData((listStartFrame1..listEndFrame1).toList())
        binding.textViewYearPeriod.text = "$listStartFrame1 - $listEndFrame1"
        binding.recyclerViewWith.adapter = adapterWith
        binding.textViewYearPeriod.setTypeface(null, Typeface.BOLD)

        binding.buttonRightFrame1.setOnClickListener {
            if (listEndFrame1 <= LocalDate.now().year) {
                val adapter = MySimpleYearAdapter { item -> onStartYearClick(item) }
                listStartFrame1 += 12
                listEndFrame1 += 12
                adapter.setData((listStartFrame1..listEndFrame1).toList())
                selectedStartYear = null
                binding.recyclerViewWith.adapter = adapter
                binding.textViewYearPeriod.text = "$listStartFrame1 - $listEndFrame1"
            }
        }

        binding.buttonLeftFrame1.setOnClickListener {
            if (listStartFrame1 != 1888) {
                val adapter = MySimpleYearAdapter { item -> onStartYearClick(item) }
                listStartFrame1 -= 12
                listEndFrame1 -= 12
                adapter.setData((listStartFrame1..listEndFrame1).toList())
                selectedStartYear = null
                binding.recyclerViewWith.adapter = adapter
                binding.textViewYearPeriod.text = "$listStartFrame1 - $listEndFrame1"
            }
        }

        var listEndFrame2 = LocalDate.now().year
        var listStartFrame2 = listEndFrame2 - 11

        adapterBy.setData((listStartFrame2..listEndFrame2).toList())
        binding.recyclerViewUntil.adapter = adapterBy

        binding.textViewYearPeriod2.text = "$listStartFrame2 - $listEndFrame2"
        binding.textViewYearPeriod2.setTypeface(null, Typeface.BOLD)

        binding.buttonLeftFrame2.setOnClickListener {
            if (listStartFrame2 >= 1888) {
                val adapter = MySimpleYearAdapter { item -> onEndYearClick(item) }
                listStartFrame2 -= 12
                listEndFrame2 -= 12
                adapter.setData((listStartFrame2..listEndFrame2).toList())
                selectedEndYear = null
                binding.recyclerViewUntil.adapter = adapter
                binding.textViewYearPeriod2.text = "$listStartFrame2 - $listEndFrame2"
            }
        }

        binding.buttonRightFrame2.setOnClickListener {
            if (listEndFrame2 != LocalDate.now().year) {
                val adapter = MySimpleYearAdapter { item -> onEndYearClick(item) }
                listStartFrame2 += 12
                listEndFrame2 += 12
                adapter.setData((listStartFrame2..listEndFrame2).toList())
                selectedEndYear = null
                binding.recyclerViewUntil.adapter = adapter
                binding.textViewYearPeriod2.text = "$listStartFrame2 - $listEndFrame2"
            }
        }

        binding.buttonPick.setOnClickListener {
            if (selectedStartYear != null && selectedEndYear != null) {
                viewModel.saveYear(selectedStartYear!!, selectedEndYear!!)
                requireActivity().onBackPressedDispatcher.onBackPressed()
            } else {
                Toast.makeText(
                    requireContext(),
                    getString(R.string.toast_in_search_year_fragment), Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun onStartYearClick(item: Int) {
        selectedStartYear = item
        println("selectedStartYear = $selectedStartYear")
    }

    private fun onEndYearClick(item: Int) {
        selectedEndYear = item
        println("selectedEndYear = $selectedEndYear")
    }

}