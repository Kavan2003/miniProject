package com.example.mini_oroject.sampledata

//data class Event(
//    val categories: List<String>,
//    val description: String,
//    val imageUrl: String,
//    val initialPrice: Double,
//    val itemname: String,
//    val startTime: String,
//    val endTime: String
//){
//    constructor() : this(, "", "", 0.0, "", "", "")
//}
class Event{
    var categories: List<String>
    var description: String
    var imageUrl: String
    var initialPrice: String
    var itemname: String
    var startTime: String
    var endTime: String

    constructor(categories: List<String>, description: String, imageUrl: String, initialPrice: String, itemname: String, startTime: String, endTime: String) {
        this.categories = categories
        this.description = description
        this.imageUrl = imageUrl
        this.initialPrice = initialPrice
        this.itemname = itemname
        this.startTime = startTime
        this.endTime = endTime
    }

    //Add this
    constructor() {
        this.categories = listOf()
        this.description = ""
        this.imageUrl = ""
        this.initialPrice = "0"
        this.itemname = ""
        this.startTime = ""
        this.endTime = ""
    }


}