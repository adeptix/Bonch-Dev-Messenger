package bonch.dev.school.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import bonch.dev.school.R
import kotlinx.android.synthetic.main.activity_sign_up.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_sign_in.*
import kotlinx.android.synthetic.main.fragment_password.*
import java.util.regex.Pattern


class SignUpActivity : AppCompatActivity() {



    private lateinit var mDatebase: FirebaseDatabase
    private lateinit var mReference: DatabaseReference
    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        mDatebase = FirebaseDatabase.getInstance()
        mReference = mDatebase.reference.child("Users")
        mAuth = FirebaseAuth.getInstance()


        setListeners()
    }


    private fun setListeners() {
        complete_sign_up_button.setOnClickListener {
            regNewUser()
            //startActivity(Intent(this, MainAppActivity::class.java))
        }
    }

    private fun regNewUser() {
        val name = username_edit_text.text.toString().trim()
        val email = email_sign_up_edit_text.text.toString().trim()
        val password = password_sign_up_edit_text.text.toString().trim()


        if(!validation(name, email, password)) return

        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
                task ->
            if (task.isSuccessful) {
                val user = mAuth.currentUser!!.uid
                val currentUserDb = mReference.child(user)
                currentUserDb.child("name").setValue(name)

                mAuth.currentUser!!.sendEmailVerification()


                toMainActivity()
            } else {
                try {
                    throw task.exception!!
                } catch (e: FirebaseAuthWeakPasswordException) {
                    password_sign_up_edit_text.error = "weak password"

                } catch (e: Exception) {
                    Log.e("Adept", e.message)
                }
            }
        }




    }

    override fun onBackPressed() {
        val intent = Intent(this, SignUpActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun validation(name:String, email : String, password : String) : Boolean{
        var valid = true

        if (name.isEmpty()) {
            username_edit_text.error = "missing name"
            valid = false
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            email_sign_up_edit_text.error = "invalid email"
            valid = false
        }

        if (password.isEmpty()) {
            password_sign_up_edit_text.error = "missing password"
            valid = false
        }

        if (password != password_confirm_edit_text.text.toString().trim()) {
            password_confirm_edit_text.error = "passwords do not match"
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
