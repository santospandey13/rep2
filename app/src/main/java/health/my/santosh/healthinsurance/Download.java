package health.my.santosh.healthinsurance;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.channels.FileChannel;

public class Download extends AppCompatActivity {
    ImageButton button1,button2;
    ProgressBar PD;
   public ProgressDialog progressDialog;
TextView textView;
    private static final int PERMISSION_REQUEST_CODE= 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download);

        progressDialog = new ProgressDialog(this);

        button1 =(ImageButton)findViewById(R.id.btn1);
        button2 =(ImageButton)findViewById(R.id.btn2);

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog.setIndeterminate(true);
                progressDialog.setTitle("Please Wait..");
                progressDialog.setMessage("Downloading…");
                progressDialog.setCancelable(false);
                progressDialog.show();



                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        new Myasynctask2().execute();
                    }
                }, 5000);

            }
        });


        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // PD.setVisibility(View.VISIBLE);


                progressDialog.setIndeterminate(true);
                progressDialog.setTitle("Please Wait..");
                progressDialog.setMessage("Downloading…");
                progressDialog.setCancelable(false);
                progressDialog.show();



                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        new Myasynctask().execute();
                    }
                }, 5000);
            }
        });




        if (Build.VERSION.SDK_INT >= 23) {
            if (checkPermission())

            {

            }
            else
            {
                requestPermission();
            }
        }
        else
        {
            // Code for Below 23 API Oriented Device
        }
    }
    private class Myasynctask extends AsyncTask
    {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected Void doInBackground(Object[] objects)

        {

            return null;
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            Copyfile1();
            progressDialog.dismiss();
            openfile();
        }
    }
    private class Myasynctask2 extends AsyncTask
    {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected Void doInBackground(Object[] objects)

        {

            return null;
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            Copyfile2();
            progressDialog.dismiss();
            openfile2();
        }
    }

    private void Copyfile2()
    {
        String  path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/download/";
        AssetManager assetManager = getAssets();
        String[] files = null;
        InputStream in = null;
        OutputStream out = null;
        String filename = "Health-insurance-claim-form.pdf";
        try
        {
            in = assetManager.open(filename);
            out = new FileOutputStream(path + filename);
            copyFile(in, out);
            in.close();
            in = null;
            out.flush();
            out.close();
            out = null;
        }
        catch(IOException e)
        {
            Log.e("tag", "Failed to copy asset file: " + filename, e);
        }



    }

    private void openfile()
    {
        String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/download/"+"SuperShip Proposal Form 2017-Final-NEW4pgs-colour.pdf";
        File targetFile = new File(path);
        Uri targetUri = Uri.fromFile(targetFile);
        Intent intent;
        intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(targetUri, "application/pdf");
        startActivity(intent);
    }

    private void openfile2()
    {
        String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/download/"+"Health-insurance-claim-form.pdf";
        File targetFile = new File(path);
        Uri targetUri = Uri.fromFile(targetFile);
        Intent intent;
        intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(targetUri, "application/pdf");
        startActivity(intent);
    }


    private void Copyfile1()
    {
        String  path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/download/";
        AssetManager assetManager = getAssets();
        String[] files = null;
        InputStream in = null;
        OutputStream out = null;
        String filename = "SuperShip Proposal Form 2017-Final-NEW4pgs-colour.pdf";
        try
        {
            in = assetManager.open(filename);
            out = new FileOutputStream(path + filename);
            copyFile(in, out);
            in.close();
            in = null;
            out.flush();
            out.close();
            out = null;
        }
        catch(IOException e)
        {
            Log.e("tag", "Failed to copy asset file: " + filename, e);
        }


    }
    private void copyFile(InputStream in, OutputStream out) throws IOException
    {
        byte[] buffer = new byte[1024];
        int read;
        while((read = in.read(buffer)) != -1)
        {
            out.write(buffer, 0, read);
        }
    }




    private void requestPermission()
    {

        if (ActivityCompat.shouldShowRequestPermissionRationale(Download.this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            Toast.makeText(Download.this, " Please allow this permission in App Settings.", Toast.LENGTH_LONG).show();
        } else {
            ActivityCompat.requestPermissions(Download.this, new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
        }
    }
    private boolean checkPermission()
    {
        int result = ContextCompat.checkSelfPermission(Download.this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (result == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            return false;
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults)
    {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(Download.this, "Permission Granted Successfully! ", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(Download.this, "Permission Denied :( ", Toast.LENGTH_LONG).show();
                }
                break;
        }
    }



    public void download2(View view)
    {


    }
}
