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
class UserDetailsViewModel @Inject constructor(
    private val fabulasRepository: FabulasRepository,
    private val ioDispatcher: CoroutineDispatcher
):ViewModel(){
    private val _userDetailsResponse: MutableLiveData<NetworkResult<UserResponse>> = MutableLiveData()
    val userDetailsResponse = _userDetailsResponse
    @RequiresApi(Build.VERSION_CODES.M)
    fun getUserDetails(context: Context,url:String) {

        _userDetailsResponse.value = NetworkResult.Loading()

        if (CommonUtil.hasInternetConnection(context = context)) {

            viewModelScope.launch(ioDispatcher) {

                try {

                    val response = fabulasRepository.fetchUserDetails(url)

                    if (response.isSuccessful){

                        _userDetailsResponse.postValue(
                            NetworkResult.Success(
                                data = response.body()!!
                            )
                        )

                    } else {

                        _userDetailsResponse.postValue(NetworkResult.Error(response.message()))

                    }

                } catch (exception: Exception) {

                    _userDetailsResponse.postValue(NetworkResult.Error(exception.message))

                }

            }

        } else {

            _userDetailsResponse.value = NetworkResult.Error("No Internet Connection!")

        }

    }

}