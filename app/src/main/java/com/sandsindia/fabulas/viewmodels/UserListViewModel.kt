package com.sandsindia.fabulas.viewmodels

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sandsindia.fabulas.data.remote.NetworkResult
import com.sandsindia.fabulas.data.repository.FabulasRepository
import com.sandsindia.fabulas.models.UserResponse
import com.sandsindia.fabulas.utils.CommonUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserListViewModel @Inject constructor(
    private val fabulasRepository: FabulasRepository,
    private val ioDispatcher: CoroutineDispatcher
):ViewModel(){
    private val _userListResponse: MutableLiveData<NetworkResult<List<UserResponse>>> = MutableLiveData()
    val userListResponse = _userListResponse
    @RequiresApi(Build.VERSION_CODES.M)
    fun getUserList(context: Context) {

        _userListResponse.value = NetworkResult.Loading()

        if (CommonUtil.hasInternetConnection(context = context)) {

            viewModelScope.launch(ioDispatcher) {

                try {

                    val response = fabulasRepository.fetchAllUserDetails()

                    if (response.isSuccessful){

                        _userListResponse.postValue(
                            NetworkResult.Success(
                                data = response.body()!!
                            )
                        )

                    } else {

                        _userListResponse.postValue(NetworkResult.Error(response.message()))

                    }

                } catch (exception: Exception) {

                    _userListResponse.postValue(NetworkResult.Error(exception.message))

                }

            }

        } else {

            _userListResponse.value = NetworkResult.Error("No Internet Connection!")

        }

    }

}