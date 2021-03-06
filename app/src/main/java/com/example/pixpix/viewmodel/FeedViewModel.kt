package com.example.pixpix.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.example.pixpix.service.PixPagingSource
import com.example.pixpix.service.RetrofitAPI
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FeedViewModel @Inject constructor(private val retrofitAPI: RetrofitAPI) : ViewModel() {

    private val queryString = MutableLiveData<String>()

    fun getImages() =
        Pager(config = PagingConfig(pageSize = 20, maxSize = 200),
            pagingSourceFactory = { PixPagingSource(retrofitAPI,queryString.value) }).flow.cachedIn(viewModelScope)

    fun searchImage(query:String){
        queryString.value = query

    }

}