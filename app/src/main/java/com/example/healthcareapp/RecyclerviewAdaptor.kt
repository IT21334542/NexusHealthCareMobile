package com.example.healthcareapp

import android.annotation.SuppressLint
import android.content.Context
import android.opengl.Visibility
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import java.util.Objects

class RecyclerviewAdaptor(mcontact:Context,username:String): RecyclerView.Adapter<RecyclerviewAdaptor.viewH>()
{
    lateinit var medicineList:ArrayList<Medicines>
    val thiscon=mcontact
    val UN=username


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewH {
        val v=LayoutInflater.from(parent.context).inflate(R.layout.mymedicines,parent,false)
        return viewH(v)
    }

    override fun getItemCount(): Int
    {
        return medicineList.size
    }

    override fun onBindViewHolder(holder: viewH, position: Int)
    {
        println("item counter "+itemCount)

        holder.mediName.text=medicineList.get(position).MedicineName
        holder.mediPrice.text=medicineList.get(position).MedicinePrice.toString()
        holder.btnVisible.isChecked=medicineList.get(position).visibile
        Glide.with(thiscon).asBitmap().load(medicineList.get(position).PreviewPicture).into(holder.imagePreview)

        holder.dropdown.setOnClickListener(View.OnClickListener {
            holder.maxCardVi.visibility=View.VISIBLE
            holder.dropdown.visibility=View.GONE
        })

        holder.btnVisible.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener { buttonView, isChecked ->
            println("clicked the visible button"+holder.btnVisible.isChecked)
            val database = Firebase.database
            val medicineref = database.getReference("Medicines").child(medicineList.get(position).id)
            val medicineVisibe = medicineref.child("Visibility")
            medicineVisibe.setValue(holder.btnVisible.isChecked)
            if (isChecked)
            {
                medicineList.get(position).visibile=true
                Toast.makeText(thiscon,"Product is now Online",Toast.LENGTH_SHORT).show()
            }
            else
            {
                medicineList.get(position).visibile=false
                Toast.makeText(thiscon,"Product is now OFF-LINE",Toast.LENGTH_SHORT).show()
            }
        })

        holder.dropup.setOnClickListener(View.OnClickListener {
            holder.maxCardVi.visibility=View.GONE
            holder.dropdown.visibility=View.VISIBLE
        })


        holder.btnDelete.setOnClickListener(View.OnClickListener {

            val database = Firebase.database
            val medicineref = database.getReference("Medicines").child(medicineList.get(position).id)
            val mymedref = database.getReference("SupplingMedicines").child(UN).child(medicineList.get(position).id)
            medicineList.removeAt(position)
            medicineref.removeValue().addOnSuccessListener(OnSuccessListener {
                println("deletion1 success")

            }).addOnFailureListener(OnFailureListener {
                println("Deletion1 failed")
            })
            mymedref.removeValue().addOnSuccessListener(OnSuccessListener {
                println("DELETIon 2 sucess")
            }).addOnFailureListener(OnFailureListener {
                println("Deletion2 failed")
            })

            Toast.makeText(thiscon,"DELEtED",Toast.LENGTH_SHORT).show()
        })
    }


    fun setArray(list:ArrayList<Medicines>)
    {
        medicineList=list
        notifyDataSetChanged()
    }




    class viewH(itemView: View) : RecyclerView.ViewHolder(itemView)
    {
        val minCardVi:RelativeLayout=itemView.findViewById(R.id.productmin)
        val maxCardVi:RelativeLayout=itemView.findViewById(R.id.productexp)
        val dropdown:ImageView=itemView.findViewById(R.id.btnexpand)
        val dropup:ImageView=itemView.findViewById(R.id.btnMinimize)
        val imagePreview:ImageView=itemView.findViewById(R.id.imgPreviews)
        val mediName:TextView=itemView.findViewById(R.id.Mediciniitxt)
        val mediPrice:TextView=itemView.findViewById(R.id.MediciniiPricestxt)
        @SuppressLint("UseSwitchCompatOrMaterialCode")
        val btnVisible:Switch=itemView.findViewById(R.id.btnSoldOut)
        val btnDelete:Button=itemView.findViewById(R.id.btnDeleteProduct)


    }


}