package com.nishantp.payback.main.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.nishantp.payback.R
import com.nishantp.payback.constants.AppConstants
import com.nishantp.payback.databinding.FragmentDetailBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFragment : Fragment() {

    private val viewModel: DetailViewModel by viewModels()
    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel

        viewModel.imageDetailState.observe(viewLifecycleOwner) {

            if (it.isLoading) {
                binding.progressBar.visibility = View.VISIBLE
            } else {
                binding.progressBar.visibility = View.GONE
            }

            if (it.isError) {
                Toast.makeText(
                    requireContext(),
                    it.errorMessage?.asString(requireContext()),
                    Toast.LENGTH_SHORT
                ).show()
            }

            it.image?.let { imageLoaded ->
                val requestOptions: RequestOptions = RequestOptions().centerCrop()
                Glide.with(binding.image).load(imageLoaded.imageUrl).apply(requestOptions)
                    .into(binding.image)

                binding.tvUserName.text = getString(R.string.username, imageLoaded.username)
                binding.tvTags.text = getString(R.string.tags, imageLoaded.tags)
                binding.tvLikes.text = getString(R.string.likes, imageLoaded.likes)
                binding.tvDownloads.text = getString(R.string.downloads, imageLoaded.downloads)
                binding.tvComments.text = getString(R.string.comments, imageLoaded.comments)
            }
        }

        viewModel.loadImageDetails(requireArguments().getInt(AppConstants.IMAGE_ID_KEY))
    }
}