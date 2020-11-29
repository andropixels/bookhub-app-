package com.Rahul.toytore

class firebase {//model class

   var book_id:Int?=null
    var bookname:String?=null
    var  bookprice:String?=null
    var  ratings:String?=null
    var  bookimage:String?=null
    var id:String?=null

    constructor(){


    }



    constructor(
        id:String?,
        book_id: Int?,
        bookname: String?,
        bookprice: String?,
        ratings: String?,
        bookimage: String?
    ) {
        this.book_id = book_id
        this.bookname = bookname
        this.bookprice = bookprice
        this.ratings = ratings
        this.bookimage = bookimage
        this.id=id
    }

}