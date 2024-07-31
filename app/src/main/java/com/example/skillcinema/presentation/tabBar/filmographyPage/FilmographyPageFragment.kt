package com.example.skillcinema.presentation.tabBar.filmographyPage

import android.graphics.Typeface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.skillcinema.R
import com.example.skillcinema.data.EntityFilmographyDto
import com.example.skillcinema.databinding.FragmentFilmographyPageBinding
import com.example.skillcinema.presentation.tabBar.actorPage.ActorPageFragment.Companion.KEY_ACTOR_NAME
import com.example.skillcinema.presentation.tabBar.actorPage.ActorPageFragment.Companion.KEY_SEX_ACTOR
import com.example.skillcinema.presentation.tabBar.actorPage.ActorPageViewModel
import com.example.skillcinema.presentation.tabBar.filmpage.FilmPageFragment
import com.example.skillcinema.presentation.tabBar.homepage.HomePageFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class FilmographyPageFragment : Fragment() {

    private var _binding: FragmentFilmographyPageBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ActorPageViewModel by activityViewModels<ActorPageViewModel>()

    private val myFilmographyPageAdapter = MyFilmographyPageAdapter { id ->  onItemClick(id) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFilmographyPageBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonBack.setOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }

        val actorId = arguments?.getInt(FilmPageFragment.KEY_PEOPLE)

        binding.textViewNameActor.let {
            it.text = arguments?.getString(KEY_ACTOR_NAME)
            it.setTypeface(null, Typeface.BOLD)
        }

        if (arguments?.getString(KEY_SEX_ACTOR) == "MALE") {
            with(binding) {
                button4.text = "Актер"
                button1.text = "Актер"
                button5.text = "Актер: играет сам себя"
                button2.text = "Актер: играет сам себя"
            }
        }

        getFilm(1, actorId)

        binding.button4.setOnClickListener {
            buttonVisible(1)
            viewModel.getFilmography(1, actorId)
            getFilm(1, actorId)
        }

        binding.button5.setOnClickListener {
            buttonVisible(2)
            getFilm(2, actorId)
        }

        binding.button6.setOnClickListener {
            buttonVisible(3)
            getFilm(3, actorId)
        }

    }

    private fun getFilm(key: Int, actorId: Int?) {
        viewModel.getFilmography(key, actorId)
        viewModel.filmographyActor.onEach {
            myFilmographyPageAdapter.setData(it)
            binding.recyclerViewPhoto.adapter = myFilmographyPageAdapter
        }.launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun buttonVisible(id: Int) {
        when (id) {
            1 -> {
                binding.let {
                    it.button1.visibility = View.VISIBLE
                    it.button2.visibility = View.INVISIBLE
                    it.button3.visibility = View.INVISIBLE
                    it.button4.visibility = View.INVISIBLE
                    it.button5.visibility = View.VISIBLE
                    it.button6.visibility = View.VISIBLE
                }
            }

            2 -> {
                binding.let {
                    it.button1.visibility = View.INVISIBLE
                    it.button3.visibility = View.INVISIBLE
                    it.button2.visibility = View.VISIBLE
                    it.button5.visibility = View.INVISIBLE
                    it.button4.visibility = View.VISIBLE
                    it.button6.visibility = View.VISIBLE
                }
            }

            3 -> {
                binding.let {
                    it.button1.visibility = View.INVISIBLE
                    it.button2.visibility = View.INVISIBLE
                    it.button3.visibility = View.VISIBLE
                    it.button6.visibility = View.INVISIBLE
                    it.button4.visibility = View.VISIBLE
                    it.button5.visibility = View.VISIBLE
                }
            }
        }
    }

    private fun onItemClick(item: EntityFilmographyDto) {
        val argument = bundleOf(HomePageFragment.ARGUMENT_FILM_KEY to item.filmId)
        findNavController().navigate(R.id.action_filmographyPageFragment_to_filmPageFragment, argument)
    }

}