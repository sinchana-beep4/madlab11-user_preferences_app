package com.example.madlab11_user_preferences_app

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.SwitchCompat
import androidx.core.content.edit

class MainActivity : AppCompatActivity() {

    private lateinit var name: EditText
    private lateinit var preferenceSwitch: SwitchCompat
    private lateinit var saveButton: Button
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {

        sharedPreferences = getSharedPreferences(
            "UserPrefs", MODE_PRIVATE
        )

        // Load saved theme before displaying the UI
        val darkMode = sharedPreferences.getBoolean(
            "switchState", false
        )

        AppCompatDelegate.setDefaultNightMode(
            if (darkMode)
                AppCompatDelegate.MODE_NIGHT_YES
            else
                AppCompatDelegate.MODE_NIGHT_NO
        )

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        name = findViewById(R.id.editName)
        preferenceSwitch = findViewById(R.id.switchPreference)
        saveButton = findViewById(R.id.btnSave)

        // Load saved username
        name.setText(
            sharedPreferences.getString("username", "")
        )

        // Load saved switch state
        preferenceSwitch.isChecked = darkMode

        // Change theme immediately when switch is clicked
        preferenceSwitch.setOnCheckedChangeListener { _, isChecked ->

            sharedPreferences.edit {
                putBoolean("switchState", isChecked)
            }

            AppCompatDelegate.setDefaultNightMode(
                if (isChecked)
                    AppCompatDelegate.MODE_NIGHT_YES
                else
                    AppCompatDelegate.MODE_NIGHT_NO
            )
        }

        // Save username and preference
        saveButton.setOnClickListener {

            sharedPreferences.edit {
                putString("username", name.text.toString())
                    .putBoolean(
                        "switchState",
                        preferenceSwitch.isChecked
                    )
            }

            Toast.makeText(
                this,
                "Preferences Saved",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {

            R.id.profileSummary -> {

                val intent = Intent(
                    this, ProfileActivity::class.java
                )

                startActivity(intent)
                return true
            }

            R.id.clearData -> {

                sharedPreferences.edit { clear() }

                // Reset theme to Light Mode
                AppCompatDelegate.setDefaultNightMode(
                    AppCompatDelegate.MODE_NIGHT_NO
                )

                name.setText("")
                preferenceSwitch.isChecked = false

                Toast.makeText(
                    this,
                    "Data Cleared",
                    Toast.LENGTH_SHORT
                ).show()

                return true
            }
        }

        return super.onOptionsItemSelected(item)
    }
}
