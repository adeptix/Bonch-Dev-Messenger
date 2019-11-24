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
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.fragment_chat.view.*
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class ChatFragment : Fragment() {

    private lateinit var sendMessageButton: ImageButton
    private lateinit var messageEditText: EditText
    private lateinit var messageRecyclerView: RecyclerView
    private var messageList: MutableList<Message> = mutableListOf()

    private lateinit var mDatabase: FirebaseDatabase
    private lateinit var mReference: DatabaseReference
    private lateinit var mAuth: FirebaseAuth


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_chat, container, false)

        initializeViews(view, container)

       Log.d("Adept", "a chto vi delaete v chat fragmente?")
        // createList2()
        //
        // retainInstance = true
        mDatabase = FirebaseDatabase.getInstance()
        mAuth = FirebaseAuth.getInstance()
        mReference = mDatabase.reference.child("Users").child(mAuth.currentUser!!.uid).child("chat")


       // val isUser = true
       // mDatabase.reference.child("Users").child(mAuth.currentUser!!.uid).setValue(Message(0, "weq", Date(), true))

        loadChat()

     //   loadFromDB()


       //     messageList = MessageFactory().messageList
        //    saveToDB()


//        messageList =
//            mBundleRecyclerViewState?.getParcelableArrayList<Message>("CHATS") ?: messageList



        setListeners()

        return view

    }

    private fun loadChat(){
        if(mBundleRecyclerViewState != null){
            messageList = mBundleRecyclerViewState!!.getParcelableArrayList<Message>("CHATS") as MutableList<Message>
            messageRecyclerView.adapter = MessageAdapter(messageList)
          //  messageRecyclerView.scrollToPosition(messageList.size - 1)
            return
        }



        mReference.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(p0: DataSnapshot) {
                if (p0.exists()) {
                 //   messageList = p0.value as MutableList<HashMap<Int, String>>
                    Log.i("Adept", "load from fire")
                    p0.children.mapNotNullTo(messageList){
                        it.getValue<Message>(Message::class.java)
                    }
                }else{
                    Log.i("Adept", "generating")
                    messageList = MessageFactory().messageList
                    saveToDB()
                }
                messageRecyclerView.adapter = MessageAdapter(messageList)
             //   messageRecyclerView.scrollToPosition(messageList.size - 1)
            }

        })
    }

    private fun initializeViews(view: View, container: ViewGroup?) {
        sendMessageButton = view.send_message_button
        messageEditText = view.message_edit_text
        messageRecyclerView = view.message_recycler_view
        messageRecyclerView.layoutManager = LinearLayoutManager(container!!.context)
    }

//    private fun loadFromDB() {
//        mReference.addListenerForSingleValueEvent(object : ValueEventListener {
//            override fun onCancelled(p0: DatabaseError) {
//
//            }
//
//            override fun onDataChange(p0: DataSnapshot) {
//                if (p0.exists()) {
//                    messageList = p0.value as MutableList<Message>
//                }else{
//                    messageList =
//                }
//            }
//
//        })
//    }

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
        Log.i("ADEPT","" + messageList.size)

        messageEditText.text.clear()
        messageList.add(Message(position, messageText, Calendar.getInstance().time, true))
        messageRecyclerView.adapter!!.notifyItemInserted(position)
        messageRecyclerView.smoothScrollToPosition(position)

        saveToDB()
    }


    override fun onPause() {
        super.onPause()
        mBundleRecyclerViewState = Bundle()
        mBundleRecyclerViewState?.putParcelableArrayList(
            "CHATS",
            ArrayList<Parcelable>(messageList)
        )

        Log.i("ADEPT","save")
    }

    override fun onResume() {
        super.onResume()
        Log.i("ADEPT","onResumeddddd")
        if (mBundleRecyclerViewState != null) {


            messageList = mBundleRecyclerViewState?.getParcelableArrayList<Message>("CHATS")!!
            Log.i("ADEPT","" + messageList.size)
        }
    }


    companion object {

        private var mBundleRecyclerViewState: Bundle? = null

        @JvmStatic
        fun newInstance() = ChatFragment()
    }


}