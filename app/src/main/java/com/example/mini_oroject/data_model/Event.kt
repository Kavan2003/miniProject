package com.example.mini_oroject.data_model


class Event {
    var priceshipping: String
    var returnpolicy: String
    var shippingpolicy: String
    var condition: String
    var id: String
    var categories: String
    var description: String
    var imageUrl: String
    var initialPrice: String
    var itemname: String
    var startTime: String
    var endTime: String

    constructor(
        id: String,
        priceshipping: String,
        returnpolicy: String,
        shippingpolicy: String,
        condition: String,

        categories: String,
        description: String,
        imageUrl: List<String>,
        initialPrice: String,
        itemname: String,
        startTime: String,
        endTime: String
    ) {
        this.id = id
        this.priceshipping = priceshipping
        this.returnpolicy = returnpolicy
        this.shippingpolicy = shippingpolicy
        this.condition = condition
        this.categories = categories
        this.description = description
        this.imageUrl = imageUrl[0]
        this.initialPrice = initialPrice
        this.itemname = itemname
        this.startTime = startTime
        this.endTime = endTime
    }

    //Add this
    constructor() {
        this.id = ""
        this.priceshipping = ""
        this.returnpolicy = ""
        this.shippingpolicy = ""
        this.condition = ""
        
        this.categories = ""
        this.description = ""
        this.imageUrl = ""
        this.initialPrice = "0"
        this.itemname = ""
        this.startTime = ""
        this.endTime = ""
    }


}