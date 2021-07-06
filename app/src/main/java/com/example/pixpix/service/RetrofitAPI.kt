package com.example.pixpix.service

import com.example.pixpix.Constants.Companion.API_KEY
import com.example.pixpix.model.ImageResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitAPI {

    @GET("api/")
    suspend fun getImages(
        @Query("key") key: String = API_KEY,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int
    ): Response<ImageResponse>

}