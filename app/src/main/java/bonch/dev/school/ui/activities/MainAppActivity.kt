package bonch.dev.school.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import bonch.dev.school.R
import bonch.dev.school.ui.fragments.ChatFragment
import bonch.dev.school.ui.fragments.PasswordFragment
import bonch.dev.school.ui.fragments.ProfileFragment

class MainAppActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        attachFragment()
    }


    private fun attachFragment() {
        supportFragmentManager
            .beginTransaction()
            .add(R.id.fragment_container, ChatFragment())
            .commit()
    }

    fun attachProfileFragment() {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, ProfileFragment())
            .addToBackStack("profile_fragment")
            .commit()
    }

    fun attachPasswordFragment() {
        PasswordFragment().show(supportFragmentManager, "password_fragment")
    }


}
