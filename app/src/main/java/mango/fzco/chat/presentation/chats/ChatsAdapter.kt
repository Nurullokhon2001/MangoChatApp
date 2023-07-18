package mango.fzco.chat.presentation.chats

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import mango.fzco.chat.R
import mango.fzco.chat.databinding.ChatsItemBinding
import mango.fzco.chat.domain.model.ChatsModel

class ChatListAdapter(
    private val itemChatClicked: (Int) -> Unit
) : ListAdapter<ChatsModel, ChatListAdapter.ViewHolder>(object :
    DiffUtil.ItemCallback<ChatsModel>() {
    override fun areItemsTheSame(oldItem: ChatsModel, newItem: ChatsModel): Boolean {
        return oldItem.chatId == newItem.chatId
    }

    override fun areContentsTheSame(oldItem: ChatsModel, newItem: ChatsModel): Boolean {
        return oldItem == newItem
    }
}) {

    class ViewHolder(view: ChatsItemBinding) : RecyclerView.ViewHolder(view.root) {
        val chatImage = view.ivChat
        val tvLastMessage = view.tvLastMessage
        val tvLastMessageTime = view.tvLastMessageTime
        val tvChatName = view.tvUserName
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ChatsItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)

        Glide.with(holder.itemView.context).load(item.image).placeholder(R.drawable.ic_user)
            .diskCacheStrategy(DiskCacheStrategy.RESOURCE).into(holder.chatImage)

        holder.tvChatName.text = item.chatName
        holder.tvLastMessage.text = item.lastMessage
        holder.tvLastMessageTime.text = item.lastMessageTime

        holder.itemView.setOnClickListener {
            itemChatClicked.invoke(item.chatId)
        }
    }
}