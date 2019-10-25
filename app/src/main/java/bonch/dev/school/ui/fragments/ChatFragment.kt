package bonch.dev.school.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.fragment.app.Fragment
import bonch.dev.school.R
import bonch.dev.school.ui.activities.MainAppActivity
import kotlinx.android.synthetic.main.fragment_chat.*
import kotlinx.android.synthetic.main.fragment_chat.view.*

class ChatFragment : Fragment(){

    private lateinit var sendMessageButton: ImageButton

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_chat, container, false)
        sendMessageButton = view.send_message_button
        setListeners()
        return view

    }

    private fun setListeners(){
        sendMessageButton.setOnClickListener{
            (context as MainAppActivity).attachProfileFragment()
        }
    }
}