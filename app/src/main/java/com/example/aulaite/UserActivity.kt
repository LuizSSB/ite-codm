package com.example.aulaite

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View

class UserActivity : AppCompatActivity() {
    companion object {
        private const val KEY_USERNAME = "username"
        private const val KEY_ID = "id"

        fun newIntent(context: Context, user: User): Intent {
            return addUserToIntent(
                Intent(context, UserActivity::class.java),
                user
            )
        }

        private fun addUserToIntent(intent: Intent, user: User) = intent
            .putExtra(KEY_USERNAME, user.username)
            .putExtra(KEY_ID, user.id)

        fun getIntentuser(intent: Intent) = User(
            intent.getIntExtra(KEY_ID, -1),
            intent.getStringExtra(KEY_USERNAME)!!
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user)

        val user = getIntentuser(intent)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Editando ${user.username}"

        findViewById<FormField>(R.id.form_field_id).value = user.id.toString()

        val formFieldUsername = findViewById<FormField>(R.id.form_field_username)
        formFieldUsername.value = user.username

        findViewById<View>(R.id.button_save).setOnClickListener {
            val newUser = user.copy(username = formFieldUsername.value)
            setResult(RESULT_OK, addUserToIntent(Intent(), newUser))
            finish()
        }

        findViewById<View>(R.id.button_show).setOnClickListener {
//            startActivityForResult(DetailActivity.newIntent(this, user.username), 242)
//            finish()
            startActivity(newIntent(this, user))
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_reload, menu!!)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            R.id.action_reload -> {
                findViewById<FormField>(R.id.form_field_username).value = ""
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
