package bonch.dev.school.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import bonch.dev.school.R
import kotlinx.android.synthetic.main.activity_sign_up.*

class SignUpActivity : AppCompatActivity() {

    private lateinit var signUpTextView: TextView
    private lateinit var emailSignUpEditText: EditText
    private lateinit var loginEditText: EditText
    private lateinit var passwordSignUpEditText: EditText
    private lateinit var passwordConfirmEditText: EditText
    private lateinit var completeSignUpButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        initializeViews()
        setListeners()
    }


    private fun initializeViews() {
        signUpTextView = sign_up_text_view
        emailSignUpEditText = email_sign_up_edit_text
        loginEditText = login_edit_text
        passwordSignUpEditText = password_sign_up_edit_text
        passwordConfirmEditText = password_confirm_edit_text
        completeSignUpButton = complete_sign_up_button
    }

    private fun setListeners() {
        completeSignUpButton.setOnClickListener {
            startActivity(Intent(this, MainAppActivity::class.java))
        }
    }
}
