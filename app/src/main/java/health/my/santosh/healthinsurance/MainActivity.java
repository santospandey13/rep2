package health.my.santosh.healthinsurance;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    Databasehandler db;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        db=new Databasehandler(this);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.nav_plan) {

        } else if (id == R.id.nav_calcy) {

            Intent intent=new Intent(this,Main2Activity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.entry_from_right, R.anim.entry_from_right);

        } else if (id == R.id.nav_details) {

        } else if (id == R.id.nav_branch) {



        } else if (id == R.id.nav_share) {

            Intent shareintent =new Intent(Intent.ACTION_SEND);
            shareintent.setType("text/plain");
            String bodytext ="Share App";
            shareintent.putExtra(Intent.EXTRA_SUBJECT,"subject");
            shareintent.putExtra(Intent.EXTRA_TEXT,bodytext);
            startActivity(Intent.createChooser(shareintent,"Sharing option"));
            return true;

        } else if (id == R.id.nav_hospitals) {

            Intent intent=new Intent(this,Searchdata.class);
            startActivity(intent);
            overridePendingTransition(R.anim.entry_from_right, R.anim.entry_from_right);

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void navigate(View view) {
        Intent intent=new Intent(this,Main2Activity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.entry_from_right, R.anim.entry_from_right);


    }
    public void navigate2(View view) {
        Toast.makeText(getApplicationContext(),"This Feature is not yet Available ",Toast.LENGTH_SHORT).show();

    }
    public void navigate3(View view) {
        Intent intent=new Intent(this,Searchdata.class);
        startActivity(intent);

        overridePendingTransition(R.anim.entry_from_right, R.anim.entry_from_right);

    }

    public void downloaddoc(View view) {

        Intent intent=new Intent(this,Download.class);
        startActivity(intent);
        overridePendingTransition(R.anim.entry_from_right, R.anim.entry_from_right);


    }
}
