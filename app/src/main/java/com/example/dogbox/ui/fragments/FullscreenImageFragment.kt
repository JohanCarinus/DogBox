package com.example.dogbox.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import coil.load
import com.example.dogbox.databinding.FullscreenImageFragmentBinding
import com.example.dogbox.viewmodel.FullscreenImageViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FullscreenImageFragment : Fragment() {

    private var _binding: FullscreenImageFragmentBinding? = null
    private val binding get() = _binding!!
    private val viewModel: FullscreenImageViewModel by viewModels()

    companion object {
        fun newInstance() = FullscreenImageFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FullscreenImageFragmentBinding.inflate(inflater, container, false)

        observeViewModel()

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun observeViewModel() {
        viewModel.getImage().observe(viewLifecycleOwner, { uriImage ->
            binding.fullImage.load(uriImage.uri) {
                placeholder(uriImage.placeholderRes)
                build()
            }
        })
    }
}