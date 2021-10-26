package com.johancarinus.dogbox.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import coil.load
import com.johancarinus.dogbox.viewmodel.FullscreenImageViewModel
import dagger.hilt.android.AndroidEntryPoint
import johancarinus.dogbox.databinding.FullscreenImageFragmentBinding

@AndroidEntryPoint
class FullscreenImageFragment : Fragment() {

    private var _binding: FullscreenImageFragmentBinding? = null
    private val binding get() = _binding!!
    private val viewModel: FullscreenImageViewModel by viewModels()
    private val safeArgs: FullscreenImageFragmentArgs by navArgs()

    companion object {
        fun newInstance() = FullscreenImageFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FullscreenImageFragmentBinding.inflate(inflater, container, false)

        setupViews()
        observeViewModel()

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupViews() {
        viewModel.viewImage(safeArgs.uri)
    }

    private fun observeViewModel() {
        viewModel.getImage().observe(viewLifecycleOwner, { uriImage ->
            binding.fullImage.load(uriImage.uri) {
                placeholder(uriImage.placeholderRes)
            }
        })
    }
}