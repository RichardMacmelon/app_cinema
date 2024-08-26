package com.example.skillcinema.presentation.tabBar.search.searchPage

import android.app.Activity
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.os.bundleOf
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.skillcinema.R
import com.example.skillcinema.data.EntitySearchDataMovieDto
import com.example.skillcinema.databinding.FragmentActorPageBinding
import com.example.skillcinema.databinding.FragmentSearchBinding
import com.example.skillcinema.presentation.tabBar.actorPage.ActorPageViewModel
import com.example.skillcinema.presentation.tabBar.actorPage.ActorPageViewModelFactory
import com.example.skillcinema.presentation.tabBar.filmpage.FilmPageViewModel
import com.example.skillcinema.presentation.tabBar.homepage.HomePageFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@AndroidEntryPoint
class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var viewModelFactory: SearchPageViewModelFactory
    private val viewModel: SearchPageViewModel by viewModels<SearchPageViewModel> { viewModelFactory }

    private val mySearchMovieAdapter = MySearchMovieAdapter {id -> onClick(id)}

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSearchBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.imageButton.setOnClickListener {
            findNavController().navigate(R.id.action_searchFragment_to_searchFilterFragment)
        }

        binding.search.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                binding.search.clearFocus()
                binding.search.let { activity?.hideKeyboard(it) }
                true
            } else {
                false
            }
        }

        binding.search.doOnTextChanged { text, _, _, _ ->
            viewModel.searchMovie(text.toString())
        }

        viewModel.infoMovie.onEach {
            if (it.isNotEmpty()) {
                mySearchMovieAdapter.setData(it)
                binding.recyclerViewPhoto.adapter = mySearchMovieAdapter
                binding.textViewError.visibility = View.INVISIBLE
                binding.recyclerViewPhoto.visibility = View.VISIBLE
            } else {
                binding.textViewError.visibility = View.VISIBLE
                binding.recyclerViewPhoto.visibility = View.INVISIBLE
            }
        }.launchIn(viewLifecycleOwner.lifecycleScope)

        binding.textViewError.visibility = View.INVISIBLE
    }

    private fun onClick(item: EntitySearchDataMovieDto) {
        val argument = bundleOf(HomePageFragment.ARGUMENT_FILM_KEY to item.filmId)
        findNavController().navigate(R.id.action_global_filmPageFragment, argument)
    }

    fun Context.hideKeyboard(view: View) {
        val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }
}