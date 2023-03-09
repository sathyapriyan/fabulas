package com.sandsindia.fabulas.presentation.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.sandsindia.fabulas.R
import com.sandsindia.fabulas.models.PostResponse
import com.sandsindia.fabulas.utils.CommonUtil.loadImageFromCoil
import com.sandsindia.fabulas.utils.CommonUtil.loadPostImageFromCoil
import com.sandsindia.fabulas.utils.CommonUtil.makePostImageUrl
import com.sandsindia.fabulas.utils.CommonUtil.makeProfieImageUrl
import com.sandsindia.fabulas.utils.CommonUtil.updatedTime


class PostListAdapter(
    private val items: List<PostResponse>,
    private val onClickListener: ((doctorData: PostResponse) -> Unit),
): RecyclerView.Adapter<PostListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.post_list_item, parent, false)

        return ViewHolder(view,onClickListener)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val item = items[position]
        holder.bind(item)

    }

    override fun getItemCount(): Int = items.size

    class ViewHolder(
        private val view: View,
        private val onClickListener: ((doctorData: PostResponse) -> Unit),
    )
        : RecyclerView.ViewHolder(view){

        private val imageViewPost : ImageView = view.findViewById(R.id.imageViewPost)
        private val imageUser : ImageView = view.findViewById(R.id.imageUser)
        private val textTitle : TextView = view.findViewById(R.id.textTitle)
        private val textBody : TextView = view.findViewById(R.id.textBody)
        private val textTime : TextView = view.findViewById(R.id.textTime)

        fun bind(data : PostResponse){
            val imageUrl1 = makeProfieImageUrl(view.context,data.userId!!)
            val imageUrl2 = makePostImageUrl(view.context,data.id!!)
            val updatedTime = updatedTime(view.context,data.id!!)

            println(imageUrl1)
            println(imageUrl2)
            textTitle.text = data.title
            textBody.text = data.body
            textTime.text = updatedTime
            imageUser.loadImageFromCoil(imageUrl1)
            imageViewPost.loadPostImageFromCoil(imageUrl2)
            view.setOnClickListener{
                onClickListener.invoke(data)
            }
        }

    }

}