package com.example.skillcinema.presentation.tabBar.filmpage

import android.content.Intent
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
import com.example.skillcinema.data.dto.EntityItemsPhotoDto
import com.example.skillcinema.data.dto.EntityItemsSimilarsFilmsDto
import com.example.skillcinema.data.dto.EntityPeopleDto
import com.example.skillcinema.data.tables.FilmDB
import com.example.skillcinema.databinding.FragmentFilmPageBinding
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
    private val photoMovieAdapter = MyPhotoMovieAdapter { position -> viewBigPhoto(position) }
    private var movieId: Int = 0

    private lateinit var webUrl: String
    private lateinit var movieUI: MovieUIModel
    private lateinit var film: FilmDB

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

        movieId = arguments?.getInt(ARGUMENT_FILM_KEY)!!
        println("movieId = $movieId")
        viewModel.loadInfoMovie(movieId)
        viewModel.loadSimilarsMovie(movieId)
        viewModel.loadInfoPeople(movieId)
        viewModel.loadPhotoForMovie(movieId)

        binding.buttonBack.setOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }

        binding.imageButtonLikes.setOnClickListener { button ->
            button.isSelected = !button.isSelected
            film = FilmDB(
                collectionId = 3,
                filmId = movieId,
                filmName = movieUI.name,
                filmGenre = movieUI.genres,
                filmPoster = movieUI.posterUrl,
                filmRating = movieUI.ratingKinopoisk,
                filmYear = movieUI.year
            )
            if (button.isSelected) {
                viewModel.insertToCollection(film)
            } else {
                viewModel.deleteFilmInCollection(film)
            }
        }

        binding.imageButtonNotes.setOnClickListener { button ->
            button.isSelected = !button.isSelected
            film = FilmDB(
                collectionId = 4,
                filmId = movieId,
                filmName = movieUI.name,
                filmGenre = movieUI.genres,
                filmPoster = movieUI.posterUrl,
                filmRating = movieUI.ratingKinopoisk,
                filmYear = movieUI.year
            )
            if (button.isSelected) {
                viewModel.insertToCollection(film)
            } else {
                viewModel.deleteFilmInCollection(film)
            }
        }

        binding.imageButtonSee.setOnClickListener { button ->
            button.isSelected = !button.isSelected
            film = FilmDB(
                collectionId = 1,
                filmId = movieId,
                filmName = movieUI.name,
                filmGenre = movieUI.genres,
                filmPoster = movieUI.posterUrl,
                filmRating = movieUI.ratingKinopoisk,
                filmYear = movieUI.year
            )
            if (button.isSelected) {
                viewModel.insertToCollection(film)
            } else {
                viewModel.deleteFilmInCollection(film)
            }
        }

        binding.imageButtonShare.setOnClickListener {
            val sendIntent: Intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, webUrl)
                type = "text/plain"
            }
            val shareIntent = Intent.createChooser(sendIntent, null)
            startActivity(shareIntent)
        }

        binding.imageButtonMore.setOnClickListener {
            val bundle = Bundle()
            bundle.putParcelable(
                KEY_BOTTOM_DIALOGUE,
                MyParcelableFilmClass(
                    film.filmId,
                    film.filmName,
                    film.filmYear,
                    film.filmGenre,
                    film.filmPoster,
                    film.filmRating
                )
            )
            findNavController().navigate(
                R.id.action_filmPageFragment_to_filmBottomDialogueFragment,
                bundle
            )
        }

        binding.buttonViewAllActor.setOnClickListener {
            val argument = Bundle().apply {
                putString(KEY_ACTOR_TITLE, getString(R.string.the_film_was_shot))
                putInt(ARGUMENT_FILM_KEY, movieId)
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
                putInt(ARGUMENT_FILM_KEY, movieId)
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
                putInt(ARGUMENT_FILM_KEY, movieId)
            }
            findNavController().navigate(
                R.id.action_filmPageFragment_to_galleryPageFragment,
                argument
            )
        }

        binding.buttonViewAllSimilarMovie.setOnClickListener {
            val argument = Bundle().apply {
                putString(KEY_ACTOR_TITLE, getString(R.string.similar_films))
                putInt(ARGUMENT_FILM_KEY, movieId)
            }
            findNavController().navigate(
                R.id.action_filmPageFragment_to_similarMovieFragment,
                argument
            )
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.similarsMovie.collect { values ->
                if (values.total == 0) {
                    binding.textViewOtherMovie.visibility = View.GONE
                    binding.textViewCounterSimilarMovie.visibility = View.GONE
                    binding.buttonViewAllSimilarMovie.visibility = View.GONE
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
                    binding.textViewGallery.visibility = View.GONE
                    binding.textViewCounterGallery.visibility = View.GONE
                    binding.buttonViewAllGallery.visibility = View.GONE
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

        viewModel.infoMovie.onEach { movie ->
            movieUI = viewModel.processMovieData(movie)
            if (movieUI.coverUrl != null && movieUI.logoUrl != null) {
                Glide.with(requireActivity()).load(movieUI.coverUrl).into(binding.imageViewFilmImg)
                Glide.with(requireActivity()).load(movieUI.logoUrl).into(binding.imageViewLogoUrl)
            } else {
                Glide.with(requireActivity()).load(movieUI.posterUrl).into(binding.imageViewFilmImg)
            }

            val infoMovie =
                "${movieUI.ratingKinopoisk} ${movieUI.name}\n${movieUI.year} ${movieUI.genres}\n${movieUI.countries} ${movieUI.filmLength} ${movieUI.ratingAgeLimits}"
            binding.textViewInfoMovie.text = infoMovie


            if (movieUI.shortDescription == null) {
                binding.textViewShortDescription.visibility = View.GONE
            } else {
                binding.textViewShortDescription.setTypeface(null, Typeface.BOLD)
                binding.textViewShortDescription.text = movieUI.shortDescription
            }

            if (movieUI.description == null) {
                binding.textViewTitleActor.marginTop.plus(10)
                binding.textViewDescription.visibility = View.GONE
            } else {
                binding.textViewDescription.text = movieUI.description
            }

            webUrl = movieUI.webUrl.toString()

            film = FilmDB(
                collectionId = 2,
                filmId = movieId,
                filmName = movieUI.name,
                filmGenre = movieUI.genres,
                filmPoster = movieUI.posterUrl,
                filmRating = movieUI.ratingKinopoisk,
                filmYear = movieUI.year
            )

            if (!viewModel.checkFilmIntoCollection(film)) {
                viewModel.insertToCollection(film)
            }

            binding.imageButtonSee.isSelected = viewModel.buttonState(1, film)
            binding.imageButtonLikes.isSelected = viewModel.buttonState(3, film)
            binding.imageButtonNotes.isSelected = viewModel.buttonState(4, film)

        }.launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun viewSimilarFilm(item: EntityItemsSimilarsFilmsDto) {
        val argument = bundleOf(ARGUMENT_FILM_KEY to item.filmId)
        findNavController().navigate(R.id.action_filmPageFragment_self, argument)
    }

    private fun viewBigPhoto(position: Int) {
        val argument = Bundle().apply {
            putInt(KEY_POSITION, position)
            putInt(ARGUMENT_FILM_KEY, movieId)
            putInt(KEY_PEOPLE_ALL, 1)
        }
        findNavController().navigate(
            R.id.action_filmPageFragment_to_viewPagerPhotoFragment,
            argument
        )
    }

    private fun viewActorPage(item: EntityPeopleDto) {
        val argument = bundleOf(KEY_PEOPLE to item.staffId)
        findNavController().navigate(R.id.action_filmPageFragment_to_actorPageFragment, argument)
    }

    companion object {
        const val KEY_ACTOR_TITLE = "KEY_ACTOR_TITLE"
        const val KEY_PEOPLE_ALL = "KEY_PEOPLE_ALL"
        const val KEY_PEOPLE = "KEY_PEOPLE"
        const val KEY_BOTTOM_DIALOGUE = "KEY_BOTTOM_DIALOGUE"
        const val KEY_POSITION = "KEY_POSITION"
    }
}