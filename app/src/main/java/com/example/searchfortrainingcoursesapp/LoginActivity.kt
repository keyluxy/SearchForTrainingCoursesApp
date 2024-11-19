package com.example.searchfortrainingcoursesapp

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.searchfortrainingcoursesapp.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        enableEdgeToEdge()
//        setContentView(R.layout.activity_login)

        val email = binding.emailEt.text.toString()
        val password = binding.passwordEt.text.toString()

        binding.loginBtn.setOnClickListener {
            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(application, "Fields cannot be empty", Toast.LENGTH_SHORT).show()
            } else {
                FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            startActivity(Intent(this, MainActivity::class.java))
                        }
                    }
            }
        }

        binding.goToRegistrActivityTv.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }

    }
}