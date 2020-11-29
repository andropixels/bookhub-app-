package adapter

import Fragments.dashboardfragment
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.Rahul.toytore.Book
import com.Rahul.toytore.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_dashboardfragment.*
import java.util.ArrayList

//linking dashboardadaptor class with the Recycleview..adaptorclass
//this will link Recycler View with adaptor class and aalso provide it with view holder


//adaptor class recieves data from the activitty or fragment
//for that we need to pass the primary constructor to the adapter class
// the primary constructors will be context and data
class daashboardadapter(val context: Context, val itemlist:ArrayList<Book>):RecyclerView.Adapter<daashboardadapter.dashboardViewholder>() {

//so we will pass the data through the onbindviewholder class with proper postioning

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): dashboardViewholder {
       //this method is responsible for creatinhg first 10 view holder
        // we will inflate the layout file of the single item in the list and then  return that view to  vview holder class
        //we have to infalte the item view through that  file it is present in
        val view =LayoutInflater.from(parent.context).inflate(R.layout.recycler_dashboard_singel_row,parent, false)
       /* //we have inflate the layout of list item using layoutinflater
        //parameters of inflater are context and the layout file
        //context is for  viewholde and the layout file is for the ..positiion
        //return the view to the view holder calss*/
        return dashboardViewholder(view)
    }

    override fun getItemCount(): Int {
     return  itemlist.size
    }

    override fun onBindViewHolder(holder: dashboardViewholder, position: Int) {
       //this method id responsible for recyliking and reusing of the view holders
        //also this controls that the correct data should go  in correct  position
        val text=itemlist[position]  //this variable gives the position of the first item of the itemlist which is the data in adaptor
        holder.textview.text=text.bookname
        holder.rating.text=text.ratings
        holder.price.text=text.bookprice
      //  holder.imgitem1.setImageResource(text.bookimage)
        //using picasso for fetching images from urlform the
        Picasso.get().load(text.bookimage).error(R.drawable.deafult).into(holder.imgitem1)//here text carries the position of arraylist items so that when code runs it gives respective ositons array lists data to the holder.
        //and then holder creates the view holder of that perticular view with its respective data
        // and then this view holder gets passed tothe view holder class below
        //then it goes to the adapter class above.
        //then adapter pass this data to the  recycler views positon by postion and recycler view creats the reeplica row by row
     /*   //here we are passing the data with proper possition via textview
        //for every new position of the view holder the value of  the  text variable will change*/

        holder.itemrelativelayout.setOnClickListener {
            Toast.makeText(context,"cliked on  ${holder.textview.text}",Toast.LENGTH_SHORT).show()

        }
    }
    //view holder presents inside the adaptor class
    //we have to pass view as an parameter to viewholder class
    class dashboardViewholder(view: View):RecyclerView.ViewHolder(view){
        //view holder needs a view inside it so we will pass the  viewitem that we have created
        val textview:TextView=view.findViewById(R.id.txtitem)
        val rating:TextView=view.findViewById(R.id.ratings)
        val price:TextView=view.findViewById(R.id.price)
        val imgitem1:ImageView=view.findViewById(R.id.imgitem)
        val itemrelativelayout:RelativeLayout=view.findViewById(R.id.itemrealativelayout)


    }
}