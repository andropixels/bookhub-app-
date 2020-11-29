package Fragments

import Activity.descriptionactivity
import Activity.query
import adapter.daashboardadapter
import android.app.Activity
import android.app.AlertDialog
import android.app.DownloadManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.*
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.Rahul.toytore.Book
import com.Rahul.toytore.R
import com.Rahul.toytore.firebase
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest

import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.*
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_dashboardfragment.*
import kotlinx.android.synthetic.main.recycler_dashboard_singel_row.*
import org.json.JSONException

import util.connectionmanager
import java.util.*
import java.util.stream.Collectors
import kotlin.Comparator


class dashboardfragment : Fragment() {

    lateinit var recyclerView: RecyclerView
    lateinit var layoutManager: LinearLayoutManager
    lateinit var progresslayout:RelativeLayout
    lateinit var progressbar:ProgressBar
    lateinit var refdb:DatabaseReference//database ref variable in which we store our adapter and give a main field from database

    //now we haveto pass the data to the adaptor class
    //for that we can use a array list or data class here
    val bookinfo = arrayListOf<Book>()




    lateinit var recyclerdashboardadaptor: daashboardadapter



   var ratingcomparator=Comparator<Book>{book1,book2  ->

        book1.ratings.compareTo(book2.ratings,true)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): (View?) {




        setHasOptionsMenu(true)//setting sorting menu at toolbar


        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_dashboardfragment, container, false)
         refdb=FirebaseDatabase.getInstance().getReference().child("data")
        progressbar=view.findViewById(R.id.progressbar)
        progresslayout=view.findViewById(R.id.progresslayout)
        recyclerView = view.findViewById(R.id.recyclerviewdash)//intislize the adaptor  or recylerview of dashboard
        layoutManager = LinearLayoutManager(activity)
        //so we need to  pass the data to the adapter class for that we need to initialize the recyclerdashboardadaptor with the adaptor laa
        //and pass it with the argument as context and data
        progresslayout.visibility=View.VISIBLE
        progressbar.visibility=View.VISIBLE


