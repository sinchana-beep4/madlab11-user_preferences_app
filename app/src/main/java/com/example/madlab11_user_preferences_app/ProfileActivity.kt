package com.example.madlab11_user_preferences_app

import android.content.SharedPreferences
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class ProfileActivity : AppCompatActivity() {

    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        sharedPreferences = getSharedPreferences(
            "UserPrefs", MODE_PRIVATE
        )

        val username = sharedPreferences.getString("username", "")
        val switchState = sharedPreferences.getBoolean(
            "switchState", false
        )

        val displayName = findViewById<TextView>(R.id.txtName)
        val displaySwitch = findViewById<TextView>(R.id.txtSwitch)

        displayName.text = getString(R.string.username, username)

        displaySwitch.text =
            buildString {
                append("Preference Status: ")
                append(if (switchState) "ON" else "OFF")
            }
    }
}
