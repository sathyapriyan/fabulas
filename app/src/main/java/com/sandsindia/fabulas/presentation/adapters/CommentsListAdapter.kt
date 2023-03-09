package com.sandsindia.fabulas.presentation.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.sandsindia.fabulas.R
import com.sandsindia.fabulas.models.CommentResponse



class CommentsListAdapter(
    private val items: List<CommentResponse>,
    private val onClickListener: ((doctorData: CommentResponse) -> Unit),
): RecyclerView.Adapter<CommentsListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.comment_list_item, parent, false)

        return ViewHolder(view,onClickListener)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val item = items[position]
        holder.bind(item)

    }

    override fun getItemCount(): Int = items.size

    class ViewHolder(
        private val view: View,
        private val onClickListener: ((doctorData: CommentResponse) -> Unit),
    )
        : RecyclerView.ViewHolder(view){

        private val textName : TextView = view.findViewById(R.id.textName)
        private val textBody : TextView = view.findViewById(R.id.textBody)
        private val textEmail : TextView = view.findViewById(R.id.textEmail)

        fun bind(data : CommentResponse){
            textName.text = data.name
            textBody.text = data.body
            textEmail.text = data.email

            view.setOnClickListener{
                onClickListener.invoke(data)
            }
        }

    }

}