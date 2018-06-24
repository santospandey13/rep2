package health.my.santosh.healthinsurance;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.EditText;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Searchdata extends AppCompatActivity {
    RecyclerView recyclerView;
    SearchView searchView;
    // List<Hospital> hospitals= new ArrayList<>();
    Myadopter myadopter;
    Databasehandler db;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchdata);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setTitle("Network Hospitals");

        db = new Databasehandler(this);
        recyclerView = (RecyclerView) findViewById(R.id.myrecycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        searchView = (SearchView) findViewById(R.id.sv1);
       getAll();

    }







    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        MenuItem searchViewItem = menu.findItem(R.id.search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchViewItem);
        searchView.setQueryHint("Enter Location,Area,City...");

        SearchView searchView1 =(SearchView) searchViewItem.getActionView();
        EditText editText=(EditText) searchView1.findViewById(android.support.v7.appcompat.R.id.search_src_text);
        editText.setTextColor(Color.WHITE);
        editText.setHintTextColor(Color.WHITE);



        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {


                displaydata(newText);


                return false;
            }
        });


        return super.onCreateOptionsMenu(menu);
    }

    public void getAll()

    {
        myadopter = null;

        myadopter = new Myadopter(db.gethospitalAll(), this, recyclerView);
        recyclerView.setAdapter(myadopter);


    }


    public void displaydata(String text) {
        myadopter = null;
        String mmm = text.toString();
        myadopter = new Myadopter(db.gethospital(mmm), this, recyclerView);
        recyclerView.setAdapter(myadopter);


    }


}
