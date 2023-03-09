package com.sandsindia.fabulas.data.remote



import com.sandsindia.fabulas.models.CommentResponse
import com.sandsindia.fabulas.models.PostResponse
import com.sandsindia.fabulas.models.UserResponse
import com.sandsindia.fabulas.utils.Constants
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Url

interface FabulasApi {

    @GET(Constants.USERS_URL)
    suspend fun fetchAllUserDetails(): Response<List<UserResponse>>

    @GET
    suspend fun fetchUserDetails(
       @Url url: String
    ): Response<UserResponse>

    @GET
    suspend fun fetchPostDetails(
       @Url url: String
    ): Response<List<PostResponse>>

    @GET
    suspend fun fetchCommentDetails(
       @Url url: String
    ): Response<List<CommentResponse>>


}