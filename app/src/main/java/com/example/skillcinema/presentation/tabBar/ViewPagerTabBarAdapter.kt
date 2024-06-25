package com.example.skillcinema.presentation.tabBar

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.skillcinema.presentation.tabBar.homepage.HomeCounterFragment
import com.example.skillcinema.presentation.tabBar.homepage.HomePageFragment
import com.example.skillcinema.presentation.tabBar.profile.ProfileFragment
import com.example.skillcinema.presentation.tabBar.search.SearchCounterFragment
import com.example.skillcinema.presentation.tabBar.search.SearchFragment

class ViewPagerTabBarAdapter(activity: FragmentActivity, private val tabCount: Int) :
    FragmentStateAdapter(activity) {

    override fun getItemCount(): Int = tabCount


override fun createFragment(position: Int): Fragment {
    return when(position) {
        0 -> HomeCounterFragment()
        1 -> SearchCounterFragment()
        2 -> ProfileFragment()
        else -> HomePageFragment()
    }
}

}