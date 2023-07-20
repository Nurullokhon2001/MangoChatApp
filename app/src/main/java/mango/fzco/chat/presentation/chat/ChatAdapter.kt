package mango.fzco.chat.presentation.chat

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import mango.fzco.chat.R
import mango.fzco.chat.domain.model.ChatModel
import mango.fzco.chat.domain.model.DateModel
import mango.fzco.chat.domain.model.MessageModel

class ChatAdapter(var data: List<ChatModel>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    class MyMessageViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvMessage: TextView = view.findViewById(R.id.tv_message)
        val tvMessageTime: TextView = view.findViewById(R.id.message_time)
    }

    class AnotherMessageViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvMessage: TextView = view.findViewById(R.id.tv_message)
        val tvMessageTime: TextView = view.findViewById(R.id.message_time)
    }

    class DateOfMessageViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvMessageTime: TextView = view.findViewById(R.id.message_time)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            MY_MESSAGE_TYPE -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_my_message, parent, false)
                MyMessageViewHolder(view)
            }

            DATE_TYPE -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_date_of_message, parent, false)
                DateOfMessageViewHolder(view)
            }

            else -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_another_message, parent, false)
                AnotherMessageViewHolder(view)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder.itemViewType) {
            MY_MESSAGE_TYPE -> {
                val itemView = holder as MyMessageViewHolder
                itemView.tvMessage.text = (data[position] as MessageModel).message
                itemView.tvMessageTime.text = (data[position] as MessageModel).timeOfMessage
            }

            DATE_TYPE -> {
                val itemView = holder as DateOfMessageViewHolder
                itemView.tvMessageTime.text = (data[position] as DateModel).dateMessage
            }

            ANOTHER_MESSAGE_TYPE -> {
                val itemView = holder as AnotherMessageViewHolder
                itemView.tvMessage.text = (data[position] as MessageModel).message
                itemView.tvMessageTime.text = (data[position] as MessageModel).timeOfMessage
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (data[position]) {
            is MessageModel -> {
                if ((data[position] as MessageModel).isMyMessage) {
                    MY_MESSAGE_TYPE
                } else {
                    ANOTHER_MESSAGE_TYPE
                }
            }

            else -> {
                DATE_TYPE
            }
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }

    companion object {
        private const val DATE_TYPE = 1
        private const val MY_MESSAGE_TYPE = 2
        private const val ANOTHER_MESSAGE_TYPE = 3
    }
}