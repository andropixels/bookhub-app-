package Fragments

import adapter.favouriteadapter
import android.content.Context
import android.os.AsyncTask
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.RelativeLayout
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.Rahul.toytore.R
import androidx.room.Room
import com.Rahul.toytore.database.Bookentity
import com.Rahul.toytore.database.Database


class favouritefragment : Fragment() {




lateinit var recyclerView: RecyclerView
    //lateinit var progressBar: ProgressBar
   // lateinit var progresslayout:RelativeLayout
    lateinit var favrelitive:RelativeLayout
    lateinit var favouriteadapter: favouriteadapter
    lateinit var layoutManager: LinearLayoutManager
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val inflate = inflater.inflate(R.layout.fragment_favouritefragment, container, false)
        recyclerView=inflate.findViewById(R.id.favrecycler)

        ////when we need to retrive any data from async task we need to exexute it and get the value of it
        /////here we have made a asynctask class which is returing the lost of bookentity as a result (getting from the dao interface)
        ////sso doinbackground function returing the getallbooks function of dao interface so it is returning all the books from the localdatabse
        ////(remember in descripton activity we have gave the vales to the columns of  the entity from the views(data which is falling in the views from the firebase database))
        val dblist=favasynctask(activity as Context).execute().get()
        favouriteadapter= favouriteadapter(activity as Context,dblist)
        layoutManager=GridLayoutManager(activity as Context,2)
        if (activity!=null){

            recyclerView.adapter=favouriteadapter
            recyclerView.layoutManager=layoutManager
        }



        return inflate
    }

    ////CREATING ASYNCTASK CLASS TO FETCH DATA FROM LOCALDATABSE HERE IN FAVORITEFRAGMENT

    class favasynctask(val context:Context):AsyncTask<Void,Void,List<Bookentity>>(){
        override fun doInBackground(vararg params: Void?): List<Bookentity> {
            val  db= Room.databaseBuilder(context, Database::class.java,"Database").build()
            return  db.bookdao().getallbooks()


        }
    }

}