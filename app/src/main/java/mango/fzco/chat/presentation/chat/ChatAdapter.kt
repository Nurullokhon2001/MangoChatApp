package mango.fzco.chat.presentation.chat

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import mango.fzco.chat.R

class ChatAdapter() : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    class MyMessageViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvMessage = view.findViewById<TextView>(R.id.tv_message)
        val tvMessageTime = view.findViewById<TextView>(R.id.tv_last_message_time)
        val ivIsRead = view.findViewById<ImageView>(R.id.iv_is_read)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            1 -> {
                val view =
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.item_my_message, parent, false)
                MyMessageViewHolder(view)
            }

            else -> {
                val view =
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.item_my_message, parent, false)
                MyMessageViewHolder(view)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }
}