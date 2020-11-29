package adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.Rahul.toytore.R
import com.Rahul.toytore.database.Bookentity
import com.squareup.picasso.Picasso

class favouriteadapter(val context:Context, val itemlist:List<Bookentity>):RecyclerView.Adapter<favouriteadapter.favouriteviewholder> (){



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): favouriteviewholder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.favouriteitems,parent, false)
        return favouriteviewholder(view)
    }

    override fun getItemCount(): Int {
         return  itemlist.size



    }

    override fun onBindViewHolder(holder: favouriteviewholder, position: Int) {

        val itemposition=itemlist[position]
      holder.favitembookname.text=itemposition.bookname
        holder.favitembookprice.text=itemposition.bookprice
        holder.favitemnookrating.text=itemposition.ratings
        Picasso.get().load(itemposition.bookimage).error(R.drawable.deafult).into(holder.favitemimage)
        ////////////

    }

    class favouriteviewholder(view: View):RecyclerView.ViewHolder(view){

        val favitemrelative=view.findViewById<RelativeLayout>(R.id.favitemrelativeview)
        val  favitemimage=view.findViewById<ImageView>(R.id.favitemimage)
        val favitembookname=view.findViewById<TextView>(R.id.favitembookname)
        val favitembookprice=view.findViewById<TextView>(R.id.favitembookprice)
        val favitemnookrating=view.findViewById<TextView>(R.id.favitembookratings)


    }
}