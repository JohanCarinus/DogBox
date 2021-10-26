package com.johancarinus.dogbox.ui.fragments

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import coil.load
import com.johancarinus.dogbox.util.saveImage
import com.johancarinus.dogbox.util.shareImage
import com.johancarinus.dogbox.viewmodel.FullscreenImageViewModel
import dagger.hilt.android.AndroidEntryPoint
import johancarinus.dogbox.R
import johancarinus.dogbox.databinding.FullscreenImageFragmentBinding

@AndroidEntryPoint
class FullscreenImageFragment : Fragment() {

    private var _binding: FullscreenImageFragmentBinding? = null
    private val binding get() = _binding!!
    private val viewModel: FullscreenImageViewModel by viewModels()
    private val safeArgs: FullscreenImageFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FullscreenImageFragmentBinding.inflate(inflater, container, false)
        setupViews()
        observeViewModel()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.topAppBar.setNavigationIcon(R.drawable.ic_arrow_back_24)
        binding.topAppBar.setNavigationOnClickListener {
            activity?.onBackPressed()
        }
        binding.topAppBar.setOnMenuItemClickListener { menuItem ->
            activity?.let { handleMenuItemClick(it, menuItem) } ?: false
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupViews() {
        setHasOptionsMenu(true)
        viewModel.viewImage(safeArgs.uri)
    }

    private fun observeViewModel() {
        viewModel.getImage().observe(viewLifecycleOwner, { uriImage ->
            binding.fullImage.load(uriImage.uri) {
                placeholder(uriImage.placeholderRes)
            }
        })
    }

    private fun handleMenuItemClick(activity: Activity, menuItem: MenuItem): Boolean {
        return when (menuItem.itemId) {
            R.id.share -> {
                shareImage(activity)
                true
            }
            R.id.save -> {
                saveImage(activity)
                true
            }
            else -> false
        }
    }

    private fun shareImage(activity: Activity) {
        val uri =
            activity.saveImage(binding.fullImage, System.currentTimeMillis().toString())
        uri?.let { imageUri -> activity.shareImage(imageUri) } ?: Toast.makeText(
            activity,
            R.string.error_something_went_wrong,
            Toast.LENGTH_LONG
        ).show()
    }

    private fun saveImage(activity: Activity) {
        val uri = activity.saveImage(
            binding.fullImage,
            System.currentTimeMillis().toString()
        )
        uri?.let {
            Toast.makeText(activity, R.string.image_saved, Toast.LENGTH_LONG).show()
        } ?: Toast.makeText(
            activity,
            R.string.error_something_went_wrong,
            Toast.LENGTH_LONG
        ).show()
    }
}