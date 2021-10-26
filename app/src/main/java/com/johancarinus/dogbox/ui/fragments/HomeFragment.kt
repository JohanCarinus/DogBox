package com.johancarinus.dogbox.ui.fragments

import android.net.Uri
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.johancarinus.dogbox.ui.adapter.InfiniteScrollListener
import com.johancarinus.dogbox.ui.adapter.MasonryImageGalleryAdapter
import com.johancarinus.dogbox.ui.adapter.MasonryImageGalleryOnClickListener
import com.johancarinus.dogbox.viewmodel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import johancarinus.dogbox.R
import johancarinus.dogbox.databinding.HomeFragmentBinding

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: HomeFragmentBinding? = null
    private val binding get() = _binding!!
    private val viewModel: HomeViewModel by viewModels()

    private lateinit var adapter: MasonryImageGalleryAdapter

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

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.top_app_bar_home, menu)
    }

    private fun setupViews() {
        adapter = MasonryImageGalleryAdapter(
            MasonryImageGalleryOnClickListener { uri: Uri -> viewModel.openImage(uri) }
        )
        val layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        binding.imageRecyclerView.layoutManager = layoutManager
        binding.imageRecyclerView.adapter = adapter
        binding.imageRecyclerView.addOnScrollListener(object : InfiniteScrollListener(layoutManager) {
            override fun onLoadMore() {
                viewModel.fetchDogUrls()
            }

            override fun isDataLoading(): Boolean {
                return viewModel.isLoading().value ?: false
            }
        })
    }

    private fun observeViewModel() {
        viewModel.initView()
        viewModel.showIsLoading().observe(viewLifecycleOwner, { showIsLoading ->
            if (showIsLoading) {
                binding.imageRecyclerView.visibility = View.GONE
                binding.progressBarLayout.visibility = View.VISIBLE
            } else {
                binding.imageRecyclerView.visibility = View.VISIBLE
                binding.progressBarLayout.visibility = View.GONE
            }
        })
        viewModel.getNavDirection().observe(viewLifecycleOwner, { navDirectionEvent ->
            navDirectionEvent.getDataIfNotConsumed()?.let {
                findNavController().navigate(it)
            }
        })
        viewModel.getDogUrls().observe(viewLifecycleOwner, { uriImages ->
            adapter.submitList(uriImages)
        })
    }
}