package util

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo


    class  connectionmanager {

        fun connectivitymanager(context: Context):Boolean{
            //this line will give us information of currently active networks
            //connectivity manager tells us wheather all c.onnec.tions are active or inactive
            val connectivityManager=context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

            //cheking foe active network
            val activeNetwork: NetworkInfo?=connectivityManager.activeNetworkInfo

            if (activeNetwork?.isConnected!=null){

                return activeNetwork.isConnected

            }
            else
                return false
        }

    }
