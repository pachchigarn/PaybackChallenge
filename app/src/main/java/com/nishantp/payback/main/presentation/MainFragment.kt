package com.nishantp.payback.main.presentation

import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.*
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.nishantp.payback.R
import com.nishantp.payback.constants.AppConstants
import com.nishantp.payback.databinding.FragmentMainBinding
import com.nishantp.payback.utils.DialogUtils
import com.nishantp.payback.utils.GridSpacingItemDecoration
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : Fragment() {

    private val viewModel: MainViewModel by viewModels()
    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel

        val imageAdapter = ImageAdapter {
            DialogUtils.showDialogOkCancel(
                context = requireContext(), title = getString(R.string.hello), message = getString(
                    R.string.find_out_more
                )
            ) { dialogInterface: DialogInterface?, _: Int ->
                dialogInterface?.dismiss()
                val bundle = bundleOf(AppConstants.IMAGE_ID_KEY to it.id)
                findNavController().navigate(R.id.detailFragment, bundle)
            }
        }
        binding.rvImages.adapter = imageAdapter
        binding.rvImages.addItemDecoration(
            GridSpacingItemDecoration(
                spanCount = 2, spacing = 50, includeEdge = true
            )
        )

        viewModel.searchImageState.observe(viewLifecycleOwner) {
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

            if (it.images.isNotEmpty()) {
                Log.d(
                    MainFragment::class.java.simpleName,
                    it.images.map { image -> image.id }.toString()
                )
                imageAdapter.submitList(it.images)
            }
        }

        binding.etSearch.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_GO) {
                searchImages(binding.etSearch.text.toString())
                true
            }
            false
        }

        binding.ivSearch.setOnClickListener {
            searchImages(binding.etSearch.text.toString())
        }

        searchImages(AppConstants.DEFAULT_SEARCH_VALUE)
    }

    private fun searchImages(query: String) {
        if (!viewModel.query.value.equals(query.trim(), false) && query.isNotBlank()) {
            viewModel.getImages(query = query.trim())
        }

        val inputManager: InputMethodManager =
            requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputManager.hideSoftInputFromWindow(
            requireActivity().currentFocus?.windowToken,
            InputMethodManager.HIDE_NOT_ALWAYS
        )
    }

}