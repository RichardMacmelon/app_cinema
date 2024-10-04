package com.example.skillcinema.presentation.tabBar.filmpage.filmBottomDialoguePage

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.skillcinema.R
import com.example.skillcinema.data.tables.FilmDB
import com.example.skillcinema.databinding.FragmentFilmBottomDialogueBinding
import com.example.skillcinema.databinding.FragmentProfileBinding
import com.example.skillcinema.presentation.tabBar.filmpage.FilmPageFragment
import com.example.skillcinema.presentation.tabBar.filmpage.MyParcelableFilmClass
import com.example.skillcinema.presentation.tabBar.profile.profilePage.MyFilmFromCollectionAdapter
import com.example.skillcinema.presentation.tabBar.profile.profilePage.MyPlaylistAdapter
import com.example.skillcinema.presentation.tabBar.profile.profilePage.ProfileViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FilmBottomDialogueFragment : BottomSheetDialogFragment() {

    private var _binding: FragmentFilmBottomDialogueBinding? = null
    private val binding get() = _binding!!

    private val viewModel: FilmBottomDialogueViewModel by viewModels()

    private val myDialogueAdapter =
        MyDialogueAdapter { isChecked, collectionId -> onCheckBoxClick(isChecked, collectionId) }

    private lateinit var filmData: MyParcelableFilmClass
    private lateinit var listCheckBoxStates: MutableList<Boolean>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFilmBottomDialogueBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonClose.setOnClickListener {
            dismiss()
        }

        binding.buttonAddNewCollection.setOnClickListener {
            findNavController().navigate(R.id.action_global_profileDialogueFragment)
        }

        binding.recyclerViewCollection.adapter = myDialogueAdapter
        filmData = arguments?.getParcelable(FilmPageFragment.KEY_BOTTOM_DIALOGUE)!!
        binding.textViewName.text = filmData.filmName

        binding.textViewGenre.text = if (filmData.filmYear.isNotEmpty()) {
            "${filmData.filmYear} ${filmData.filmGenre}"
        } else {
            filmData?.filmGenre.toString()
        }

        binding.textViewRating.text = if (filmData.filmRating == "null") {
            "-"
        } else {
            filmData.filmRating
        }

        Glide.with(requireActivity()).load(filmData.filmPoster)
            .into(binding.imageViewBackground)


        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.allCollections.collect { collections ->
                if (collections.size >= 4) {
                    listCheckBoxStates = viewModel.checkBoxStates(
                        FilmDB(
                            collectionId = 0,
                            filmId = filmData.filmId,
                            filmName = filmData.filmName,
                            filmGenre = filmData.filmGenre,
                            filmPoster = filmData.filmPoster,
                            filmRating = filmData.filmRating,
                            filmYear = filmData.filmYear
                        )
                    )
                    myDialogueAdapter.setData(collections.subList(2, collections.size), listCheckBoxStates.subList(2, listCheckBoxStates.size))
                }
            }
        }
    }

    private fun onCheckBoxClick(isChecked: Boolean, collectionId: Int) {
        val film = FilmDB(
            collectionId = collectionId,
            filmId = filmData.filmId,
            filmName = filmData.filmName,
            filmGenre = filmData.filmGenre,
            filmPoster = filmData.filmPoster,
            filmRating = filmData.filmRating,
            filmYear = filmData.filmYear
        )

        if (isChecked) {
            viewModel.insertToCollection(film)
        } else {
            viewModel.deleteFilmInCollection(film)
        }
    }
}