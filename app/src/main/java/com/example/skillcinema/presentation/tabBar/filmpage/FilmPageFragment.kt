package com.example.skillcinema.presentation.tabBar.filmpage

import android.graphics.Typeface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.view.isGone
import androidx.core.view.marginTop
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.skillcinema.R
import com.example.skillcinema.data.EntityItemsSimilarsFilmsDto
import com.example.skillcinema.data.EntityPeopleDto
import com.example.skillcinema.databinding.FragmentFilmPageBinding
import com.example.skillcinema.entity.EntityPeople
import com.example.skillcinema.presentation.tabBar.homepage.HomePageFragment.Companion.ARGUMENT_FILM_KEY
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class FilmPageFragment : Fragment() {

    private var _binding: FragmentFilmPageBinding? = null
    private val binding get() = _binding!!

    private val similarMovieAdapter = MySimilarMovieAdapter { id -> viewSimilarFilm(id) }
    private val actorMovieAdapter = MyPeopleCardAdapter { item -> viewActorPage(item) }
    private val workerMovieAdapter = MyPeopleCardAdapter { item -> viewActorPage(item) }
    private val photoMovieAdapter = MyPhotoMovieAdapter()

    @Inject
    lateinit var filmPageViewModelFactory: FilmPageViewModelFactory
    private val viewModel: FilmPageViewModel by viewModels<FilmPageViewModel> { filmPageViewModelFactory }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFilmPageBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val movieId: Int? = arguments?.getInt(ARGUMENT_FILM_KEY)
        println(movieId)

        arguments?.let {
            viewModel.loadInfoMovie(movieId!!)
            viewModel.loadSimilarsMovie(movieId)
            viewModel.loadInfoPeople(movieId)
            viewModel.loadPhotoForMovie(movieId)
        }

        binding.buttonBack.setOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }

        binding.buttonViewAllActor.setOnClickListener {
            val argument = Bundle().apply {
                putString(KEY_ACTOR_TITLE, getString(R.string.the_film_was_shot))
                putInt(ARGUMENT_FILM_KEY, movieId!!)
                putInt(KEY_PEOPLE_ALL, 1)
            }
            findNavController().navigate(
                R.id.action_filmPageFragment_to_allPeopleInPageFragment,
                argument
            )
        }

        binding.buttonViewAllJob.setOnClickListener {
            val argument = Bundle().apply {
                putString(KEY_ACTOR_TITLE, getString(R.string.we_were_working_on_the_film))
                putInt(ARGUMENT_FILM_KEY, movieId!!)
                putInt(KEY_PEOPLE_ALL, 2)
            }
            findNavController().navigate(
                R.id.action_filmPageFragment_to_allPeopleInPageFragment,
                argument
            )
        }

        binding.buttonViewAllGallery.setOnClickListener {
            val argument = Bundle().apply {
                putString(KEY_ACTOR_TITLE, getString(R.string.gallery))
                putInt(ARGUMENT_FILM_KEY, movieId!!)
            }
            findNavController().navigate(
                R.id.action_filmPageFragment_to_galleryPageFragment,
                argument
            )
        }

        binding.buttonViewAllSimilarMovie.setOnClickListener {
            val argument = Bundle().apply {
                putString(KEY_ACTOR_TITLE, getString(R.string.similar_films))
                putInt(ARGUMENT_FILM_KEY, movieId!!)
            }
            findNavController().navigate(
                R.id.action_filmPageFragment_to_similarMovieFragment,
                argument
            )
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.similarsMovie.collect { values ->
                if (values.total == 0) {
                    binding.textViewOtherMovie.isGone = true
                    binding.textViewCounterSimilarMovie.isGone = true
                    binding.buttonViewAllSimilarMovie.isGone = true
                    binding.imageViewSimilarMovie.isGone = true
                } else {
                    binding.textViewCounterSimilarMovie.text = values.total.toString()
                    similarMovieAdapter.setData(values.items)
                    binding.recyclerViewSimilarMovie.adapter = similarMovieAdapter
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.photoMovie.collect { values ->
                if (values.total == 0) {
                    binding.textViewGallery.isGone = true
                    binding.imageViewGallery.isGone = true
                    binding.textViewCounterGallery.isGone = true
                    binding.buttonViewAllGallery.isGone = true
                } else {
                    binding.textViewCounterGallery.text = values.total.toString()
                    photoMovieAdapter.setData(values.items)
                    binding.recyclerViewGallery.adapter = photoMovieAdapter
                }
            }
        }

        viewModel.actorInfo.onEach {
            binding.textViewCounterActor.text = it.size.toString()
            actorMovieAdapter.setData(it)
            binding.recyclerViewActor.adapter = actorMovieAdapter
        }.launchIn(viewLifecycleOwner.lifecycleScope)

        viewModel.workerMovieInfo.onEach {
            binding.textViewCounterJob.text = it.size.toString()
            workerMovieAdapter.setData(it)
            binding.recyclerViewJob.adapter = workerMovieAdapter
        }.launchIn(viewLifecycleOwner.lifecycleScope)

        viewModel.infoMovie.onEach {
            if (it.coverUrl != null && it.logoUrl != null) {
                Glide.with(requireActivity()).load(it.coverUrl).into(binding.imageViewFilmImg)
                Glide.with(requireActivity()).load(it.logoUrl).into(binding.imageViewLogoUrl)
            } else {
                Glide.with(requireActivity()).load(it.posterUrl).into(binding.imageViewFilmImg)
            }

            var ratingKinopoisk: String
            if (it.ratingKinopoisk == null) {
                ratingKinopoisk = ""
            } else {
                ratingKinopoisk = it.ratingKinopoisk.toString()
            }

            val name = it.nameRu ?: it.nameEn ?: ""

            val year: String = if (it.year == null) {
                ""
            } else {
                "${it.year},"
            }

            val genres = it.genres.joinToString { it.genre }
            val countries = "${it.countries.joinToString { it.country }},"

            var filmLength: String
            if (it.filmLength == null) {
                filmLength = ""
            } else if (it.filmLength > 60) {
                val hour = it.filmLength / 60
                val minutes = it.filmLength % 60
                filmLength = "$hour ч $minutes мин,"
            } else {
                filmLength = "${it.filmLength} мин,"
            }

            var ratingAgeLimits: String
            if (it.ratingAgeLimits == null) {
                filmLength.replace(",", "")
                ratingAgeLimits = ""
            } else {
                ratingAgeLimits = "${it.ratingAgeLimits?.replace("age", "")}+"
            }

            val infoMovie =
                "$ratingKinopoisk $name \n$year $genres\n $countries $filmLength $ratingAgeLimits"
            binding.textViewInfoMovie.text = infoMovie

            if (it.shortDescription == null) {
                binding.textViewShortDescription.isGone = true
            } else {
                binding.textViewShortDescription.setTypeface(null, Typeface.BOLD)
                binding.textViewShortDescription.text = it.shortDescription
            }

            if (it.description == null) {
                binding.textViewTitleActor.marginTop.plus(10)
                binding.textViewDescription.isGone = true
            } else {
                binding.textViewDescription.text = it.description
            }
        }.launchIn(viewLifecycleOwner.lifecycleScope)

    }

    private fun viewSimilarFilm(item: EntityItemsSimilarsFilmsDto) {
        val argument = bundleOf(ARGUMENT_FILM_KEY to item.filmId)
        findNavController().navigate(R.id.action_filmPageFragment_self, argument)
    }

    private fun viewActorPage(item: EntityPeopleDto) {
        val argument = bundleOf(KEY_PEOPLE to item.staffId)
        findNavController().navigate(R.id.action_filmPageFragment_to_actorPageFragment, argument)
    }

    companion object {
        const val KEY_ACTOR_TITLE = "KEY_ACTOR_TITLE"
        const val KEY_PEOPLE_ALL = "KEY_PEOPLE_ALL"
        const val KEY_PEOPLE = "KEY_PEOPLE"
    }
}