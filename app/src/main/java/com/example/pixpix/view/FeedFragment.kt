package com.example.pixpix.view

import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
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
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject


class FeedFragment : Fragment() {

    lateinit var viewModel: FeedViewModel
    lateinit var imagesAdapter: ImagesAdapter
    private var _binding: FragmentFeedBinding? = null
    private val binding get() = _binding!!
    var job: Job? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

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

        job = viewLifecycleOwner.lifecycleScope.launch {
            viewModel.getImages().collectLatest { pagingData ->
                imagesAdapter.submitData(pagingData)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.search_menu, menu)

        val searchItem = menu.findItem(R.id.action_search)
        val searchView = searchItem.actionView as SearchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                job?.cancel()
                query?.let {
                    viewModel.searchImage(it)
                    job =  viewLifecycleOwner.lifecycleScope.launch {
                        viewModel.getImages().collectLatest { pagingData ->
                            imagesAdapter.submitData(pagingData)
                        }
                    }

                }
                searchView.clearFocus()
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }

        })
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}