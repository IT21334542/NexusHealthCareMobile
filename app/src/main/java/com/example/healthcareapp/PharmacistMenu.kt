package com.example.healthcareapp

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView

class PharmacistMenu : AppCompatActivity() {

    lateinit var btnaddProducts:TextView
    lateinit var btnEditProfile:TextView
    lateinit var btnOrderHistory:TextView
    lateinit var btnQRscan:TextView
    lateinit var btnclosemenu:ImageView
    lateinit var divertor:Intent
    lateinit var username:String
    lateinit var UsName:TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pharmacist_menu)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        val UN=intent.getStringExtra("UN")
        if (UN != null) {
            username=UN
        }
        UsName=findViewById(R.id.profileusername)
        UsName.text=username

        initalls()


        btnaddProducts.setOnClickListener(View.OnClickListener {
            divertor= Intent(this@PharmacistMenu,Pharmacist_addMedicine::class.java)
            divertor.putExtra("UN",username)
            startActivity(divertor)

        })


        btnQRscan.setOnClickListener(View.OnClickListener {
            divertor=Intent(this@PharmacistMenu,Pharmacist_QRScanner::class.java)
            divertor.putExtra("UN",username)
            startActivity(divertor)
        })



        btnclosemenu.setOnClickListener(View.OnClickListener {
            divertor=Intent(this@PharmacistMenu,DashboardPharmacist::class.java)
            divertor.putExtra("UN",username)
            startActivity(divertor)
        })


        btnOrderHistory.setOnClickListener(View.OnClickListener {
            divertor=Intent(this@PharmacistMenu,Pharmacist_Myproducts::class.java)
            divertor.putExtra("UN",username)
            startActivity(divertor)
        })





    }

    private fun initalls() {
        btnaddProducts=findViewById(R.id.txtaddProductToSale)
        btnEditProfile=findViewById(R.id.txtEditProfiledetails)
        btnOrderHistory=findViewById(R.id.txtOrderHistory)
        btnQRscan=findViewById(R.id.txtScanQr)
        btnclosemenu=findViewById(R.id.closeMenu)





    }
}