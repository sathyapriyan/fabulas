package com.sandsindia.fabulas.data.repository


import com.sandsindia.fabulas.data.remote.FabulasApi
import com.sandsindia.fabulas.models.CommentResponse
import com.sandsindia.fabulas.models.PostResponse
import com.sandsindia.fabulas.models.UserResponse
import retrofit2.Response
import javax.inject.Inject

class FabulasRepository @Inject constructor(
    private val fabulasApi: FabulasApi
) {
    suspend fun fetchAllUserDetails(): Response<List<UserResponse>> {

        return fabulasApi.fetchAllUserDetails()

    }
    suspend fun fetchUserDetails(url:String): Response<UserResponse> {

        return fabulasApi.fetchUserDetails(url)

    }
    suspend fun fetchPostDetails(url:String): Response<List<PostResponse>> {

        return fabulasApi.fetchPostDetails(url)

    }
    suspend fun fetchCommentDetails(url:String): Response<List<CommentResponse>> {

        return fabulasApi.fetchCommentDetails(url)

    }

}