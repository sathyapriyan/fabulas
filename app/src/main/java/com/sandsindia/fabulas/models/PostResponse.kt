package com.sandsindia.fabulas.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class PostResponse(
    @SerializedName("userId" )
    var userId : Int?    = null,
    @SerializedName("id"     )
    var id     : Int?    = null,
    @SerializedName("title"  )
    var title  : String? = null,
    @SerializedName("body"   )
    var body   : String? = null
) : Parcelable