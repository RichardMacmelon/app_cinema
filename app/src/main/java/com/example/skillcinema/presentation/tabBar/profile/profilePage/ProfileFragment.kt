package com.example.skillcinema.presentation.tabBar.profile.profilePage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ConcatAdapter
import com.example.skillcinema.R
import com.example.skillcinema.data.tables.CollectionDB
import com.example.skillcinema.data.tables.FilmDB
import com.example.skillcinema.databinding.FragmentProfileBinding
import com.example.skillcinema.presentation.tabBar.homepage.HomePageFragment
import com.example.skillcinema.presentation.tabBar.homepage.MyButtonViewAllAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ProfileViewModel by viewModels()

    private val recyclerViewCollectionAdapter =
        MyPlaylistAdapter({ item -> onCollectionClick(item) }, { item -> buttonDeletePress(item) })
    private val recyclerViewFilmViewAdapter =
        MyFilmFromCollectionAdapter { item -> onFilmClick(item) }
    private val recyclerViewFilmInterestingAdapter =
        MyFilmFromCollectionAdapter { item -> onFilmClick(item) }

    private val contactViewedFilmsAdapter =
        ConcatAdapter(recyclerViewFilmViewAdapter, MyButtonDeleteAllFilmAdapter {
            buttonClearHistoryPress(1)
        })
    private val contactInterestingFilmsAdapter =
        ConcatAdapter(recyclerViewFilmInterestingAdapter, MyButtonDeleteAllFilmAdapter {
            buttonClearHistoryPress(2)
        })

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.imageButtonAllInterestingFilms.setOnClickListener {
            val argument = bundleOf(COLLECTION_ID to 2)
            findNavController().navigate(
                R.id.action_profileFragment_to_allFilmInCollectionFragment,
                argument
            )
        }

        binding.imageButtonAllViewedFilms.setOnClickListener {
            val argument = bundleOf(COLLECTION_ID to 1)
            findNavController().navigate(
                R.id.action_profileFragment_to_allFilmInCollectionFragment,
                argument
            )
        }

        binding.buttonCreateNewCollection.setOnClickListener {
            findNavController().navigate(R.id.action_global_profileDialogueFragment)
        }

        binding.recyclerViewPlaylist.adapter = recyclerViewCollectionAdapter
        binding.recyclerViewViewedFilms.adapter = contactViewedFilmsAdapter
        binding.recyclerViewInterestingFilms.adapter = contactInterestingFilmsAdapter

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.viewedFilms.collect { films ->
                if (films.isEmpty()) {
                    binding.let { view ->
                        view.recyclerViewViewedFilms.visibility = View.GONE
                        view.textView1.visibility = View.GONE
                        view.textViewContainerViewedFilms.visibility = View.GONE
                        view.imageButtonAllViewedFilms.visibility = View.GONE
                    }
                } else {
                    binding.let { view ->
                        view.recyclerViewViewedFilms.visibility = View.VISIBLE
                        view.textView1.visibility = View.VISIBLE
                        view.textViewContainerViewedFilms.visibility = View.VISIBLE
                        view.imageButtonAllViewedFilms.visibility = View.VISIBLE
                    }
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.interestingFilms.collect { films ->
                if (films.isEmpty()) {
                    binding.let { view ->
                        view.textView4.visibility = View.GONE
                        view.textViewCounterInterestingFilms.visibility = View.GONE
                        view.imageButtonAllInterestingFilms.visibility = View.GONE
                        view.recyclerViewInterestingFilms.visibility = View.GONE
                    }
                } else {
                    binding.let { view ->
                        view.textView4.visibility = View.VISIBLE
                        view.textViewCounterInterestingFilms.visibility = View.VISIBLE
                        view.imageButtonAllInterestingFilms.visibility = View.VISIBLE
                        view.recyclerViewInterestingFilms.visibility = View.VISIBLE
                    }
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.allCollections.collect { collections ->
                if (collections.size >= 4) {
                    recyclerViewCollectionAdapter.setData(
                        collections.subList(
                            2,
                            collections.size
                        )
                    )
                }
            }
        }


        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.allInfo.collect { listCollectionWithUser ->
                if (listCollectionWithUser.size >= 4) {
                    val listViewedFilm = listCollectionWithUser[0].filmDB!!
                    val listInterestingFilm = listCollectionWithUser[1].filmDB!!

                    recyclerViewFilmViewAdapter.setData(listViewedFilm.reversed())
                    recyclerViewFilmInterestingAdapter.setData(listInterestingFilm.reversed())

                    binding.textViewContainerViewedFilms.text =
                        listViewedFilm.size.toString()
                    binding.textViewCounterInterestingFilms.text =
                        listInterestingFilm.size.toString()
                }
            }
        }
    }

    private fun onFilmClick(item: FilmDB) {
        val argument = bundleOf(HomePageFragment.ARGUMENT_FILM_KEY to item.filmId)
        findNavController().navigate(R.id.action_global_filmPageFragment, argument)
    }

    private fun onCollectionClick(item: CollectionDB) {
        if (item.collectionSize == 0) {
            Toast.makeText(
                requireContext(),
                getString(R.string.collection_is_empty, item.collectionName), Toast.LENGTH_SHORT
            ).show()
        } else {
            val argument = bundleOf(COLLECTION_ID to item.collectionId)
            findNavController().navigate(
                R.id.action_profileFragment_to_allFilmInCollectionFragment,
                argument
            )
        }
    }

    private fun buttonDeletePress(item: CollectionDB) {
        viewModel.deleteCollection(item.collectionId)
        viewModel.deleteAllFilmsInCollectionId(item.collectionId)
    }

    private fun buttonClearHistoryPress(collectionId: Int) {
        viewModel.deleteAllFilmsInCollectionId(collectionId)
    }

    companion object {
        const val COLLECTION_ID = "COLLECTION_ID"
    }
}