package com.example.skillcinema.presentation.tabBar.homepage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ConcatAdapter
import com.example.skillcinema.R
import com.example.skillcinema.databinding.FragmentHomePageBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@AndroidEntryPoint
class HomePageFragment : Fragment() {

    private var _binding: FragmentHomePageBinding? = null
    private val binding get() = _binding!!

    private val moviePremieresAdapter = MyPremieresAdapter()
    private val movieTop250Adapter = MyPremieresAdapter()
    private val moviePopularAdapter = MyPremieresAdapter()
    private val movieVampireAdapter = MyPremieresAdapter()
    private val movieFamilyAdapter = MyPremieresAdapter()

    @Inject
    lateinit var mainViewModelFactory: MainViewModelFactory
    private val viewModel: HomeViewModel by viewModels { mainViewModelFactory }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomePageBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val contactAdapterPremieres =
            ConcatAdapter(moviePremieresAdapter, MyButtonViewAllAdapter {
                 onItemClick()
            })
        val contactAdapterTop250 =
            ConcatAdapter(movieTop250Adapter, MyButtonViewAllAdapter {
                 onItemClick()
            })
        val contactAdapterPopular =
            ConcatAdapter(moviePopularAdapter, MyButtonViewAllAdapter {
                 onItemClick()
            })
        val contactAdapterVampire =
            ConcatAdapter(movieVampireAdapter, MyButtonViewAllAdapter {
                 onItemClick()
            })
        val contactAdapterFamily =
            ConcatAdapter(movieFamilyAdapter, MyButtonViewAllAdapter {
                onItemClick()
            })

        viewModel.moviePremiers.onEach {
            moviePremieresAdapter.setData(it)
        }.launchIn(viewLifecycleOwner.lifecycleScope)

        binding.recyclerView.adapter = contactAdapterPremieres

        viewModel.movieTop250Collections.onEach {
            movieTop250Adapter.setData(it)
        }.launchIn(viewLifecycleOwner.lifecycleScope)

        binding.recyclerViewTop250.adapter = contactAdapterTop250

        viewModel.moviePopularCollections.onEach {
            moviePopularAdapter.setData(it)
        }.launchIn(viewLifecycleOwner.lifecycleScope)

        binding.recyclerViewTopPopular.adapter = contactAdapterPopular

        viewModel.movieVampireCollections.onEach {
            movieVampireAdapter.setData(it)
        }.launchIn(viewLifecycleOwner.lifecycleScope)

        binding.recyclerViewTopVampire.adapter = contactAdapterVampire

        viewModel.movieFamilyCollections.onEach {
            movieFamilyAdapter.setData(it)
        }.launchIn(viewLifecycleOwner.lifecycleScope)

        binding.recyclerViewTopFamily.adapter = contactAdapterFamily
    }

    private fun onItemClick() {
        findNavController().navigate(R.id.action_homePageFragment_to_allMovieInCollectionFragment)
    }
}