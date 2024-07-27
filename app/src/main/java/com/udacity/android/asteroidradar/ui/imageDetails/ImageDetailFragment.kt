package com.udacity.android.asteroidradar.ui.imageDetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.udacity.android.asteroidradar.databinding.FragmentImageDetailBinding


class ImageDetailFragment : Fragment() {

    private lateinit var viewModel: ImageDetailViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val binding = FragmentImageDetailBinding.inflate(inflater)

        binding.lifecycleOwner = this

        viewModel = ViewModelProvider(this)[ImageDetailViewModel::class.java]

        binding.viewModel = viewModel

        return binding.root
    }


}