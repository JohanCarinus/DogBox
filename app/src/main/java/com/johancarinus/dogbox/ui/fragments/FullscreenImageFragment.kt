package com.johancarinus.dogbox.ui.fragments

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.os.Environment
import android.os.FileUtils
import android.provider.MediaStore
import android.view.*
import androidx.core.graphics.drawable.toBitmap
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import coil.load
import com.johancarinus.dogbox.viewmodel.FullscreenImageViewModel
import dagger.hilt.android.AndroidEntryPoint
import johancarinus.dogbox.R
import johancarinus.dogbox.databinding.FullscreenImageFragmentBinding
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.util.*
import android.net.Uri

import android.content.ContentValues
import android.content.pm.PackageManager

import android.os.Build
import android.widget.ImageView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.FileProvider
import androidx.core.net.toUri
import com.johancarinus.dogbox.DogBoxApplication_HiltComponents
import com.johancarinus.dogbox.util.*

import java.io.OutputStream
import java.util.jar.Manifest


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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.topAppBar.setOnMenuItemClickListener {
            when(it.itemId) {
                R.id.share -> {
                    activity?.let { activity ->
                        val uri = activity.saveImage(binding.fullImage, System.currentTimeMillis().toString())
                        uri?.let { it1 -> activity.shareImage(it1) }

                        if (uri == null) {
                            Toast.makeText(activity, R.string.error_something_went_wrong, Toast.LENGTH_LONG).show()
                        }
                    }
                    true
                }
                R.id.save -> {
                    activity?.let { activity ->
                        val uri = activity.saveImage(binding.fullImage, System.currentTimeMillis().toString())
                        if (uri == null) {
                            Toast.makeText(activity, R.string.error_something_went_wrong, Toast.LENGTH_LONG).show()
                        } else {
                            Toast.makeText(activity, R.string.image_saved, Toast.LENGTH_LONG).show()
                        }
                    }
                    true
                }
                else -> false
            }
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
}