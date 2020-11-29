package Activity

import Fragments.aboutfragment
import Fragments.dashboardfragment
import Fragments.favouritefragment
import Fragments.profileFragment
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.FrameLayout
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.Rahul.toytore.*
import com.google.android.material.navigation.NavigationView


class MainActivity : AppCompatActivity() {

    lateinit var drawerLayout: DrawerLayout
    lateinit var coordinatorLayout: CoordinatorLayout
    lateinit var toolbar: Toolbar
    lateinit var frameLayout: FrameLayout
    lateinit var navigationView: NavigationView
    var preaviousitem:MenuItem?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        drawerLayout=findViewById(R.id.drawerLayout)
        coordinatorLayout=findViewById(R.id.coordinatorLayout)
        toolbar=findViewById(R.id.toolbar)
        navigationView=findViewById(R.id.navigationView)
        //hambergur is also known as actionbardrawertoggel
       val actionBarDrawerToggle=ActionBarDrawerToggle(this@MainActivity,drawerLayout,toolbar,
           R.string.open_drawer,
           R.string.close_drawer/////////out two string filles with open drwer and closed drawer
       )/////////////////////////////////////////////actionbardrawertoggel is the hamburger icon
         //addding listener to hambergur through drawer layout
        drawerLayout.addDrawerListener(actionBarDrawerToggle)//thats how to make hambuerger(actionbardrawertoggel) clikable with the whole drawerlayout
        actionBarDrawerToggle.syncState()//syncking the icon with toggel
     setupactionbar()
        opendashboard()

        navigationView.setNavigationItemSelectedListener(){


            if (preaviousitem!=null){

                preaviousitem?.isChecked=false

            }
            it.isCheckable=true
            it.isChecked=true
            preaviousitem=it


            when(it.itemId){///////HERE IT CONTAINS THECURRENT ITEM FROM MENU(MENU_DRAWER.XML FILE WHICHIS UNDER THE NAVIGATION VIEW)..............
                R.id.dashboard ->{
                    opendashboard()
                    drawerLayout.closeDrawers()
                }
                R.id.about ->{
                    supportFragmentManager.beginTransaction()
                        .replace(////REPLACE THE FRAME LAYOUT  WITH THE CURRENT /RESPECTIVE FRAGMENT
                            R.id.frameLayout,
                            aboutfragment()//////////////THESE ARE ACTULLY THE CLASSES  which are being called here
                        )

                        .commit()
                    supportActionBar?.title="About US"
                    drawerLayout.closeDrawers()
                }
                R.id.fav ->{
                    supportFragmentManager.beginTransaction()
                        .replace(
                            R.id.frameLayout,
                            favouritefragment()
                        )

                                                .commit()
                    supportActionBar?.title="FAvourite"
                    drawerLayout.closeDrawers()

                }
                R.id.profile ->{
                    supportFragmentManager.beginTransaction()
                        .replace(
                            R.id.frameLayout,
                            profileFragment()
                        )
                        .commit()
                    supportActionBar?.title="Profile"
                    drawerLayout.closeDrawers()
                }

            }
            return@setNavigationItemSelectedListener true
        }




    }


    fun setupactionbar(){

      //setting home buton of toolbar
        //toolbar is imported from androidx.appcompat.widget.Toolbar
        setSupportActionBar(findViewById(R.id.toolbar) )
        supportActionBar?.title="TOY STORE"
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
               //accesing the toolbar as acxtion bar
    }

    //ADDING CLICK LISTENER TO THE HAMBURGER ICON (HOMEBUTTON OF TOOLBAR)

    override fun onOptionsItemSelected(item: MenuItem): Boolean {//making it enabled
        val id = item.getItemId()/////GETTING THE CLICKED ITEM ID
        if (id == android.R.id.home){
            drawerLayout.openDrawer(GravityCompat.START)
        }
        return super.onOptionsItemSelected(item)
    }
    fun opendashboard(){
        val transaction=supportFragmentManager.beginTransaction()
        transaction.replace(/////////////REPLACE THIS FRAME LAYOUT WITH THE RESPECTIVE FRAGMENT
            R.id.frameLayout,
            dashboardfragment()
        )
        transaction.commit()
        navigationView.setCheckedItem(R.id.dashboard)
        supportActionBar?.title="dashboard"

    }

    override fun onBackPressed() {
        val  transac=supportFragmentManager.findFragmentById(R.id.frameLayout)
       when(transac){
           !is dashboardfragment -> opendashboard()
           else -> super.onBackPressed()
       }

    }
}