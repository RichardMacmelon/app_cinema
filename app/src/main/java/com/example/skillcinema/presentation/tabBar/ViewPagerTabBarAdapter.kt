package com.example.skillcinema.presentation.tabBar

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.skillcinema.presentation.tabBar.homepage.HomePageFragment
import com.example.skillcinema.presentation.tabBar.profile.profilePage.ProfileFragment
import com.example.skillcinema.presentation.tabBar.search.searchPage.SearchFragment

class ViewPagerTabBarAdapter(activity: FragmentActivity, private val tabCount: Int) :
    FragmentStateAdapter(activity) {

    override fun getItemCount(): Int = tabCount

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> HomePageFragment()
            1 -> SearchFragment()
            2 -> ProfileFragment()
            else -> HomePageFragment()
        }
    }

}