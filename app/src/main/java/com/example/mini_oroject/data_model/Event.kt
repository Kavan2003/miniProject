package com.example.mini_oroject.data_model


class Event {
    var id: String
    var categories: List<String>
    var description: String
    var imageUrl: String
    var initialPrice: String
    var itemname: String
    var startTime: String
    var endTime: String

    constructor(
        id: String,
        categories: List<String>,
        description: String,
        imageUrl: List<String>,
        initialPrice: String,
        itemname: String,
        startTime: String,
        endTime: String
    ) {
        this.id = id
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
        this.categories = listOf()
        this.description = ""
        this.imageUrl = ""
        this.initialPrice = "0"
        this.itemname = ""
        this.startTime = ""
        this.endTime = ""
    }


}