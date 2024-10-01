package com.example.skillcinema.presentation.tabBar.homepage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ConcatAdapter
import com.example.skillcinema.R
import com.example.skillcinema.data.dto.EntityItemsDto
import com.example.skillcinema.databinding.FragmentHomePageBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class HomePageFragment : Fragment() {

    private var _binding: FragmentHomePageBinding? = null
    private val binding get() = _binding!!

    private val moviePremieresAdapter = MyPremieresAdapter { id ->  onItemClick(id) }
    private val movieTop250Adapter = MyPremieresAdapter { id ->  onItemClick(id) }
    private val moviePopularAdapter = MyPremieresAdapter { id ->  onItemClick(id) }
    private val movieVampireAdapter = MyPremieresAdapter { id ->  onItemClick(id) }
    private val movieFamilyAdapter = MyPremieresAdapter{ id ->  onItemClick(id) }

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
                viewFilmAll(1)
            })
        val contactAdapterTop250 =
            ConcatAdapter(movieTop250Adapter, MyButtonViewAllAdapter {
                viewFilmAll(2)
            })
        val contactAdapterPopular =
            ConcatAdapter(moviePopularAdapter, MyButtonViewAllAdapter {
                viewFilmAll(3)
            })
        val contactAdapterVampire =
            ConcatAdapter(movieVampireAdapter, MyButtonViewAllAdapter {
                viewFilmAll(4)
            })
        val contactAdapterFamily =
            ConcatAdapter(movieFamilyAdapter, MyButtonViewAllAdapter {
                viewFilmAll(5)
            })

        viewModel.popularSeries.onEach {
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

        binding.textViewSkip.setOnClickListener {
            viewFilmAll(1)
        }

        binding.textViewSkipTop250.setOnClickListener {
            viewFilmAll(2)
        }

        binding.textViewSkipPopular.setOnClickListener {
            viewFilmAll(3)
        }

        binding.textViewSkipVampire.setOnClickListener {
            viewFilmAll(4)
        }

        binding.textViewSkipFamily.setOnClickListener {
            viewFilmAll(5)
        }
    }

    private fun viewFilmAll(keyBottom: Int) {
        val argument = Bundle()
        when (keyBottom) {
            1 -> {
                argument.apply {
                    putString(ARGUMENT_KEY, getString(R.string.comics))
                    putInt(ARGUMENT_DOWNLOAD_KEY, keyBottom)
                }
                findNavController().navigate(
                    R.id.action_homePageFragment_to_allMovieInCollectionFragment,
                    argument
                )
            }

            2 -> {
                argument.apply {
                    argument.putString(ARGUMENT_KEY, getString(R.string.top_250))
                    putInt(ARGUMENT_DOWNLOAD_KEY, keyBottom)
                }
                findNavController().navigate(
                    R.id.action_homePageFragment_to_allMovieInCollectionFragment,
                    argument
                )

            }

            3 -> {
                argument.apply {
                    putString(ARGUMENT_KEY, getString(R.string.popular))
                    putInt(ARGUMENT_DOWNLOAD_KEY, keyBottom)
                }
                findNavController().navigate(
                    R.id.action_homePageFragment_to_allMovieInCollectionFragment,
                    argument
                )
            }

            4 -> {
                argument.apply {
                    putString(ARGUMENT_KEY, getString(R.string.vampire))
                    putInt(ARGUMENT_DOWNLOAD_KEY, keyBottom)
                }
                findNavController().navigate(
                    R.id.action_homePageFragment_to_allMovieInCollectionFragment,
                    argument
                )
            }

            5 -> {
                argument.apply {
                    putString(ARGUMENT_KEY, getString(R.string.top_250_tv_shows))
                    putInt(ARGUMENT_DOWNLOAD_KEY, keyBottom)
                }
                findNavController().navigate(
                    R.id.action_homePageFragment_to_allMovieInCollectionFragment,
                    argument
                )
            }

        }
    }

    private fun onItemClick(item: EntityItemsDto) {
        val argument = bundleOf(ARGUMENT_FILM_KEY to item.kinopoiskId)
        findNavController().navigate(R.id.action_global_filmPageFragment, argument)
    }

    companion object {
        const val ARGUMENT_FILM_KEY = "ARGUMENT_FILM_KEY"
        const val ARGUMENT_KEY = "ARGUMENT_KEY"
        const val ARGUMENT_DOWNLOAD_KEY = "ARGUMENT_DOWNLOAD_KEY"
    }
}