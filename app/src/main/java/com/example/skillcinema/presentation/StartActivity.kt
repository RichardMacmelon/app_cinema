package com.example.skillcinema.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.skillcinema.databinding.ActivityStartBinding
import com.example.skillcinema.presentation.onBoarding.ViewPagerAdapter
import com.example.skillcinema.presentation.onBoarding.screensStart.LoaderFragment
import com.example.skillcinema.presentation.onBoarding.screensStart.OnboardingFragment1
import com.example.skillcinema.presentation.onBoarding.screensStart.OnboardingFragment2
import com.example.skillcinema.presentation.onBoarding.screensStart.OnboardingFragment3
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class StartActivity : AppCompatActivity() {

    private lateinit var binding: ActivityStartBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStartBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val fragmentList = arrayListOf(
            OnboardingFragment1(),
            OnboardingFragment2(),
            OnboardingFragment3(),
            LoaderFragment()
        )

        val adapter = ViewPagerAdapter(
            fragmentList,
            supportFragmentManager,
            lifecycle
        )

        binding.viewPager.adapter = adapter

    }

}