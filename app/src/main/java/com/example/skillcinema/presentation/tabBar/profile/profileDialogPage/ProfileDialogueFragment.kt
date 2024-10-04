package com.example.skillcinema.presentation.tabBar.profile.profileDialogPage

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.example.skillcinema.R
import com.example.skillcinema.databinding.FragmentProfileBinding
import com.example.skillcinema.databinding.FragmentProfileDialogueBinding
import com.example.skillcinema.presentation.tabBar.profile.profilePage.MyFilmFromCollectionAdapter
import com.example.skillcinema.presentation.tabBar.profile.profilePage.MyPlaylistAdapter
import com.example.skillcinema.presentation.tabBar.profile.profilePage.ProfileViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileDialogueFragment : DialogFragment() {

    private var _binding: FragmentProfileDialogueBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ProfileDialogueViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileDialogueBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonAddNewCollection.setOnClickListener {
            val collectionName = binding.editText.text.toString()
            if (collectionName.isNotEmpty()) {
                viewModel.addCollection(collectionName)
                dismiss()
            } else {
                Toast.makeText(
                    requireContext(),
                    getString(R.string.dialogue_erorr),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        binding.buttonClose.setOnClickListener {
            dismiss()
        }
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setBackgroundDrawableResource(R.drawable.background_dialogue)
        dialog?.window?.setLayout(
            (resources.displayMetrics.widthPixels * 0.8).toInt(),
            (resources.displayMetrics.heightPixels * 0.3).toInt()
        )

    }

}