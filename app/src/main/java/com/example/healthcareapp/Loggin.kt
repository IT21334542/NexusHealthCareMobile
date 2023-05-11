package com.example.healthcareapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.*
import com.google.firebase.ktx.Firebase

class Loggin<K:String,T : Any,Q:Any> : AppCompatActivity() {
    lateinit var btnlog:Button
    lateinit var trans:Intent
    lateinit var Etname:EditText
    lateinit var Etpass:EditText
    lateinit var DataName:String
    lateinit var DataPass:String
    val database = Firebase.database
    val myRef = database.getReference("LoginCredentials")
    val contx = this
    var count =0
    var valid =0
    var validname=""
    lateinit var sign:TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_loggin)
        btnlog=findViewById(R.id.btnLogin)
        var kill="need"
        btnlog.text="ENTER USERNAME"
        Etname=findViewById(R.id.etUsername)
        Etpass=findViewById(R.id.etPassword)
        Etpass.visibility=View.GONE
        btnlog.visibility=View.VISIBLE
        sign=findViewById(R.id.txtSignup)
        //notHave.visibility=View.VISIBLE
        sign.visibility=View.VISIBLE
        DataName=""
        DataPass=""
        println("from ground"+Etname.text.toString())


        sign.setOnClickListener(View.OnClickListener {
            //val in:Intent=Intent(this@Loggin,Signup::class.java)
            //startActivity(in)
        })




        btnlog.setOnClickListener(View.OnClickListener {

            if (valid == 0)
            {
                validname = Etname.text.toString()
                var users = myRef.child(validname)
                users.addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot)
                    {
                        val values = snapshot.value
                        if (values == null)
                        {
                            Toast.makeText(this@Loggin, "User Name is Incorrect", Toast.LENGTH_LONG)
                                .show()

                        }
                        else
                        {
                            valid=1
                            Etname.visibility=View.GONE
                            //notHave.visibility=View.GONE
                            sign.visibility=View.GONE
                            Etpass.visibility=View.VISIBLE
                            btnlog.text="Login"

                        }
                    }

                    override fun onCancelled(error: DatabaseError) {

                    }

                })


            }
            if(valid==1)
            {
                val id=validname
                var users = myRef.child(validname)
                val pass = Etpass.text.toString()


                users.addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot)
                    {
                            val valus=snapshot.value as HashMap<String,String>
                            val password=valus.get("Password")

                            if(password.equals(pass))
                            {
                                valid=2
                                val intenter: Intent = Intent(this@Loggin, DashboardPharmacist::class.java)
                                intenter.putExtra("UN", validname)
                                startActivity(intenter)

                            }
                            else
                            {
                                Toast.makeText(this@Loggin,"Invaild Password",Toast.LENGTH_LONG).show()
                            }
                    }

                    override fun onCancelled(error: DatabaseError) {
                        TODO("Not yet implemented")
                    }
                })





        }
            if(valid==2)
            {
                val intenter: Intent = Intent(this@Loggin, DashboardPharmacist::class.java)
                intenter.putExtra("UN", validname)
                startActivity(intenter)
            }
        /*
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
            val contx = this

            val loginRef = database.getReference("LoginCredentials").child(DataName)
            loginRef.addValueEventListener(object: ValueEventListener {


            override fun onDataChange(snapshot: DataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                val value = snapshot.value
                kill = value?.javaClass.toString()
                DataName = Etname.text.toString()
                DataPass = Etpass.text.toString()

                if(DataPass==null)
                    DataPass=""
                if(DataName==null)
                    DataName=""



                var valid=0
                var datasfetched: HashMap<K, Q> = HashMap<K, Q>()
                //converstions
                try {
                    datasfetched = value as HashMap<K, Q>

                    if (datasfetched.containsKey(DataName)) {
                        Etname.visibility = View.GONE
                        valid = 1
                    } else {
                        Toast.makeText(this@Loggin, "Invaild User name", Toast.LENGTH_SHORT).show()
                    }
                }
                catch (E:java.lang.Exception)
                {

                }

                var DBusername: Q?=null
                var DBpassword: Q?=null
                var DBuserType: Q?=null
                    try {
                        val datafetchresult: Q? = datasfetched[DataName]
                        val finalMap: HashMap<K, Q> = datafetchresult as HashMap<K, Q>

                        DBusername= finalMap["Username"]
                        DBpassword = finalMap["Password"]
                         DBuserType = finalMap["UserType"]
                    }
                    catch (E:java.lang.Exception)
                    {

                    }
                    if(DBusername.toString().equals(DataName))
                    {
                        if(DBpassword.toString().equals(DataPass))
                        {
                            valid=2
                        }
                        else
                        {
                            Toast.makeText(this@Loggin,"Invaild Password",Toast.LENGTH_LONG).show()
                            Etname.visibility=View.VISIBLE
                            Etname.setText("")
                        }
                    }


                        //converstion finished
                        Log.d("", "Value is: " + value)

                       // Log.d("fetched2", finalMap.toString())

                        Log.d(
                            "fetched222",
                            DBusername.toString() + "  vs  " + DBpassword.toString()
                        )


                        if(valid==2) {
                            val intenter: Intent = Intent(contx, DashboardPharmacist::class.java)
                            intenter.putExtra("UN", DataName)
                            startActivity(intenter)
                        }


                        //Toast.makeText(this@Loggin,"Invaild Password",Toast.LENGTH_LONG).show()





                    //Toast.makeText(this@Loggin,"Invaild UserName",Toast.LENGTH_LONG).show()


            }
                override fun onCancelled(error: DatabaseError) {
                    Log.w("TAG", "Failed to read value.", error.toException())
                }


        })



        })

*/
            })
        }
}