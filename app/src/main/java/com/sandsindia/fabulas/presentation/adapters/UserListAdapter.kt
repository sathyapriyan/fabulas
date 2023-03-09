package com.sandsindia.fabulas.presentation.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.sandsindia.fabulas.R
import com.sandsindia.fabulas.models.UserResponse
import com.sandsindia.fabulas.utils.CommonUtil.loadImageFromCoil
import com.sandsindia.fabulas.utils.CommonUtil.makeProfieImageUrl


class UserListAdapter(
    private val items: List<UserResponse>,
    private val onClickListener: ((doctorData: UserResponse) -> Unit),
): RecyclerView.Adapter<UserListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.user_list_item, parent, false)

        return ViewHolder(view,onClickListener)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val item = items[position]
        holder.bind(item)

    }

    override fun getItemCount(): Int = items.size

    class ViewHolder(
        private val view: View,
        private val onClickListener: ((doctorData: UserResponse) -> Unit),
    )
        : RecyclerView.ViewHolder(view){

        private val imgViewProfile : ImageView = view.findViewById(R.id.imgViewProfile)
        private val textViewName : TextView = view.findViewById(R.id.textViewName)
        private val textViewUserName : TextView = view.findViewById(R.id.textViewUserName)
        private val textCompaneyName : TextView = view.findViewById(R.id.textCompaneyName)
        private val textEmail : TextView = view.findViewById(R.id.textEmail)

        fun bind(data : UserResponse){

            textViewName.text = data.name
            textViewUserName.text = data.username
            textCompaneyName.text = data.company!!.name
            textEmail.text = data.email
            val imageUrl = makeProfieImageUrl(view.context,data.id!!)
           // val imageUrl= view.context.resources.getStringArray(R.array.profile_photo)[data.id!!.toInt()]
            imgViewProfile.loadImageFromCoil(imageUrl)

            // view.setOnClickListener { onClickListener.invoke(data) }
            view.setOnClickListener{
                onClickListener.invoke(data)
            }
        }

    }

}