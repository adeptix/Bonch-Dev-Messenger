package bonch.dev.school.ui.fragments

import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageButton
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import bonch.dev.school.R
import bonch.dev.school.models.Message
import bonch.dev.school.models.MessageFactory
import bonch.dev.school.ui.MessageAdapter
import bonch.dev.school.ui.activities.MainAppActivity
import kotlinx.android.synthetic.main.fragment_chat.*
import kotlinx.android.synthetic.main.fragment_chat.view.*
import java.util.*
import kotlin.collections.ArrayList

class ChatFragment : Fragment() {

    private lateinit var sendMessageButton: ImageButton
    private lateinit var messageEditText: EditText
    private lateinit var messageRecyclerView: RecyclerView
    private lateinit var messageList: MutableList<Message>

    private val LIST_KEY = "MESSAGE_LIST"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_chat, container, false)
        initializeViews(view, container)

        createList(savedInstanceState)

        messageRecyclerView.scrollToPosition(messageList.size - 1)

        setListeners()

        return view

    }

    private fun initializeViews(view: View, container: ViewGroup?) {
        sendMessageButton = view.send_message_button
        messageEditText = view.message_edit_text
        messageRecyclerView = view.message_recycler_view
        messageRecyclerView.layoutManager = LinearLayoutManager(container!!.context)


    }




    private fun createList(savedInstanceState: Bundle?) {
        if (savedInstanceState != null) {
            messageList = savedInstanceState.getParcelableArrayList<Message>(LIST_KEY)!!.toMutableList()


        } else {
            messageList = MessageFactory().messageList

        }

        messageRecyclerView.adapter = MessageAdapter(messageList)
    }



    private fun setListeners() {
        sendMessageButton.setOnClickListener {
            sendMessage()
        }
    }

    private fun sendMessage() {
        val messageText = messageEditText.text.toString()


        if (messageText.trim().isEmpty()) return

        val position = messageList.size

        messageEditText.text.clear()
        messageRecyclerView.scrollToPosition(position)
        messageList.add(Message(position, messageText, Calendar.getInstance().time, true))
        messageRecyclerView.adapter!!.notifyItemInserted(position)
    }

     override fun onSaveInstanceState(outState: Bundle) {
        Log.i("Adept", "in saveInstance")
        super.onSaveInstanceState(outState)
        outState.putParcelableArrayList(LIST_KEY, ArrayList<Parcelable>(messageList))
     }









}