   if(connectionmanager().connectivitymanager(activity as Context)){


            try{

                progresslayout.visibility=View.GONE
                progressbar.visibility=View.GONE
                logrecyclerview()


            }

            catch (e:JSONException){

                Toast.makeText(activity as Context,"smething went wrong",Toast.LENGTH_SHORT).show()
            }

      /* val queue=Volley.newRequestQueue(activity as Context)
       val url="http://192.168.43.57/api.php"
       val jsonobjectrequest=object:JsonObjectRequest(Request.Method.GET,url,null,Response.Listener{
           try { progresslayout.visibility=View.GONE
               val array=it.getJSONArray("data")
               for (i in 0 until array.length()){

                   val data=array.getJSONObject(i)
                   val  bookobject=Book(

                       data.getString("bookname"),
                       data.getString("bookprice"),
                       data.getString("ratings"),
                       data.getString("bookimage")


                   )
                   bookinfo.add(bookobject)
                   recyclerdashboardadaptor = daashboardadapter(activity as Context, bookinfo)//here we have pass the data list as the data to  our adapter class
                   //now the thing remains as how to pass this data that adaptor holds to the recycler view so that it can give that data to the list
                   //ofcourse firstly adaptor will craete an viewholder bin data with the view and then pass it to the recyclerview

                   recyclerView.adapter = recyclerdashboardadaptor//thats how we have passed the data from adaptor to the recycler view
                   recyclerView.layoutManager = layoutManager//thats how we tell the recyler view to which view it should give to the list linear or grid

                   // to remove thespace between the each item of the list of recyclerview
               /*   recyclerView.addItemDecoration(
                       DividerItemDecoration(
                           recyclerView.context,
                           (layoutManager as LinearLayoutManager).orientation
                       )
                   )*/

               }
           } catch (e:JSONException){

               Toast.makeText(activity as Context,"smething went wrong",Toast.LENGTH_SHORT).show()
           }


       },Response.ErrorListener {

           //println("error is $it")
           ///volley errors are handles inside  this block
           //our responce is $it so if we dont acces $it  here only  responce.error will be execute
           Toast.makeText(activity as Context,"v.o.lley error occoured",Toast.LENGTH_SHORT).show()
       }) {


           override fun getHeaders(): MutableMap<String, String> {
               val headers = HashMap<String, String>()
               return headers
           }
       }
       queue.add(jsonobjectrequest)*/
   }

        else{

       val dailog=AlertDialog.Builder(activity as Context)
       dailog.setTitle("Error")
       dailog.setMessage("no connection")
       dailog.setPositiveButton("open setting"){text,listner ->

            //weneed  to open phones setting here
           //for that we can use explisit intent
           val settingintent= Intent(Settings.ACTION_WIRELESS_SETTINGS)
           startActivity(settingintent)
           activity?.finish()//when we press back activity/fragment will start from the starting
       //Settings class provide the path that user wants to navigate

       }
       dailog.setNegativeButton("exit"){text,listener->
            //this line below lets us close app from any point and app closes completley
           ActivityCompat.finishAffinity(activity as Activity)
       }

      dailog.create()
       dailog.show()

   }


        return view



    }

      private fun logrecyclerview (){


//////////////ALL  DATA OF FIREBASE IS IN OPTION NOW
          val options = FirebaseRecyclerOptions.Builder<firebase>()
              .setQuery(refdb,firebase::class.java)  
              .setLifecycleOwner(this)
              .build()//option contains all data from the firebasedatabase
          //create firebase recycler adapter and pass model class to it with view holder class

          val firrebaserecycleradapter=object :FirebaseRecyclerAdapter<firebase,firebaseviewholder>( options)

          {
              override fun onCreateViewHolder(
                  parent: ViewGroup,
                  viewType: Int
              ): firebaseviewholder {
                  val view =LayoutInflater.from(parent.context).inflate(R.layout.recycler_dashboard_singel_row,parent, false)
                  return firebaseviewholder(view)
              }

              override fun onBindViewHolder(
                  holder: firebaseviewholder,
                  position: Int,
                  model: firebase
              ) {

                  holder.textview.setText(model.bookname)
                  holder.price.setText(model.bookprice)
                  holder.rating.setText(model.ratings)
                  Picasso.get().load(model.bookimage).error(R.drawable.deafult).into(holder.imgitem1)

                  holder.itemrelativelayout.setOnClickListener {
                     // Toast.makeText(context,"cliked on  ${holder.textview.text}",Toast.LENGTH_SHORT).show()

                      val intent=Intent(context,descriptionactivity::class.java)
                      intent.putExtra("id",model.id)
                      intent.putExtra("img",model.bookimage)
                      context?.startActivity(intent)
                           /////HERE CONTE GIVES THE ACCESS TO THE METHOD WHICH ARE REQUIRED FOR THE CIRRENT OPERATIONS
                  }
              }



          }
         recyclerView.adapter=firrebaserecycleradapter//direct passing class to the recycler view thts the unique thing about firebase that you dont need to makr an other adapter
          recyclerView.layoutManager = layoutManager
     // val rsdsf=    firrebaserecycleradapter.notifyDataSetChanged()
          firrebaserecycleradapter.notifyDataSetChanged()

      }




    class firebaseviewholder(itemView:View):RecyclerView.ViewHolder(itemView){
        val textview: TextView =itemView.findViewById(R.id.txtitem)
        val rating: TextView =itemView.findViewById(R.id.ratings)
        val price: TextView =itemView.findViewById(R.id.price)
        val imgitem1: ImageView =itemView.findViewById(R.id.imgitem)
        val itemrelativelayout:RelativeLayout=itemView.findViewById(R.id.itemrealativelayout)


    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menutoolbar,menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        val id=item.itemId
        if (id==R.id.actionsort){

            Collections.sort(bookinfo,ratingcomparator)
            bookinfo.reverse()
        }
       // firrebaserecycleradapter.notifyDataSetChanged()

        return super.onOptionsItemSelected(item)
    }


    }

