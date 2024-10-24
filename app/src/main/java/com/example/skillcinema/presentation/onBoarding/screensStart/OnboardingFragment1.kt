package com.example.skillcinema.presentation.onBoarding.screensStart

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.widget.ViewPager2
import com.example.skillcinema.R
import com.example.skillcinema.databinding.FragmentOnboarding1Binding
import com.example.skillcinema.presentation.MainActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OnboardingFragment1 : Fragment() {

    private var _binding: FragmentOnboarding1Binding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentOnboarding1Binding.inflate(inflater)
        val viewPager = activity?.findViewById<ViewPager2>(R.id.viewPager)

        if (onBoardingFinished()) {
            val intent = Intent(requireContext(), MainActivity::class.java)
            startActivity(intent)
            activity?.finish()
        } else {
            binding.textViewSkip.setOnClickListener {
                viewPager?.currentItem = 3
            }
        }

        return binding.root
    }

    private fun onBoardingFinished(): Boolean {
        val sharedPreferences =
            requireActivity().getSharedPreferences("onBoarding", Context.MODE_PRIVATE)

        return sharedPreferences.getBoolean("Finished", false)
    }
}


