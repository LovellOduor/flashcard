package com.eduteam2.tunani.ui
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.eduteam2.tunani.R
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity() {
    lateinit var navigationDrawer: DrawerLayout
    lateinit var navController:NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        navigationDrawer = findViewById(R.id.drawerLayout)
        var navHost = supportFragmentManager.findFragmentById(R.id.FragmentContainerView) as NavHostFragment
        navController = navHost.navController
        var navigationView =  findViewById<NavigationView>(R.id.navigation_view)
        navigationView.setupWithNavController(navController)
        var toolbar:androidx.appcompat.widget.Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        val appBarConfiguration = AppBarConfiguration(navController.graph, navigationDrawer)
        toolbar.setupWithNavController(navController,appBarConfiguration)
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

    override fun onBackPressed() {
        if(navigationDrawer.isOpen) navigationDrawer.close() else navController.navigateUp()
    }

    }