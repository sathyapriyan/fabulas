package com.sandsindia.fabulas.viewmodels

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sandsindia.fabulas.data.remote.NetworkResult
import com.sandsindia.fabulas.data.repository.FabulasRepository
import com.sandsindia.fabulas.models.CommentResponse
import com.sandsindia.fabulas.utils.CommonUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CommentListViewModel @Inject constructor(
    private val fabulasRepository: FabulasRepository,
    private val ioDispatcher: CoroutineDispatcher
):ViewModel(){
    private val _commentListResponse: MutableLiveData<NetworkResult<List<CommentResponse>>> = MutableLiveData()
    val commentListResponse = _commentListResponse
    @RequiresApi(Build.VERSION_CODES.M)
    fun getComments(context: Context,url:String) {

        _commentListResponse.value = NetworkResult.Loading()

        if (CommonUtil.hasInternetConnection(context = context)) {

            viewModelScope.launch(ioDispatcher) {

                try {

                    val response = fabulasRepository.fetchCommentDetails(url)

                    if (response.isSuccessful){

                        _commentListResponse.postValue(
                            NetworkResult.Success(
                                data = response.body()!!
                            )
                        )

                    } else {

                        _commentListResponse.postValue(NetworkResult.Error(response.message()))

                    }

                } catch (exception: Exception) {

                    _commentListResponse.postValue(NetworkResult.Error(exception.message))

                }

            }

        } else {

            _commentListResponse.value = NetworkResult.Error("No Internet Connection!")

        }

    }

}