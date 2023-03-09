package com.sandsindia.fabulas.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class CommentResponse(
    @SerializedName("postId" )
    var postId : Int?    = null,
    @SerializedName("id"     )
    var id     : Int?    = null,
    @SerializedName("name"   )
    var name   : String? = null,
    @SerializedName("email"  )
    var email  : String? = null,
    @SerializedName("body"   )
    var body   : String? = null
) : Parcelable