package com.example.healthcareapp

class CardItems
{
    var productId:String="not assigned"
        get(){
            return field
        }
        set(value) {
            field=value
        }

    var quantity:String=""
        get() {
            return field
        }
        set(value) {
            field=value
        }

    var price:Int = -1
        get() {
            return field
        }
        set(value) {
            field=value
        }

    var productname:String="not assigned"
        get(){
            return field
        }
        set(value) {
            field=value
        }
    var productpreview="not assigned"
        get(){
            return field
        }
        set(value) {
            field=value
        }

    override fun toString(): String {
        return "CardItems(productId='$productId', quantity='$quantity', price=$price, productname='$productname', productpreview='$productpreview')"
    }


}