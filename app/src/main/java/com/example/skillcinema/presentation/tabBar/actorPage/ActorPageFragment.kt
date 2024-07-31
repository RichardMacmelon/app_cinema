package com.example.skillcinema.presentation.tabBar.actorPage

import android.graphics.Typeface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.skillcinema.R
import com.example.skillcinema.databinding.FragmentActorPageBinding
import com.example.skillcinema.databinding.FragmentAllMovieInCollectionBinding
import com.example.skillcinema.databinding.FragmentFilmPageBinding
import com.example.skillcinema.presentation.tabBar.AllMoviePage.AllMovieViewModel
import com.example.skillcinema.presentation.tabBar.AllMoviePage.AllMovieViewModelFactory
import com.example.skillcinema.presentation.tabBar.filmpage.FilmPageFragment
import com.example.skillcinema.presentation.tabBar.filmpage.FilmPageFragment.Companion.KEY_PEOPLE
import com.example.skillcinema.presentation.tabBar.homepage.HomePageFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@AndroidEntryPoint
class ActorPageFragment : Fragment() {

    private var _binding: FragmentActorPageBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var actorPageViewModelFactory: ActorPageViewModelFactory
    private val viewModel: ActorPageViewModel by activityViewModels<ActorPageViewModel> { actorPageViewModelFactory }

    private lateinit var nameActor: String
    private lateinit var sexActor: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentActorPageBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonBack.setOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }

        val actorId: Int? = arguments?.getInt(KEY_PEOPLE)
        println(actorId)
        viewModel.getActorInfo(actorId)

        viewModel.actorInfo.onEach { item ->
            Glide.with(requireContext())
                .load(item.posterUrl)
                .centerCrop()
                .into(binding.imageViewPhotoActor)

            binding.textViewNameActor.let {
                it.text = if (item.nameRu == "") item.nameEn else item.nameRu
                nameActor = it.text.toString()
                it.setTypeface(null, Typeface.BOLD)
            }

            binding.textViewTitle.let {

                it.text = if (item.sex == "MALE") {
                    sexActor = item.sex
                    getString(R.string.title_page_male)
                } else {
                    sexActor = item.sex
                    getString(R.string.title_page_fimale)
                }
                it.setTypeface(null, Typeface.BOLD)
            }

            if (item.profession == null) {
                binding.textViewJobActor.visibility = View.GONE
            } else {
                binding.textViewJobActor.text = item.profession
            }

            binding.textViewSumFilmography.text = item.films.size.toString()
        }.launchIn(viewLifecycleOwner.lifecycleScope)

        binding.imageButtonAllFilm.setOnClickListener {
            val argument = Bundle().apply {
                putInt(KEY_PEOPLE, actorId!!)
                putString(KEY_ACTOR_NAME, nameActor)
                putString(KEY_SEX_ACTOR, sexActor)
            }
            findNavController().navigate(
                R.id.action_actorPageFragment_to_filmographyPageFragment,
                argument
            )
        }
    }

    companion object {
        const val KEY_ACTOR_NAME = "KEY_ACTOR_NAME"
        const val KEY_SEX_ACTOR = "KEY_SEX_ACTOR"
    }

}