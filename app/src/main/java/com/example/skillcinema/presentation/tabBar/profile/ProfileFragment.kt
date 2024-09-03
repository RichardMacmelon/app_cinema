package com.example.skillcinema.presentation.tabBar.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.skillcinema.R
import com.example.skillcinema.databinding.FragmentHomePageBinding
import com.example.skillcinema.databinding.FragmentProfileBinding
import com.example.skillcinema.presentation.tabBar.homepage.HomeViewModel
import com.example.skillcinema.presentation.tabBar.homepage.MainViewModelFactory
import com.example.skillcinema.presentation.tabBar.homepage.MyPremieresAdapter
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

//    @Inject
//    lateinit var mainViewModelFactory: MainViewModelFactory
//    private val viewModel: HomeViewModel by viewModels { mainViewModelFactory }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProfileBinding.inflate(inflater)
        return binding.root
    }

}