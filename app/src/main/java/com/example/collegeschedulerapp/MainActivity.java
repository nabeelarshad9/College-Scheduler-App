package com.example.collegeschedulerapp;

import android.os.Bundle;
import android.view.Menu;

import com.google.android.material.navigation.NavigationView;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.example.collegeschedulerapp.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarMain.toolbar);

        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_courses, R.id.nav_exams)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
/*        int layoutResourceId = getResources().getIdentifier("activity_main","layout",getPackageName());
        Log.d("Main Activity","Using Layout : "+ getResources().getResourceName(layoutResourceId));
        Toast.makeText(MainActivity.this, ": "+ getResources().getResourceName(layoutResourceId), Toast.LENGTH_SHORT).show();
        bStatus = findViewById(R.id.btnStatus);
        bCourse = findViewById(R.id.btnCourse);
        bDuedate = findViewById(R.id.btnDuedate);
        bTask = findViewById(R.id.btnTaskname);
        if (bStatus != null) {
            Toast.makeText(MainActivity.this, "bStatus not null"+String.valueOf(sortBy), Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(MainActivity.this, "bStatus is null", Toast.LENGTH_SHORT).show();
        }
        navController.addOnDestinationChangedListener((controller,destination,argument) -> {
            int currentFrgmenId = destination.getId();
            if (currentFrgmenId == R.id.nav_slideshow){
                Log.d("ButtonClick", "Clicked on Task - "+sortBy);
                Toast.makeText(MainActivity.this,String.valueOf(sortBy),Toast.LENGTH_SHORT).show();
                sortBy++;
                if (bStatus != null) {
                    Toast.makeText(MainActivity.this, String.valueOf(sortBy), Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(MainActivity.this, "bStatus is null", Toast.LENGTH_SHORT).show();
                }
                bStatus.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        sortBy = 1;
                    }
                });
            }
        });*/

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

}