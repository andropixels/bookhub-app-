package com.Rahul.toytore.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.ColumnInfo



@Entity(tableName = "booktable")
 data class Bookentity(

    val book_id:Int,
    @PrimaryKey val id:String,
    @ColumnInfo(name = "book_name")  val bookname:String,
    @ColumnInfo(name = "book_price") val bookprice:String,
    @ColumnInfo(name = "rating_s") val ratings:String,
    @ColumnInfo(name = "book_image")  val bookimage:String


)
