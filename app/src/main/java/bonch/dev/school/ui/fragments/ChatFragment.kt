package bonch.dev.school.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import bonch.dev.school.R
import bonch.dev.school.ui.MessageAdapter
import bonch.dev.school.ui.activities.MainAppActivity
import kotlinx.android.synthetic.main.fragment_chat.*
import kotlinx.android.synthetic.main.fragment_chat.view.*

class ChatFragment : Fragment(){

    private lateinit var sendMessageButton: ImageButton
    private lateinit var messageRecyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_chat, container, false)
        initializeViews(view, container)
        setListeners()
        return view

    }

    private fun initializeViews(view: View, container: ViewGroup?){
        sendMessageButton = view.send_message_button
        messageRecyclerView = view.message_recycler_view
        messageRecyclerView.layoutManager = LinearLayoutManager(container!!.context)
        messageRecyclerView.adapter = MessageAdapter()
    }

    private fun setListeners(){
        sendMessageButton.setOnClickListener{
            (context as MainAppActivity).attachProfileFragment()
        }
    }
}