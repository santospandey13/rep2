package health.my.santosh.healthinsurance;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

public class Slash extends AppCompatActivity {

    ProgressBar PD;
    Databasehandler db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slash);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                new Myasynctask().execute();
            }
        }, 5000);
        PD=(ProgressBar)findViewById(R.id.PD);
        db=new Databasehandler(this);


        }

    private class Myasynctask extends AsyncTask
    {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            PD.setVisibility(View.VISIBLE);
        }

        @Override
        protected Void doInBackground(Object[] objects)

        {

            return null;
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);

            move();


            }
    }

    private void move() {

        Intent intent=new Intent(this,MainActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.entry_from_right, R.anim.entry_from_right);
    }

}
