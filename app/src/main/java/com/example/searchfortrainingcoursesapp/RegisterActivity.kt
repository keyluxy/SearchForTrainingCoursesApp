package com.example.searchfortrainingcoursesapp

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.searchfortrainingcoursesapp.databinding.ActivityLoginBinding
import com.example.searchfortrainingcoursesapp.databinding.ActivityRegisterBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        enableEdgeToEdge()
//        setContentView(R.layout.activity_register)




        binding.registrBtn.setOnClickListener {
            if (binding.emailRegEt.text.toString().isEmpty() || binding.passwordRegEt.text.toString().isEmpty()
                || binding.rememberPassRegEt.text.toString().isEmpty()) {
                Toast.makeText(application, "Fields cannot be empty", Toast.LENGTH_SHORT).show()
            } else {
                FirebaseAuth.getInstance().createUserWithEmailAndPassword(binding.emailRegEt.text.toString(), binding.passwordRegEt.text.toString())
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {

                            val userInfo: HashMap<String, String> = HashMap()
                            userInfo["email"] = binding.emailRegEt.text.toString()
                            userInfo["password"] = binding.passwordRegEt.text.toString()
                            userInfo["package_name"] = packageName
                            FirebaseAuth.getInstance().currentUser?.let {
                                FirebaseDatabase.getInstance().getReference().child("Users").child(
                                    it.uid).setValue(userInfo)
                            }

                            startActivity(Intent(this, MainActivity::class.java))

                        } else {
                            Toast.makeText(application, "Registration failed: ${task.exception?.message}", Toast.LENGTH_SHORT).show()

                        }
                    }

            }
        }

        binding.goToLoginActivity.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }
    }
}