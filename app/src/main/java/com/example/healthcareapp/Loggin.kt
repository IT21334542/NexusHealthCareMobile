package com.example.healthcareapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.Button
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase

class Loggin : AppCompatActivity() {
    lateinit var btnlog:Button
    lateinit var ineds:Intent
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_loggin)
        btnlog=findViewById(R.id.btnLogin)
        var kill="need"
        btnlog.setOnClickListener(View.OnClickListener {

            //write data to db under loginCredentials
            /*
            val database = Firebase.database
            val myRef = database.getReference("LoginCredentials").child("User 1")
            val userName =myRef.child("Username")
            userName.setValue("Username")
            val userPass =myRef.child("Password")
            userPass.setValue("Password")
            val userType =myRef.child("UserType")
            userType.setValue("Type")
            */




            //Read Data from db under Login Credentials
            val database = Firebase.database

            val loginRef = database.getReference("LoginCredentials").child("User 1")

            loginRef.addValueEventListener(object: ValueEventListener {


            override fun onDataChange(snapshot: DataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                val value = snapshot.value
                kill = value.toString()
                Log.d("", "Value is: " + value)
                Log.d("fetched2",kill)
            }

            override fun onCancelled(error: DatabaseError) {
                Log.w("TAG", "Failed to read value.", error.toException())
            }

        })

        })


    }
}