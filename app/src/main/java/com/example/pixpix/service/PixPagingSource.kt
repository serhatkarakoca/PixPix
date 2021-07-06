package com.example.pixpix.service

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.pixpix.model.ImageModel
import dagger.hilt.android.AndroidEntryPoint
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject


class PixPagingSource(
    private val retrofitAPI: RetrofitAPI,
    private var query:String
) : PagingSource<Int, ImageModel>() {



    override fun getRefreshKey(state: PagingState<Int, ImageModel>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ImageModel> {
        val position = params.key ?: FIRST_PAGE_INDEX
        println("page" + position)
        return try {
            val response = retrofitAPI.getImages(query = query ,page = position, perPage = params.loadSize)
            val photos = response.body()?.hits!!
            LoadResult.Page(
                data = photos,
                prevKey = if (position == FIRST_PAGE_INDEX) null else position - 1,
                nextKey = if (photos.isEmpty()) null else position + 1
            )
        } catch (exception: IOException) {
            LoadResult.Error(exception)
        } catch (exception: HttpException) {
            LoadResult.Error(exception)
        }
    }

    companion object {
        private const val FIRST_PAGE_INDEX = 1
    }
}