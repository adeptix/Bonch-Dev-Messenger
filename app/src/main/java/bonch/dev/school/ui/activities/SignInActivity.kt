package bonch.dev.school.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import bonch.dev.school.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import kotlinx.android.synthetic.main.activity_sign_in.*
import kotlinx.android.synthetic.main.activity_sign_up.*

class SignInActivity : AppCompatActivity() {

    private lateinit var mAuth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        mAuth = FirebaseAuth.getInstance()

        setListeners()
    }


    private fun setListeners() {
        sign_in_button.setOnClickListener {
            signIn()
        }

        sign_up_button.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
        }

    }

    private fun signIn() {
        val email = email_sign_in_edit_text.text.toString().trim()
        val password = password_sign_in_edit_text.text.toString().trim()


        if (!validation(email, password)) return




        mAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    toMainActivity()

                } else {


                    try {
                        throw task.exception!!
                    } catch (e: FirebaseAuthWeakPasswordException) {
                        password_sign_in_edit_text.error = "weak password"

                    } catch (e: FirebaseAuthInvalidUserException) {
                        Toast.makeText(this, "Such user does not exist", Toast.LENGTH_LONG).show()

                    } catch (e: Exception) {
                        Log.e("Adept", e.message)
                    }

                }
            }

    }

    private fun validation(email: String, password: String): Boolean {
        var valid = true


        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            email_sign_in_edit_text.error = "invalid email"
            valid = false
        }

        if (password.isEmpty()) {
            password_sign_in_edit_text.error = "missing password"
            valid = false
        }

        return valid
    }


    private fun toMainActivity() {
        val intent = Intent(this, MainAppActivity::class.java)
        startActivity(intent)
        finish()
    }


}
