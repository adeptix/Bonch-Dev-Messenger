package bonch.dev.school.ui

import android.graphics.drawable.Drawable
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import bonch.dev.school.R
import bonch.dev.school.models.ImageFactory
import bonch.dev.school.models.MessageFactory
import kotlinx.android.synthetic.main.other_message_item.view.*
import kotlinx.android.synthetic.main.user_message_item.view.*
import kotlinx.android.synthetic.main.user_message_item.view.message_text_view
import kotlinx.android.synthetic.main.user_message_item.view.send_time_text_view

class MessageAdapter : RecyclerView.Adapter<MessageAdapter.MessageHolder>() {

    private val TYPE_USER = 0
    private val TYPE_OTHER = 1

    private val messageList = MessageFactory().messageList
    lateinit var imageFactory: ImageFactory

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageHolder {
        val view = when (viewType) {
            TYPE_USER -> LayoutInflater.from(parent.context).inflate(
                R.layout.user_message_item,
                parent,
                false
            )
            TYPE_OTHER -> LayoutInflater.from(parent.context).inflate(
                R.layout.other_message_item,
                parent,
                false
            )
            else -> null
        }
        imageFactory = ImageFactory(parent.context)

        return MessageHolder(view!!)
    }

    override fun getItemViewType(position: Int): Int {
        return if (messageList[position].isUser) TYPE_USER else TYPE_OTHER
    }

    override fun getItemCount(): Int {
        return messageList.size
    }

    override fun onBindViewHolder(holder: MessageHolder, position: Int) {
        when (getItemViewType(position)) {
            TYPE_USER -> holder.bindUserMessage(position)
            TYPE_OTHER -> holder.bindOtherMessage(position)
        }


    }


    inner class MessageHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bindUserMessage(position: Int) {
            val messageTextView = itemView.message_text_view
            messageTextView.text = messageList[position].messageText
            val timeTextView = itemView.send_time_text_view
            timeTextView.text = messageList[position].sentDate.toString()

        }

        fun bindOtherMessage(position: Int) {
            val messageTextView = itemView.message_text_view
            messageTextView.text = messageList[position].messageText
            val timeTextView = itemView.send_time_text_view
            timeTextView.text = messageList[position].sentDate.toString()
            val userNameText = itemView.user_name_text_view
            userNameText.text = "other users"

            imageFactory.setRandomImage(userNameText)

        }
    }
}