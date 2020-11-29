package Activity

import Fragments.dashboardfragment
import android.content.Context
import android.icu.number.NumberFormatter.with
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.*
import androidx.room.Room
import com.Rahul.toytore.Book
import com.Rahul.toytore.R
import com.Rahul.toytore.database.Bookentity
import com.Rahul.toytore.database.Database
import com.google.firebase.database.*
import com.squareup.picasso.Picasso
import java.lang.StringBuilder

lateinit var imglauncher:ImageView
      lateinit var txt1bookname:TextView
      lateinit var txt2authorname:TextView
      lateinit var txt3price:TextView
      lateinit var decratings:TextView
      lateinit var textstatic:TextView
      lateinit var textstatic2:TextView
      lateinit var addtofav:Button
     // lateinit var progresslay:RelativeLayout
      //lateinit var probar:ProgressBar
       lateinit var query:DatabaseReference



class descriptionactivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_descriptionactivity)


        //onOptionsItemSelected()


         var bookid:String?
        var imga:String?
       //  var id=bookid.toString()

        imglauncher=findViewById(R.id.launcher)
        txt1bookname=findViewById(R.id.txt1)
        txt2authorname=findViewById(R.id.txt2)
        txt3price=findViewById(R.id.txt3)
        decratings=findViewById(R.id.decrating)
        textstatic=findViewById(R.id.textstatic)
        textstatic2=findViewById(R.id.textstatic2)
        addtofav=findViewById(R.id.addtofav)
       // progresslay=findViewById(R.id.progesslay)
        //probar=findViewById(R.id.probar)
        query=FirebaseDatabase.getInstance().getReference().child("data")


        if (intent!=null){

            bookid= intent.getStringExtra("id")
                 imga=intent.getStringExtra("img")
           // Toast.makeText(this@descriptionactivity,"intent id is $bookid",Toast.LENGTH_LONG).show()
            query.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    Log.d("Data",dataSnapshot.toString())


                    for (postSnapshot in dataSnapshot.children) {

                        //val player = postSnapshot.getValue(String::class.java)
                        var book_id=postSnapshot.child("id").getValue().toString()
                      //POSTSNAPSHOT IS AN ARRAY OF ALL THE CHILDS

                       if (book_id==bookid){

                           var name=postSnapshot.child("bookname").getValue().toString()
                           var price=postSnapshot.child("bookprice").getValue().toString()
                           var ratin=postSnapshot.child("ratings").getValue().toString()

                           var dsf = postSnapshot.child("bookimage ").getValue().toString()
                           Picasso.get().load(imga).error(R.drawable.deafult).into(imglauncher)

                           txt1bookname.setText(name)
                           txt3price.setText(price)
                           decratings.setText(ratin)




                           val bookentity=Bookentity(
                               //THIS IS VERY IMPORTANT TO INITALISE ALL THE PARAMETERES OF THE BOOKENTITY DATA CLASS
                               //WE ARE INITILISING THEM TO THE OUR VIEWS OF  DESCRIPTION ACTIVITY .XML FILE
                               // SO OUR ENTITY CLASS /TABLE GETS THE DATA IN THIER RESPECTIVE FILEDS
                               //HERE WE HAVE OUR TABLE READY WITH OUR DATABASE PARESD DATA ........TA DA..........
                               //HERE WE FILLED OUR TABLE FIELDS WITH THE DATA WE GET FROM THE SERVER........
                               //NOW WE PASS THIS OBJECT TO THE ASYNCTASK CLASS
                               //SO THAT WE CAN ACCESS THE BOOKDAO CLASS'S METHOD WHICH REQUIRED BOOKENTITY AS AARGUMENT TO PASS
                               // AND ALSO WE CAN ACCESS THE INDIVIDUAL PRIMARY CONSTRUCTOR OF THE BOOKENTITY DATACLASS
                               //WHERE WE NEDDED
                               id = bookid.toString(),
                               bookname = name.toString(),///////////initilisinng local database
                               bookprice = price.toString(),
                               bookimage = imga.toString(),//image is not coming from database here , it is coming from the intent of  dashboardfragment
                               ratings = ratin.toString(),
                               book_id = 2234

                               ///**********************************************
                               // now we have our table filed with the rows with data from the firebase database TADAA.............
                               ////REMEMBER DATASNAPSHOT IS AN ARRAY OF THE RESPWCTIVE CHILDS FROM THE FIREBASE JSON TREE

                               //********************************************


                           )
                           val checkfav=Dbasynctask(applicationContext,bookentity,mode = 1).execute()//here we are passing the async class three arguments which it requires and gave mode as 1 in order to imlementget book by id function which will tell us the book having the intent id is already in database or not
                           val isfav=checkfav.get()
                           if(isfav){

                               addtofav.text="REMOVE FROM FRAGMENT"
                           } else addtofav.text="ADD TO FAV"

                           addtofav.setOnClickListener {


                               if(!Dbasynctask(applicationContext,bookentity,mode = 1).execute().get()){

                                   val addbook=Dbasynctask(applicationContext,bookentity,mode = 2).execute()
                                   val result=addbook.get()//if it is inserted or not
                                   if (result){
                                       Toast.makeText(this@descriptionactivity,"BOOK INSERTED",Toast.LENGTH_SHORT).show()

                                       addtofav.text="REMOVE FROM FAV"
                                   }else{

                                       Toast.makeText(this@descriptionactivity,"somehing went wrong",Toast.LENGTH_SHORT).show()
                                   }
                               }else{

                                   val deletebook=Dbasynctask(applicationContext,bookentity,mode = 3).execute()
                                   val result=deletebook.get()//if it is deleted  or not
                                   if (result){
                                       Toast.makeText(this@descriptionactivity,"BOOK DELETED",Toast.LENGTH_SHORT).show()

                                       addtofav.text="ADD TO FAV"
                                   }else{
                                       Toast.makeText(this@descriptionactivity,"somehing went wrong",Toast.LENGTH_SHORT).show()

                                   }

                               }
                           }

                       }


                        /* var name=postSnapshot.child("bookname").getValue().toString()
                         var price=postSnapshot.child("bookprice").getValue().toString()

                         var ratin=postSnapshot.child("ratings").getValue().toString()
                        Picasso.get().load(postSnapshot.child("bookimage ").getValue().toString()).error(R.drawable.deafult).into(imglauncher)

                             txt1bookname.setText(name)
                             txt3price.setText(price)
                             decratings.setText(ratin)*/


                    }


                }

                override fun onCancelled(databaseError: DatabaseError) {
                    // Getting Post failed, log a message
                    /////// Log.w(TAG, "loadPost:onCancelled", databaseError.toException())
                    // ...
                    Toast.makeText(this@descriptionactivity,"fetch error",Toast.LENGTH_SHORT).show()
                }
            })



        }
        setSupportActionBar(findViewById(R.id.toolbardescription) )
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)



    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

                    val id=item.itemId
        if (id==android.R.id.home){

            onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }


    ///////////////THIS ASYNC TASK CLASS NEDDS CONTEXT FOR KNOWING WHICH METHOD OF DAO/DATABASE REQUIRED A OPERATION TO PERFORMED
    ///////////////////ALSO WE  HAVE GIVEN THE BOOKENTITY TO FETCH THE DATA
    //////////////      MODE IS USED HERE FOR VARIOUS OPERRATINS SUCH AS ADDING REMOVING ETC.
    ///////////////////// DOUNBACKGROUND METHOD IS THE MANDATORY METHOD OF ASYNC TASK
          class Dbasynctask(val context: Context, val bookentity: Bookentity,val mode:Int) :
              AsyncTask<Void, Void, Boolean>() {
        
        
        /////////////INITIALISING DB/  building db
           val  db= Room.databaseBuilder(context,Database::class.java,"Database").build()
              override fun doInBackground(vararg params: Void?): Boolean {
                  //// WE WILL USE WHEN STATEMENT TO USE VARIOUS OPERATIONS TO BE PERFORMED ON DBB
                  when(mode){
                  

                        1->{ //  chk db BOOK IS ADDED TO FAV OR NOT
                            val book:Bookentity=db.bookdao().getbookbyid(bookentity.id)//pass the function arguments
                            // the .bookdao here is the function that is cretaed in the databse class of the type database by parent
                            // and return the BookDao interface so it canimplement any method of theBoolDao interface
                            db.close()
                            return book!=null
                        }
                        2->{ // save the book into db as a fvourite
                            db.bookdao().insert(bookentity)//passs the function argument WITH THE BOOKTABLE HAVING ALLTTHE DATA AS DATABASE  FETCHED FROM DATABASSE
                            db.close()
                            return true

                        }
                        3->{   // remove the book from fav
                            db.bookdao().delete(bookentity)
                            db.close()
                            return true


                        }
                      
                  }
                  return false
              }
          }


}