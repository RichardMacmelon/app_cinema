package com.example.skillcinema.presentation.onBoarding

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

class ViewPagerAdapter(
    listFragments: List<Fragment>,
    fragmentManager: FragmentManager,
    lifeCycle: Lifecycle
) : FragmentStateAdapter(fragmentManager, lifeCycle) {

    private val fragmentList = listFragments

    override fun getItemCount(): Int = fragmentList.size

    override fun createFragment(position: Int): Fragment = fragmentList[position]

}