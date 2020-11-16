package mobilesdkdemo.rbbn.itswinter;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import mobilesdkdemo.rbbn.itswinter.audio.AudioHomeActivity;
import mobilesdkdemo.rbbn.itswinter.audio.MyAlbumActivity;
import mobilesdkdemo.rbbn.itswinter.covid.CovidHomeActivity;
import mobilesdkdemo.rbbn.itswinter.event.EventHomeActivity;
import mobilesdkdemo.rbbn.itswinter.recipe.RecipeHomeActivity;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private DrawerLayout drawerLayout;
    private ActionBar actionBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        actionBar=getSupportActionBar();
        // actionBar.setIcon(R.drawable.logo);
        actionBar.setTitle("It's Winter");
        actionBar.setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_main);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        initNavigationDrawer();

//        DrawerLayout drawer = findViewById(R.id.drawer_layout);
//        NavigationView navigationView = findViewById(R.id.nav_view);
//        // Passing each menu ID as a set of Ids because each
//        // menu should be considered as top level destinations.
//        mAppBarConfiguration = new AppBarConfiguration.Builder(
//                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow)
//                .setDrawerLayout(drawer)
//                .build();
//        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
//        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
//        NavigationUI.setupWithNavController(navigationView, navController);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){

            case android.R.id.home:

                if (!drawerLayout.isDrawerOpen(Gravity.LEFT)) {
                    drawerLayout.openDrawer(Gravity.LEFT) ;
                }else{
                    drawerLayout.closeDrawer(Gravity.LEFT); ;
                }
                break;
            case R.id.action_recipe:
                startActivity(new Intent(MainActivity.this, RecipeHomeActivity.class));
                break;
            case R.id.action_covid19:
                startActivity(new Intent(MainActivity.this, CovidHomeActivity.class));
                break;
            case R.id.action_audioApi:
                startActivity(new Intent(MainActivity.this, AudioHomeActivity.class));
                break;
            case R.id.action_event:
                startActivity(new Intent(MainActivity.this, EventHomeActivity.class));
                break;
            case R.id.action_help:
                new AlertDialog.Builder(this).setTitle("Help").setMessage("Do you want to know how to use this page?")
                        .setPositiveButton(R.string.yes,(click, arg) -> {
                            startActivity(new Intent(MainActivity.this, HelpActivity.class));
                        } )
                        .setNegativeButton("No", (click, arg) -> {  })
                        .create().show();
               break;
        }
        return super.onOptionsItemSelected(item);
    }

    //    @Override
//    public boolean onSupportNavigateUp() {
//        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
//        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
//                || super.onSupportNavigateUp();
//    }

    public void initNavigationDrawer() {

        NavigationView navigationView = (NavigationView)findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {

                int id = menuItem.getItemId();
                Intent intent;
                switch (id){
                    case R.id.home:
                        //Toast.makeText(getApplicationContext(),"Home",Toast.LENGTH_SHORT).show();

                        drawerLayout.closeDrawers();
                        break;
                    case R.id.recipe:
                        // Toast.makeText(getApplicationContext(),"Settings",Toast.LENGTH_SHORT).show();
                        intent=new Intent(MainActivity.this, RecipeHomeActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.nav_covid:
                        //Toast.makeText(getApplicationContext(),"Trash",Toast.LENGTH_SHORT).show();
                        intent=new Intent(MainActivity.this, CovidHomeActivity.class);
                        startActivity(intent);
                        drawerLayout.closeDrawers();
                        break;
                    case R.id.nav_audio:
                        //Toast.makeText(getApplicationContext(),"Trash",Toast.LENGTH_SHORT).show();
                        intent=new Intent(MainActivity.this, AudioHomeActivity.class);
                        startActivity(intent);
                        drawerLayout.closeDrawers();
                        break;
                    case R.id.nav_event:
                        //Toast.makeText(getApplicationContext(),"Trash",Toast.LENGTH_SHORT).show();
                        intent=new Intent(MainActivity.this, EventHomeActivity.class);
                        startActivity(intent);
                        drawerLayout.closeDrawers();
                        break;
//                    case R.id.logout:
//                        finish();

                }
                return true;
            }
        });
        View header = navigationView.getHeaderView(0);
//        TextView tv_email = (TextView)header.findViewById(R.id.tv_email);
//        tv_email.setText("kw2446@gmail.com");
        drawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this,drawerLayout,R.string.drawer_open,R.string.drawer_close){

            @Override
            public void onDrawerClosed(View v){
                super.onDrawerClosed(v);
            }

            @Override
            public void onDrawerOpened(View v) {
                super.onDrawerOpened(v);
            }
        };
        actionBarDrawerToggle.setDrawerIndicatorEnabled(true);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        // toolbar.setNavigationIcon(R.drawable.logo);
    }

}