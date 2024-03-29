package com.example.connect.data.api

import com.example.connect.data.auth.ResponseListWrapper
import com.example.connect.data.auth.ResponseObjectWrapper
import com.example.connect.data.model.response.DetailArtikelResponse
import com.example.connect.data.model.response.LayananResponse
import com.example.connect.data.model.response.elearning.PlaylistElearningByIdResponse
import com.example.connect.data.model.response.elearning.PlaylistElearningResponse
import com.example.connect.data.model.response.elearning.VideoELearningResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface LayananApiClient {
    // API PLAYLIST E LEARNING
    @GET("playlist")
    suspend fun getPlaylist(): Response<ResponseListWrapper<PlaylistElearningResponse>>

    // API PLAYLIST E LEARNING By ID
    @GET("playlist/{id}")
    suspend fun getPlaylist(@Path("id") id: Int): Response<ResponseObjectWrapper<PlaylistElearningByIdResponse>>

    // API VIDEO E LEARNING
    @GET("video/{id}")
    suspend fun getVideoPlaylist(
        @Path("id") id: Int
    ): Response<ResponseListWrapper<VideoELearningResponse>>

    //Layanan
    // API GET ALL LAYANAN
    @GET("layanan-public")
    suspend fun getAllLayanan(
    ): Response<ResponseListWrapper<LayananResponse>>

    @GET("layanan/{id}")
    suspend fun getDetailLayanan(
        @Path("id") id: Int
    ): Response<ResponseObjectWrapper<DetailArtikelResponse>>
}