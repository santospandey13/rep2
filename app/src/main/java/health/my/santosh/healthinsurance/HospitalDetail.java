package health.my.santosh.healthinsurance;

import android.database.Cursor;
import android.hardware.Camera;
import android.location.Address;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class HospitalDetail extends AppCompatActivity {

    EditText editText2,editText3,editText4,editText5,editText6,editText7,editText8;
    TextView textView;
Databasehandler db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hospital_detail);
        db=new Databasehandler(this);
        if(getIntent().hasExtra("Name") && getIntent().hasExtra("Address")&& getIntent().hasExtra("Area")&& getIntent().hasExtra("City"))
        {
            String Name=getIntent().getStringExtra("Name");
            String Address= getIntent().getStringExtra("Address");
            String Area =getIntent().getStringExtra("Area");
            String City=getIntent().getStringExtra("City");

            textView=(TextView)findViewById(R.id.name);
            editText2=(EditText)findViewById(R.id.Address);
            editText3=(EditText)findViewById(R.id.area);
            editText4=(EditText)findViewById(R.id.city);
            editText5=(EditText) findViewById(R.id.state);
            editText6=(EditText)findViewById(R.id.Contactperson);
            editText7=(EditText)findViewById(R.id.contnum);
            editText8=(EditText)findViewById(R.id.pin);


            textView.setText(Name.toString());
            editText2.setText(Address.toString());
            editText3.setText(Area.toString());
            editText4.setText(City.toString());
            displaydata(Address,Area,City);


            }
        else
        {
            Toast.makeText(getApplicationContext(),"No Data Found",Toast.LENGTH_LONG).show();
        }


    }

    private void displaydata(String Address,String Area,String City) {

      String myid="";
        String myid1="";
        String myid2="";
        String myid3="";
        Cursor res=db.getNethospitaldata(Address, Area,City);
        while (res.moveToNext())
        {

             myid=res.getString(0);
              myid1=res.getString(1);
              myid2=res.getString(2);
              myid3=res.getString(3);
            }
            editText5.setText(myid.toString());
        editText6.setText(myid1.toString());
        editText7.setText(myid2.toString());
        editText8.setText(myid3.toString());


        db.close();




    }


}
