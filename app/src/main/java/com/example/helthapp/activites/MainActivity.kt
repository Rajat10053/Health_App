package com.example.helthapp.activites

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import com.example.helthapp.R
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    var mAuth : FirebaseAuth? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mAuth = FirebaseAuth.getInstance()

        LoginButtonId.setOnClickListener {
            var email = LoginEmailID.text.toString().trim()
            var password = LoginPasswordId.text.toString().trim()

            if (!TextUtils.isEmpty(email) || !TextUtils.isEmpty(password)){
                loginUser(email,password)
            }else{
                Toast.makeText(this,"Please enter email and password ",Toast.LENGTH_LONG).show()
            }
        }


    }

    private fun loginUser(email: String, password: String) {

        mAuth?.signInWithEmailAndPassword(email,password)
            ?.addOnCompleteListener {
                    task: Task<AuthResult> ->
                if (task.isSuccessful){

                    var dashboardIntent = Intent(this, Statelist::class.java)

                    startActivity(dashboardIntent)
                    finish()
                }else{
                    Toast.makeText(this,"User Not Created", Toast.LENGTH_LONG).show()
                }

            }

    }
}