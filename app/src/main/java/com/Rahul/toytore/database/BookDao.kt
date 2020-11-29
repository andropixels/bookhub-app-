package com.Rahul.toytore.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query


///this intrface will contain al the functionn that arte nedeed to perform on the database like  insert delete
@Dao
interface BookDao {
   //***************************************************************************************************************
    //OPERATIONS SUCH AS INSERT DELETE SELECTING A SPECIIC DATA ARE DONE INTO THIS INTERFACE
    /////THIS INTERFACE GONNA INTERFARE WITH THE ABSTARCT DATABSE CLASS IN ORDER TO IMPLEMENT ALL THE METHOD ABOVE IN THE ACTIVITY
 //*******************************************************************************************************************************
    //// THE NON ABSTRACT METHOD OF INTERFACE MAY OR MAY NOT HAVE A DEAFULT IMPLEMETATION
    @Insert
    fun insert(bookentity: Bookentity)//BOOK ENTITY IS PASSED AS A PARAMETERE SO THE ARGUMENT ALSO.
    ////HERE WE ARE PASSING BOOKENTITY I.E TABLE AS AARRGUMENT
    ///FURTHER WE WILL PASS THE ARGUMENT OF TABLE WITH THE DATA WHEN WE WILL MAKE THE ASYNCTASK CLASS
    //SO ITS AN ARGUMENT FOR THE ASYNC CLASS  ALSO FOR THE BOOKDAO'S FUNCTION
    //WHICH WE ARE GETTING FROM THE ABSTRACT DATABASE CLAAS'S ABSTRACT bookda METHOD


    @Delete// DONT NEED TO MAKE QUERY FOR DELETE AND INSERT ROOM LIBRARY TAKE CARE OF THIS TO TWO METHODS
    fun delete(bookentity: Bookentity)

    @Query("SELECT * FROM booktable")
    fun getallbooks():List<Bookentity>

   @Query("SELECT * FROM booktable WHERE id=:id")
    fun getbookbyid(id:String):Bookentity
}
