package bonch.dev.school.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import bonch.dev.school.R
import kotlinx.android.synthetic.main.activity_sign_in.*

class SignInActivity : AppCompatActivity() {

    private lateinit var helloTextView: TextView
    private lateinit var emailSignInEditText: EditText
    private lateinit var passwordSignInEditText: EditText
    private lateinit var signInButton: Button
    private lateinit var signUpButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        initializeViews()
        setListeners()
    }


    private fun initializeViews(){
        helloTextView = hello_text_view
        emailSignInEditText = email_sign_in_edit_text
        passwordSignInEditText = password_sign_in_edit_text
        signInButton = sign_in_button
        signUpButton = sign_up_button

        signUpButton.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
        }
    }

    private fun setListeners(){
        signInButton.setOnClickListener {
            startActivity(Intent(this, MainAppActivity::class.java))
        }

        signUpButton.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
        }

    }


}
