package com.example.healthcareapp

import android.os.Parcel
import android.os.Parcelable

class Orders() :Parcelable
{
    var orderId:String=""
        get(){
            return field
        }
        set(value){
        field=value
        }
    var paymentType:String="not assigned"
        get() {
            return field
        }
        set(value) {
            field=value
        }

    var paymentStatus:String="not assigned"
        get() {
            return field
        }
        set(value) {
            field=value
        }

    var CustomerId:String="not assigned"
        get() {
            return field
        }
        set(value) {
            field=value
        }

    var SupplierId:String="not assigned"
        get() {
            return field
        }
        set(value) {
            field=value
        }

    var OrderStatus:String="not assigned"
        get() {
            return field
        }
        set(value) {
            field=value
        }

    var Cart:ArrayList<CardItems> =ArrayList<CardItems>()
        get() {
            return field
        }
        set(value) {
            field=value
        }

    constructor(parcel: Parcel) : this() {
    }

    override fun toString(): String {
        return "Orders(orderId='$orderId', paymentType='$paymentType', paymentStatus='$paymentStatus', CustomerId='$CustomerId', SupplierId='$SupplierId', OrderStatus='$OrderStatus', Cart=$Cart)"
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {

    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Orders> {
        override fun createFromParcel(parcel: Parcel): Orders {
            return Orders(parcel)
        }

        override fun newArray(size: Int): Array<Orders?> {
            return arrayOfNulls(size)
        }
    }


}