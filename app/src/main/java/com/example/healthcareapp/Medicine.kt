package com.example.healthcareapp

class Medicines(name:String,price:Int,Previewpath:String,visi:Boolean,k:String)
{

     var MedicineName:String=name
        get()=field
        set(valu)
        {
            field=valu
        }
     var MedicinePrice:Int=price
        get()=field
        set(value) {
            field=value
        }
    var PreviewPicture:String=Previewpath
        get()=field
        set(value) {
        field=value
        }

    var visibile:Boolean=visi
        get()=field
        set(value) {
            field=value
        }
    var id:String=k
        get()=field
        set(value) {
            field=value
        }

    override fun toString(): String {
        return "Medicines(MedicineName='$MedicineName', MedicinePrice=$MedicinePrice, PreviewPicture='$PreviewPicture', visibile=$visibile)"
    }


}