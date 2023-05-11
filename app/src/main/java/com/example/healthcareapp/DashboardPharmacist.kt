package com.example.healthcareapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.ImageView

class DashboardPharmacist : AppCompatActivity() {


    lateinit var btnQr:ImageView
    lateinit var menubtn:ImageView
    lateinit var username:String
    lateinit var btn_Orders:ImageView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_dashboard_pharmacist)
        //btnQrcode.setOnClickListener(View.OnClickListener {
            //val intenter=Intent(this,Pharmarcist_QRscanner::class.java)
            //startActivity(intenter)
        //})
        val UN=intent.getStringExtra("UN")
        if (UN != null) {
            username=UN
        }

        initalls()
        btnQr=findViewById(R.id.imgviewscanners)
        btnQr.setOnClickListener(View.OnClickListener {
            val IntentHolder =Intent(this,Pharmacist_QRScanner::class.java)
            IntentHolder.putExtra("UN",username)
            startActivity(IntentHolder)
        })


        menubtn.setOnClickListener(View.OnClickListener {
            var divetor:Intent=Intent(this@DashboardPharmacist,PharmacistMenu::class.java)
            divetor.putExtra("UN",username)
            startActivity(divetor)
        })

        btn_Orders.setOnClickListener(View.OnClickListener {
            var divetor:Intent=Intent(this@DashboardPharmacist,Pharmacist_Ordersview::class.java)
            divetor.putExtra("UN",username)
            startActivity(divetor)
        })



    }

    private fun initalls()
    {
        menubtn=findViewById(R.id.menuids)
        btn_Orders=findViewById(R.id.imgvieworders)

    }
}