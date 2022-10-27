package com.example.connect.domain.repo

import com.example.connect.data.auth.ResponseListWrapper
import com.example.connect.data.auth.ResponseObjectWrapper
import com.example.connect.data.model.request.AgendaRequest
import com.example.connect.data.model.request.KirimanRequest
import com.example.connect.data.model.request.ProductRequest
import com.example.connect.data.model.request.ProfileRequest
import com.example.connect.data.model.response.*
import com.example.connect.domain.entity.*
import com.example.connect.utilites.base.Result
import kotlinx.coroutines.flow.Flow

interface ProfileApiRepository {
    suspend fun getProfile(id: Int): Flow<Result<List<ProfileEntity>, ResponseListWrapper<ProfileResponse>>>

    suspend fun postAgenda(agendaRequest: AgendaRequest): Flow<Result<List<PostAgendaEntity>, ResponseListWrapper<AgendaResponse>>>

    suspend fun postKiriman(kirimanRequest: KirimanRequest): Flow<Result<PostKirimanEntity, ResponseObjectWrapper<PostKirimanResponse>>>

    suspend fun postProduct(productRequest: ProductRequest): Flow<Result<List<PostProductEntity>, ResponseListWrapper<PostProductResponse>>>

    suspend fun updateMyProfile(
        profileRequest: ProfileRequest,
        id: Int,
        method: Map<String, String>
    ):
            Flow<Result<EditProfleEntity, ResponseObjectWrapper<EditProfileResponse>>>

    suspend fun deletePendidikan(id: Int): Flow<Result<DeletePendidikanEntity, ResponseObjectWrapper<DeletePendidikanResponse>>>

    //belom diperbaiki

    suspend fun postPendidikan(): Flow<Result<SementaraEntity, ResponseObjectWrapper<ProfileResponse>>>

}