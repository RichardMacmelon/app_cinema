package com.example.skillcinema.presentation.onBoarding

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.skillcinema.R
import com.example.skillcinema.databinding.FragmentViewPagerBinding
import com.example.skillcinema.presentation.onBoarding.screensStart.LoaderFragment
import com.example.skillcinema.presentation.onBoarding.screensStart.OnboardingFragment1
import com.example.skillcinema.presentation.onBoarding.screensStart.OnboardingFragment2
import com.example.skillcinema.presentation.onBoarding.screensStart.OnboardingFragment3
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ViewPagerFragment : Fragment() {

    private var _binding: FragmentViewPagerBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentViewPagerBinding.inflate(inflater)

        val fragmentList = arrayListOf(
            OnboardingFragment1(),
            OnboardingFragment2(),
            OnboardingFragment3(),
            LoaderFragment()
        )

        val adapter = ViewPagerAdapter(
            fragmentList,
            requireActivity().supportFragmentManager,
            lifecycle
        )

        binding.viewPager.adapter = adapter

        return binding.root
    }
}