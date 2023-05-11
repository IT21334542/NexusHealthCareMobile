package com.example.healthcareapp

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class Pharmacist_Ordersview : AppCompatActivity()
{

    lateinit var Activeorders:RecyclerView
    lateinit var Pastorders:RecyclerView

    var UserName:String=""
    var UnfromHandler:String=""

    val orderids:ArrayList<String> = ArrayList<String>()
    val productids:ArrayList<String> = ArrayList<String>()


    val OrdersList:ArrayList<Orders> = ArrayList<Orders>()
    //val CartList:ArrayList<CardItems> = ArrayList<CardItems>()

    var count:Int=0



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pharmacist_ordersview)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        initsall()

        val UN=intent.getStringExtra("UN")
        if (UN != null)
        {
            UserName=UN
            //println("username is arrived : "+UserName )


            val database = Firebase.database
            val OrderRef = database.getReference("SupplierOrders").child(UserName)

            OrderRef.addValueEventListener(object: ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    val datafetched=snapshot.value
                    val orderidsHash:HashMap<String,Any> = datafetched as HashMap<String, Any>
                    for (keys in orderidsHash.keys)
                    {
                        //println("keys i is "+keys)
//got the order ids of the certain user
  //added to orderid arraylist
                        orderids.add(keys)
                    }

                    for (i in orderids)
                    {
                        println("key is "+i)
//fetching details from Order document
                        //val database = Firebase.database
                        val orderdetailsRef=database.getReference("Orders").child(i)
                        orderdetailsRef.addValueEventListener(object: ValueEventListener{
                            override fun onDataChange(snapshot: DataSnapshot) {
                                val value=snapshot.value
//retrieved data of Orders
                                val ordershash:HashMap<String,Any> = value as HashMap<String,Any>
                                val cart = ordershash.get("CartItems")
                                val Cartshash:HashMap<String,Any> = cart as HashMap<String, Any>







                                val orderObj:Orders =Orders()
                                orderObj.orderId=i
                                orderObj.CustomerId=ordershash.get("Customerid").toString()
                                orderObj.SupplierId=ordershash.get("Supplierid").toString()
                                orderObj.OrderStatus=ordershash.get("OrderStatus").toString()
                                orderObj.paymentType=ordershash.get("PaymentType").toString()
                                orderObj.paymentStatus=ordershash.get("PaymentStatus").toString()
                                println("this is the order model "+orderObj.toString())
                                //println("this is the the value of the  cartds"+ordershash.get("CartItems").toString())
                                var looppass = true



                                    for (keys2 in Cartshash.keys)
                                    {


                                        val OrderRef =
                                            database.getReference("Medicines").child(keys2)
                                        OrderRef.addValueEventListener(object : ValueEventListener
                                        {
                                            override fun onDataChange(snapshot: DataSnapshot) {
                                                val ve = snapshot.value
                                                val produxt: HashMap<String, Any> =
                                                    ve as HashMap<String, Any>
                                                val item1: CardItems = CardItems()
                                                item1.productname =
                                                    produxt.get("MedicineName").toString()
                                                item1.productpreview =
                                                    produxt.get("MedicinePreview").toString()
                                                item1.price =
                                                    produxt.get("MedicinePrice").toString().toInt()
                                                item1.productId = keys2
                                                item1.quantity = Cartshash.get(keys2).toString()
                                                orderObj.Cart.add(item1)
                                                Cartshash.remove(keys2)
                                                if(Cartshash.isEmpty())
                                                {
                                                    println("this is the order model after added the items to card "+orderObj.toString())
                                                    OrdersList.add(orderObj)
                                                    orderids.remove(i)
                                                    count++
                                                    if (orderids.isEmpty())
                                                    {
                                                        println("total orders in the db "+count+" : "+OrdersList.toString())

                                                        val ActiveList:ArrayList<Orders> =ArrayList<Orders>()
                                                        val PastList:ArrayList<Orders> =ArrayList<Orders>()

                                                        for(i in OrdersList)
                                                        {
                                                            if(i.OrderStatus.equals("Active",false))
                                                            {
                                                                ActiveList.add(i)
                                                            }
                                                            else
                                                            {
                                                                PastList.add(i)
                                                            }

                                                        }




                                                        val adaptActive:Adaptor_Orders=Adaptor_Orders(this@Pharmacist_Ordersview)
                                                        adaptActive.setArray(ActiveList)

                                                        val adaptPast:Adaptor_Orders=Adaptor_Orders(this@Pharmacist_Ordersview)
                                                        adaptPast.setArray(PastList)

                                                        Activeorders.adapter=adaptActive
                                                        Activeorders.layoutManager = LinearLayoutManager(this@Pharmacist_Ordersview)


                                                        Pastorders.adapter=adaptPast
                                                        Pastorders.layoutManager = LinearLayoutManager(this@Pharmacist_Ordersview)



                                                    }

                                                }
                                            }

                                            override fun onCancelled(error: DatabaseError) {
                                                Log.w(
                                                    TAG,
                                                    "Failed to read value.",
                                                    error.toException()
                                                )
                                            }


                                        })

                                    }












                            }

                            override fun onCancelled(error: DatabaseError) {
                                Log.w(TAG, "Failed to read value.", error.toException())                                }

                        })
















                    }
















                }

                override fun onCancelled(error: DatabaseError) {
                    Log.w(TAG, "Failed to read value.", error.toException())                }


            })

























        }
        else
        {
            val inter:Intent= Intent(this@Pharmacist_Ordersview,Seesion_Error::class.java)
            startActivity(inter)

        }





















    }

    private fun initsall()
    {
        Activeorders=findViewById(R.id.recycler_active_orders)
        Pastorders=findViewById(R.id.recycler_past_orders)

    }
}