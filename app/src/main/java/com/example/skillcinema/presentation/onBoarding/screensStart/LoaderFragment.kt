package com.example.skillcinema.presentation.onBoarding.screensStart

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.skillcinema.R
import com.example.skillcinema.presentation.MainActivity
import com.example.skillcinema.presentation.SecondActivity
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class LoaderFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Handler().postDelayed({
            val intent = Intent(activity, SecondActivity::class.java)
            activity?.startActivity(intent)
            activity?.finish()
        }, 3000)
        return inflater.inflate(R.layout.fragment_loader, container, false)
    }

}