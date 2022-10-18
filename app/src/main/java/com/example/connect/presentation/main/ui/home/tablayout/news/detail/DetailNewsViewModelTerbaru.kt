package com.example.connect.presentation.main.ui.home.tablayout.news.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.connect.domain.entity.KirimanEntity
import com.example.connect.domain.usecase.UseCase
import com.example.connect.utilites.base.Result
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

class DetailNewsViewModelTerbaru @Inject constructor(
    val useCase: UseCase
):ViewModel() {
    private val _state = MutableStateFlow<DetailNewsState>(DetailNewsState.Init)
    val state get() = _state

    private val _data = MutableStateFlow<KirimanEntity?>(null)
    val data get() = _data

    private fun loading() {
        _state.value = DetailNewsState.Loading()
    }

    private fun success(detailNewsEntity: KirimanEntity){
        _state.value = DetailNewsState.Success(detailNewsEntity)
    }

    private fun error(detailNewsEntity: KirimanEntity){
        _state.value = DetailNewsState.Error(detailNewsEntity)
    }

    fun detailNews(id:Int){
        viewModelScope.launch {
            useCase.getDetailKiriman(id)
                .onStart { loading()

                }.catch {

                }.collect{ result ->
                    when(result){
                        is Result.Success -> success(result.data)
                        is Result.Error -> { }
                    }
                }
        }
    }
}

sealed class DetailNewsState {
    object Init : DetailNewsState()

    data class Loading(val loading: Boolean = true) : DetailNewsState()
    data class Success(val detailNewsEntity: KirimanEntity) : DetailNewsState()
    data class Error(val response: KirimanEntity) : DetailNewsState()
}