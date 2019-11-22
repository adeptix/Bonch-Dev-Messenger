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
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.fragment_chat.*
import kotlinx.android.synthetic.main.fragment_chat.view.*
import java.util.*
import kotlin.collections.ArrayList

class ChatFragment : Fragment() {

    private lateinit var sendMessageButton: ImageButton
    private lateinit var messageEditText: EditText
    private lateinit var messageRecyclerView: RecyclerView
    private lateinit var messageList: MutableList<Message>

    private lateinit var mDatebase: FirebaseDatabase
    private lateinit var mReference: DatabaseReference
    private lateinit var mAuth: FirebaseAuth


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_chat, container, false)
        initializeViews(view, container)


        // createList2()
        //
        // retainInstance = true
        mDatebase = FirebaseDatabase.getInstance()
        mAuth = FirebaseAuth.getInstance()
        mReference = mDatebase.reference.child("Users").child(mAuth.currentUser!!.uid).child("chat")


        loadFromDB()

        if (messageList == null) {
            messageList = MessageFactory().messageList
            saveToDB()
        }

        messageList =
            mBundleRecyclerViewState?.getParcelableArrayList<Message>("CHATS") ?: messageList

        messageRecyclerView.adapter = MessageAdapter(messageList)
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

    private fun loadFromDB() {
        mReference.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(p0: DataSnapshot) {
                if (p0.exists()) {
                    messageList = p0.value as MutableList<Message>
                }
            }

        })
    }

    private fun saveToDB() {
        mReference.setValue(messageList)
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

        saveToDB()
    }


    override fun onPause() {
        super.onPause()
        mBundleRecyclerViewState = Bundle()
        mBundleRecyclerViewState?.putParcelableArrayList(
            "CHATS",
            ArrayList<Parcelable>(messageList)
        )
    }

//    override fun onResume() {
//        super.onResume()
//
//        if (mBundleRecyclerViewState != null) {
//            items = mBundleRecyclerViewState?.getParcelableArrayList<Message>("CHATS")!!
//            adapter.notifyItemInserted(items.size - 1)
//            message_recycler_view.scrollToPosition(items.size - 1)
//        }
//    }


    companion object {

        private var mBundleRecyclerViewState: Bundle? = null

        @JvmStatic
        fun newInstance() = ChatFragment()
    }


}