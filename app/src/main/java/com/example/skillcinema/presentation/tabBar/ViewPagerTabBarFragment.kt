package com.example.skillcinema.presentation.tabBar

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.widget.ViewPager2
import com.example.skillcinema.R
import com.example.skillcinema.databinding.FragmentViewPagerBinding
import com.example.skillcinema.databinding.FragmentViewPagerTabBarBinding
import com.example.skillcinema.presentation.onBoarding.ViewPagerAdapter
import com.example.skillcinema.presentation.onBoarding.screensStart.LoaderFragment
import com.example.skillcinema.presentation.onBoarding.screensStart.OnboardingFragment1
import com.example.skillcinema.presentation.onBoarding.screensStart.OnboardingFragment2
import com.example.skillcinema.presentation.onBoarding.screensStart.OnboardingFragment3
import com.google.android.material.tabs.TabLayout
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ViewPagerTabBarFragment : Fragment() {

    private var _binding: FragmentViewPagerTabBarBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentViewPagerTabBarBinding.inflate(inflater)


        setUpTabBar()

        return binding.root
    }

    private fun setUpTabBar() {
        val adapter = ViewPagerTabBarAdapter(requireActivity(), binding.tabLayout.tabCount)
        binding.viewPager.adapter = adapter

        binding.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                binding.tabLayout.selectTab(binding.tabLayout.getTabAt(position))
            }
        })

        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                binding.viewPager.currentItem = tab.position
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {}

            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })

    }
}