package bonch.dev.school.ui.fragments

import android.content.Intent
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
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.fragment_profile.view.*

class ProfileFragment : Fragment() {

    private lateinit var avatarImageView: ImageView
    private lateinit var nameEditText: EditText
    private lateinit var emailTextView: TextView
    private lateinit var emailConfirmButton: Button
    private lateinit var changePasswordButton: Button
    private lateinit var signOutButton: Button

    private lateinit var mAuth: FirebaseAuth
    private lateinit var mDatabase: FirebaseDatabase
    private lateinit var mReference: DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_profile, container, false)

        mAuth = FirebaseAuth.getInstance()

        val user = mAuth.currentUser!!.uid

        mDatabase = FirebaseDatabase.getInstance()
        mReference = mDatabase.reference.child("Users").child(user)

        initializeViews(view)
        setListeners(view)
        setUserData(view)


        return view
    }

    private fun initializeViews(view: View) {
        avatarImageView = view.avatar_image_view

        emailTextView = view.email_text_view
        emailConfirmButton = view.email_confirm_button
        changePasswordButton = view.change_password_button
        signOutButton = view.sign_out_button

        if (mAuth.currentUser!!.isEmailVerified) {
            view.email_confirm_button.visibility = View.GONE
        }
    }

    private fun setListeners(view : View) {
        changePasswordButton.setOnClickListener {
            PasswordFragment().show(fragmentManager!!, "password_fragment")
        }

        view.sign_out_button.setOnClickListener {
            mAuth.signOut()
            (context as MainAppActivity).signOut()
        }

        view.email_confirm_button.setOnClickListener {
            mAuth.currentUser!!.sendEmailVerification()
        }
    }

    private fun setUserData(view : View) {


        mReference.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(p0: DataSnapshot) {
                view.name_text_view.text = (p0.child("name").value.toString())

            }

        })



        avatarImageView.setImageResource(R.drawable.img_example)
        emailTextView.text = mAuth.currentUser!!.email
    }
}