package bonch.dev.school.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import bonch.dev.school.R
import bonch.dev.school.ui.activities.MainAppActivity
import kotlinx.android.synthetic.main.fragment_profile.view.*

class ProfileFragment : Fragment() {

    private lateinit var avatarImageView: ImageView
    private lateinit var nameEditText: EditText
    private lateinit var emailTextView: TextView
    private lateinit var emailConfirmButton: Button
    private lateinit var changePasswordButton: Button
    private lateinit var signOutButton: Button

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_profile, container, false)

        initializeViews(view)
        setListeners()
        setExampleData()
        return view
    }

    private fun initializeViews(view: View) {
        avatarImageView = view.avatar_image_view
        nameEditText = view.name_edit_text
        emailTextView = view.email_text_view
        emailConfirmButton = view.email_confirm_button
        changePasswordButton = view.change_password_button
        signOutButton = view.sign_out_button
    }

    private fun setListeners() {
        changePasswordButton.setOnClickListener {
            (context as MainAppActivity).attachPasswordFragment()
        }
    }

    private fun setExampleData(){
        avatarImageView.setImageResource(R.drawable.img_example)
        nameEditText.setText("Unknown")
        emailTextView.text = "random_email@gmail.com"
    }
}