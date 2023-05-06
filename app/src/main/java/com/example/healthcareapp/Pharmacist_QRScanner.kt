package com.example.healthcareapp

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.google.zxing.integration.android.IntentIntegrator
import com.google.zxing.integration.android.IntentResult

class Pharmacist_QRScanner : AppCompatActivity() {
    lateinit var scanbutton:Button
    lateinit var scanResult:TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pharmacist_qrscanner)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        initall()

        scanbutton.setOnClickListener(View.OnClickListener {

            //Toast.makeText(this@Pharmacist_QRScanner,"Scanning",Toast.LENGTH_SHORT).show()
             val intintegrator:IntentIntegrator= IntentIntegrator(this@Pharmacist_QRScanner)
            intintegrator.setOrientationLocked(true)
            intintegrator.setPrompt("scan a QR CODE")
            intintegrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE)
            intintegrator.setTorchEnabled(true)
            intintegrator.setTimeout(10000)
            intintegrator.initiateScan()

        })




    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        @Suppress("DEPRECATION")
        if(requestCode==Activity.RESULT_OK)
        {
            val scanRes:IntentResult =IntentIntegrator.parseActivityResult(requestCode,resultCode,data)

            if(scanRes!=null)
            {
                val fetchedData:String =scanRes.contents

                if(fetchedData!=null)
                {
                    Toast.makeText(this@Pharmacist_QRScanner,fetchedData,Toast.LENGTH_SHORT).show()
                    scanResult.text=scanRes.contents
                    Log.d("gympo","found")
                }
                else
                {
                    Toast.makeText(this@Pharmacist_QRScanner,"no data founded by scan",Toast.LENGTH_SHORT).show()
                    Log.d("gympo","not found")

                }

            }
            else
            {
                Log.d("gympo","not went in fot found")
                super.onActivityResult(requestCode, resultCode, data)
            }
        }
    }

    private fun initall()
    {
        scanbutton=findViewById(R.id.btnScan)
        scanResult=findViewById(R.id.scanResult)

    }
}