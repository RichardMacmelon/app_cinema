package com.example.skillcinema.presentation.onBoarding.screensStart

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.widget.ViewPager2
import com.example.skillcinema.R
import com.example.skillcinema.databinding.FragmentOnboarding1Binding
import com.example.skillcinema.databinding.FragmentOnboarding2Binding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OnboardingFragment2 : Fragment() {

    private var _binding: FragmentOnboarding2Binding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentOnboarding2Binding.inflate(inflater)

        val viewPager = activity?.findViewById<ViewPager2>(R.id.viewPager)

        binding.textViewSkip.setOnClickListener {
            viewPager?.currentItem = 4
        }

        return binding.root
    }
}