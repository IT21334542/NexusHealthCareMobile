package com.example.healthcareapp

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

class Adaptor_Orders(mcontxt:Context): RecyclerView.Adapter<Adaptor_Orders.ViewHolderOrder>()
{
    lateinit var List:ArrayList<Orders>
    val Cont:Context=mcontxt


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderOrder
    {
        val v= LayoutInflater.from(parent.context).inflate(R.layout.order_cards,parent,false)
        return ViewHolderOrder(v)
    }

    override fun getItemCount(): Int {
        return List.size
    }

    override fun onBindViewHolder(holder: ViewHolderOrder, position: Int)
    {

        holder.Orderid.text=List.get(position).orderId
        var status=List.get(position).OrderStatus
        if(status.equals("Active",false))
        {
            holder.OrderStatus.setTextColor(Color.GREEN)
            holder.OrderStatus.text=status
        }
        else
        {
            holder.OrderStatus.text=status
        }

        holder.OrderedCustomer.text=List.get(position).CustomerId

        holder.btnScaner.setOnClickListener(View.OnClickListener {
            val intin:Intent= Intent(Cont,Pharmacist_QRScanner::class.java)
            Cont.startActivity(intin)
            //Toast.makeText(Cont,"to scan",Toast.LENGTH_SHORT).show()
            //Toast.makeText(Cont,"to scan",Toast.LENGTH_SHORT).show()
        })


        holder.smallcard.setOnClickListener(View.OnClickListener {
            //val intin:Intent= Intent(Cont,Orderbrief::class.java)
            //intin.putExtra("Order",List.get(position))
            //Cont.startActivity(intin)
        })









        holder.btnexpand.setOnClickListener(View.OnClickListener {
            holder.btnexpand.visibility=View.GONE
            holder.exapandedcard.visibility=View.VISIBLE
        })

        holder.btnMinimize.setOnClickListener(View.OnClickListener {
            holder.exapandedcard.visibility=View.GONE
            holder.btnexpand.visibility=View.VISIBLE
        })








    }


    fun setArray(list:ArrayList<Orders>)
    {
        List=list
        notifyDataSetChanged()
    }


    class ViewHolderOrder(itemView: View) : RecyclerView.ViewHolder(itemView)
    {
        //small card
        val smallcard:RelativeLayout=itemView.findViewById(R.id.orders_details_min)
        val Orderid:TextView=itemView.findViewById(R.id.txt_recycler_orderiddb)
        val OrderStatus:TextView=itemView.findViewById(R.id.txt_recycler_orderStatusdb)
        val btnexpand:RelativeLayout=itemView.findViewById(R.id.order_brief_holder)



        //after expand
        val exapandedcard:RelativeLayout=itemView.findViewById(R.id.orders_details_max)
        val OrderedCustomer:TextView=itemView.findViewById(R.id.txt_recycler_order_cutomernamedb)
        val btnScaner:RelativeLayout=itemView.findViewById(R.id.btn_order_scan)
        val btnMinimize:RelativeLayout=itemView.findViewById(R.id.order_min_holder)
    }
}