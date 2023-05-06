package com.example.healthcareapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import android.widget.LinearLayout
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class Pharmacist_Myproducts : AppCompatActivity() {

    lateinit var recycler:RecyclerView
    lateinit var username:String
    var Mid:String=""
    var Mname:String=""
    var MImgurl:String=""
    var MVisibility:Boolean=true
    var Mprice:Int=0
    var count:Int=0
    var Alist:ArrayList<Medicines> = ArrayList<Medicines>()
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pharmacist_myproducts)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)

        val UN=intent.getStringExtra("UN")
        if (UN != null) {
            username=UN
        }



        fun readfromDb()
        {
            val database = Firebase.database
            val myproductsref = database.getReference("SupplingMedicines").child(username)
///
            myproductsref.addValueEventListener(object: ValueEventListener {

                override fun onDataChange(snapshot: DataSnapshot) {
                    // This method is called once with the initial value and again
                    // whenever data at this location is updated.
                    val value = snapshot.value
                    println(value.toString()+"contain keys")
                    val keysMaps:HashMap<String,Int> = value as HashMap<String, Int>
                    for (keys in keysMaps.keys)
                    {


                        var KI = keys
                        val productref=database.getReference("Medicines").child(KI)

                        productref.addValueEventListener(object: ValueEventListener
                        {
                            override fun onDataChange(snapshot: DataSnapshot) {
                                val V = snapshot.value
                                var bingo: HashMap<String, Any> = HashMap<String,Any>()
                                try {
                                     bingo= V as HashMap<String, Any>
                                }
                                catch (e:java.lang.Exception)
                                {
                                    Toast.makeText(this@Pharmacist_Myproducts,"No products you owning to show add new products to db",Toast.LENGTH_SHORT).show()
                                }
                                Mname=bingo.get("MedicineName").toString()
                                MImgurl=bingo.get("MedicinePreview").toString()
                                Mid=KI
                                try {
                                    Mprice = bingo.get("MedicinePrice").toString().toInt()
                                }
                                catch (e:java.lang.Exception)
                                {
                                    print("error caused"+e.message)
                                }
                                try {
                                    MVisibility=bingo.get("Visibility").toString().toBoolean()
                                }
                                catch (np:java.lang.NullPointerException)
                                {
                                    println("null pointer found .. ->"+np.message)
                                }
                                catch (e:java.lang.Exception)
                                {
                                    println(e.message)
                                }

                                println("name of the medicine : "+Mname)
                                println("Price of the medicine : "+Mprice)
                                println("visibility is medicine"+MVisibility.toString())

                                recycler=findViewById(R.id.myproductrecycle)


                                //val med1 = Medicines("soorya 23",7000,"https://firebasestorage.googleapis.com/v0/b/nexuscarepath-health-care-app.appspot.com/o/medicinePics%2Fwindow1?alt=media&token=49ec1205-75d6-478c-92c2-d03a93bf9bc0",false)
                                //Alist.add(med1)



//add to arrayList

                                try{
                                Alist.add(count,Medicines(Mname,Mprice,MImgurl,MVisibility,KI))
                                }
                                catch (E:java.lang.Exception)
                                {

                                }




//end of arraylist addition







                                val adapt:RecyclerviewAdaptor = RecyclerviewAdaptor(this@Pharmacist_Myproducts,username)
                                adapt.setArray(Alist)

                                recycler.adapter=adapt
                                recycler.layoutManager = LinearLayoutManager(this@Pharmacist_Myproducts)
                                count+=1
                                println("counter value is "+count)
                                println("array size is "+Alist.size)







                            }







                            override fun onCancelled(error: DatabaseError) {
                                Toast.makeText(this@Pharmacist_Myproducts,"medicine "+KI+" is not available", Toast.LENGTH_SHORT).show()
                            }

                        })






                    }
                }

                override fun onCancelled(error: DatabaseError)
                {
                    Toast.makeText(this@Pharmacist_Myproducts,"No data found for this user", Toast.LENGTH_SHORT).show()

                }

            })

///
        }

        println("called up")
        readfromDb()
        println("called down")

    }



}