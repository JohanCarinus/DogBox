package com.johancarinus.dogbox.ui.fragments

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.johancarinus.dogbox.ui.adapter.MasonryImageGalleryAdapter
import com.johancarinus.dogbox.ui.adapter.MasonryImageGalleryOnClickListener
import com.johancarinus.dogbox.viewmodel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import johancarinus.dogbox.databinding.HomeFragmentBinding

// TODO: Should the NavHostFragment only be on a single class
@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: HomeFragmentBinding? = null
    private val binding get() = _binding!!
    private val viewModel: HomeViewModel by viewModels()

    private lateinit var adapter: MasonryImageGalleryAdapter

    companion object {
        fun newInstance() = HomeFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = HomeFragmentBinding.inflate(inflater, container, false)

        setupViews()
        observeViewModel()

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupViews() {
        adapter = MasonryImageGalleryAdapter(
            MasonryImageGalleryOnClickListener { uri: Uri -> viewModel.openImage(uri) }
        )
        binding.imageRecyclerView.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        binding.imageRecyclerView.adapter = adapter
    }

    private fun observeViewModel() {
        viewModel.getNavDirection().observe(viewLifecycleOwner, { navDirection ->
            findNavController().navigate(navDirection)
        })
        viewModel.getDogUrls().observe(viewLifecycleOwner, { uriImages ->
            adapter.submitList(uriImages)
        })
    }
}