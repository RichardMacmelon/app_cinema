package com.example.skillcinema.presentation.tabBar.allPeopleInPage

import android.graphics.Typeface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.skillcinema.R
import com.example.skillcinema.data.EntityPeopleDto
import com.example.skillcinema.databinding.FragmentAllPeopleInPageBinding
import com.example.skillcinema.presentation.tabBar.filmpage.FilmPageFragment
import com.example.skillcinema.presentation.tabBar.filmpage.FilmPageFragment.Companion.KEY_ACTOR_TITLE
import com.example.skillcinema.presentation.tabBar.filmpage.FilmPageFragment.Companion.KEY_PEOPLE_ALL
import com.example.skillcinema.presentation.tabBar.filmpage.MyPeopleCardAdapter
import com.example.skillcinema.presentation.tabBar.homepage.HomePageFragment.Companion.ARGUMENT_FILM_KEY
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@AndroidEntryPoint
class AllPeopleInPageFragment : Fragment() {

    private var _binding: FragmentAllPeopleInPageBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var allPeopleInPageViewModelFactory: AllPeopleInPageViewModelFactory
    private val viewModel: AllPeopleInPageViewModel by viewModels<AllPeopleInPageViewModel> { allPeopleInPageViewModelFactory }

    private val peopleCardAdapter = MyPeopleCardAdapter{ item -> viewActorPage(item) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAllPeopleInPageBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val key: Int? = arguments?.getInt(ARGUMENT_FILM_KEY)
        arguments?.let {
            viewModel.loadInfoPeople(key!!)
        }

        when(arguments?.getInt(KEY_PEOPLE_ALL)) {
            1 -> {
                viewModel.actorInfo.onEach {
                    peopleCardAdapter.setData(it)
                    binding.recyclerView.adapter = peopleCardAdapter
                }.launchIn(viewLifecycleOwner.lifecycleScope)
            }
            else -> {
                viewModel.workerMovieInfo.onEach {
                    peopleCardAdapter.setData(it)
                    binding.recyclerView.adapter = peopleCardAdapter
                }.launchIn(viewLifecycleOwner.lifecycleScope)
            }
        }

        binding.view.setOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }

        binding.titleTextView.let {
            it.text = arguments?.getString(KEY_ACTOR_TITLE)
            it.setTypeface(null, Typeface.BOLD)
        }

    }

    private fun viewActorPage(item: EntityPeopleDto) {
        val argument = bundleOf(FilmPageFragment.KEY_PEOPLE to item.staffId)
        findNavController().navigate(R.id.action_allPeopleInPageFragment_to_actorPageFragment2, argument)
    }

}