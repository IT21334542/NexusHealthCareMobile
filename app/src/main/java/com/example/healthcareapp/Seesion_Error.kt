package com.example.healthcareapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager

class Seesion_Error : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_seesion_error)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)


    }
}