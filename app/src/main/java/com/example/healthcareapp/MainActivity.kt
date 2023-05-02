package com.example.healthcareapp

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.Window
import android.view.WindowManager
import android.widget.TextView
import android.widget.Toast
import android.widget.VideoView

class MainActivity : AppCompatActivity() {

    @Suppress("DEPRECATION")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN)

        Handler().postDelayed({
                              val intenter:Intent=Intent(this,Splash2::class.java)
            startActivity(intenter)
            overridePendingTransition(com.google.android.material.R.anim.m3_bottom_sheet_slide_in, com.google.android.material.R.anim.m3_bottom_sheet_slide_out);
        },900)



    }
}