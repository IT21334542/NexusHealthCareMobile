package com.example.healthcareapp

import android.content.DialogInterface
import android.content.Intent
import android.icu.text.SimpleDateFormat
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.sql.Date
import java.sql.Time


class Pharmacist_addMedicine : AppCompatActivity() {
    lateinit var medicinename:EditText
    lateinit var medicineprice:EditText
    lateinit var MedicinePic:ImageView
    lateinit var btnSubmit:Button
    lateinit var progressBar: ProgressBar
    lateinit var  imageuri: Uri
    lateinit var Imagepath:String
    lateinit var btnHome:ImageView
    lateinit var viewsd:RelativeLayout
    lateinit var username:String

    val StorageRef:StorageReference=FirebaseStorage.getInstance().reference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_pharmacist_add_medicine)
        val UN=intent.getStringExtra("UN")
        if (UN != null) {
            username=UN
        }

        initalls()




        MedicinePic.setOnClickListener(View.OnClickListener {


            selectImage()


           // var storageRef = storage.reference

            // Create a reference to 'images/mountains.jpg'
            //val mountainImagesRef = storageRef.child("images/mountains.jpg")

            // While the file names are the same, the references point to different files
           // mountainsRef.name == mountainImagesRef.name // true
            //mountainsRef.path == mountainImagesRef.path // false
        })



        btnSubmit.setOnClickListener(View.OnClickListener {
            progressBar.visibility=View.VISIBLE
            var mName:String=medicinename.text.toString()
            var mPrice:String=medicineprice.text.toString()
            uploadimage(mName)




        })




        btnHome.setOnClickListener(View.OnClickListener {
            val inte:Intent=Intent(this@Pharmacist_addMedicine,DashboardPharmacist::class.java)
            inte.putExtra("UN",username)
            startActivity(inte)
        })






    }


    private fun uploadimage(name:String)
    {
        val picturefolderRef =StorageRef.child("medicinePics/"+name)
        if(imageuri!=null)
        {
            picturefolderRef.putFile(imageuri).addOnSuccessListener(OnSuccessListener {
                //Toast.makeText(this@Pharmacist_addMedicine,"image uploadded to db",Toast.LENGTH_SHORT).show()


                picturefolderRef.downloadUrl.addOnSuccessListener(OnSuccessListener {
                    Log.d("path",it.toString())
                    Imagepath=it.toString()
                    addtoDB(medicinename.text.toString(),medicineprice.text.toString(),Imagepath)
                }).addOnFailureListener(OnFailureListener {
                    Log.d("path","failture")
                })
            }).addOnFailureListener(OnFailureListener {
                Toast.makeText(this@Pharmacist_addMedicine,"failure",Toast.LENGTH_SHORT).show()

            })
            //Imagepath=picturefolderRef.downloadUrl.result.toString()

        }
        else
        {
            Toast.makeText(this@Pharmacist_addMedicine,"image of the medicine not selected",Toast.LENGTH_SHORT).show()
        }



    }

    private  fun  addtoDB(medicinenamee: String, medicinepricee: String, imagepath: String)
    {
        var d:java.util.Date=java.util.Date()
        val date=SimpleDateFormat("ddMMyyyy").format(d)
        val medName:String=medicinenamee
        var checker:Int=1

        val id:String=date.plus(medName)
        val database = Firebase.database
        val medicineref = database.getReference("Medicines").child(id)
        val PharmacistProductsRef=database.getReference("SupplingMedicines").child(username)
        PharmacistProductsRef.child(id).setValue(0)


        try {
            val medicinepriceref=medicineref.child("MedicinePrice")
            val medPrice:Int=medicinepricee.toInt()
            medicinepriceref.setValue(medPrice)
        }
        catch (error:java.lang.NumberFormatException)
        {
            checker=0
            val alert=AlertDialog.Builder(this@Pharmacist_addMedicine)
            alert.setMessage("Enter a vaild amount")
            alert.setTitle("Medicine amount not vaild")
            alert.setNeutralButton("Ok", DialogInterface.OnClickListener { dialog, which ->  })
            alert.create().show()
            //Toast.makeText(this@Pharmacist_addMedicine,"Enter Valid Amount",Toast.LENGTH_SHORT)
        }

        if(checker!=0) {

            val medicinenameref = medicineref.child("MedicineName")
            medicinenameref.setValue(medName)

            val medicineVisibiliref = medicineref.child("Visibility")
            medicineVisibiliref.setValue(false)


            val medicineimageref = medicineref.child("MedicinePreview")
            medicineimageref.setValue(imagepath)
            //Toast.makeText(this@Pharmacist_addMedicine,"Datas upload to DB",Toast.LENGTH_SHORT).show()

            progressBar.visibility = View.GONE
            val snackB =
                Snackbar.make(viewsd, "Upload Sucessful", Snackbar.LENGTH_INDEFINITE).setAction("Ok",
                    View.OnClickListener {

                        medicinename.text = null
                        medicineprice.text = null
                    }).show()
        }
    }

    private fun selectImage()
    {
        val gallery = Intent(Intent.ACTION_GET_CONTENT, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
        @Suppress("DEPRECATION")
        startActivityForResult(gallery,100)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        @Suppress("DEPRECATION")
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode== RESULT_OK && requestCode == 100)
        {
           imageuri = data?.data!!
            MedicinePic.setImageURI(imageuri)
        }

    }

    private fun initalls()
    {
        medicinename=findViewById(R.id.etmediname)
        medicineprice=findViewById(R.id.stmediprice)
        btnHome=findViewById(R.id.homebtn)
        MedicinePic=findViewById(R.id.imageviewaddpreview)
        btnSubmit=findViewById(R.id.addtoDB)
        progressBar=findViewById(R.id.uploadProgress)
        progressBar.visibility= View.GONE
        viewsd=findViewById(R.id.rl)


    }
}