package com.nikhileshsinha.firebaseexample.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.nikhileshsinha.firebaseexample.R
import com.nikhileshsinha.firebaseexample.modles.Message

class MessageAdapter(private val layout: Int, private val  messages: List<Message>):
    RecyclerView.Adapter<MessageAdapter.Holder>() {
    class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tuser : TextView = itemView.findViewById(R.id.txtUserName)
        private val tmsg : TextView = itemView.findViewById(R.id.txtMessage)

        fun bind(message: Message){
            tuser.text = message.name
            tmsg.text = message.text
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view: View = LayoutInflater.from(parent.context).inflate(layout, parent, false)
        return Holder(view)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val message = messages[position]
        holder.bind(message)
    }

    override fun getItemCount() = messages.size


}