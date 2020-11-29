package com.Rahul.toytore.database
import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Bookentity::class],version = 1)
 abstract class Database: RoomDatabase() {

     abstract fun bookdao():BookDao

}