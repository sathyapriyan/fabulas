package com.sandsindia.fabulas.viewmodels

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sandsindia.fabulas.data.remote.NetworkResult
import com.sandsindia.fabulas.data.repository.FabulasRepository
import com.sandsindia.fabulas.models.PostResponse
import com.sandsindia.fabulas.utils.CommonUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PostListViewModel @Inject constructor(
    private val fabulasRepository: FabulasRepository,
    private val ioDispatcher: CoroutineDispatcher
):ViewModel(){
    private val _postListResponse: MutableLiveData<NetworkResult<List<PostResponse>>> = MutableLiveData()
    val postListResponse = _postListResponse
    @RequiresApi(Build.VERSION_CODES.M)
    fun getPosts(context: Context,url:String) {

        _postListResponse.value = NetworkResult.Loading()

        if (CommonUtil.hasInternetConnection(context = context)) {

            viewModelScope.launch(ioDispatcher) {

                try {
                    val response = fabulasRepository.fetchPostDetails(url)

                    if (response.isSuccessful){

                        _postListResponse.postValue(
                            NetworkResult.Success(
                                data = response.body()!!
                            )
                        )

                    } else {

                        _postListResponse.postValue(NetworkResult.Error(response.message()))

                    }

                } catch (exception: Exception) {

                    _postListResponse.postValue(NetworkResult.Error(exception.message))

                }

            }

        } else {

            _postListResponse.value = NetworkResult.Error("No Internet Connection!")

        }

    }

}