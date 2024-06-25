package com.example.skillcinema.presentation.onBoarding.screensStart

import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.skillcinema.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoaderFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Handler().postDelayed({
            findNavController().navigate(R.id.action_viewPagerFragment_to_viewPagerTabBarFragment)
        }, 3000)
        return inflater.inflate(R.layout.fragment_loader, container, false)
    }

}