package com.sandsindia.fabulas.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.widget.ImageView
import androidx.annotation.RequiresApi
import coil.load
import coil.request.CachePolicy
import coil.transform.CircleCropTransformation
import coil.transform.RoundedCornersTransformation
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.sandsindia.fabulas.R

object CommonUtil {

    @RequiresApi(Build.VERSION_CODES.M)
    fun hasInternetConnection(context: Context): Boolean {

        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = connectivityManager.activeNetwork

        if (activeNetwork != null) {

            val networkCapabilities = connectivityManager.getNetworkCapabilities(activeNetwork)

            return networkCapabilities!!.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ||
                    networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)

        }

        return false

    }

    fun ImageView.loadImageFromCoil(url: String?) {
        if(url!=null) {

            this.load(url){
                crossfade(true)
                placeholder(R.drawable.ic_user_image_place_holder)
                error(R.drawable.ic_user_image_place_holder)
                diskCachePolicy(CachePolicy.ENABLED)
                transformations(CircleCropTransformation())
            }

        }

    }
    fun ImageView.loadPostImageFromCoil(url: String?) {
        if(url!=null) {

            this.load(url){
                crossfade(true)
                placeholder(R.drawable.ic_image)
                error(R.drawable.ic_user_image_error)
                diskCachePolicy(CachePolicy.ENABLED)
                transformations(RoundedCornersTransformation())
            }

        }

    }

    fun makeUserDetailsUrl(id: Int?): String {

        return Constants.BASE_URL+Constants.USERS_URL+id

    }

    fun makeAddress(street: String?, city: String?, zipcode: String?): String {

        return """$street
            |$city
            |$zipcode
        """.trimMargin()
    }

    fun makeC0mpaney(name: String?, bs: String?, catchPhrase: String?): String {
        return """$name
            |$bs
            |$catchPhrase
        """.trimMargin()
    }

    fun makeProfieImageUrl(context: Context, id: Int): String{
            return context.resources.getStringArray(R.array.profile_photo)[digSum(id)]
    }
    fun makePostImageUrl(context: Context, id: Int): String{
            return context.resources.getStringArray(R.array.post_photo)[digSum(id)]
    }
    fun updatedTime(context: Context, id: Int): String {
            return context.resources.getStringArray(R.array.updated_time)[digSum(id)]
    }
    fun makePostUrl(id: Int?): String {
        return Constants.BASE_URL+Constants.USERS_URL+id+Constants.POSTS_URL_PRE
    }

    fun makeCommentsUrl(id: Int?): String {
        return Constants.BASE_URL+Constants.POSTS_URL_SEF+id+Constants.COMMENTS_URL
    }

    //function to calculate the sum of digits
    private fun digSum(n: Int): Int {
        //variable to store sum of digits
        var num = n
        var sum = 0
        //loop to do sum while sum is not less than or equal to 9
        while (num > 0 || sum > 9) {
            if (num == 0) {
                num = sum
                sum = 0
            }
            //determines the last digit of the number and add that digit to the sum variable
            sum += num % 10
            //remove the last digit of the number
            num /= 10
        }
        //returns the number
        return sum
    }

    fun alertDialogError(context: Context, error:String){
        MaterialAlertDialogBuilder(context)
            .setMessage("$error?")
            .setIcon(R.drawable.ic_question_mark)
            .setCancelable(false)
            .setNegativeButton("OK") {  dialog, _ -> dialog.dismiss() }
            .show()

    }

}