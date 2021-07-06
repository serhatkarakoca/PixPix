package com.example.pixpix.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.paging.PagingData
import com.example.pixpix.R
import com.example.pixpix.adapter.ImagesAdapter
import com.example.pixpix.databinding.FragmentFeedBinding
import com.example.pixpix.model.ImageModel
import com.example.pixpix.viewmodel.FeedViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject


class FeedFragment : Fragment() {

    lateinit var viewModel: FeedViewModel
    lateinit var imagesAdapter: ImagesAdapter
    private var _binding: FragmentFeedBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentFeedBinding.inflate(inflater, container, false)
        val view = binding.root
        return view

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(requireActivity()).get(FeedViewModel::class.java)

        imagesAdapter = ImagesAdapter()
        binding.recyclerView.adapter = imagesAdapter

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.getImages().collectLatest { pagingData ->
                imagesAdapter.submitData(pagingData)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}