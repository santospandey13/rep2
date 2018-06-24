package health.my.santosh.healthinsurance;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Build;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.github.aakira.expandablelayout.ExpandableLayout;
import com.github.aakira.expandablelayout.ExpandableRelativeLayout;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.itextpdf.text.Element.ALIGN_CENTER;
import static com.itextpdf.text.Element.ALIGN_RIGHT;

public class Main2Activity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    Databasehandler db;

    ExpandableLayout expandableLayout1,expandableLayout2;
    Spinner spinner,familydef,Planopted,myzo,term,spin_cash,spin_maternity,spin_crit_ill;
    TextView mytext;
    String busi[]={"Individual","Family Floater"};
    String family[]={"1A","2A","2A+1C","2A+2C","2A+3C","1A+1C","1A+2C","1A+3C"};
    String plan[]={"VALUE 5L","VALUE 7.5L","CLASSIC 10L","CLASSIC 15L","CLASSIC 20L","UBER 20L","UBER 30L","UBER 50L",
            "UBER 60L","UBER 70L","UBER 80L","UBER 90L","UBER 1CR"};
    String Zone1[]={"Zone 1","Zone 2","Zone 3"};
    String Term[]={"1 Year","2 Year","3 Year"};


    TextView pp1,pp2,pp3;
    String item = "";
    String myid;
    EditText mycal,myelderdate,eldage,myelderband,mmyspouse_dob,chield1_dob,chield2_dob,chield3_dob,spouse_age,spouse_band,
    chield1_age,chield1_band,chield2_age,chield2_band,chield3_age,chield3_band,cashforhospital,mcriticself,Mater_Amount,criticaldep1,
            criticaldep2,criticaldep3,criticaldep4;

    Spinner spincash;

    Button button;
    static int timeout=3000;
    private static final int PERMISSION_REQUEST_CODE= 1;

    Switch dep1,dep2,dep3,dep4;

    DatePickerDialog datepicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        db=new Databasehandler(this);

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



        spinner=(Spinner)findViewById(R.id.spneer1);
        familydef=(Spinner) findViewById(R.id.spneer2);
        Planopted=(Spinner)findViewById(R.id.myvalue) ;
        myzo=(Spinner) findViewById(R.id.Zone);
        term=(Spinner)findViewById(R.id.period) ;
        String today= new SimpleDateFormat("dd-MM-yyyy").format(new Date());
        mycal=(EditText) findViewById(R.id.ageelder);

        cashforhospital=(EditText) findViewById(R.id.cash);
        spincash=(Spinner)findViewById(R.id.hospital_cash) ;

        mycal.setText(today);
        myelderdate=(EditText) findViewById(R.id.elderdate);
        myelderdate.setText(today);


        eldage=(EditText) findViewById(R.id.elderage);
        myelderband=(EditText)findViewById(R.id.elderband);

        mmyspouse_dob=(EditText)findViewById(R.id.spousedate) ;
        spouse_age=(EditText) findViewById(R.id.spouseage);
        spouse_band=(EditText)findViewById(R.id.spouseband) ;


        chield1_dob=(EditText)findViewById(R.id.dobc1) ;
        chield1_age=(EditText)findViewById(R.id.agec1);
        chield1_band=(EditText)findViewById(R.id.bandc1);

        chield2_dob=(EditText)findViewById(R.id.dobc2) ;
        chield2_age=(EditText)findViewById(R.id.agec2);
        chield2_band=(EditText)findViewById(R.id.bandc2);

        chield3_dob=(EditText)findViewById(R.id.dobc3);
        chield3_age=(EditText)findViewById(R.id.agec3);
        chield3_band=(EditText)findViewById(R.id.band3);
        Mater_Amount=(EditText)findViewById(R.id.mater_nity);

        mcriticself=(EditText)findViewById(R.id.critic_self);

        criticaldep1=(EditText)findViewById(R.id.critic_1);
        criticaldep2=(EditText)findViewById(R.id.critic_Dep2) ;
        criticaldep3=(EditText)findViewById(R.id.critic_dep3);
        criticaldep4=(EditText)findViewById(R.id.critic_dep4);

        //spin_cash,spin_maternity,spin_crit_ill;

        spin_cash=(Spinner)findViewById(R.id.hospital_cash);
        spin_maternity=(Spinner)findViewById(R.id.maternity);
        spin_crit_ill=(Spinner)findViewById(R.id.criticalill);

        dep1=(Switch)findViewById(R.id.criticdep_1);
        dep2=(Switch)findViewById(R.id.criticalill_2);
        dep3=(Switch)findViewById(R.id.criticalill_3);
        dep4=(Switch)findViewById(R.id.criticalill_4);


        EditText getbasic=(EditText) findViewById(R.id.mybasic);
        EditText mytotal=(EditText)findViewById(R.id.addontotal);

         pp1=(TextView) findViewById(R.id.premium2);
         pp2=(TextView) findViewById(R.id.gst2);
         pp3=(TextView) findViewById(R.id.total2);


        ArrayAdapter<String> myadapter =new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,busi);
        myadapter.setDropDownViewResource(R.layout.spinner2);
        spinner.setAdapter(myadapter);
        spinner.setOnItemSelectedListener(this);


        ArrayAdapter<String> myadapter2 =new ArrayAdapter<String>(this,R.layout.mmm,plan);
        myadapter2.setDropDownViewResource(R.layout.spinnertext);
        Planopted.setAdapter(myadapter2);
        Planopted.setOnItemSelectedListener(this);

        ArrayAdapter<String> myadapter3 =new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,Zone1);
        myadapter3.setDropDownViewResource(R.layout.spinner2);
        myzo.setAdapter(myadapter3);
        myzo.setOnItemSelectedListener(this);

        ArrayAdapter<String> myadapter4 =new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,Term);
        myadapter4.setDropDownViewResource(R.layout.spinner2);
        term.setAdapter(myadapter4);
        term.setOnItemSelectedListener(this);






        dep1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if(dep1.isChecked())
                    {
                        if(mmyspouse_dob.length()<10)
                        {
                            Toast.makeText(getApplicationContext(),"Please Enter DOB of Depdent 1",Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            if(spin_crit_ill.getSelectedItem().equals("None"))
                            {
                                Toast.makeText(getApplicationContext(),"Please Select Cover Amount",Toast.LENGTH_SHORT).show();
                                criticaldep1.setText("0.0");
                            }
                            else
                            {
                                getspousecritical();
                                AddOnTotal();
                            }


                        }
                    }
                    else
                    {
                        criticaldep1.setText("0.0");
                        AddOnTotal();
                    }
            }
            });
        dep2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(dep2.isChecked())
                {
                    if(chield1_dob.length()<10)
                    {
                        Toast.makeText(getApplicationContext(),"Please Enter DOB of Depdent 2",Toast.LENGTH_SHORT).show();

                    }
                    else
                    {
                        if(spin_crit_ill.getSelectedItem().equals("None"))
                        {
                            Toast.makeText(getApplicationContext(),"Please Select Cover Amount",Toast.LENGTH_SHORT).show();
                            criticaldep2.setText("0.0");

                        }
                        else
                        {
                            getsc2critical();
                            AddOnTotal();
                        }
                    }
                }
                else
                {
                    criticaldep2.setText("0.0");
                    AddOnTotal();
                }
            }
        });
        dep3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(dep3.isChecked())
                {
                    if(chield2_dob.length()<10)
                    {
                        Toast.makeText(getApplicationContext(),"Please Enter DOB of Depdent 3",Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        if(spin_crit_ill.getSelectedItem().equals("None"))
                        {
                            Toast.makeText(getApplicationContext(),"Please Select Cover Amount",Toast.LENGTH_SHORT).show();
                            criticaldep3.setText("0.0");

                        }
                        else
                        {
                            getc3critical();
                            AddOnTotal();
                        }
                    }
                }
                else
                {
                    criticaldep3.setText("0.0");
                    AddOnTotal();
                }
            }
        });
        dep4.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(dep4.isChecked())
                {
                    if(chield2_dob.length()<10)
                    {
                        Toast.makeText(getApplicationContext(),"Please Enter DOB of Depdent 4",Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        if(spin_crit_ill.getSelectedItem().equals("None"))
                        {
                            Toast.makeText(getApplicationContext(),"Please Select Cover Amount",Toast.LENGTH_SHORT).show();
                            criticaldep4.setText("0.0");

                        }
                        else

                        {
                            getc4critical();
                            AddOnTotal();
                        }
                    }
                }
                else
                {
                    criticaldep4.setText("0.0");
                    AddOnTotal();
                }
            }
        });




        myelderdate.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                if(myelderdate.length()>=10)
                {
                    getage();
                }


            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        mmyspouse_dob.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                if(mmyspouse_dob.length()>=10)
                {
                    getspousedata();

                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        chield1_dob.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                if(chield1_dob.length()>=10)
                {
                    getchield1data();

                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        chield2_dob.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                if(chield1_dob.length()>=10)
                {
                    getchield2data();

                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        chield3_dob.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(chield3_dob.length()>=10)
                {
                    getchield3data();

                }


            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });



        }

    private void requestPermission()
    {

        if (ActivityCompat.shouldShowRequestPermissionRationale(Main2Activity.this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            Toast.makeText(Main2Activity.this, " Please allow this permission in App Settings.", Toast.LENGTH_LONG).show();
        } else {
            ActivityCompat.requestPermissions(Main2Activity.this, new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
        }
    }
    private boolean checkPermission()
    {
        int result = ContextCompat.checkSelfPermission(Main2Activity.this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
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
                    Toast.makeText(Main2Activity.this, "Permission Granted Successfully! ", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(Main2Activity.this, "Permission Denied :( ", Toast.LENGTH_LONG).show();
                }
                break;
        }
    }
    private void getc4critical() {
        String depband=chield3_band.getText().toString();
        String myamount=spin_crit_ill.getSelectedItem().toString();
        Spinner myperiod=(Spinner) findViewById(R.id.period);
        String p1=myperiod.getSelectedItem().toString();
        if(p1.equals("1 Year")) {
            Cursor res = db.getCriticalldata(depband, myamount);
            while (res.moveToNext()) {
                myid = res.getString(0);

            }

            Double bb=Double.parseDouble(myid.toString());
           // criticaldep3.setText( String.format("%.1f",bb));

            criticaldep4.setText(String.format("%.1f",bb));
            db.close();
        }else if(p1.equals("2 Year"))
        {
            EditText age=(EditText)findViewById(R.id.agec3);

            String agedata=age.getText().toString();
            Integer ag=Integer.parseInt(agedata);
            String myband="";
            String myid2="";

            if (ag<=25)
            {
                myband="0-25";

            }
            else if(ag>25 && ag<=35)
            {
                myband="26-35";

            }
            else if(ag>35 && ag<=40)
            {
                myband="36-40";

            }
            else if(ag>40 && ag<=45)
            {
                myband="41-45";

            }
            else if(ag>45 && ag<=50)
            {
                myband="46-50";

            }
            else if(ag>50 && ag<=55)
            {
                myband="51-55";

            }
            else if(ag>55 && ag<=60)
            {
                myband="56-60";

            }
            else if(ag>60 && ag<=65)
            {
                myband="61-65";

            }
            else if (ag > 65 && ag<=70)
            {
                myband="66-70";
            }
            else if (ag > 70 && ag<=75)
            {
                myband="71-75";
            }
            else if (ag > 75 && ag<=80)
            {
                myband="76-80";
            }
            else if (ag > 80 && ag<=85)
            {
                myband="81-85";
            }
            else if (ag > 85 && ag<=90)
            {
                myband="86-90";
            }
            else if (ag > 90 && ag<=95)
            {
                myband="91-95";
            }
            else if (ag > 95)
            {
                myband=">95";
            }
            else if (ag > 65 && ag<=70)
            {
                myband="66-70";
            }
            else if (ag > 70 && ag<=75)
            {
                myband="71-75";
            }
            else if (ag > 75 && ag<=80)
            {
                myband="76-80";
            }
            else if (ag > 80 && ag<=85)
            {
                myband="81-85";
            }
            else if (ag > 85 && ag<=90)
            {
                myband="86-90";
            }
            else if (ag > 90 && ag<=95)
            {
                myband="91-95";
            }
            else if (ag > 95)
            {
                myband=">95";
            }


            Cursor res1=db.getCriticalldata(myband,myamount);
            while (res1.moveToNext())
            {
                myid2=res1.getString(0);

            }
            String ba_sic=myid2.toString();
            Integer ag2=ag+1;
            //  Toast.makeText(getApplicationContext(),""+ag2.toString(),Toast.LENGTH_SHORT).show();
            String myband2="" ;
            String myid3="";

            if (ag2<=25)
            {
                myband2="0-25";

            }
            else if(ag2>25 && ag2<=35)
            {
                myband2="26-35";

            }
            else if(ag2>35 && ag2<=40)
            {
                myband2="36-40";

            }
            else if(ag2>40 && ag2<=40)
            {
                myband2="41-45";

            }
            else if(ag>40 && ag<=50)
            {
                myband2="46-50";

            }
            else if(ag2>50 && ag2<=55)
            {
                myband2="51-55";

            }
            else if(ag2>55 && ag2<=60)
            {
                myband2="56-60";

            }
            else if(ag2>60 && ag2<=65)
            {
                myband2="61-65";

            }
            else if (ag2 > 65 && ag2<=70)
            {
                myband2="66-70";
            }
            else if (ag2 > 70 && ag2<=75)
            {
                myband2="71-75";
            }
            else if (ag2 > 75 && ag2<=80)
            {
                myband2="76-80";
            }
            else if (ag2 > 80 && ag2<=85)
            {
                myband2="81-85";
            }
            else if (ag2 > 85 && ag2<=90)
            {
                myband2="86-90";
            }
            else if (ag2 > 90 && ag2<=95)
            {
                myband2="91-95";
            }
            else if (ag2 > 95)
            {
                myband2=">95";
            }


            //   mcriticself.setText(myid.toString());

            Cursor res2=db.getCriticalldata(myband2,myamount);
            while (res2.moveToNext())
            {
                myid3=res2.getString(0);

            }
            //  Toast.makeText(getApplicationContext(),""+myid2+"  "+myid3,Toast.LENGTH_SHORT).show();
            String ba_sic2=myid3.toString();
            Double a1=Double.parseDouble(ba_sic);
            Double a2=Double.parseDouble(ba_sic2);
            Double a3=a1+a2;

            // Toast.makeText(getApplicationContext(),"a -"+a1.toString()+"  "+a2.toString(),Toast.LENGTH_SHORT).show();
            criticaldep4.setText(String.format("%.1f",a3));
            db.close();

        }else if(p1.equals("3 Year"))
        {
            EditText age=(EditText)findViewById(R.id.agec3);

            String agedata=age.getText().toString();
            Integer ag=Integer.parseInt(agedata);
            String myband="";
            String myid2="";

            if (ag<=25)
            {
                myband="0-25";

            }
            else if(ag>25 && ag<=35)
            {
                myband="26-35";

            }
            else if(ag>35 && ag<=40)
            {
                myband="36-40";

            }
            else if(ag>40 && ag<=45)
            {
                myband="41-45";

            }
            else if(ag>45 && ag<=50)
            {
                myband="46-50";

            }
            else if(ag>50 && ag<=55)
            {
                myband="51-55";

            }
            else if(ag>55 && ag<=60)
            {
                myband="56-60";

            }
            else if(ag>60 && ag<=65)
            {
                myband="61-65";

            }
            else if (ag > 65 && ag<=70)
            {
                myband="66-70";
            }
            else if (ag > 70 && ag<=75)
            {
                myband="71-75";
            }
            else if (ag > 75 && ag<=80)
            {
                myband="76-80";
            }
            else if (ag > 80 && ag<=85)
            {
                myband="81-85";
            }
            else if (ag > 85 && ag<=90)
            {
                myband="86-90";
            }
            else if (ag > 90 && ag<=95)
            {
                myband="91-95";
            }
            else if (ag > 95)
            {
                myband=">95";
            }


            Cursor res1=db.getCriticalldata(myband,myamount);
            while (res1.moveToNext())
            {
                myid2=res1.getString(0);

            }
            String ba_sic=myid2.toString();
            Integer ag2=ag+1;
            //  Toast.makeText(getApplicationContext(),""+ag2.toString(),Toast.LENGTH_SHORT).show();
            String myband2="" ;
            String myid3="";

            if (ag2<=25)
            {
                myband2="0-25";

            }
            else if(ag2>25 && ag2<=35)
            {
                myband2="26-35";

            }
            else if(ag2>35 && ag2<=40)
            {
                myband2="36-40";

            }
            else if(ag2>40 && ag2<=40)
            {
                myband2="41-45";

            }
            else if(ag2>40 && ag2<=50)
            {
                myband2="46-50";

            }
            else if(ag2>50 && ag2<=55)
            {
                myband2="51-55";

            }
            else if(ag2>55 && ag2<=60)
            {
                myband2="56-60";

            }
            else if(ag2>60 && ag2<=65)
            {
                myband2="61-65";

            }
            else if (ag2 > 65 && ag2<=70)
            {
                myband2="66-70";
            }
            else if (ag2 > 70 && ag2<=75)
            {
                myband2="71-75";
            }
            else if (ag2 > 75 && ag2<=80)
            {
                myband2="76-80";
            }
            else if (ag2 > 80 && ag2<=85)
            {
                myband2="81-85";
            }
            else if (ag2 > 85 && ag2<=90)
            {
                myband2="86-90";
            }
            else if (ag2 > 90 && ag2<=95)
            {
                myband2="91-95";
            }
            else if (ag2 > 95)
            {
                myband2=">95";
            }


            Cursor res2=db.getCriticalldata(myband2,myamount);
            while (res2.moveToNext())
            {
                myid3=res2.getString(0);

            }
            //  Toast.makeText(getApplicationContext(),""+myid2+"  "+myid3,Toast.LENGTH_SHORT).show();
            String ba_sic2=myid3.toString();

            Integer ag3=ag2+1;
            //  Toast.makeText(getApplicationContext(),""+ag2.toString(),Toast.LENGTH_SHORT).show();
            String myband3="";
            String myid="";

            if (ag3<=25)
            {
                myband3="0-25";

            }
            else if(ag3>25 && ag3<=35)
            {
                myband3="26-35";

            }
            else if(ag3>35 && ag3<=40)
            {
                myband3="36-40";

            }
            else if(ag3>40 && ag3<=40)
            {
                myband3="41-45";

            }
            else if(ag3>40 && ag3<=50)
            {
                myband3="46-50";

            }
            else if(ag3>50 && ag3<=55)
            {
                myband3="51-55";

            }
            else if(ag3>55 && ag3<=60)
            {
                myband3="56-60";

            }
            else if(ag3>60 && ag3<=65)
            {
                myband3="61-65";

            }
            else if (ag3 > 65 && ag3<=70)
            {
                myband3="66-70";
            }
            else if (ag3 > 70 && ag3<=75)
            {
                myband3="71-75";
            }
            else if (ag3 > 75 && ag3<=80)
            {
                myband3="76-80";
            }
            else if (ag3 > 80 && ag3<=85)
            {
                myband3="81-85";
            }
            else if (ag3> 85 && ag3<=90)
            {
                myband3="86-90";
            }
            else if (ag3 > 90 && ag3<=95)
            {
                myband3="91-95";
            }
            else if (ag3 > 95)
            {
                myband3=">95";
            }


            Cursor res=db.getCriticalldata(myband3,myamount);
            while (res.moveToNext())
            {
                myid=res.getString(0);

            }
            //  Toast.makeText(getApplicationContext(),""+myid2+"  "+myid3,Toast.LENGTH_SHORT).show();
            String ba_sic3=myid.toString();

            Double r1=Double.parseDouble(ba_sic3);
            Double a1=Double.parseDouble(ba_sic);
            Double a2=Double.parseDouble(ba_sic2);
            Double a3=a1+a2+r1;

            // Toast.makeText(getApplicationContext(),"a -"+a1.toString()+"  "+a2.toString(),Toast.LENGTH_SHORT).show();
            criticaldep4.setText(String.format("%.1f",a3));
            db.close();
        }



    }

    private void getc3critical() {

        String depband=chield2_band.getText().toString();
        String myamount=spin_crit_ill.getSelectedItem().toString();
        Spinner myperiod=(Spinner) findViewById(R.id.period);
        String p1=myperiod.getSelectedItem().toString();
        if(p1.equals("1 Year"))
        {
                Cursor res = db.getCriticalldata(depband, myamount);
                while (res.moveToNext())
                {
                myid = res.getString(0);
                }

                Double bb=Double.parseDouble(myid.toString());
                criticaldep3.setText( String.format("%.1f",bb));
                db.close();
        }else if(p1.equals("2 Year"))
        {
            EditText age=(EditText)findViewById(R.id.agec2);

            String agedata=age.getText().toString();
            Integer ag=Integer.parseInt(agedata);
            String myband="";
            String myid2="";

            if (ag<=25)
            {
                myband="0-25";

            }
            else if(ag>25 && ag<=35)
            {
                myband="26-35";

            }
            else if(ag>35 && ag<=40)
            {
                myband="36-40";

            }
            else if(ag>40 && ag<=45)
            {
                myband="41-45";

            }
            else if(ag>45 && ag<=50)
            {
                myband="46-50";

            }
            else if(ag>50 && ag<=55)
            {
                myband="51-55";

            }
            else if(ag>55 && ag<=60)
            {
                myband="56-60";

            }
            else if(ag>60 && ag<=65)
            {
                myband="61-65";

            }
            else if (ag > 65 && ag<=70)
            {
                myband="66-70";
            }
            else if (ag > 70 && ag<=75)
            {
                myband="71-75";
            }
            else if (ag > 75 && ag<=80)
            {
                myband="76-80";
            }
            else if (ag > 80 && ag<=85)
            {
                myband="81-85";
            }
            else if (ag > 85 && ag<=90)
            {
                myband="86-90";
            }
            else if (ag > 90 && ag<=95)
            {
                myband="91-95";
            }
            else if (ag > 95)
            {
                myband=">95";
            }


            Cursor res1=db.getCriticalldata(myband,myamount);
            while (res1.moveToNext())
            {
                myid2=res1.getString(0);

            }
            String ba_sic=myid2.toString();
            Integer ag2=ag+1;
            //  Toast.makeText(getApplicationContext(),""+ag2.toString(),Toast.LENGTH_SHORT).show();
            String myband2="" ;
            String myid3="";

            if (ag2<=25)
            {
                myband2="0-25";

            }
            else if(ag2>25 && ag2<=35)
            {
                myband2="26-35";

            }
            else if(ag2>35 && ag2<=40)
            {
                myband2="36-40";

            }
            else if(ag2>40 && ag2<=40)
            {
                myband2="41-45";

            }
            else if(ag>40 && ag<=50)
            {
                myband2="46-50";

            }
            else if(ag2>50 && ag2<=55)
            {
                myband2="51-55";

            }
            else if(ag2>55 && ag2<=60)
            {
                myband2="56-60";

            }
            else if(ag2>60 && ag2<=65)
            {
                myband2="61-65";

            }
            else if (ag2 > 65 && ag2<=70)
            {
                myband2="66-70";
            }
            else if (ag2 > 70 && ag2<=75)
            {
                myband2="71-75";
            }
            else if (ag2 > 75 && ag2<=80)
            {
                myband2="76-80";
            }
            else if (ag2 > 80 && ag2<=85)
            {
                myband2="81-85";
            }
            else if (ag2 > 85 && ag2<=90)
            {
                myband2="86-90";
            }
            else if (ag2 > 90 && ag2<=95)
            {
                myband2="91-95";
            }
            else if (ag2 > 95)
            {
                myband2=">95";
            }


            //   mcriticself.setText(myid.toString());

            Cursor res2=db.getCriticalldata(myband2,myamount);
            while (res2.moveToNext())
            {
                myid3=res2.getString(0);

            }
            //  Toast.makeText(getApplicationContext(),""+myid2+"  "+myid3,Toast.LENGTH_SHORT).show();
            String ba_sic2=myid3.toString();
            Double a1=Double.parseDouble(ba_sic);
            Double a2=Double.parseDouble(ba_sic2);
            Double a3=a1+a2;

            // Toast.makeText(getApplicationContext(),"a -"+a1.toString()+"  "+a2.toString(),Toast.LENGTH_SHORT).show();
            criticaldep3.setText(String.format("%.1f",a3));
            db.close();

        }else if(p1.equals("3 Year"))
        {
            EditText age=(EditText)findViewById(R.id.agec2);

            String agedata=age.getText().toString();
            Integer ag=Integer.parseInt(agedata);
            String myband="";
            String myid2="";

            if (ag<=25)
            {
                myband="0-25";

            }
            else if(ag>25 && ag<=35)
            {
                myband="26-35";

            }
            else if(ag>35 && ag<=40)
            {
                myband="36-40";

            }
            else if(ag>40 && ag<=45)
            {
                myband="41-45";

            }
            else if(ag>45 && ag<=50)
            {
                myband="46-50";

            }
            else if(ag>50 && ag<=55)
            {
                myband="51-55";

            }
            else if(ag>55 && ag<=60)
            {
                myband="56-60";

            }
            else if(ag>60 && ag<=65)
            {
                myband="61-65";

            }
            else if (ag > 65 && ag<=70)
            {
                myband="66-70";
            }
            else if (ag > 70 && ag<=75)
            {
                myband="71-75";
            }
            else if (ag > 75 && ag<=80)
            {
                myband="76-80";
            }
            else if (ag > 80 && ag<=85)
            {
                myband="81-85";
            }
            else if (ag > 85 && ag<=90)
            {
                myband="86-90";
            }
            else if (ag > 90 && ag<=95)
            {
                myband="91-95";
            }
            else if (ag > 95)
            {
                myband=">95";
            }


            Cursor res1=db.getCriticalldata(myband,myamount);
            while (res1.moveToNext())
            {
                myid2=res1.getString(0);

            }
            String ba_sic=myid2.toString();
            Integer ag2=ag+1;
            //  Toast.makeText(getApplicationContext(),""+ag2.toString(),Toast.LENGTH_SHORT).show();
            String myband2="" ;
            String myid3="";

            if (ag2<=25)
            {
                myband2="0-25";

            }
            else if(ag2>25 && ag2<=35)
            {
                myband2="26-35";

            }
            else if(ag2>35 && ag2<=40)
            {
                myband2="36-40";

            }
            else if(ag2>40 && ag2<=40)
            {
                myband2="41-45";

            }
            else if(ag2>40 && ag2<=50)
            {
                myband2="46-50";

            }
            else if(ag2>50 && ag2<=55)
            {
                myband2="51-55";

            }
            else if(ag2>55 && ag2<=60)
            {
                myband2="56-60";

            }
            else if(ag2>60 && ag2<=65)
            {
                myband2="61-65";

            }
            else if (ag2 > 65 && ag2<=70)
            {
                myband2="66-70";
            }
            else if (ag2 > 70 && ag2<=75)
            {
                myband2="71-75";
            }
            else if (ag2 > 75 && ag2<=80)
            {
                myband2="76-80";
            }
            else if (ag2 > 80 && ag2<=85)
            {
                myband2="81-85";
            }
            else if (ag2 > 85 && ag2<=90)
            {
                myband2="86-90";
            }
            else if (ag2 > 90 && ag2<=95)
            {
                myband2="91-95";
            }
            else if (ag2 > 95)
            {
                myband2=">95";
            }


            Cursor res2=db.getCriticalldata(myband2,myamount);
            while (res2.moveToNext())
            {
                myid3=res2.getString(0);

            }
            //  Toast.makeText(getApplicationContext(),""+myid2+"  "+myid3,Toast.LENGTH_SHORT).show();
            String ba_sic2=myid3.toString();

            Integer ag3=ag2+1;
            //  Toast.makeText(getApplicationContext(),""+ag2.toString(),Toast.LENGTH_SHORT).show();
            String myband3="";
            String myid="";

            if (ag3<=25)
            {
                myband3="0-25";

            }
            else if(ag3>25 && ag3<=35)
            {
                myband3="26-35";

            }
            else if(ag3>35 && ag3<=40)
            {
                myband3="36-40";

            }
            else if(ag3>40 && ag3<=40)
            {
                myband3="41-45";

            }
            else if(ag3>40 && ag3<=50)
            {
                myband3="46-50";

            }
            else if(ag3>50 && ag3<=55)
            {
                myband3="51-55";

            }
            else if(ag3>55 && ag3<=60)
            {
                myband3="56-60";

            }
            else if(ag3>60 && ag3<=65)
            {
                myband3="61-65";

            }
            else if (ag3 > 65 && ag3<=70)
            {
                myband3="66-70";
            }
            else if (ag3 > 70 && ag3<=75)
            {
                myband3="71-75";
            }
            else if (ag3 > 75 && ag3<=80)
            {
                myband3="76-80";
            }
            else if (ag3 > 80 && ag3<=85)
            {
                myband3="81-85";
            }
            else if (ag3 > 85 && ag3<=90)
            {
                myband3="86-90";
            }
            else if (ag3 > 90 && ag3<=95)
            {
                myband3="91-95";
            }
            else if (ag3 > 95)
            {
                myband3=">95";
            }


            Cursor res=db.getCriticalldata(myband3,myamount);
            while (res.moveToNext())
            {
                myid=res.getString(0);

            }
            //  Toast.makeText(getApplicationContext(),""+myid2+"  "+myid3,Toast.LENGTH_SHORT).show();
            String ba_sic3=myid.toString();

            Double r1=Double.parseDouble(ba_sic3);
            Double a1=Double.parseDouble(ba_sic);
            Double a2=Double.parseDouble(ba_sic2);
            Double a3=a1+a2+r1;

            // Toast.makeText(getApplicationContext(),"a -"+a1.toString()+"  "+a2.toString(),Toast.LENGTH_SHORT).show();
            criticaldep3.setText(String.format("%.1f",a3));
            db.close();
        }





    }


    private void getchield3data() {

        String date1 =mycal.getText().toString();
        String date2=chield3_dob.getText().toString();
        SimpleDateFormat sdf1=new SimpleDateFormat("dd-mm-yyyy");
        try {

            Date parse= sdf1.parse(date1.toString());
            Date parse2= sdf1.parse(date2.toString());
            long diff=parse.getTime()- parse2.getTime();
            long myage=diff/(24*60*60*1000);
            long ag=myage/365;
           // ag=ag-1;
            String gg=Long.toString(ag);
            chield3_age.setText(gg);
            String t=chield3_age.getText().toString();
            if(t.equals("-1"))
            {
                chield3_age.setText("0");
            }



            if (ag<=25)
            {
                chield3_band.setText("0-25");
            }
            else if(ag>25 && ag<=35)
            {
                chield3_band.setText("26-35");
            }
            else if(ag>35 && ag<=40)
            {
                chield3_band.setText("36-40");
            }
            else if(ag>40 && ag<=45)
            {
                chield3_band.setText("41-45");
            }
            else if(ag>45 && ag<=50)
            {
                chield3_band.setText("46-50");
            }
            else if(ag>50 && ag<=55)
            {
                chield3_band.setText("51-55");
            }
            else if(ag>55 && ag<=60)
            {
                chield3_band.setText("56-60");
            }
            else if(ag>60 && ag<=65)
            {
                chield3_band.setText("61-65");
            }
            else if (ag > 65 && ag<=70)
            {
                chield3_band.setText("66-70");
            }
            else if (ag > 70 && ag<=75)
            {
                chield3_band.setText("71-75");
            }
            else if (ag > 75 && ag<=80)
            {
                chield3_band.setText("75-80");
            }
            else if (ag > 80 && ag<=85)
            {
                chield3_band.setText("81-85");
            }
            else if (ag > 85 && ag<=90)
            {
                chield3_band.setText("86-90");
            }
            else if (ag > 90 && ag<=95)
            {
                chield3_band.setText("91-95");
            }
            else if (ag > 95)
            {
                chield3_band.setText(">95");
            }




        } catch (ParseException e) {
            e.printStackTrace();
        }


    }

    private void getchield2data() {
        String date1 =mycal.getText().toString();
        String date2=chield2_dob.getText().toString();
        SimpleDateFormat sdf1=new SimpleDateFormat("dd-mm-yyyy");
        try {

            Date parse= sdf1.parse(date1.toString());
            Date parse2= sdf1.parse(date2.toString());
            long diff=parse.getTime()- parse2.getTime();
            long myage=diff/(24*60*60*1000);
            long ag=myage/365;
           // ag=ag-1;
            String gg=Long.toString(ag);
            chield2_age.setText(gg);

            String t=chield2_age.getText().toString();
            if(t.equals("-1"))
            {
                chield2_age.setText("0");

            }
            if (ag<=25)
            {
                chield2_band.setText("0-25");
            }
            else if(ag>25 && ag<=35)
            {
                chield2_band.setText("26-35");
            }
            else if(ag>35 && ag<=40)
            {
                chield2_band.setText("36-40");
            }
            else if(ag>40 && ag<=45)
            {
                chield2_band.setText("41-45");
            }
            else if(ag>45 && ag<=50)
            {
                chield2_band.setText("46-50");
            }
            else if(ag>50 && ag<=55)
            {
                chield2_band.setText("51-55");
            }
            else if(ag>55 && ag<=60)
            {
                chield2_band.setText("56-60");
            }
            else if(ag>60 && ag<=65)
            {
                chield2_band.setText("61-65");
            }
            else if (ag > 65 && ag<=70)
            {
                chield2_band.setText("66-70");
            }
            else if (ag > 70 && ag<=75)
            {
                chield2_band.setText("71-75");
            }
            else if (ag > 75 && ag<=80)
            {
                chield2_band.setText("75-80");
            }
            else if (ag > 80 && ag<=85)
            {
                chield2_band.setText("81-85");
            }
            else if (ag > 85 && ag<=90)
            {
                chield2_band.setText("86-90");
            }
            else if (ag > 90 && ag<=95)
            {
                chield2_band.setText("91-95");
            }
            else if (ag > 95)
            {
                chield2_band.setText(">95");
            }


        } catch (ParseException e) {
            e.printStackTrace();
        }



    }

    private void getchield1data()
    {

        String date1 =mycal.getText().toString();
        String date2=chield1_dob.getText().toString();
        SimpleDateFormat sdf1=new SimpleDateFormat("dd-mm-yyyy");
        try {

            Date parse= sdf1.parse(date1.toString());
            Date parse2= sdf1.parse(date2.toString());
            long diff=parse.getTime()- parse2.getTime();
            long myage=diff/(24*60*60*1000);
            long ag=myage/365;
            //ag=ag-1;
            String gg=Long.toString(ag);
            chield1_age.setText(gg);
            String t=chield1_age.getText().toString();
            if(t.equals("-1"))
            {
                chield1_age.setText("0");
            }

            if (ag<=25)
            {
                chield1_band.setText("0-25");
            }
            else if(ag>25 && ag<=35)
            {
                chield1_band.setText("26-35");
            }
            else if(ag>35 && ag<=40)
            {
                chield1_band.setText("36-40");
            }
            else if(ag>40 && ag<=45)
            {
                chield1_band.setText("41-45");
            }
            else if(ag>45 && ag<=50)
            {
                chield1_band.setText("46-50");
            }
            else if(ag>50 && ag<=55)
            {
                chield1_band.setText("51-55");
            }
            else if(ag>55 && ag<=60)
            {
                chield1_band.setText("56-60");
            }
            else if(ag>60 && ag<=65)
            {
                chield1_band.setText("61-65");
            }
            else if (ag > 65 && ag<=70)
            {
                chield1_band.setText("66-70");
            }
            else if (ag > 70 && ag<=75)
            {
                chield1_band.setText("71-75");
            }
            else if (ag > 75 && ag<=80)
            {
                chield1_band.setText("75-80");
            }
            else if (ag > 80 && ag<=85)
            {
                chield1_band.setText("81-85");
            }
            else if (ag > 85 && ag<=90)
            {
                chield1_band.setText("86-90");
            }
            else if (ag > 90 && ag<=95)
            {
                chield1_band.setText("91-95");
            }
            else if (ag > 95)
            {
                chield1_band.setText(">95");
            }


        } catch (ParseException e) {
            e.printStackTrace();
        }




    }

    private void getspousedata()
    {
        String date1 =mycal.getText().toString();
        String date2=mmyspouse_dob.getText().toString();
        SimpleDateFormat sdf1=new SimpleDateFormat("dd-mm-yyyy");
        try {

            Date parse= sdf1.parse(date1.toString());
            Date parse2= sdf1.parse(date2.toString());
            long diff=parse.getTime()- parse2.getTime();
            long myage=diff/(24*60*60*1000);
            long ag=myage/365;
           // ag=ag-1;
            String gg=Long.toString(ag);
            spouse_age.setText(gg);

            String t=spouse_age.getText().toString();
            if(t.equals("-1"))
            {
                spouse_age.setText("0");

            }
            if (ag<=25)
            {
                spouse_band.setText("0-25");
            }
            else if(ag>25 && ag<=35)
            {
                spouse_band.setText("26-35");
            }
            else if(ag>35 && ag<=40)
            {
                spouse_band.setText("36-40");
            }
            else if(ag>40 && ag<=45)
            {
                spouse_band.setText("41-45");
            }
            else if(ag>45 && ag<=50)
            {
                spouse_band.setText("46-50");
            }
            else if(ag>50 && ag<=55)
            {
                spouse_band.setText("51-55");
            }
            else if(ag>55 && ag<=60)
            {
                spouse_band.setText("56-60");
            }
            else if(ag>60 && ag<=65)
            {
                spouse_band.setText("61-65");
            }
            else if (ag > 65 && ag<=70)
            {
                spouse_band.setText("66-70");
            }
            else if (ag > 70 && ag<=75)
            {
                spouse_band.setText("71-75");
            }
            else if (ag > 75 && ag<=80)
            {
                spouse_band.setText("75-80");
            }
            else if (ag > 80 && ag<=85)
            {
                spouse_band.setText("81-85");
            }
            else if (ag > 85 && ag<=90)
            {
                spouse_band.setText("86-90");
            }
            else if (ag > 90 && ag<=95)
            {
                spouse_band.setText("91-95");
            }
            else if (ag > 95)
            {
                spouse_band.setText(">95");
            }





        } catch (ParseException e) {
            e.printStackTrace();
        }




    }

    public void clickme(View view) {

        expandableLayout1 = (ExpandableRelativeLayout) findViewById(R.id.expandableLayout101);
        expandableLayout1.toggle();

    }

    public void clickme2(View view) {
        expandableLayout2 = (ExpandableRelativeLayout) findViewById(R.id.expandableLayout12);
        expandableLayout2.toggle();

    }

    public void myclick(View view) {
        final Calendar c = Calendar.getInstance();
        int myear = c.get(Calendar.YEAR);
        int mmonth = c.get(Calendar.MONTH);
        int mdate = c.get(Calendar.DAY_OF_MONTH);
        datepicker = new DatePickerDialog(Main2Activity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                int mymonth=month+1;
                String formonth=""+mymonth;
                String forday=""+dayOfMonth;
                if(month<10)
                {
                    formonth="0"+mymonth;
                }
                if(dayOfMonth<10)
                {
                    forday="0"+dayOfMonth;
                }


                myelderdate.setText(forday + "-" + formonth  + "-" + year);


            }
        },
                myear, mmonth, mdate);
        datepicker.show();



    }

    public void elderdate(View view) {
        final Calendar c = Calendar.getInstance();
        int myear = c.get(Calendar.YEAR);
        int mmonth = c.get(Calendar.MONTH);
        int mdate = c.get(Calendar.DAY_OF_MONTH);
        datepicker = new DatePickerDialog(Main2Activity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                int mymonth=month+1;
                String formonth=""+mymonth;
                String forday=""+dayOfMonth;
                if(month<10)
                {
                    formonth="0"+mymonth;
                }
                if(dayOfMonth<10)
                {
                    forday="0"+dayOfMonth;
                }


                mycal.setText(forday + "-" + formonth  + "-" + year);


            }
        },
                myear, mmonth, mdate);
        datepicker.show();



    }






    public void getage() {

        String date1 =mycal.getText().toString();
        String date2=myelderdate.getText().toString();
        SimpleDateFormat sdf1=new SimpleDateFormat("dd-mm-yyyy");
        try {

            Date parse= sdf1.parse(date1.toString());
            Date parse2= sdf1.parse(date2.toString());
            long diff=parse.getTime()- parse2.getTime();
            long myage=diff/(24*60*60*1000);
            long ag=myage/365;
                //ag=ag-1;

                String gg = Long.toString(ag);
                eldage.setText(gg);

            String f=eldage.getText().toString();
            if(f.equals("-1"))
            {
                eldage.setText("0");
            }


                    if (ag <= 25) {
                        myelderband.setText("0-25");
                    } else if (ag > 25 && ag <= 35) {
                        myelderband.setText("26-35");
                    } else if (ag > 35 && ag <= 40) {
                        myelderband.setText("36-40");
                    } else if (ag > 40 && ag <= 45) {
                        myelderband.setText("41-45");
                    } else if (ag > 41 && ag <= 50) {
                        myelderband.setText("46-50");
                    } else if (ag > 50 && ag <= 55) {
                        myelderband.setText("51-55");
                    } else if (ag > 55 && ag <= 60) {
                        myelderband.setText("56-60");
                    } else if (ag > 60 && ag <= 65)
                    {
                        myelderband.setText("61-65");
                    }
                    else if (ag > 65 && ag<=70)
                    {
                        myelderband.setText("66-70");
                    }
                    else if (ag > 70 && ag<=75)
                    {
                        myelderband.setText("71-75");
                    }
                    else if (ag > 75 && ag<=80)
                    {
                        myelderband.setText("75-80");
                    }
                    else if (ag > 80 && ag<=85)
                    {
                        myelderband.setText("81-85");
                    }
                    else if (ag > 85 && ag<=90)
                    {
                        myelderband.setText("86-90");
                    }
                    else if (ag > 90 && ag<=95)
                    {
                        myelderband.setText("91-95");
                    }
                    else if (ag > 95)
                    {
                        myelderband.setText(">95");
                    }

        } catch (ParseException e) {
            e.printStackTrace();
        }

        calculatebasic();
        AddOnTotal();
        FinalTotal();
    }

    private void calculatebasic()
    {

        EditText my_basic = (EditText) findViewById(R.id.mybasic);
        String fd=familydef.getSelectedItem().toString();
        String band=myelderband.getText().toString();
        String plan= Planopted.getSelectedItem().toString();
        Cursor res=db.getdata(band,plan,fd);
        while (res.moveToNext())
        {
            myid=res.getString(0);

        }

        Spinner myzone=(Spinner)findViewById(R.id.Zone);
        EditText age=(EditText)findViewById(R.id.elderage);
        String z1=myzone.getSelectedItem().toString();

        Spinner myperiod=(Spinner) findViewById(R.id.period);
        String p1=myperiod.getSelectedItem().toString();


        if(p1.equals("1 Year")&& z1.equals("Zone 1"))
        {

            Double bb=Double.parseDouble(myid.toString());

            my_basic.setText(String.format("%.1f",bb));
        }
       else if(p1.equals("1 Year")&& z1.equals("Zone 2"))
        {
            Double mmyid=Double.parseDouble(myid.toString());
            Double rr1=Double.parseDouble(myid.toString());
            Double qqq=rr1*10/100;
            Double ppp = mmyid-qqq;

            //p3.setText(String.format("%.1f", bbb));
            my_basic.setText(String.format("%.1f",ppp));
        }
        else if(p1.equals("1 Year")&& z1.equals("Zone 3"))
        {
            Double mmyid=Double.parseDouble(myid.toString());
            Double rr1=Double.parseDouble(myid.toString());
            Double qqq=rr1*15/100;
            Double ppp = mmyid-qqq;

            //p3.setText(String.format("%.1f", bbb));
            my_basic.setText(String.format("%.1f",ppp));

        }
        else if(p1.equals("2 Year")&& z1.equals("Zone 1"))
        {
            String agedata=age.getText().toString();
            Integer ag=Integer.parseInt(agedata);
            String myband="";
            String myid2="";

            if (ag<=25)
            {
                myband="0-25";

            }
            else if(ag>25 && ag<=35)
            {
                myband="26-35";

            }
            else if(ag>35 && ag<=40)
            {
                myband="36-40";

            }
            else if(ag>40 && ag<=45)
            {
                myband="41-45";

            }
            else if(ag>45 && ag<=50)
            {
                myband="46-50";

            }
            else if(ag>50 && ag<=55)
            {
                myband="51-55";

            }
            else if(ag>55 && ag<=60)
            {
                myband="56-60";

            }
            else if(ag>60 && ag<=65)
            {
                myband="61-65";

            }
            else if (ag > 65 && ag<=70)
            {
                myband="66-70";
            }
            else if (ag > 70 && ag<=75)
            {
                myband="71-75";
            }
            else if (ag > 75 && ag<=80)
            {
                myband="76-80";
            }
            else if (ag > 80 && ag<=85)
            {
                myband="81-85";
            }
            else if (ag > 85 && ag<=90)
            {
                myband="86-90";
            }
            else if (ag > 90 && ag<=95)
            {
                myband="91-95";
            }
            else if (ag > 95)
            {
                myband=">95";
            }

            Cursor res1=db.getdata(myband,plan,fd);
            while (res1.moveToNext())
            {
                myid2=res1.getString(0);

            }





            String ba_sic=myid2.toString();
            Integer ag2=ag+1;
          //  Toast.makeText(getApplicationContext(),""+ag2.toString(),Toast.LENGTH_SHORT).show();
            String myband2="" ;
           String myid3="";

            if (ag2<=25)
            {
                myband2="0-25";

            }
            else if(ag2>25 && ag2<=35)
            {
                myband2="26-35";

            }
            else if(ag2>35 && ag2<=40)
            {
                myband2="36-40";

            }
            else if(ag2>40 && ag2<=45)
            {
                myband2="41-45";

            }
            else if(ag2>45 && ag2<=50)
            {
                myband2="46-50";

            }
            else if(ag2>50 && ag2<=55)
            {
                myband2="51-55";

            }
            else if(ag2>55 && ag2<=60)
            {
                myband2="56-60";

            }
            else if(ag2>60 && ag2<=65)
            {
                myband2="61-65";

            }
            else if (ag2 > 65 && ag2<=70)
            {
                myband2="66-70";
            }
            else if (ag2 > 70 && ag2<=75)
            {
                myband2="71-75";
            }
            else if (ag2 > 75 && ag2<=80)
            {
                myband2="76-80";
            }
            else if (ag2 > 80 && ag2<=85)
            {
                myband2="81-85";
            }
            else if (ag2 > 85 && ag2<=90)
            {
                myband2="86-90";
            }
            else if (ag2 > 90 && ag2<=95)
            {
                myband2="91-95";
            }
            else if (ag2 > 95)
            {
                myband2=">95";
            }

            Cursor res2=db.getdata(myband2,plan,fd);
            while (res2.moveToNext())
            {
                myid3=res2.getString(0);

            }




            //   Toast.makeText(getApplicationContext(),""+myid2+"  "+myid3,Toast.LENGTH_SHORT).show();
            String ba_sic2=myid3.toString();
            Double a1=Double.parseDouble(ba_sic);
            Double a2=Double.parseDouble(ba_sic2);
            Double a3=a1+a2;
            Double qqq=a3*5/100;
            Double mm=a3-qqq;
            // Toast.makeText(getApplicationContext(),"a -"+a1.toString()+"  "+a2.toString(),Toast.LENGTH_SHORT).show();
            my_basic.setText(String.format("%.1f",mm));

        }
        else if(p1.equals("2 Year")&& z1.equals("Zone 2"))
        {
            String agedata=age.getText().toString();
            Integer ag=Integer.parseInt(agedata);
            String myband="";
            String myid2="";

            if (ag<=25)
            {
                myband="0-25";

            }
            else if(ag>25 && ag<=35)
            {
                myband="26-35";

            }
            else if(ag>35 && ag<=40)
            {
                myband="36-40";

            }
            else if(ag>40 && ag<=45)
            {
                myband="41-45";

            }
            else if(ag>45 && ag<=50)
            {
                myband="46-50";

            }
            else if(ag>50 && ag<=55)
            {
                myband="51-55";

            }
            else if(ag>55 && ag<=60)
            {
                myband="56-60";

            }
            else if(ag>60 && ag<=65)
            {
                myband="61-65";

            }
            else if (ag > 65 && ag<=70)
            {
                myband="66-70";
            }
            else if (ag > 70 && ag<=75)
            {
                myband="71-75";
            }
            else if (ag > 75 && ag<=80)
            {
                myband="76-80";
            }
            else if (ag > 80 && ag<=85)
            {
                myband="81-85";
            }
            else if (ag > 85 && ag<=90)
            {
                myband="86-90";
            }
            else if (ag > 90 && ag<=95)
            {
                myband="91-95";
            }
            else if (ag > 95)
            {
                myband=">95";
            }

            Cursor res1=db.getdata(myband,plan,fd);
            while (res1.moveToNext())
            {
                myid2=res1.getString(0);

            }
            String ba_sic=myid2.toString();
            Integer ag2=ag+1;
            //Toast.makeText(getApplicationContext(),""+ag2.toString(),Toast.LENGTH_SHORT).show();

            String myband2="" ;
            String myid3="";

            if (ag2<=25)
            {
                myband2="0-25";

            }
            else if(ag2>25 && ag2<=35)
            {
                myband2="26-35";

            }
            else if(ag2>35 && ag2<=40)
            {
                myband2="36-40";

            }
            else if(ag2>40 && ag2<=45)
            {
                myband2="41-45";

            }
            else if(ag2>45 && ag2<=50)
            {
                myband2="46-50";

            }
            else if(ag2>50 && ag2<=55)
            {
                myband2="51-55";

            }
            else if(ag2>55 && ag2<=60)
            {
                myband2="56-60";

            }
            else if(ag2>60 && ag2<=65)
            {
                myband2="61-65";

            }
            else if (ag2 > 65 && ag2<=70)
            {
                myband2="66-70";
            }
            else if (ag2 > 70 && ag2<=75)
            {
                myband2="71-75";
            }
            else if (ag2 > 75 && ag2<=80)
            {
                myband2="76-80";
            }
            else if (ag2 > 80 && ag2<=85)
            {
                myband2="81-85";
            }
            else if (ag2 > 85 && ag2<=90)
            {
                myband2="86-90";
            }
            else if (ag2 > 90 && ag2<=95)
            {
                myband2="91-95";
            }
            else if (ag2 > 95)
            {
                myband2=">95";
            }


            Cursor res2=db.getdata(myband2,plan,fd);
            while (res2.moveToNext())
            {
                myid3=res2.getString(0);

            }
           // Toast.makeText(getApplicationContext(),""+myid2+"  "+myid3,Toast.LENGTH_SHORT).show();
            String ba_sic2=myid3.toString();
            Double a1=Double.parseDouble(ba_sic);
            Double a2=Double.parseDouble(ba_sic2);
            Double a3=a1+a2;
            Double qqq=a3*5/100;
            Double mm=a3-qqq;
            Double r1=mm*10/100;
            Double r2=mm-r1;

            // Toast.makeText(getApplicationContext(),"a -"+a1.toString()+"  "+a2.toString(),Toast.LENGTH_SHORT).show();

            my_basic.setText(String.format("%.1f",r2));

        db.close();
        }

        else if(p1.equals("2 Year")&& z1.equals("Zone 3"))
        {
            String agedata=age.getText().toString();
            Integer ag=Integer.parseInt(agedata);
            String myband="";
            String myid2="";

            if (ag<=25)
            {
                myband="0-25";

            }
            else if(ag>25 && ag<=35)
            {
                myband="26-35";

            }
            else if(ag>35 && ag<=40)
            {
                myband="36-40";

            }
            else if(ag>40 && ag<=45)
            {
                myband="41-45";

            }
            else if(ag>45 && ag<=50)
            {
                myband="46-50";

            }
            else if(ag>50 && ag<=55)
            {
                myband="51-55";

            }
            else if(ag>55 && ag<=60)
            {
                myband="56-60";

            }
            else if(ag>60 && ag<=65)
            {
                myband="61-65";

            }
            else if (ag > 65 && ag<=70)
            {
                myband="66-70";
            }
            else if (ag > 70 && ag<=75)
            {
                myband="71-75";
            }
            else if (ag > 75 && ag<=80)
            {
                myband="76-80";
            }
            else if (ag > 80 && ag<=85)
            {
                myband="81-85";
            }
            else if (ag > 85 && ag<=90)
            {
                myband="86-90";
            }
            else if (ag > 90 && ag<=95)
            {
                myband="91-95";
            }
            else if (ag > 95)
            {
                myband=">95";
            }


            Cursor res1=db.getdata(myband,plan,fd);
            while (res1.moveToNext())
            {
                myid2=res1.getString(0);

            }
            String ba_sic=myid2.toString();
            Integer ag2=ag+1;
           // Toast.makeText(getApplicationContext(),""+ag2.toString(),Toast.LENGTH_SHORT).show();
            String myband2="" ;
            String myid3="";

            if (ag2<=25)
            {
                myband2="0-25";

            }
            else if(ag2>25 && ag2<=35)
            {
                myband2="26-35";

            }
            else if(ag2>35 && ag2<=40)
            {
                myband2="36-40";

            }
            else if(ag2>40 && ag2<=45)
            {
                myband2="41-45";

            }
            else if(ag2>45 && ag2<=50)
            {
                myband2="46-50";

            }
            else if(ag2>50 && ag2<=55)
            {
                myband2="51-55";

            }
            else if(ag2>55 && ag2<=60)
            {
                myband2="56-60";

            }
            else if(ag2>60 && ag2<=65)
            {
                myband2="61-65";

            }
            else if (ag2 > 65 && ag2<=70)
            {
                myband2="66-70";
            }
            else if (ag2 > 70 && ag2<=75)
            {
                myband2="71-75";
            }
            else if (ag2 > 75 && ag2<=80)
            {
                myband2="76-80";
            }
            else if (ag2 > 80 && ag2<=85)
            {
                myband2="81-85";
            }
            else if (ag2 > 85 && ag2<=90)
            {
                myband2="86-90";
            }
            else if (ag2 > 90 && ag2<=95)
            {
                myband2="91-95";
            }
            else if (ag2 > 95)
            {
                myband2=">95";
            }


            Cursor res2=db.getdata(myband2,plan,fd);
            while (res2.moveToNext())
            {
                myid3=res2.getString(0);

            }

           // Toast.makeText(getApplicationContext(),""+myid2+"  "+myid3,Toast.LENGTH_SHORT).show();
            String ba_sic2=myid3.toString();
            Double a1=Double.parseDouble(ba_sic);
            Double a2=Double.parseDouble(ba_sic2);
            Double a3=a1+a2;
            Double qqq=a3*5/100;
            Double mm=a3-qqq;
            Double r1=mm*15/100;
            Double r2=mm-r1;

            // Toast.makeText(getApplicationContext(),"a -"+a1.toString()+"  "+a2.toString(),Toast.LENGTH_SHORT).show();

            my_basic.setText(String.format("%.1f",r2));

        }

        else if(p1.equals("3 Year")&& z1.equals("Zone 1"))
        {
            String agedata=age.getText().toString();
            Integer ag=Integer.parseInt(agedata);
            String myband="";
            String myid2="";

            if (ag<=25)
            {
                myband="0-25";

            }
            else if(ag>25 && ag<=35)
            {
                myband="26-35";

            }
            else if(ag>35 && ag<=40)
            {
                myband="36-40";

            }
            else if(ag>40 && ag<=45)
            {
                myband="41-45";

            }
            else if(ag>45 && ag<=50)
            {
                myband="46-50";

            }
            else if(ag>51 && ag<=55)
            {
                myband="51-55";

            }
            else if(ag>55 && ag<=60)
            {
                myband="56-60";

            }
            else if(ag>60 && ag<=65)
            {
                myband="61-65";

            }
            else if (ag > 65 && ag<=70)
            {
                myband="66-70";
            }
            else if (ag > 70 && ag<=75)
            {
                myband="71-75";
            }
            else if (ag > 75 && ag<=80)
            {
                myband="76-80";
            }
            else if (ag > 80 && ag<=85)
            {
                myband="81-85";
            }
            else if (ag > 85 && ag<=90)
            {
                myband="86-90";
            }
            else if (ag > 90 && ag<=95)
            {
                myband="91-95";
            }
            else if (ag > 95)
            {
                myband=">95";
            }


            Cursor res1=db.getdata(myband,plan,fd);
            while (res1.moveToNext())
            {
                myid2=res1.getString(0);

            }
            String ba_sic=myid2.toString();
            Integer ag2=ag+1;
            //Toast.makeText(getApplicationContext(),""+ag2.toString(),Toast.LENGTH_SHORT).show();

            String myband2="" ;
            String myid3="";

            if (ag2<=25)
            {
                myband2="0-25";

            }
            else if(ag2>25 && ag2<=35)
            {
                myband2="26-35";

            }
            else if(ag2>35 && ag2<=40)
            {
                myband2="36-40";

            }
            else if(ag2>40 && ag2<=45)
            {
                myband2="41-45";

            }
            else if(ag2>45 && ag2<=50)
            {
                myband2="46-50";

            }
            else if(ag2>50 && ag2<=55)
            {
                myband2="51-55";

            }
            else if(ag2>55 && ag2<=60)
            {
                myband2="56-60";

            }
            else if(ag2>60 && ag2<=65)
            {
                myband2="61-65";

            }
            else if (ag2 > 65 && ag2<=70)
            {
                myband2="66-70";
            }
            else if (ag2 > 70 && ag2<=75)
            {
                myband2="71-75";
            }
            else if (ag2 > 75 && ag2<=80)
            {
                myband2="76-80";
            }
            else if (ag2 > 80 && ag2<=85)
            {
                myband2="81-85";
            }
            else if (ag2 > 85 && ag2<=90)
            {
                myband2="86-90";
            }
            else if (ag2 > 90 && ag2<=95)
            {
                myband2="91-95";
            }
            else if (ag2 > 95)
            {
                myband2=">95";
            }


            Cursor res2=db.getdata(myband2,plan,fd);
            while (res2.moveToNext())
            {
                myid3=res2.getString(0);

            }
            Integer ag3=ag2+1;
            // Toast.makeText(getApplicationContext(),""+ag2.toString(),Toast.LENGTH_SHORT).show();

            String myband4="" ;
            String myid4="";

            if (ag3<=25)
            {
                myband4="0-25";

            }
            else if(ag3>25 && ag3<=35)
            {
                myband4="26-35";

            }
            else if(ag3>35 && ag3<=40)
            {
                myband4="36-40";

            }
            else if(ag3>40 && ag3<=45)
            {
                myband4="41-45";

            }
            else if(ag3>45 && ag3<=50)
            {
                myband4="46-50";

            }
            else if(ag3>50 && ag3<=55)
            {
                myband4="51-55";

            }
            else if(ag3>55 && ag3<=60)
            {
                myband4="56-60";

            }
            else if(ag3>60 && ag3<=65)
            {
                myband4="61-65";

            }
            else if (ag3 > 65 && ag3<=70)
            {
                myband4="66-70";
            }
            else if (ag3 > 70 && ag3<=75)
            {
                myband4="71-75";
            }
            else if (ag3> 75 && ag3<=80)
            {
                myband4="76-80";
            }
            else if (ag3 > 80 && ag3<=85)
            {
                myband4="81-85";
            }
            else if (ag3 > 85 && ag3<=90)
            {
                myband4="86-90";
            }
            else if (ag3 > 90 && ag3<=95)
            {
                myband4="91-95";
            }
            else if (ag3 > 95)
            {
                myband4=">95";
            }


            Cursor res4=db.getdata(myband4,plan,fd);
            while (res4.moveToNext())
            {
                myid4=res4.getString(0);

            }
                String ba_sic3=myid4.toString();

          //  Toast.makeText(getApplicationContext(),""+myid2+"  "+myid3+" "+myid4,Toast.LENGTH_SHORT).show();

            String ba_sic2=myid3.toString();
            Double a1=Double.parseDouble(ba_sic);
            Double a2=Double.parseDouble(ba_sic2);
            Double a3=Double.parseDouble(ba_sic3);
            Double a4=a1+a2+a3;
            Double qqq=a4*10/100;
            Double mm=a4-qqq;
            my_basic.setText(String.format("%.1f",mm));

            }
        else if(p1.equals("3 Year")&& z1.equals("Zone 2"))
        {
            String agedata=age.getText().toString();
            Integer ag=Integer.parseInt(agedata);
            String myband="";
            String myid2="";

            if (ag<=25)
            {
                myband="0-25";

            }
            else if(ag>25 && ag<=35)
            {
                myband="26-35";

            }
            else if(ag>35 && ag<=40)
            {
                myband="36-40";

            }
            else if(ag>40 && ag<=45)
            {
                myband="41-45";

            }
            else if(ag>45 && ag<=50)
            {
                myband="46-50";

            }
            else if(ag>50 && ag<=55)
            {
                myband="51-55";

            }
            else if(ag>55 && ag<=60)
            {
                myband="56-60";

            }
            else if(ag>60 && ag<=65)
            {
                myband="61-65";

            }
            else if (ag > 65 && ag<=70)
            {
                myband="66-70";
            }
            else if (ag > 70 && ag<=75)
            {
                myband="71-75";
            }
            else if (ag > 75 && ag<=80)
            {
                myband="76-80";
            }
            else if (ag > 80 && ag<=85)
            {
                myband="81-85";
            }
            else if (ag > 85 && ag<=90)
            {
                myband="86-90";
            }
            else if (ag > 90 && ag<=95)
            {
                myband="91-95";
            }
            else if (ag > 95)
            {
                myband=">95";
            }


            Cursor res1=db.getdata(myband,plan,fd);
            while (res1.moveToNext())
            {
                myid2=res1.getString(0);

            }
            String ba_sic=myid2.toString();
            Integer ag2=ag+1;
            //Toast.makeText(getApplicationContext(),""+ag2.toString(),Toast.LENGTH_SHORT).show();

            String myband2="" ;
            String myid3="";

            if (ag2<=25)
            {
                myband2="0-25";

            }
            else if(ag2>25 && ag2<=35)
            {
                myband2="26-35";

            }
            else if(ag2>35 && ag2<=40)
            {
                myband2="36-40";

            }
            else if(ag2>40 && ag2<=45)
            {
                myband2="41-45";

            }
            else if(ag2>45 && ag2<=50)
            {
                myband2="46-50";

            }
            else if(ag2>50 && ag2<=55)
            {
                myband2="51-55";

            }
            else if(ag2>55 && ag2<=60)
            {
                myband2="56-60";

            }
            else if(ag2>60 && ag2<=65)
            {
                myband2="61-65";

            }
            else if (ag2 > 65 && ag2<=70)
            {
                myband2="66-70";
            }
            else if (ag2 > 70 && ag2<=75)
            {
                myband2="71-75";
            }
            else if (ag2 > 75 && ag2<=80)
            {
                myband2="76-80";
            }
            else if (ag2 > 80 && ag2<=85)
            {
                myband2="81-85";
            }
            else if (ag2 > 85 && ag2<=90)
            {
                myband2="86-90";
            }
            else if (ag2 > 90 && ag2<=95)
            {
                myband2="91-95";
            }
            else if (ag2 > 95)
            {
                myband2=">95";
            }


            Cursor res2=db.getdata(myband2,plan,fd);
            while (res2.moveToNext())
            {
                myid3=res2.getString(0);

            }

            Integer ag3=ag2+1;
            //Toast.makeText(getApplicationContext(),""+ag2.toString(),Toast.LENGTH_SHORT).show();

            String myband4="" ;
            String myid4="";

            if (ag3<=25)
            {
                myband4="0-25";

            }
            else if(ag3>25 && ag3<=35)
            {
                myband4="26-35";

            }
            else if(ag3>35 && ag3<=40)
            {
                myband4="36-40";

            }
            else if(ag3>40 && ag3<=45)
            {
                myband4="41-45";

            }
            else if(ag3>45 && ag3<=50)
            {
                myband4="46-50";

            }
            else if(ag3>50 && ag3<=55)
            {
                myband4="51-55";

            }
            else if(ag3>55 && ag3<=60)
            {
                myband4="56-60";

            }
            else if(ag3>60 && ag3<=65)
            {
                myband4="61-65";

            }
            else if (ag3 > 65 && ag3<=70)
            {
                myband4="66-70";
            }
            else if (ag3 > 70 && ag3<=75)
            {
                myband4="71-75";
            }
            else if (ag3 > 75 && ag3<=80)
            {
                myband4="76-80";
            }
            else if (ag3 > 80 && ag3<=85)
            {
                myband4="81-85";
            }
            else if (ag3 > 85 && ag3<=90)
            {
                myband4="86-90";
            }
            else if (ag3 > 90 && ag3<=95)
            {
                myband4="91-95";
            }
            else if (ag3 > 95)
            {
                myband4=">95";
            }


            Cursor res4=db.getdata(myband4,plan,fd);
            while (res4.moveToNext())
            {
                myid4=res4.getString(0);

            }
            String ba_sic3=myid4.toString();

          //  Toast.makeText(getApplicationContext(),""+myid2+"  "+myid3+" "+myid4,Toast.LENGTH_SHORT).show();

            String ba_sic2=myid3.toString();
            Double a1=Double.parseDouble(ba_sic);
            Double a2=Double.parseDouble(ba_sic2);
            Double a3=Double.parseDouble(ba_sic3);
            Double a4=a1+a2+a3;
            Double qqq=a4*10/100;
            Double mm=a4-qqq;

            Double o1=mm*10/100;
            Double p12=mm-o1;

            my_basic.setText(String.format("%.1f",p12));

        }
        else if(p1.equals("3 Year")&& z1.equals("Zone 3"))
        {

            String agedata=age.getText().toString();
            Integer ag=Integer.parseInt(agedata);
            String myband="";
            String myid2="";

            if (ag<=25)
            {
                myband="0-25";

            }
            else if(ag>25 && ag<=35)
            {
                myband="26-35";

            }
            else if(ag>35 && ag<=40)
            {
                myband="36-40";

            }
            else if(ag>40 && ag<=45)
            {
                myband="41-45";

            }
            else if(ag>45 && ag<=50)
            {
                myband="46-50";

            }
            else if(ag>50 && ag<=55)
            {
                myband="51-55";

            }
            else if(ag>55 && ag<=60)
            {
                myband="56-60";

            }
            else if(ag>60 && ag<=65)
            {
                myband="61-65";

            }
            else if (ag > 65 && ag<=70)
            {
                myband="66-70";
            }
            else if (ag > 70 && ag<=75)
            {
                myband="71-75";
            }
            else if (ag > 75 && ag<=80)
            {
                myband="76-80";
            }
            else if (ag > 80 && ag<=85)
            {
                myband="81-85";
            }
            else if (ag > 85 && ag<=90)
            {
                myband="86-90";
            }
            else if (ag > 90 && ag<=95)
            {
                myband="91-95";
            }
            else if (ag > 95)
            {
                myband=">95";
            }

            Cursor res1=db.getdata(myband,plan,fd);
            while (res1.moveToNext())
            {
                myid2=res1.getString(0);

            }

            String ba_sic=myid2.toString();

            Integer ag2=ag+1;
            //   Toast.makeText(getApplicationContext(),""+ag2.toString(),Toast.LENGTH_SHORT).show();

            String myband2="" ;
            String myid3="";

            if (ag2<=25)
            {
                myband2="0-25";

            }
            else if(ag2>25 && ag2<=35)
            {
                myband2="26-35";

            }
            else if(ag2>35 && ag2<=40)
            {
                myband2="36-40";

            }
            else if(ag2>40 && ag2<=45)
            {
                myband2="41-45";

            }
            else if(ag2>45 && ag2<=50)
            {
                myband2="46-50";

            }
            else if(ag2>50 && ag2<=55)
            {
                myband2="51-55";

            }
            else if(ag2>55 && ag2<=60)
            {
                myband2="56-60";

            }
            else if(ag2>60 && ag2<=65)
            {
                myband2="61-65";

            }
            else if (ag2 > 65 && ag2<=70)
            {
                myband2="66-70";
            }
            else if (ag2 > 70 && ag2<=75)
            {
                myband2="71-75";
            }
            else if (ag2 > 75 && ag2<=80)
            {
                myband2="76-80";
            }
            else if (ag2 > 80 && ag2<=85)
            {
                myband2="81-85";
            }
            else if (ag2 > 85 && ag2<=90)
            {
                myband2="86-90";
            }
            else if (ag2 > 90 && ag2<=95)
            {
                myband2="91-95";
            }
            else if (ag2 > 95)
            {
                myband2=">95";
            }


            Cursor res2=db.getdata(myband2,plan,fd);
            while (res2.moveToNext())
            {
                myid3=res2.getString(0);

            }

            Integer ag3=ag2+1;


           // Toast.makeText(getApplicationContext(),""+ag2.toString(),Toast.LENGTH_SHORT).show();

            String myband4="" ;
            String myid4="";

            if (ag3<=25)
            {
                myband4="0-25";

            }
            else if(ag3>25 && ag3<=35)
            {
                myband4="26-35";

            }
            else if(ag3>35 && ag3<=40)
            {
                myband4="36-40";

            }
            else if(ag3>40 && ag3<=45)
            {
                myband4="41-45";

            }
            else if(ag3>45 && ag3<=50)
            {
                myband4="46-50";

            }
            else if(ag3>50 && ag3<=55)
            {
                myband4="51-55";

            }
            else if(ag3>55 && ag3<=60)
            {
                myband4="56-60";

            }
            else if(ag3>60 && ag3<=65)
            {
                myband4="61-65";

            }
            else if (ag3 > 65 && ag3<=70)
            {
                myband4="66-70";
            }
            else if (ag3 > 70 && ag3<=75)
            {
                myband4="71-75";
            }
            else if (ag3> 75 && ag3<=80)
            {
                myband4="76-80";
            }
            else if (ag3 > 80 && ag3<=85)
            {
                myband4="81-85";
            }
            else if (ag3 > 85 && ag3<=90)
            {
                myband4="86-90";
            }
            else if (ag3 > 90 && ag3<=95)
            {
                myband4="91-95";
            }
            else if (ag3 > 95)
            {
                myband4=">95";
            }

            Cursor res4=db.getdata(myband4,plan,fd);
            while (res4.moveToNext())
            {
                myid4=res4.getString(0);

            }
            String ba_sic3=myid4.toString();

           // Toast.makeText(getApplicationContext(),""+myid2+"  "+myid3+" "+myid4,Toast.LENGTH_SHORT).show();

            String ba_sic2=myid3.toString();
            Double a1=Double.parseDouble(ba_sic);
            Double a2=Double.parseDouble(ba_sic2);
            Double a3=Double.parseDouble(ba_sic3);
            Double a4=a1+a2+a3;
            Double qqq=a4*15/100;
            Double mm=a4-qqq;

            Double o1=mm*10/100;
            Double p12=mm-o1;

            my_basic.setText(String.format("%.1f",p12));
            }

        db.close();
    }

    public void DobSpouse(View view)
    {
        final Calendar c = Calendar.getInstance();
        int myear = c.get(Calendar.YEAR);
        int mmonth = c.get(Calendar.MONTH);
        int mdate = c.get(Calendar.DAY_OF_MONTH);
        datepicker = new DatePickerDialog(Main2Activity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                int mymonth=month+1;
                String formonth=""+mymonth;
                String forday=""+dayOfMonth;
                if(month<10)
                {
                    formonth="0"+mymonth;
                }
                if(dayOfMonth<10)
                {
                    forday="0"+dayOfMonth;
                }


                mmyspouse_dob.setText(forday + "-" + formonth  + "-" + year);


            }
        },
                myear, mmonth, mdate);
        datepicker.show();



    }

    public void Dobchield1(View view)
    {
        final Calendar c = Calendar.getInstance();
        int myear = c.get(Calendar.YEAR);
        int mmonth = c.get(Calendar.MONTH);
        int mdate = c.get(Calendar.DAY_OF_MONTH);
        datepicker = new DatePickerDialog(Main2Activity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                int mymonth=month+1;
                String formonth=""+mymonth;
                String forday=""+dayOfMonth;
                if(month<10)
                {
                    formonth="0"+mymonth;
                }
                if(dayOfMonth<10)
                {
                    forday="0"+dayOfMonth;
                }


                chield1_dob.setText(forday + "-" + formonth  + "-" + year);


            }
        },
                myear, mmonth, mdate);
        datepicker.show();



    }

    public void Dobchield2(View view)
    {
        final Calendar c = Calendar.getInstance();
        int myear = c.get(Calendar.YEAR);
        int mmonth = c.get(Calendar.MONTH);
        int mdate = c.get(Calendar.DAY_OF_MONTH);
        datepicker = new DatePickerDialog(Main2Activity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                int mymonth=month+1;
                String formonth=""+mymonth;
                String forday=""+dayOfMonth;
                if(month<10)
                {
                    formonth="0"+mymonth;
                }
                if(dayOfMonth<10)
                {
                    forday="0"+dayOfMonth;
                }


                chield2_dob.setText(forday + "-" + formonth  + "-" + year);


            }
        },
                myear, mmonth, mdate);
        datepicker.show();

        }

    public void Dobchield3(View view)
    {
        final Calendar c = Calendar.getInstance();
        int myear = c.get(Calendar.YEAR);
        int mmonth = c.get(Calendar.MONTH);
        int mdate = c.get(Calendar.DAY_OF_MONTH);
        datepicker = new DatePickerDialog(Main2Activity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                int mymonth=month+1;
                String formonth=""+mymonth;
                String forday=""+dayOfMonth;
                if(month<10)
                {
                    formonth="0"+mymonth;
                }
                if(dayOfMonth<10)
                {
                    forday="0"+dayOfMonth;
                }
                chield3_dob.setText(forday + "-" + formonth  + "-" + year);
                }
        },
                myear, mmonth, mdate);
        datepicker.show();


    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l)
    {
        switch(adapterView.getId())
        {
            case R.id.hospital_cash:
            {

                //String Cash_av[]={"None","500/day","1000/day","200/day","3000/day"};

                item = adapterView.getItemAtPosition(i).toString();
                if(item.equals("None"))
                {

                    cashforhospital.setText("0.0");
                    AddOnTotal();
                }
                else if((item.equals("500"))||(item.equals("1000")))
                {
                    String elderdob=myelderdate.getText().toString();
                    if (elderdob.equals(""))
                    {
                        cashforhospital.setText("0.0");

                        Toast.makeText(getApplicationContext(),"Please Fill Dob of Elder",Toast.LENGTH_SHORT).show();
                    }
                    else
                    {

                        gethospitalcash();
                        AddOnTotal();
                    }
                }
                else if(item.equals("2000"))
                {
                    String elderdob=myelderdate.getText().toString();
                    if (elderdob.equals(""))
                    {
                        cashforhospital.setText("0.0");
                        Toast.makeText(getApplicationContext(),"Please Fill Dob of Elder",Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        //String plan[]={"VALUE 5L","VALUE 7.5L","CLASSIC 10L","CLASSIC 15L","CLASSIC 20L","UBER 20L","UBER 30L","UBER 50L",
                               // "UBER 60L","UBER 70L","UBER 80L","UBER 90L","UBER 1CR"};
                        String plan= Planopted.getSelectedItem().toString();
                        if((plan.equals("CLASSIC 10L")) || (plan.equals("CLASSIC 15L"))|| (plan.equals("CLASSIC 20L"))|| (plan.equals("UBER 20L"))|| (plan.equals("UBER 30L"))|| (plan.equals("UBER 50L"))|| (plan.equals("UBER 60L"))|| (plan.equals("UBER 70L"))|| (plan.equals("UBER 80L"))|| (plan.equals("UBER 90L"))|| (plan.equals("UBER 1CR")) )
                        {
                            gethospitalcash();
                            AddOnTotal();
                        }
                        else
                        {
                            cashforhospital.setText("0.0");
                            Toast.makeText(getApplicationContext(),"Please Select Correct Sum Assured",Toast.LENGTH_SHORT).show();
                        }

                    }
                }
                else if(item.equals("3000"))
                {
                    String elderdob=myelderdate.getText().toString();
                    if (elderdob.equals(""))
                    {
                        cashforhospital.setText("0.0");
                        Toast.makeText(getApplicationContext(),"Please Fill Dob of Elder",Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        //String plan[]={"VALUE 5L","VALUE 7.5L","CLASSIC 10L","CLASSIC 15L","CLASSIC 20L","UBER 20L","UBER 30L","UBER 50L",
                        // "UBER 60L","UBER 70L","UBER 80L","UBER 90L","UBER 1CR"};
                        String plan= Planopted.getSelectedItem().toString();
                        if((plan.equals("UBER 20L"))|| (plan.equals("UBER 30L"))|| (plan.equals("UBER 50L"))|| (plan.equals("UBER 60L"))|| (plan.equals("UBER 70L"))|| (plan.equals("UBER 80L"))|| (plan.equals("UBER 90L"))|| (plan.equals("UBER 1CR")) )
                        {
                            gethospitalcash();
                            AddOnTotal();
                        }
                        else
                        {

                            cashforhospital.setText("0.0");
                          Toast.makeText(getApplicationContext(),"Please Select Correct Sum Assured",Toast.LENGTH_SHORT).show();
                        }

                    }
                }

                break;
            }
            case R.id.criticalill:
            {
                item = adapterView.getItemAtPosition(i).toString();
                if(item.equals("None"))
                {
                    mcriticself.setText("0.0");
                    
                    AddOnTotal();

                }

                else if(item.equals("200000")||item.equals("300000")||item.equals("500000"))
                {
                    String elderdob=myelderdate.getText().toString();
                    if (elderdob.equals(""))
                    {
                        Toast.makeText(getApplicationContext(),"Please Fill Dob of Elder",Toast.LENGTH_SHORT).show();
                    }
                    else {
                        //String plan[]={"VALUE 5L","VALUE 7.5L","CLASSIC 10L","CLASSIC 15L","CLASSIC 20L","UBER 20L","UBER 30L","UBER 50L",
                        // "UBER 60L","UBER 70L","UBER 80L","UBER 90L","UBER 1CR"};

                        getcritical();
                        AddOnTotal();
                    }

                }
                else if(item.equals("750000"))

                {
                        String elderdob=myelderdate.getText().toString();
                        if (elderdob.equals(""))
                            {
                                Toast.makeText(getApplicationContext(),"Please Fill Dob of Elder",Toast.LENGTH_SHORT).show();
                            }
                         else
                            {
                                 //String plan[]={"VALUE 5L","VALUE 7.5L","CLASSIC 10L","CLASSIC 15L","CLASSIC 20L","UBER 20L","UBER 30L","UBER 50L",
                                    // "UBER 60L","UBER 70L","UBER 80L","UBER 90L","UBER 1CR"};
                                    String plan= Planopted.getSelectedItem().toString();
                                if((plan.equals("VALUE 7.5L")) ||(plan.equals("CLASSIC 10L")) || (plan.equals("CLASSIC 15L"))|| (plan.equals("CLASSIC 20L"))|| (plan.equals("UBER 20L"))|| (plan.equals("UBER 30L"))|| (plan.equals("UBER 50L"))|| (plan.equals("UBER 60L"))|| (plan.equals("UBER 70L"))|| (plan.equals("UBER 80L"))|| (plan.equals("UBER 90L"))|| (plan.equals("UBER 1CR")) )
                                    {
                                     getcritical();
                                     AddOnTotal();
                                     }
                                    else
                                    {

                                    Toast.makeText(getApplicationContext(),"Please Select Correct Sum Assured",Toast.LENGTH_SHORT).show();
                                    }
                            }
                }
                else if(item.equals("1000000"))

                {
                    String elderdob=myelderdate.getText().toString();
                    if (elderdob.equals(""))
                    {
                        Toast.makeText(getApplicationContext(),"Please Fill Dob of Elder",Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        //String plan[]={"VALUE 5L","VALUE 7.5L","CLASSIC 10L","CLASSIC 15L","CLASSIC 20L","UBER 20L","UBER 30L","UBER 50L",
                        // "UBER 60L","UBER 70L","UBER 80L","UBER 90L","UBER 1CR"};
                        String plan= Planopted.getSelectedItem().toString();
                        if((plan.equals("CLASSIC 10L")) || (plan.equals("CLASSIC 15L"))|| (plan.equals("CLASSIC 20L"))|| (plan.equals("UBER 20L"))|| (plan.equals("UBER 30L"))|| (plan.equals("UBER 50L"))|| (plan.equals("UBER 60L"))|| (plan.equals("UBER 70L"))|| (plan.equals("UBER 80L"))|| (plan.equals("UBER 90L"))|| (plan.equals("UBER 1CR")) )
                        {
                            getcritical();
                            AddOnTotal();
                        }
                        else
                        {

                            Toast.makeText(getApplicationContext(),"Please Select Correct Sum Assured",Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                else if(item.equals("1500000"))

                {
                    String elderdob=myelderdate.getText().toString();
                    if (elderdob.equals(""))
                    {
                        Toast.makeText(getApplicationContext(),"Please Fill Dob of Elder",Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        //String plan[]={"VALUE 5L","VALUE 7.5L","CLASSIC 10L","CLASSIC 15L","CLASSIC 20L","UBER 20L","UBER 30L","UBER 50L",
                        // "UBER 60L","UBER 70L","UBER 80L","UBER 90L","UBER 1CR"};
                        String plan= Planopted.getSelectedItem().toString();
                        String myterm= term.getSelectedItem().toString();
                       // String Term[]={"1 Year","2 Year","3 Year"};

                            if ((plan.equals("CLASSIC 15L")) || (plan.equals("CLASSIC 20L")) || (plan.equals("UBER 20L")) || (plan.equals("UBER 30L")) || (plan.equals("UBER 50L")) || (plan.equals("UBER 60L")) || (plan.equals("UBER 70L")) || (plan.equals("UBER 80L")) || (plan.equals("UBER 90L")) || (plan.equals("UBER 1CR"))) {
                                getcritical();
                                AddOnTotal();
                            } else {

                                Toast.makeText(getApplicationContext(), "Please Select Correct Sum Assured", Toast.LENGTH_SHORT).show();
                            }
                    }
                }
                else if(item.equals("2000000"))

                {
                    String elderdob=myelderdate.getText().toString();
                    if (elderdob.equals(""))
                    {
                        Toast.makeText(getApplicationContext(),"Please Fill Dob of Elder",Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        //String plan[]={"VALUE 5L","VALUE 7.5L","CLASSIC 10L","CLASSIC 15L","CLASSIC 20L","UBER 20L","UBER 30L","UBER 50L",
                        // "UBER 60L","UBER 70L","UBER 80L","UBER 90L","UBER 1CR"};
                        String plan= Planopted.getSelectedItem().toString();
                        String myterm= term.getSelectedItem().toString();
                        // String Term[]={"1 Year","2 Year","3 Year"};

                            if ((plan.equals("CLASSIC 20L")) || (plan.equals("UBER 20L")) || (plan.equals("UBER 30L")) || (plan.equals("UBER 50L")) || (plan.equals("UBER 60L")) || (plan.equals("UBER 70L")) || (plan.equals("UBER 80L")) || (plan.equals("UBER 90L")) || (plan.equals("UBER 1CR"))) {
                                getcritical();
                                AddOnTotal();
                            } else {

                                Toast.makeText(getApplicationContext(), "Please Select Correct Sum Assured", Toast.LENGTH_SHORT).show();
                            }

                    }
                }
                else if(item.equals("2500000"))

                {
                    String elderdob=myelderdate.getText().toString();
                    if (elderdob.equals(""))
                    {
                        Toast.makeText(getApplicationContext(),"Please Fill Dob of Elder",Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        //String plan[]={"VALUE 5L","VALUE 7.5L","CLASSIC 10L","CLASSIC 15L","CLASSIC 20L","UBER 20L","UBER 30L","UBER 50L",
                        // "UBER 60L","UBER 70L","UBER 80L","UBER 90L","UBER 1CR"};
                        String plan= Planopted.getSelectedItem().toString();
                        String myterm= term.getSelectedItem().toString();
                        // String Term[]={"1 Year","2 Year","3 Year"};

                            if ((plan.equals("UBER 30L")) || (plan.equals("UBER 50L")) || (plan.equals("UBER 60L")) || (plan.equals("UBER 70L")) || (plan.equals("UBER 80L")) || (plan.equals("UBER 90L")) || (plan.equals("UBER 1CR"))) {
                                getcritical();
                                AddOnTotal();
                            } else {

                                Toast.makeText(getApplicationContext(), "Please Select Correct Sum Assured", Toast.LENGTH_SHORT).show();
                            }

                        }
                }
                else if(item.equals("3000000"))

                {
                    String elderdob=myelderdate.getText().toString();
                    if (elderdob.equals(""))
                    {
                        Toast.makeText(getApplicationContext(),"Please Fill Dob of Elder",Toast.LENGTH_SHORT).show();
                    }
                    else
                    {

                        String plan= Planopted.getSelectedItem().toString();
                        String myterm= term.getSelectedItem().toString();


                            if ((plan.equals("UBER 30L")) || (plan.equals("UBER 50L")) || (plan.equals("UBER 60L")) || (plan.equals("UBER 70L")) || (plan.equals("UBER 80L")) || (plan.equals("UBER 90L")) || (plan.equals("UBER 1CR"))) {
                                getcritical();
                                AddOnTotal();
                            } else {

                                Toast.makeText(getApplicationContext(), "Please Select Correct Sum Assured", Toast.LENGTH_SHORT).show();
                            }

                        }
                }

                break;
            }
            case R.id.maternity:
            {
               // String mater_nity_av[]={"None","35000","50000","75000","100000"};
                item = adapterView.getItemAtPosition(i).toString();
                String fd=familydef.getSelectedItem().toString();
                String myterm=term.getSelectedItem().toString();
                String mmm=Planopted.getSelectedItem().toString();


                if(item.equals("None"))
                {

                    Mater_Amount.setText("0.0");
                    AddOnTotal();

                }
                else if(item.equals("35000")&&(fd.equals("2A")||fd.equals("2A+1C"))&& myterm.equals("3 Year")&&(mmm.equals("VALUE 5L")|| mmm.equals("VALUE 7.5L")))
                {

                    getMaternity();
                    AddOnTotal();
                }
                else if(item.equals("50000")&&(fd.equals("2A")||fd.equals("2A+1C"))&& myterm.equals("3 Year")&&(mmm.equals("CLASSIC 10L")|| mmm.equals("CLASSIC 15L")|| mmm.equals("CLASSIC 20L")))
                {

                    getMaternity();
                    AddOnTotal();
                }
                else if(item.equals("75000")&&(fd.equals("2A")||fd.equals("2A+1C"))&& myterm.equals("3 Year")&&(mmm.equals("UBER 20L")|| mmm.equals("UBER 30L")))
                {

                    getMaternity();
                    AddOnTotal();
                }
                else if(item.equals("100000")&&(fd.equals("2A")||fd.equals("2A+1C"))&& myterm.equals("3 Year")&&(mmm.equals("UBER 50L")
                        || mmm.equals("UBER 60L")|| mmm.equals("UBER 70L")|| mmm.equals("UBER 80L")|| mmm.equals("UBER 90L")||
                        mmm.equals("UBER 1CR")))
                {

                    getMaternity();
                    AddOnTotal();
                }
                else
                {
                    Mater_Amount.setText("0.0");
                    Toast.makeText(getApplicationContext(), "Please Reset Parameters", Toast.LENGTH_SHORT).show();
                    AddOnTotal();
                }
                break;
            }
            case R.id.Zone:
            {
                item = adapterView.getItemAtPosition(i).toString();


                    if (item.equals("Zone 1"))
                    {
                       getage();

                    } else if (item.equals("Zone 2")) {
                        getage();
                    } else if (item.equals("Zone 3")) {
                        getage();
                    }
                    break;
            }
            case R.id.period:
            {
                item = adapterView.getItemAtPosition(i).toString();


                    if (item.equals("1 Year"))
                    {
                        getage();
                    }
                    else if (item.equals("2 Year")) {
                        getage();
                    } else if (item.equals("3 Year")) {
                        getage();
                    }
                    break;
            }
            case R.id.spneer1:
            {
               // String busi[]={"Individual","Family Floater"};
                item = adapterView.getItemAtPosition(i).toString();


                if(item.equals("Individual"))
                {
                   // String family2[]={"1A","2A","2A+1C","2A+2C","2A+3C","1A+1C","1A+2C","1A+3C"};
                    String family2[]={"1A"};

                    ArrayAdapter<String> myadapter1 =new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,family2);
                    myadapter1.setDropDownViewResource(R.layout.spinner2);
                    familydef.setAdapter(myadapter1);
                    familydef.setOnItemSelectedListener(this);

                    }
                if(item.equals("Family Floater"))
                {
                     String family1[]={"2A","2A+1C","2A+2C","2A+3C","1A+1C","1A+2C","1A+3C"};

                    ArrayAdapter<String> myadapter1 =new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,family1);
                    myadapter1.setDropDownViewResource(R.layout.spinner2);
                    familydef.setAdapter(myadapter1);
                    familydef.setOnItemSelectedListener(this);

                }
                break;

            }
            case R.id.spneer2:
            {
                item = adapterView.getItemAtPosition(i).toString();
                if(item.equals("1A"))
                {
                    getage();
                }
                else if(item.equals("2A"))
                {
                    getage();
                }
                else if(item.equals("2A+1C"))
                {
                    getage();
                }
                else if(item.equals("2A+2C"))
                {
                    getage();
                }
                else if(item.equals("2A+3C"))
                {
                    getage();
                }
                else if(item.equals("1A+1C"))
                {
                    getage();
                }
                else if(item.equals("1A+2C"))
                {
                    getage();
                }
                else if(item.equals("1A+3C"))
                {
                    getage();
                }
             break;
            }
           case R.id.myvalue:
            {
                item = adapterView.getItemAtPosition(i).toString();
                if(item.equals("VALUE 5L"))
                {
                    getage();

                   // String Cash_av[]={"None","500","1000","2000","3000"};
                   // String mater_nity_av[]={"None","35000","50000","75000","100000"};
                     String Cash_av[]={"None","500","1000"};
                     String mater_nity_av[]={"None","35000"};


                    ArrayAdapter<String> myadapter5 =new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,Cash_av);
                    myadapter5.setDropDownViewResource(R.layout.spinner2);
                    spin_cash.setAdapter(myadapter5);
                    spin_cash.setOnItemSelectedListener(this);

                    ArrayAdapter<String> myadapter6 =new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,mater_nity_av);
                    myadapter6.setDropDownViewResource(R.layout.spinner2);
                    spin_maternity.setAdapter(myadapter6);
                    spin_maternity.setOnItemSelectedListener(this);

                    //String crit_ill []={"None","200000","300000","500000","750000","1000000","1500000","2000000","2500000","3000000"};
                    String crit_ill []={"None","200000","300000","500000"};
                    ArrayAdapter<String> myadapter7 =new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,crit_ill);
                    myadapter7.setDropDownViewResource(R.layout.spinner2);
                    spin_crit_ill.setAdapter(myadapter7);
                    spin_crit_ill.setOnItemSelectedListener(this);

                }
                else if(item.equals("VALUE 7.5L"))
                {
                    getage();

                    // String Cash_av[]={"None","500","1000","2000","3000"};
                    // String mater_nity_av[]={"None","35000","50000","75000","100000"};
                    String Cash_av[]={"None","500","1000"};
                    String mater_nity_av[]={"None","35000"};


                    ArrayAdapter<String> myadapter5 =new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,Cash_av);
                    myadapter5.setDropDownViewResource(R.layout.spinner2);
                    spin_cash.setAdapter(myadapter5);
                    spin_cash.setOnItemSelectedListener(this);

                    ArrayAdapter<String> myadapter6 =new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,mater_nity_av);
                    myadapter6.setDropDownViewResource(R.layout.spinner2);
                    spin_maternity.setAdapter(myadapter6);
                    spin_maternity.setOnItemSelectedListener(this);

                    //String crit_ill []={"None","200000","300000","500000","750000","1000000","1500000","2000000","2500000","3000000"};
                    String crit_ill []={"None","200000","300000","500000","750000"};
                    ArrayAdapter<String> myadapter7 =new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,crit_ill);
                    myadapter7.setDropDownViewResource(R.layout.spinner2);
                    spin_crit_ill.setAdapter(myadapter7);
                    spin_crit_ill.setOnItemSelectedListener(this);
                }
                else if(item.equals("CLASSIC 10L"))
                {
                    getage();
                    // String Cash_av[]={"None","500","1000","2000","3000"};
                    // String mater_nity_av[]={"None","35000","50000","75000","100000"};
                    String Cash_av[]={"None","500","1000","2000"};
                    String mater_nity_av[]={"None","50000"};


                    ArrayAdapter<String> myadapter5 =new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,Cash_av);
                    myadapter5.setDropDownViewResource(R.layout.spinner2);
                    spin_cash.setAdapter(myadapter5);
                    spin_cash.setOnItemSelectedListener(this);

                    ArrayAdapter<String> myadapter6 =new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,mater_nity_av);
                    myadapter6.setDropDownViewResource(R.layout.spinner2);
                    spin_maternity.setAdapter(myadapter6);
                    spin_maternity.setOnItemSelectedListener(this);

                    //String crit_ill []={"None","200000","300000","500000","750000","1000000","1500000","2000000","2500000","3000000"};
                    String crit_ill []={"None","200000","300000","500000","750000","1000000"};
                    ArrayAdapter<String> myadapter7 =new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,crit_ill);
                    myadapter7.setDropDownViewResource(R.layout.spinner2);
                    spin_crit_ill.setAdapter(myadapter7);
                    spin_crit_ill.setOnItemSelectedListener(this);
                }
                else if(item.equals("CLASSIC 15L"))
                {
                    getage();
                    // String Cash_av[]={"None","500","1000","2000","3000"};
                    // String mater_nity_av[]={"None","35000","50000","75000","100000"};
                    String Cash_av[]={"None","500","1000","2000"};
                    String mater_nity_av[]={"None","50000"};


                    ArrayAdapter<String> myadapter5 =new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,Cash_av);
                    myadapter5.setDropDownViewResource(R.layout.spinner2);
                    spin_cash.setAdapter(myadapter5);
                    spin_cash.setOnItemSelectedListener(this);

                    ArrayAdapter<String> myadapter6 =new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,mater_nity_av);
                    myadapter6.setDropDownViewResource(R.layout.spinner2);
                    spin_maternity.setAdapter(myadapter6);
                    spin_maternity.setOnItemSelectedListener(this);

                    String crit_ill []={"None","200000","300000","500000","750000","1000000","1500000"};
                    ArrayAdapter<String> myadapter7 =new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,crit_ill);
                    myadapter7.setDropDownViewResource(R.layout.spinner2);
                    spin_crit_ill.setAdapter(myadapter7);
                    spin_crit_ill.setOnItemSelectedListener(this);
                }
                else if(item.equals("CLASSIC 20L"))
                {
                    getage();
                    String Cash_av[]={"None","500","1000","2000"};
                    String mater_nity_av[]={"None","50000"};


                    ArrayAdapter<String> myadapter5 =new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,Cash_av);
                    myadapter5.setDropDownViewResource(R.layout.spinner2);
                    spin_cash.setAdapter(myadapter5);
                    spin_cash.setOnItemSelectedListener(this);

                    ArrayAdapter<String> myadapter6 =new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,mater_nity_av);
                    myadapter6.setDropDownViewResource(R.layout.spinner2);
                    spin_maternity.setAdapter(myadapter6);
                    spin_maternity.setOnItemSelectedListener(this);

                    String crit_ill []={"None","200000","300000","500000","750000","1000000","1500000","2000000"};
                    ArrayAdapter<String> myadapter7 =new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,crit_ill);
                    myadapter7.setDropDownViewResource(R.layout.spinner2);
                    spin_crit_ill.setAdapter(myadapter7);
                    spin_crit_ill.setOnItemSelectedListener(this);
                }
                else if(item.equals("UBER 20L"))
                {
                    getage();
                    String Cash_av[]={"None","500","1000","2000","3000"};
                    String mater_nity_av[]={"None","75000"};


                    ArrayAdapter<String> myadapter5 =new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,Cash_av);
                    myadapter5.setDropDownViewResource(R.layout.spinner2);
                    spin_cash.setAdapter(myadapter5);
                    spin_cash.setOnItemSelectedListener(this);

                    ArrayAdapter<String> myadapter6 =new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,mater_nity_av);
                    myadapter6.setDropDownViewResource(R.layout.spinner2);
                    spin_maternity.setAdapter(myadapter6);
                    spin_maternity.setOnItemSelectedListener(this);

                    String crit_ill []={"None","200000","300000","500000","750000","1000000","1500000","2000000"};
                    ArrayAdapter<String> myadapter7 =new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,crit_ill);
                    myadapter7.setDropDownViewResource(R.layout.spinner2);
                    spin_crit_ill.setAdapter(myadapter7);
                    spin_crit_ill.setOnItemSelectedListener(this);
                }
                else if(item.equals("UBER 30L"))
                {
                    //String plan[]={"VALUE 5L","VALUE 7.5L","CLASSIC 10L","CLASSIC 15L","CLASSIC 20L","UBER 20L","UBER 30L","UBER 50L",
                         //   "UBER 60L","UBER 70L","UBER 80L","UBER 90L","UBER 1CR"};

                    getage();

                    String Cash_av[]={"None","500","1000","2000","3000"};
                    String mater_nity_av[]={"None","75000"};


                    ArrayAdapter<String> myadapter5 =new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,Cash_av);
                    myadapter5.setDropDownViewResource(R.layout.spinner2);
                    spin_cash.setAdapter(myadapter5);
                    spin_cash.setOnItemSelectedListener(this);

                    ArrayAdapter<String> myadapter6 =new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,mater_nity_av);
                    myadapter6.setDropDownViewResource(R.layout.spinner2);
                    spin_maternity.setAdapter(myadapter6);
                    spin_maternity.setOnItemSelectedListener(this);


                    String crit_ill []={"None","200000","300000","500000","750000","1000000","1500000","2000000","2500000","3000000"};
                    ArrayAdapter<String> myadapter7 =new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,crit_ill);
                    myadapter7.setDropDownViewResource(R.layout.spinner2);
                    spin_crit_ill.setAdapter(myadapter7);
                    spin_crit_ill.setOnItemSelectedListener(this);
                }
                else if(item.equals("UBER 50L"))
                {
                    getage();
                    String Cash_av[]={"None","500","1000","2000","3000"};
                    String mater_nity_av[]={"None","100000"};


                    ArrayAdapter<String> myadapter5 =new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,Cash_av);
                    myadapter5.setDropDownViewResource(R.layout.spinner2);
                    spin_cash.setAdapter(myadapter5);
                    spin_cash.setOnItemSelectedListener(this);

                    ArrayAdapter<String> myadapter6 =new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,mater_nity_av);
                    myadapter6.setDropDownViewResource(R.layout.spinner2);
                    spin_maternity.setAdapter(myadapter6);
                    spin_maternity.setOnItemSelectedListener(this);

                    String crit_ill []={"None","200000","300000","500000","750000","1000000","1500000","2000000","2500000","3000000"};
                    ArrayAdapter<String> myadapter7 =new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,crit_ill);
                    myadapter7.setDropDownViewResource(R.layout.spinner2);
                    spin_crit_ill.setAdapter(myadapter7);
                    spin_crit_ill.setOnItemSelectedListener(this);
                }
                else if(item.equals("UBER 60L"))
                {
                    getage();
                    String Cash_av[]={"None","500","1000","2000","3000"};
                    String mater_nity_av[]={"None","100000"};


                    ArrayAdapter<String> myadapter5 =new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,Cash_av);
                    myadapter5.setDropDownViewResource(R.layout.spinner2);
                    spin_cash.setAdapter(myadapter5);
                    spin_cash.setOnItemSelectedListener(this);

                    ArrayAdapter<String> myadapter6 =new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,mater_nity_av);
                    myadapter6.setDropDownViewResource(R.layout.spinner2);
                    spin_maternity.setAdapter(myadapter6);
                    spin_maternity.setOnItemSelectedListener(this);


                    String crit_ill []={"None","200000","300000","500000","750000","1000000","1500000","2000000","2500000","3000000"};
                    ArrayAdapter<String> myadapter7 =new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,crit_ill);
                    myadapter7.setDropDownViewResource(R.layout.spinner2);
                    spin_crit_ill.setAdapter(myadapter7);
                    spin_crit_ill.setOnItemSelectedListener(this);
                }
                else if(item.equals("UBER 70L"))
                {
                    getage();
                    String Cash_av[]={"None","500","1000","2000","3000"};
                    String mater_nity_av[]={"None","100000"};


                    ArrayAdapter<String> myadapter5 =new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,Cash_av);
                    myadapter5.setDropDownViewResource(R.layout.spinner2);
                    spin_cash.setAdapter(myadapter5);
                    spin_cash.setOnItemSelectedListener(this);

                    ArrayAdapter<String> myadapter6 =new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,mater_nity_av);
                    myadapter6.setDropDownViewResource(R.layout.spinner2);
                    spin_maternity.setAdapter(myadapter6);
                    spin_maternity.setOnItemSelectedListener(this);

                    String crit_ill []={"None","200000","300000","500000","750000","1000000","1500000","2000000","2500000","3000000"};
                    ArrayAdapter<String> myadapter7 =new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,crit_ill);
                    myadapter7.setDropDownViewResource(R.layout.spinner2);
                    spin_crit_ill.setAdapter(myadapter7);
                    spin_crit_ill.setOnItemSelectedListener(this);
                }
                else if(item.equals("UBER 80L"))
                {
                    getage();
                    String Cash_av[]={"None","500","1000","2000","3000"};
                    String mater_nity_av[]={"None","100000"};


                    ArrayAdapter<String> myadapter5 =new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,Cash_av);
                    myadapter5.setDropDownViewResource(R.layout.spinner2);
                    spin_cash.setAdapter(myadapter5);
                    spin_cash.setOnItemSelectedListener(this);

                    ArrayAdapter<String> myadapter6 =new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,mater_nity_av);
                    myadapter6.setDropDownViewResource(R.layout.spinner2);
                    spin_maternity.setAdapter(myadapter6);
                    spin_maternity.setOnItemSelectedListener(this);

                    String crit_ill []={"None","200000","300000","500000","750000","1000000","1500000","2000000","2500000","3000000"};
                    ArrayAdapter<String> myadapter7 =new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,crit_ill);
                    myadapter7.setDropDownViewResource(R.layout.spinner2);
                    spin_crit_ill.setAdapter(myadapter7);
                    spin_crit_ill.setOnItemSelectedListener(this);


                }
                else if(item.equals("UBER 90L"))
                {
                    getage();
                    String Cash_av[]={"None","500","1000","2000","3000"};
                    String mater_nity_av[]={"None","100000"};


                    ArrayAdapter<String> myadapter5 =new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,Cash_av);
                    myadapter5.setDropDownViewResource(R.layout.spinner2);
                    spin_cash.setAdapter(myadapter5);
                    spin_cash.setOnItemSelectedListener(this);

                    ArrayAdapter<String> myadapter6 =new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,mater_nity_av);
                    myadapter6.setDropDownViewResource(R.layout.spinner2);
                    spin_maternity.setAdapter(myadapter6);
                    spin_maternity.setOnItemSelectedListener(this);


                    String crit_ill []={"None","200000","300000","500000","750000","1000000","1500000","2000000","2500000","3000000"};
                    ArrayAdapter<String> myadapter7 =new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,crit_ill);
                    myadapter7.setDropDownViewResource(R.layout.spinner2);
                    spin_crit_ill.setAdapter(myadapter7);
                    spin_crit_ill.setOnItemSelectedListener(this);
                }
                else if(item.equals("UBER 1CR"))
                {
                    getage();

                    String Cash_av[]={"None","500","1000","2000","3000"};
                    String mater_nity_av[]={"None","100000"};


                    ArrayAdapter<String> myadapter5 =new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,Cash_av);
                    myadapter5.setDropDownViewResource(R.layout.spinner2);
                    spin_cash.setAdapter(myadapter5);
                    spin_cash.setOnItemSelectedListener(this);

                    ArrayAdapter<String> myadapter6 =new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,mater_nity_av);
                    myadapter6.setDropDownViewResource(R.layout.spinner2);
                    spin_maternity.setAdapter(myadapter6);
                    spin_maternity.setOnItemSelectedListener(this);

                    String crit_ill []={"None","200000","300000","500000","750000","1000000","1500000","2000000","2500000","3000000"};
                    ArrayAdapter<String> myadapter7 =new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,crit_ill);
                    myadapter7.setDropDownViewResource(R.layout.spinner2);
                    spin_crit_ill.setAdapter(myadapter7);
                    spin_crit_ill.setOnItemSelectedListener(this);
                }

                break;

            }

        }


    }

    private void getsc2critical() {

        String depband=chield1_band.getText().toString();
        String myamount=spin_crit_ill.getSelectedItem().toString();
        Spinner myperiod=(Spinner) findViewById(R.id.period);
        String p1=myperiod.getSelectedItem().toString();
        if(p1.equals("1 Year"))
        {
            Cursor res = db.getCriticalldata(depband, myamount);
            while (res.moveToNext()) {
                myid = res.getString(0);

            }

            Double rr=Double.parseDouble(myid.toString());
            criticaldep2.setText(String.format("%.1f",rr));
            db.close();
        }else if(p1.equals("2 Year"))
        {
            EditText age=(EditText)findViewById(R.id.agec1);

            String agedata=age.getText().toString();
            Integer ag=Integer.parseInt(agedata);
            String myband="";
            String myid2="";

            if (ag<=25)
            {
                myband="0-25";

            }
            else if(ag>25 && ag<=35)
            {
                myband="26-35";

            }
            else if(ag>35 && ag<=40)
            {
                myband="36-40";

            }
            else if(ag>40 && ag<=45)
            {
                myband="41-45";

            }
            else if(ag>45 && ag<=50)
            {
                myband="46-50";

            }
            else if(ag>50 && ag<=55)
            {
                myband="51-55";

            }
            else if(ag>55 && ag<=60)
            {
                myband="56-60";

            }
            else if(ag>60 && ag<=65)
            {
                myband="61-65";

            }
            else if(ag>65 && ag<=70)
            {
                myband="66-70";

            }
            else if(ag>70 && ag<=75)
            {
                myband="71-75";

            }
            else if(ag>75 && ag<=80)
            {
                myband="76-80";

            }
            else if(ag>80 && ag<=85)
            {
                myband="81-85";

            }
            else if(ag>85 && ag<=90)
            {
                myband="86-90";

            }
            else if(ag>90 && ag<=95)
            {
                myband="91-95";

            }
            else if(ag>95)
            {
                myband=">95";

            }


            Cursor res1=db.getCriticalldata(myband,myamount);
            while (res1.moveToNext())
            {
                myid2=res1.getString(0);

            }
            String ba_sic=myid2.toString();
            Integer ag2=ag+1;
            //  Toast.makeText(getApplicationContext(),""+ag2.toString(),Toast.LENGTH_SHORT).show();
            String myband2="" ;
            String myid3="";

            if (ag2<=25)
            {
                myband2="0-25";

            }
            else if(ag2>25 && ag2<=35)
            {
                myband2="26-35";

            }
            else if(ag2>35 && ag2<=40)
            {
                myband2="36-40";

            }
            else if(ag2>40 && ag2<=40)
            {
                myband2="41-45";

            }
            else if(ag>40 && ag<=50)
            {
                myband2="46-50";

            }
            else if(ag2>50 && ag2<=55)
            {
                myband2="51-55";

            }
            else if(ag2>55 && ag2<=60)
            {
                myband2="56-60";

            }
            else if(ag2>60 && ag2<=65)
            {
                myband2="61-65";

            }
            else if(ag2>65 && ag2<=70)
            {
                myband2="66-70";

            }
            else if(ag2>70 && ag2<=75)
            {
                myband2="71-75";

            }
            else if(ag2>75 && ag2<=80)
            {
                myband2="76-80";

            }
            else if(ag2>80 && ag2<=85)
            {
                myband2="81-85";

            }
            else if(ag2>85 && ag2<=90)
            {
                myband2="86-90";

            }
            else if(ag2>90 && ag2<=95)
            {
                myband2="91-95";

            }
            else if(ag2>95)
            {
                myband2=">95";

            }

            //   mcriticself.setText(myid.toString());

            Cursor res2=db.getCriticalldata(myband2,myamount);
            while (res2.moveToNext())
            {
                myid3=res2.getString(0);

            }
            //  Toast.makeText(getApplicationContext(),""+myid2+"  "+myid3,Toast.LENGTH_SHORT).show();
            String ba_sic2=myid3.toString();
            Double a1=Double.parseDouble(ba_sic);
            Double a2=Double.parseDouble(ba_sic2);
            Double a3=a1+a2;

            // Toast.makeText(getApplicationContext(),"a -"+a1.toString()+"  "+a2.toString(),Toast.LENGTH_SHORT).show();
            criticaldep2.setText(String.format("%.1f",a3));
            db.close();

        }else if(p1.equals("3 Year"))
        {
            EditText age=(EditText)findViewById(R.id.agec1);

            String agedata=age.getText().toString();
            Integer ag=Integer.parseInt(agedata);
            String myband="";
            String myid2="";

            if (ag<=25)
            {
                myband="0-25";

            }
            else if(ag>25 && ag<=35)
            {
                myband="26-35";

            }
            else if(ag>35 && ag<=40)
            {
                myband="36-40";

            }
            else if(ag>40 && ag<=45)
            {
                myband="41-45";

            }
            else if(ag>45 && ag<=50)
            {
                myband="46-50";

            }
            else if(ag>50 && ag<=55)
            {
                myband="51-55";

            }
            else if(ag>55 && ag<=60)
            {
                myband="56-60";

            }
            else if(ag>60 && ag<=65)
            {
                myband="61-65";

            }
            else if(ag>65 && ag<=70)
            {
                myband="66-70";

            }
            else if(ag>70 && ag<=75)
            {
                myband="71-75";

            }
            else if(ag>75 && ag<=80)
            {
                myband="76-80";

            }
            else if(ag>80 && ag<=85)
            {
                myband="81-85";

            }
            else if(ag>85 && ag<=90)
            {
                myband="86-90";

            }
            else if(ag>90 && ag<=95)
            {
                myband="91-95";

            }
            else if(ag>95)
            {
                myband=">95";

            }


            Cursor res1=db.getCriticalldata(myband,myamount);
            while (res1.moveToNext())
            {
                myid2=res1.getString(0);

            }
            String ba_sic=myid2.toString();
            Integer ag2=ag+1;
            //  Toast.makeText(getApplicationContext(),""+ag2.toString(),Toast.LENGTH_SHORT).show();
            String myband2="" ;
            String myid3="";

            if (ag2<=25)
            {
                myband2="0-25";

            }
            else if(ag2>25 && ag2<=35)
            {
                myband2="26-35";

            }
            else if(ag2>35 && ag2<=40)
            {
                myband2="36-40";

            }
            else if(ag2>40 && ag2<=40)
            {
                myband2="41-45";

            }
            else if(ag2>40 && ag2<=50)
            {
                myband2="46-50";

            }
            else if(ag2>50 && ag2<=55)
            {
                myband2="51-55";

            }
            else if(ag2>55 && ag2<=60)
            {
                myband2="56-60";

            }
            else if(ag2>60 && ag2<=65)
            {
                myband2="61-65";

            }
            else if(ag2>65 && ag2<=70)
            {
                myband2="66-70";

            }
            else if(ag2>70 && ag2<=75)
            {
                myband2="71-75";

            }
            else if(ag2>75 && ag2<=80)
            {
                myband2="76-80";

            }
            else if(ag2>80 && ag2<=85)
            {
                myband2="81-85";

            }
            else if(ag2>85 && ag2<=90)
            {
                myband2="86-90";

            }
            else if(ag2>90 && ag2<=95)
            {
                myband2="91-95";

            }
            else if(ag2>95)
            {
                myband2=">95";

            }


            Cursor res2=db.getCriticalldata(myband2,myamount);
            while (res2.moveToNext())
            {
                myid3=res2.getString(0);

            }
            //  Toast.makeText(getApplicationContext(),""+myid2+"  "+myid3,Toast.LENGTH_SHORT).show();
            String ba_sic2=myid3.toString();

            Integer ag3=ag2+1;
            //  Toast.makeText(getApplicationContext(),""+ag2.toString(),Toast.LENGTH_SHORT).show();
            String myband3="";
            String myid="";

            if (ag3<=25)
            {
                myband3="0-25";

            }
            else if(ag3>25 && ag3<=35)
            {
                myband3="26-35";

            }
            else if(ag3>35 && ag3<=40)
            {
                myband3="36-40";

            }
            else if(ag3>40 && ag3<=40)
            {
                myband3="41-45";

            }
            else if(ag3>40 && ag3<=50)
            {
                myband3="46-50";

            }
            else if(ag3>50 && ag3<=55)
            {
                myband3="51-55";

            }
            else if(ag3>55 && ag3<=60)
            {
                myband3="56-60";

            }
            else if(ag3>60 && ag3<=65)
            {
                myband3="61-65";

            }
            else if(ag3>65 && ag3<=70)
            {
                myband3="66-70";

            }
            else if(ag3>70 && ag3<=75)
            {
                myband3="71-75";

            }
            else if(ag3>75 && ag3<=80)
            {
                myband3="76-80";

            }
            else if(ag3>80 && ag3<=85)
            {
                myband3="81-85";

            }
            else if(ag3>85 && ag3<=90)
            {
                myband3="86-90";

            }
            else if(ag3>90 && ag3<=95)
            {
                myband3="91-95";

            }
            else if(ag3>95)
            {
                myband3=">95";

            }

            Cursor res=db.getCriticalldata(myband3,myamount);
            while (res.moveToNext())
            {
                myid=res.getString(0);

            }
            //  Toast.makeText(getApplicationContext(),""+myid2+"  "+myid3,Toast.LENGTH_SHORT).show();
            String ba_sic3=myid.toString();

            Double r1=Double.parseDouble(ba_sic3);
            Double a1=Double.parseDouble(ba_sic);
            Double a2=Double.parseDouble(ba_sic2);
            Double a3=a1+a2+r1;

            // Toast.makeText(getApplicationContext(),"a -"+a1.toString()+"  "+a2.toString(),Toast.LENGTH_SHORT).show();
            criticaldep2.setText(String.format("%.1f",a3));
            db.close();
        }
    }

    private void getspousecritical()
    {
        String depband=spouse_band.getText().toString();
        String myamount=spin_crit_ill.getSelectedItem().toString();

        Spinner myperiod=(Spinner) findViewById(R.id.period);
        String p1=myperiod.getSelectedItem().toString();
        if(p1.equals("1 Year"))

        {
            Cursor res = db.getCriticalldata(depband, myamount);

            while (res.moveToNext())
            {
                myid = res.getString(0);

            }
           // my_basic.setText(String.format("%.1f",p12));

            Double bb=Double.parseDouble(myid.toString());
            criticaldep1.setText(String.format("%.1f",bb));



            db.close();
        }else if(p1.equals("2 Year"))
        {
            EditText age=(EditText)findViewById(R.id.spouseage);

            String agedata=age.getText().toString();
            Integer ag=Integer.parseInt(agedata);
            String myband="";
            String myid2="";

            if (ag<=25)
            {
                myband="0-25";

            }
            else if(ag>25 && ag<=35)
            {
                myband="26-35";

            }
            else if(ag>35 && ag<=40)
            {
                myband="36-40";

            }
            else if(ag>40 && ag<=45)
            {
                myband="41-45";

            }
            else if(ag>45 && ag<=50)
            {
                myband="46-50";

            }
            else if(ag>50 && ag<=55)
            {
                myband="51-55";

            }
            else if(ag>55 && ag<=60)
            {
                myband="56-60";

            }
            else if(ag>60 && ag<=65)
            {
                myband="61-65";

            }
            else if(ag>65 && ag<=70)
            {
                myband="66-70";

            }
            else if(ag>70 && ag<=75)
            {
                myband="71-75";

            }
            else if(ag>75 && ag<=80)
            {
                myband="76-80";

            }
            else if(ag>80 && ag<=85)
            {
                myband="81-85";

            }
            else if(ag>85 && ag<=90)
            {
                myband="86-90";

            }
            else if(ag>90 && ag<=95)
            {
                myband="91-95";

            }
            else if(ag>95)
            {
                myband=">95";

            }

            Cursor res1=db.getCriticalldata(myband,myamount);
            while (res1.moveToNext())
            {
                myid2=res1.getString(0);

            }
            String ba_sic=myid2.toString();
            Integer ag2=ag+1;
            //  Toast.makeText(getApplicationContext(),""+ag2.toString(),Toast.LENGTH_SHORT).show();
            String myband2="" ;
            String myid3="";

            if (ag2<=25)
            {
                myband2="0-25";

            }
            else if(ag2>25 && ag2<=35)
            {
                myband2="26-35";

            }
            else if(ag2>35 && ag2<=40)
            {
                myband2="36-40";

            }
            else if(ag2>40 && ag2<=40)
            {
                myband2="41-45";

            }
            else if(ag>40 && ag<=50)
            {
                myband2="46-50";

            }
            else if(ag2>50 && ag2<=55)
            {
                myband2="51-55";

            }
            else if(ag2>55 && ag2<=60)
            {
                myband2="56-60";

            }
            else if(ag2>60 && ag2<=65)
            {
                myband2="61-65";

            }
            else if(ag2>65 && ag2<=70)
            {
                myband2="66-70";

            }
            else if(ag2>70 && ag2<=75)
            {
                myband2="71-75";

            }
            else if(ag2>75 && ag2<=80)
            {
                myband2="76-80";

            }
            else if(ag2>80 && ag2<=85)
            {
                myband2="81-85";

            }
            else if(ag2>85 && ag2<=90)
            {
                myband2="86-90";

            }
            else if(ag2>90 && ag2<=95)
            {
                myband2="91-95";

            }
            else if(ag2>95)
            {
                myband2=">95";

            }


            //   mcriticself.setText(myid.toString());

            Cursor res2=db.getCriticalldata(myband2,myamount);
            while (res2.moveToNext())
            {
                myid3=res2.getString(0);

            }
            //  Toast.makeText(getApplicationContext(),""+myid2+"  "+myid3,Toast.LENGTH_SHORT).show();
            String ba_sic2=myid3.toString();
            Double a1=Double.parseDouble(ba_sic);
            Double a2=Double.parseDouble(ba_sic2);
            Double a3=a1+a2;

            // Toast.makeText(getApplicationContext(),"a -"+a1.toString()+"  "+a2.toString(),Toast.LENGTH_SHORT).show();

            criticaldep1.setText(String.format("%.1f",a3));
            db.close();

        }else if(p1.equals("3 Year"))
        {
            EditText age=(EditText)findViewById(R.id.spouseage);

            String agedata=age.getText().toString();
            Integer ag=Integer.parseInt(agedata);
            String myband="";
            String myid2="";

            if (ag<=25)
            {
                myband="0-25";

            }
            else if(ag>25 && ag<=35)
            {
                myband="26-35";

            }
            else if(ag>35 && ag<=40)
            {
                myband="36-40";

            }
            else if(ag>40 && ag<=45)
            {
                myband="41-45";

            }
            else if(ag>45 && ag<=50)
            {
                myband="46-50";

            }
            else if(ag>50 && ag<=55)
            {
                myband="51-55";

            }
            else if(ag>55 && ag<=60)
            {
                myband="56-60";

            }
            else if(ag>60 && ag<=65)
            {
                myband="61-65";

            }
            else if(ag>65 && ag<=70)
            {
                myband="66-70";

            }
            else if(ag>70 && ag<=75)
            {
                myband="71-75";

            }
            else if(ag>75 && ag<=80)
            {
                myband="76-80";

            }
            else if(ag>80 && ag<=85)
            {
                myband="81-85";

            }
            else if(ag>85 && ag<=90)
            {
                myband="86-90";

            }
            else if(ag>90 && ag<=95)
            {
                myband="91-95";

            }
            else if(ag>95)
            {
                myband=">95";

            }


            Cursor res1=db.getCriticalldata(myband,myamount);
            while (res1.moveToNext())
            {
                myid2=res1.getString(0);

            }
            String ba_sic=myid2.toString();
            Integer ag2=ag+1;
            //  Toast.makeText(getApplicationContext(),""+ag2.toString(),Toast.LENGTH_SHORT).show();
            String myband2="" ;
            String myid3="";

            if (ag2<=25)
            {
                myband2="0-25";

            }
            else if(ag2>25 && ag2<=35)
            {
                myband2="26-35";

            }
            else if(ag2>35 && ag2<=40)
            {
                myband2="36-40";

            }
            else if(ag2>40 && ag2<=40)
            {
                myband2="41-45";

            }
            else if(ag2>40 && ag2<=50)
            {
                myband2="46-50";

            }
            else if(ag2>50 && ag2<=55)
            {
                myband2="51-55";

            }
            else if(ag2>55 && ag2<=60)
            {
                myband2="56-60";

            }
            else if(ag2>60 && ag2<=65)
            {
                myband2="61-65";

            }
            else if(ag2>65 && ag2<=70)
            {
                myband2="66-70";

            }
            else if(ag2>70 && ag2<=75)
            {
                myband2="71-75";

            }
            else if(ag2>75 && ag2<=80)
            {
                myband2="76-80";

            }
            else if(ag2>80 && ag2<=85)
            {
                myband2="81-85";

            }
            else if(ag2>85 && ag2<=90)
            {
                myband2="86-90";

            }
            else if(ag2>90 && ag2<=95)
            {
                myband2="91-95";

            }
            else if(ag2>95)
            {
                myband2=">95";

            }


            Cursor res2=db.getCriticalldata(myband2,myamount);
            while (res2.moveToNext())
            {
                myid3=res2.getString(0);

            }
            //  Toast.makeText(getApplicationContext(),""+myid2+"  "+myid3,Toast.LENGTH_SHORT).show();
            String ba_sic2=myid3.toString();

            Integer ag3=ag2+1;
            //  Toast.makeText(getApplicationContext(),""+ag2.toString(),Toast.LENGTH_SHORT).show();
            String myband3="";
            String myid="";

            if (ag3<=25)
            {
                myband3="0-25";

            }
            else if(ag3>25 && ag3<=35)
            {
                myband3="26-35";

            }
            else if(ag3>35 && ag3<=40)
            {
                myband3="36-40";

            }
            else if(ag3>40 && ag3<=40)
            {
                myband3="41-45";

            }
            else if(ag3>40 && ag3<=50)
            {
                myband3="46-50";

            }
            else if(ag3>50 && ag3<=55)
            {
                myband3="51-55";

            }
            else if(ag3>55 && ag3<=60)
            {
                myband3="56-60";

            }
            else if(ag3>60 && ag3<=65)
            {
                myband3="61-65";

            }
            else if(ag3>65 && ag3<=70)
            {
                myband3="66-70";

            }
            else if(ag3>70 && ag3<=75)
            {
                myband3="71-75";

            }
            else if(ag3>75 && ag3<=80)
            {
                myband3="76-80";

            }
            else if(ag3>80 && ag3<=85)
            {
                myband3="81-85";

            }
            else if(ag3>85 && ag3<=90)
            {
                myband3="86-90";

            }
            else if(ag3>90 && ag3<=95)
            {
                myband3="91-95";

            }
            else if(ag3>95)
            {
                myband3=">95";

            }

            Cursor res=db.getCriticalldata(myband3,myamount);
            while (res.moveToNext())
            {
                myid=res.getString(0);

            }
            //  Toast.makeText(getApplicationContext(),""+myid2+"  "+myid3,Toast.LENGTH_SHORT).show();
            String ba_sic3=myid.toString();

            Double r1=Double.parseDouble(ba_sic3);
            Double a1=Double.parseDouble(ba_sic);
            Double a2=Double.parseDouble(ba_sic2);
            Double a3=a1+a2+r1;

            // Toast.makeText(getApplicationContext(),"a -"+a1.toString()+"  "+a2.toString(),Toast.LENGTH_SHORT).show();
            criticaldep1.setText(String.format("%.1f",a3));
            db.close();
        }






    }


    private void getMaternity()
    {
        String band=myelderband.getText().toString();
        String myplan =Planopted.getSelectedItem().toString();


       // Toast.makeText(getApplicationContext(),""+band+" "+myplan,Toast.LENGTH_SHORT).show();

        Cursor res=db.getMaternitydata(band,myplan);
        while (res.moveToNext())
        {
            myid=res.getString(0);

        }


       // Mater_Amount.setText(myid.toString());
        Double ll=Double.parseDouble(myid.toString());

        Mater_Amount.setText(String.format("%.1f",ll));
        db.close();

    }

    private void getcritical()
    {
        String band=myelderband.getText().toString();
        String selectcri= spin_crit_ill.getSelectedItem().toString();

        Spinner myperiod=(Spinner) findViewById(R.id.period);
        String p1=myperiod.getSelectedItem().toString();
        if(p1.equals("1 Year"))
        {

            Cursor res = db.getCriticalldata(band, selectcri);
            while (res.moveToNext()) {
                myid = res.getString(0);

            }
           // mcriticself.setText(myid.toString());
            Double ui= Double.parseDouble(myid.toString());
            mcriticself.setText(String.format("%.1f",ui));

            db.close();
        }
        else if(p1.equals("2 Year"))
        {
            EditText age=(EditText)findViewById(R.id.elderage);

            String agedata=age.getText().toString();
            Integer ag=Integer.parseInt(agedata);
            String myband="";
            String myid2="";

            if (ag<=25)
            {
                myband="0-25";

            }
            else if(ag>25 && ag<=35)
            {
                myband="26-35";

            }
            else if(ag>35 && ag<=40)
            {
                myband="36-40";

            }
            else if(ag>40 && ag<=45)
            {
                myband="41-45";

            }
            else if(ag>45 && ag<=50)
            {
                myband="46-50";

            }
            else if(ag>50 && ag<=55)
            {
                myband="51-55";

            }
            else if(ag>55 && ag<=60)
            {
                myband="56-60";

            }
            else if(ag>60 && ag<=65)
            {
                myband="61-65";

            }
            else if(ag>65 && ag<=70)
            {
                myband="66-70";

            }
            else if(ag>70 && ag<=75)
            {
                myband="71-75";

            }
            else if(ag>75 && ag<=80)
            {
                myband="76-80";

            }
            else if(ag>80 && ag<=85)
            {
                myband="81-85";

            }
            else if(ag>85 && ag<=90)
            {
                myband="86-90";

            }
            else if(ag>90 && ag<=95)
            {
                myband="91-95";

            }
            else if(ag>95)
            {
                myband=">95";

            }


            Cursor res1=db.getCriticalldata(myband,selectcri);
            while (res1.moveToNext())
            {
                myid2=res1.getString(0);

            }
            String ba_sic=myid2.toString();
            Integer ag2=ag+1;
            //  Toast.makeText(getApplicationContext(),""+ag2.toString(),Toast.LENGTH_SHORT).show();
            String myband2="" ;
            String myid3="";

            if (ag2<=25)
            {
                myband2="0-25";

            }
            else if(ag2>25 && ag2<=35)
            {
                myband2="26-35";

            }
            else if(ag2>35 && ag2<=40)
            {
                myband2="36-40";

            }
            else if(ag2>40 && ag2<=40)
            {
                myband2="41-45";

            }
            else if(ag>40 && ag<=50)
            {
                myband2="46-50";

            }
            else if(ag2>50 && ag2<=55)
            {
                myband2="51-55";

            }
            else if(ag2>55 && ag2<=60)
            {
                myband2="56-60";

            }
            else if(ag2>60 && ag2<=65)
            {
                myband2="61-65";

            }
            else if(ag2>65 && ag2<=70)
            {
                myband2="66-70";

            }
            else if(ag2>70 && ag2<=75)
            {
                myband2="71-75";

            }
            else if(ag2>75 && ag2<=80)
            {
                myband2="76-80";

            }
            else if(ag2>80 && ag2<=85)
            {
                myband2="81-85";

            }
            else if(ag2>85 && ag2<=90)
            {
                myband2="86-90";

            }
            else if(ag2>90 && ag2<=95)
            {
                myband2="91-95";

            }
            else if(ag2>95)
            {
                myband2=">95";

            }


            //   mcriticself.setText(myid.toString());

            Cursor res2=db.getCriticalldata(myband2,selectcri);
            while (res2.moveToNext())
            {
                myid3=res2.getString(0);

            }
            //  Toast.makeText(getApplicationContext(),""+myid2+"  "+myid3,Toast.LENGTH_SHORT).show();
            String ba_sic2=myid3.toString();
            Double a1=Double.parseDouble(ba_sic);
            Double a2=Double.parseDouble(ba_sic2);
            Double a3=a1+a2;

            // Toast.makeText(getApplicationContext(),"a -"+a1.toString()+"  "+a2.toString(),Toast.LENGTH_SHORT).show();
            mcriticself.setText(String.format("%.1f",a3));
            db.close();

        }else if(p1.equals("3 Year"))
        {
            EditText age=(EditText)findViewById(R.id.elderage);

            String agedata=age.getText().toString();
            Integer ag=Integer.parseInt(agedata);
            String myband="";
            String myid2="";

            if (ag<=25)
            {
                myband="0-25";

            }
            else if(ag>25 && ag<=35)
            {
                myband="26-35";

            }
            else if(ag>35 && ag<=40)
            {
                myband="36-40";

            }
            else if(ag>40 && ag<=45)
            {
                myband="41-45";

            }
            else if(ag>45 && ag<=50)
            {
                myband="46-50";

            }
            else if(ag>50 && ag<=55)
            {
                myband="51-55";

            }
            else if(ag>55 && ag<=60)
            {
                myband="56-60";

            }
            else if(ag>60 && ag<=65)
            {
                myband="61-65";

            }
            else if(ag>65 && ag<=70)
            {
                myband="66-70";

            }
            else if(ag>70 && ag<=75)
            {
                myband="71-75";

            }
            else if(ag>75 && ag<=80)
            {
                myband="76-80";

            }
            else if(ag>80 && ag<=85)
            {
                myband="81-85";

            }
            else if(ag>85 && ag<=90)
            {
                myband="86-90";

            }
            else if(ag>90 && ag<=95)
            {
                myband="91-95";

            }
            else if(ag>95)
            {
                myband=">95";

            }


            Cursor res1=db.getCriticalldata(myband,selectcri);
            while (res1.moveToNext())
            {
                myid2=res1.getString(0);

            }
            String ba_sic=myid2.toString();
            Integer ag2=ag+1;
            //  Toast.makeText(getApplicationContext(),""+ag2.toString(),Toast.LENGTH_SHORT).show();
            String myband2="" ;
            String myid3="";

            if (ag2<=25)
            {
                myband2="0-25";

            }
            else if(ag2>25 && ag2<=35)
            {
                myband2="26-35";

            }
            else if(ag2>35 && ag2<=40)
            {
                myband2="36-40";

            }
            else if(ag2>40 && ag2<=40)
            {
                myband2="41-45";

            }
            else if(ag2>40 && ag2<=50)
            {
                myband2="46-50";

            }
            else if(ag2>50 && ag2<=55)
            {
                myband2="51-55";

            }
            else if(ag2>55 && ag2<=60)
            {
                myband2="56-60";

            }
            else if(ag2>60 && ag2<=65)
            {
                myband2="61-65";

            }
            else if(ag2>65 && ag2<=70)
            {
                myband2="66-70";

            }
            else if(ag2>70 && ag2<=75)
            {
                myband2="71-75";

            }
            else if(ag2>75 && ag2<=80)
            {
                myband2="76-80";

            }
            else if(ag2>80 && ag2<=85)
            {
                myband2="81-85";

            }
            else if(ag2>85 && ag2<=90)
            {
                myband2="86-90";

            }
            else if(ag2>90 && ag2<=95)
            {
                myband2="91-95";

            }
            else if(ag2>95)
            {
                myband2=">95";

            }


            Cursor res2=db.getCriticalldata(myband2,selectcri);
            while (res2.moveToNext())
            {
                myid3=res2.getString(0);

            }
            //  Toast.makeText(getApplicationContext(),""+myid2+"  "+myid3,Toast.LENGTH_SHORT).show();
            String ba_sic2=myid3.toString();

            Integer ag3=ag2+1;
            //  Toast.makeText(getApplicationContext(),""+ag2.toString(),Toast.LENGTH_SHORT).show();
            String myband3="";
            String myid="";

            if (ag3<=25)
            {
                myband3="0-25";

            }
            else if(ag3>25 && ag3<=35)
            {
                myband3="26-35";

            }
            else if(ag3>35 && ag3<=40)
            {
                myband3="36-40";

            }
            else if(ag3>40 && ag3<=40)
            {
                myband3="41-45";

            }
            else if(ag3>40 && ag3<=50)
            {
                myband3="46-50";

            }
            else if(ag3>50 && ag3<=55)
            {
                myband3="51-55";

            }
            else if(ag3>55 && ag3<=60)
            {
                myband3="56-60";

            }
            else if(ag3>60 && ag3<=65)
            {
                myband3="61-65";

            }
            else if(ag3>65 && ag3<=70)
            {
                myband3="66-70";

            }
            else if(ag3>70 && ag3<=75)
            {
                myband3="71-75";

            }
            else if(ag3>75 && ag3<=80)
            {
                myband3="76-80";

            }
            else if(ag3>80 && ag3<=85)
            {
                myband3="81-85";

            }
            else if(ag3>85 && ag3<=90)
            {
                myband3="86-90";

            }
            else if(ag3>90 && ag3<=95)
            {
                myband3="91-95";

            }
            else if(ag3>95)
            {
                myband3=">95";

            }


            Cursor res=db.getCriticalldata(myband3,selectcri);
            while (res.moveToNext())
            {
                myid=res.getString(0);

            }
            //  Toast.makeText(getApplicationContext(),""+myid2+"  "+myid3,Toast.LENGTH_SHORT).show();
            String ba_sic3=myid.toString();

            Double r1=Double.parseDouble(ba_sic3);
            Double a1=Double.parseDouble(ba_sic);
            Double a2=Double.parseDouble(ba_sic2);
            Double a3=a1+a2+r1;

            // Toast.makeText(getApplicationContext(),"a -"+a1.toString()+"  "+a2.toString(),Toast.LENGTH_SHORT).show();
            mcriticself.setText(String.format("%.1f",a3));
            db.close();
        }



    }

    private void gethospitalcash()
    {

       EditText my_h_cash = (EditText) findViewById(R.id.cash);
       // my_h_cash.setText(myid.toString());
        String band=myelderband.getText().toString();
        String fd=familydef.getSelectedItem().toString();
        String selectcash= spin_cash.getSelectedItem().toString();

        Spinner myperiod=(Spinner) findViewById(R.id.period);
        String p1=myperiod.getSelectedItem().toString();
        if(p1.equals("1 Year"))
        {
            Cursor res1 = db.getHospitaldata(band, fd, selectcash);
            while (res1.moveToNext()) {
                myid = res1.getString(0);

            }
            //Toast.makeText(getApplicationContext(),""+myid.toString(),Toast.LENGTH_SHORT).show();
            my_h_cash.setText(myid.toString());
            Double kk =Double.parseDouble( myid.toString());
            my_h_cash.setText(String.format("%.1f",kk));
            db.close();
        }
       else if(p1.equals("2 Year"))
       {
           EditText age=(EditText)findViewById(R.id.elderage);

           String agedata=age.getText().toString();
           Integer ag=Integer.parseInt(agedata);
           String myband="";
           String myid2="";

           if (ag<=25)
           {
               myband="0-25";

           }
           else if(ag>25 && ag<=35)
           {
               myband="26-35";

           }
           else if(ag>35 && ag<=40)
           {
               myband="36-40";

           }
           else if(ag>40 && ag<=45)
           {
               myband="41-45";

           }
           else if(ag>45 && ag<=50)
           {
               myband="46-50";

           }
           else if(ag>50 && ag<=55)
           {
               myband="51-55";

           }
           else if(ag>55 && ag<=60)
           {
               myband="56-60";

           }
           else if(ag>60 && ag<=65)
           {
               myband="61-65";

           }
           else if(ag>65 && ag<=70)
           {
               myband="66-70";

           }
           else if(ag>70 && ag<=75)
           {
               myband="71-75";

           }
           else if(ag>75 && ag<=80)
           {
               myband="76-80";

           }
           else if(ag>80 && ag<=85)
           {
               myband="81-85";

           }
           else if(ag>85 && ag<=90)
           {
               myband="86-90";

           }
           else if(ag>90 && ag<=95)
           {
               myband="91-95";

           }
           else if(ag>95)
           {
               myband=">95";

           }


           Cursor res1=db.getHospitaldata(myband,fd,selectcash);
           while (res1.moveToNext())
           {
               myid2=res1.getString(0);

           }
           String ba_sic=myid2.toString();
           Integer ag2=ag+1;
           //  Toast.makeText(getApplicationContext(),""+ag2.toString(),Toast.LENGTH_SHORT).show();
           String myband2="" ;
           String myid3="";

           if (ag2<=25)
           {
               myband2="0-25";

           }
           else if(ag2>25 && ag2<=35)
           {
               myband2="26-35";

           }
           else if(ag2>35 && ag2<=40)
           {
               myband2="36-40";

           }
           else if(ag2>40 && ag2<=40)
           {
               myband2="41-45";

           }
           else if(ag>40 && ag<=50)
           {
               myband2="46-50";

           }
           else if(ag2>50 && ag2<=55)
           {
               myband2="51-55";

           }
           else if(ag2>55 && ag2<=60)
           {
               myband2="56-60";

           }
           else if(ag2>60 && ag2<=65)
           {
               myband2="61-65";

           }
           else if(ag2>65 && ag2<=70)
           {
               myband2="66-70";

           }
           else if(ag2>70 && ag2<=75)
           {
               myband2="71-75";

           }
           else if(ag2>75 && ag2<=80)
           {
               myband2="76-80";

           }
           else if(ag2>80 && ag2<=85)
           {
               myband2="81-85";

           }
           else if(ag2>85 && ag2<=90)
           {
               myband2="86-90";

           }
           else if(ag2>90 && ag2<=95)
           {
               myband2="91-95";

           }
           else if(ag2>95)
           {
               myband2=">95";

           }


           Cursor res2=db.getHospitaldata(myband2,fd,selectcash);
           while (res2.moveToNext())
           {
               myid3=res2.getString(0);

           }
         //  Toast.makeText(getApplicationContext(),""+myid2+"  "+myid3,Toast.LENGTH_SHORT).show();
           String ba_sic2=myid3.toString();
           Double a1=Double.parseDouble(ba_sic);
           Double a2=Double.parseDouble(ba_sic2);
           Double a3=a1+a2;

           // Toast.makeText(getApplicationContext(),"a -"+a1.toString()+"  "+a2.toString(),Toast.LENGTH_SHORT).show();
           my_h_cash.setText(String.format("%.1f",a3));
           db.close();



        }
        else if(p1.equals("3 Year"))
        {
            EditText age=(EditText)findViewById(R.id.elderage);

            String agedata=age.getText().toString();
            Integer ag=Integer.parseInt(agedata);
            String myband="";
            String myid2="";

            if (ag<=25)
            {
                myband="0-25";

            }
            else if(ag>25 && ag<=35)
            {
                myband="26-35";

            }
            else if(ag>35 && ag<=40)
            {
                myband="36-40";

            }
            else if(ag>40 && ag<=45)
            {
                myband="41-45";

            }
            else if(ag>45 && ag<=50)
            {
                myband="46-50";

            }
            else if(ag>50 && ag<=55)
            {
                myband="51-55";

            }
            else if(ag>55 && ag<=60)
            {
                myband="56-60";

            }
            else if(ag>60 && ag<=65)
            {
                myband="61-65";

            }
            else if(ag>65 && ag<=70)
            {
                myband="66-70";

            }
            else if(ag>70 && ag<=75)
            {
                myband="71-75";

            }
            else if(ag>75 && ag<=80)
            {
                myband="76-80";

            }
            else if(ag>80 && ag<=85)
            {
                myband="81-85";

            }
            else if(ag>85 && ag<=90)
            {
                myband="86-90";

            }
            else if(ag>90 && ag<=95)
            {
                myband="91-95";

            }
            else if(ag>95)
            {
                myband=">95";

            }


            Cursor res1=db.getHospitaldata(myband,fd,selectcash);
            while (res1.moveToNext())
            {
                myid2=res1.getString(0);

            }
            String ba_sic=myid2.toString();
            Integer ag2=ag+1;
            //  Toast.makeText(getApplicationContext(),""+ag2.toString(),Toast.LENGTH_SHORT).show();
            String myband2="" ;
            String myid3="";

            if (ag2<=25)
            {
                myband2="0-25";

            }
            else if(ag2>25 && ag2<=35)
            {
                myband2="26-35";

            }
            else if(ag2>35 && ag2<=40)
            {
                myband2="36-40";

            }
            else if(ag2>40 && ag2<=40)
            {
                myband2="41-45";

            }
            else if(ag2>40 && ag2<=50)
            {
                myband2="46-50";

            }
            else if(ag2>50 && ag2<=55)
            {
                myband2="51-55";

            }
            else if(ag2>55 && ag2<=60)
            {
                myband2="56-60";

            }
            else if(ag2>60 && ag2<=65)
            {
                myband2="61-65";

            }
            else if(ag2>65 && ag2<=70)
            {
                myband2="66-70";

            }
            else if(ag2>70 && ag2<=75)
            {
                myband2="71-75";

            }
            else if(ag2>75 && ag2<=80)
            {
                myband2="76-80";

            }
            else if(ag2>80 && ag2<=85)
            {
                myband2="81-85";

            }
            else if(ag2>85 && ag2<=90)
            {
                myband2="86-90";

            }
            else if(ag2>90 && ag2<=95)
            {
                myband2="91-95";

            }
            else if(ag2>95)
            {
                myband2=">95";

            }


            Cursor res2=db.getHospitaldata(myband2,fd,selectcash);
            while (res2.moveToNext())
            {
                myid3=res2.getString(0);

            }
            //  Toast.makeText(getApplicationContext(),""+myid2+"  "+myid3,Toast.LENGTH_SHORT).show();
            String ba_sic2=myid3.toString();

            Integer ag3=ag2+1;
            //  Toast.makeText(getApplicationContext(),""+ag2.toString(),Toast.LENGTH_SHORT).show();
            String myband3="";
            String myid="";

            if (ag3<=25)
            {
                myband3="0-25";

            }
            else if(ag3>25 && ag3<=35)
            {
                myband3="26-35";

            }
            else if(ag3>35 && ag3<=40)
            {
                myband3="36-40";

            }
            else if(ag3>40 && ag3<=40)
            {
                myband3="41-45";

            }
            else if(ag3>40 && ag3<=50)
            {
                myband3="46-50";

            }
            else if(ag3>50 && ag3<=55)
            {
                myband3="51-55";

            }
            else if(ag3>55 && ag3<=60)
            {
                myband3="56-60";

            }
            else if(ag3>60 && ag3<=65)
            {
                myband3="61-65";

            }
            else if(ag3>65 && ag3<=70)
            {
                myband3="66-70";

            }
            else if(ag3>70 && ag3<=75)
            {
                myband3="71-75";

            }
            else if(ag3>75 && ag3<=80)
            {
                myband3="76-80";

            }
            else if(ag3>80 && ag3<=85)
            {
                myband3="81-85";

            }
            else if(ag3>85 && ag3<=90)
            {
                myband3="86-90";

            }
            else if(ag3>90 && ag3<=95)
            {
                myband3="91-95";

            }
            else if(ag3>95)
            {
                myband3=">95";

            }

            Cursor res=db.getHospitaldata(myband3,fd,selectcash);
            while (res.moveToNext())
            {
                myid=res.getString(0);

            }
            //  Toast.makeText(getApplicationContext(),""+myid2+"  "+myid3,Toast.LENGTH_SHORT).show();
            String ba_sic3=myid.toString();

            Double r1=Double.parseDouble(ba_sic3);
            Double a1=Double.parseDouble(ba_sic);
            Double a2=Double.parseDouble(ba_sic2);
            Double a3=a1+a2+r1;

            // Toast.makeText(getApplicationContext(),"a -"+a1.toString()+"  "+a2.toString(),Toast.LENGTH_SHORT).show();
            my_h_cash.setText(String.format("%.1f",a3));
            db.close();
        }


    }

    private void AddOnTotal()
    {
        Double a1= Double.parseDouble(cashforhospital.getText().toString());
        Double a2=Double.parseDouble(Mater_Amount.getText().toString());
        Double a3=Double.parseDouble(mcriticself.getText().toString());
        Double a4=Double.parseDouble(criticaldep1.getText().toString());
        Double a5=Double.parseDouble(criticaldep2.getText().toString());
        Double a6=Double.parseDouble(criticaldep3.getText().toString());
        Double a7=Double.parseDouble(criticaldep4.getText().toString());
        Double total=a1+a2+a3+a4+a5+a6+a7;
        EditText mytotal=(EditText)findViewById(R.id.addontotal);
        mytotal.setText(String.format("%.1f", total));
        FinalTotal();
    }

    private void FinalTotal()
    {
        EditText getbasic=(EditText) findViewById(R.id.mybasic);
        EditText mytotal=(EditText)findViewById(R.id.addontotal);

        TextView p1=(TextView) findViewById(R.id.premium2);
        TextView p2=(TextView) findViewById(R.id.gst2);
        TextView p3=(TextView) findViewById(R.id.total2);

        Double b1=Double.parseDouble(getbasic.getText().toString());
        Double b2=Double.parseDouble(mytotal.getText().toString());
        Double dd=b1+b2;

        long bbk=Math.round(dd);
        p1.setText(""+bbk);

        Double pr=Double.parseDouble(p1.getText().toString());
        Double per=pr*18/100;
        long bbk2=Math.round(per);
        p2.setText(""+bbk2);

        Double t1=Double.parseDouble(p1.getText().toString());
        Double t2=Double.parseDouble(p2.getText().toString());

        Double bbb=t1+t2;

        long bbk3=Math.round(bbb);
        p3.setText(""+bbk3);



    }


    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
    public void sendEmail() {

        EditText sendlist = (EditText)findViewById(R.id.editTextDialogUserInput);
        // String email= "santospandey13@gmail.com";
        String subject = "Health Insurance quote";
        String message = "Dear Sir\n" + "Please find Health Insurance Quote\n\n\b\b\b\b\n" + "santosh pandey\n"+"new Delhi";
        String toto=sendlist.getText().toString();
        Toast.makeText(getApplicationContext(),""+sendlist.getText().toString(),Toast.LENGTH_SHORT).show();

        SendMail sm = new SendMail(this,subject, message,toto);
        // email,
        //Executing sendmail to send email
        sm.execute();
    }


    public void writepdffile()
    {
        Document doc = new Document(PageSize.A4);

        try {

            String  path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/HEALTH INSURANCE QUOTE";
            File dir = new File(path);
            if (!dir.exists()) {
                dir.mkdirs();
            }

            //String file = Environment.getExternalStorageDirectory().getPath()+ "/PANDEY.pdf";
            //String root = Environment.getExternalStorageDirectory().toString();
            // File myDIR= new File(root,"/PANDEYFOLDER");

            File file=new File(dir,"Health Insurance.PDF");

            PdfWriter.getInstance(doc, new FileOutputStream(file));
            doc.open();
            Paragraph p1 = new Paragraph("Health Insurance Quote\n\n\n");

            Font headfont=new Font(Font.FontFamily.TIMES_ROMAN,13,Font.BOLD);
            p1.setAlignment(ALIGN_CENTER);
            p1.setFont(headfont);
            doc.add(p1);
            Font Bfont=new Font(Font.FontFamily.TIMES_ROMAN,12,Font.BOLD);
            Font nfont=new Font(Font.FontFamily.TIMES_ROMAN,12);

            PdfPTable table =new PdfPTable(4);
            table.setWidthPercentage(100);
            float[] width={25,25,25,25};
            table.setWidths(width);

            insertcell(table,"Health Insurance Quote",Element.ALIGN_CENTER,Element.ALIGN_MIDDLE,4,1,Bfont);
            insertcell(table,"Policy Type",Element.ALIGN_CENTER,Element.ALIGN_MIDDLE,1,1,nfont);
            insertcell(table,spinner.getSelectedItem().toString(),Element.ALIGN_CENTER,Element.ALIGN_MIDDLE,1,1,nfont);
            insertcell(table,"Family Definition",Element.ALIGN_CENTER,Element.ALIGN_MIDDLE,1,1,nfont);
            insertcell(table,familydef.getSelectedItem().toString(),Element.ALIGN_CENTER,Element.ALIGN_MIDDLE,1,1,nfont);

            insertcell(table,"Zone",Element.ALIGN_CENTER,Element.ALIGN_MIDDLE,1,1,nfont);
            insertcell(table,myzo.getSelectedItem().toString(),Element.ALIGN_CENTER,Element.ALIGN_MIDDLE,1,1,nfont);

            insertcell(table,"Policy Term",Element.ALIGN_CENTER,Element.ALIGN_MIDDLE,1,1,nfont);
            insertcell(table,term.getSelectedItem().toString(),Element.ALIGN_CENTER,Element.ALIGN_MIDDLE,1,1,nfont);

            insertcell(table,"Plan Opted",Element.ALIGN_CENTER,Element.ALIGN_MIDDLE,2,1,nfont);
            insertcell(table,Planopted.getSelectedItem().toString(),Element.ALIGN_CENTER,Element.ALIGN_MIDDLE,2,1,nfont);


            insertcell(table,"Policy Start date",Element.ALIGN_CENTER,Element.ALIGN_MIDDLE,1,1,nfont);
            insertcell(table,mycal.getText().toString(),Element.ALIGN_CENTER,Element.ALIGN_MIDDLE,1,1,nfont);

            insertcell(table,"Dob of Elder",Element.ALIGN_CENTER,Element.ALIGN_MIDDLE,1,1,nfont);
            insertcell(table,myelderdate.getText().toString(),Element.ALIGN_CENTER,Element.ALIGN_MIDDLE,1,1,nfont);

            insertcell(table,"Elder Age",Element.ALIGN_CENTER,Element.ALIGN_MIDDLE,1,1,nfont);
            insertcell(table,eldage.getText().toString(),Element.ALIGN_CENTER,Element.ALIGN_MIDDLE,1,1,nfont);

            insertcell(table,"Elder Age Band",Element.ALIGN_CENTER,Element.ALIGN_MIDDLE,1,1,nfont);
            insertcell(table,myelderband.getText().toString(),Element.ALIGN_CENTER,Element.ALIGN_MIDDLE,1,1,nfont);

            insertcell(table,"Dependent Details",Element.ALIGN_CENTER,Element.ALIGN_MIDDLE,4,1,Bfont);

            insertcell(table,"Spouse Dob",Element.ALIGN_CENTER,Element.ALIGN_MIDDLE,1,1,nfont);
            insertcell(table,mmyspouse_dob.getText().toString(),Element.ALIGN_CENTER,Element.ALIGN_MIDDLE,1,1,nfont);

            insertcell(table,"Spouse Age",Element.ALIGN_CENTER,Element.ALIGN_MIDDLE,1,1,nfont);
            insertcell(table,spouse_age.getText().toString(),Element.ALIGN_CENTER,Element.ALIGN_MIDDLE,1,1,nfont);


            insertcell(table,"Child 1 Dob",Element.ALIGN_CENTER,Element.ALIGN_MIDDLE,1,1,nfont);
            insertcell(table,chield1_dob.getText().toString(),Element.ALIGN_CENTER,Element.ALIGN_MIDDLE,1,1,nfont);

            insertcell(table,"Child 1 Age",Element.ALIGN_CENTER,Element.ALIGN_MIDDLE,1,1,nfont);
            insertcell(table,chield1_age.getText().toString(),Element.ALIGN_CENTER,Element.ALIGN_MIDDLE,1,1,nfont);

            insertcell(table,"Child 2 Dob",Element.ALIGN_CENTER,Element.ALIGN_MIDDLE,1,1,nfont);
            insertcell(table,chield2_dob.getText().toString(),Element.ALIGN_CENTER,Element.ALIGN_MIDDLE,1,1,nfont);

            insertcell(table,"Child 2 Age",Element.ALIGN_CENTER,Element.ALIGN_MIDDLE,1,1,nfont);
            insertcell(table,chield2_age.getText().toString(),Element.ALIGN_CENTER,Element.ALIGN_MIDDLE,1,1,nfont);

            insertcell(table,"Child 3 Dob",Element.ALIGN_CENTER,Element.ALIGN_MIDDLE,1,1,nfont);
            insertcell(table,chield3_dob.getText().toString(),Element.ALIGN_CENTER,Element.ALIGN_MIDDLE,1,1,nfont);

            insertcell(table,"Child 3 Age",Element.ALIGN_CENTER,Element.ALIGN_MIDDLE,1,1,nfont);
            insertcell(table,chield3_age.getText().toString(),Element.ALIGN_CENTER,Element.ALIGN_MIDDLE,1,1,nfont);
            insertcell(table,"Add On Covers",Element.ALIGN_CENTER,Element.ALIGN_MIDDLE,4,1,Bfont);

            insertcell(table,"Hospital Cash",Element.ALIGN_CENTER,Element.ALIGN_MIDDLE,1,1,nfont);
            insertcell(table,spin_cash.getSelectedItem().toString(),Element.ALIGN_CENTER,Element.ALIGN_MIDDLE,1,1,nfont);
            insertcell(table,cashforhospital.getText().toString(),Element.ALIGN_CENTER,Element.ALIGN_MIDDLE,1,1,nfont);
            insertcell(table," ",Element.ALIGN_CENTER,Element.ALIGN_MIDDLE,1,1,nfont);


            insertcell(table,"Maternity",Element.ALIGN_CENTER,Element.ALIGN_MIDDLE,1,1,nfont);
            insertcell(table,spin_maternity.getSelectedItem().toString(),Element.ALIGN_CENTER,Element.ALIGN_MIDDLE,1,1,nfont);
            insertcell(table,Mater_Amount.getText().toString(),Element.ALIGN_CENTER,Element.ALIGN_MIDDLE,1,1,nfont);
            insertcell(table," ",Element.ALIGN_CENTER,Element.ALIGN_MIDDLE,1,1,nfont);


            insertcell(table,"Critical illness  Self",Element.ALIGN_CENTER,Element.ALIGN_MIDDLE,1,1,nfont);
            insertcell(table,spin_crit_ill.getSelectedItem().toString(),Element.ALIGN_CENTER,Element.ALIGN_MIDDLE,1,1,nfont);
            insertcell(table,mcriticself.getText().toString(),Element.ALIGN_CENTER,Element.ALIGN_MIDDLE,1,1,nfont);
            insertcell(table," ",Element.ALIGN_CENTER,Element.ALIGN_MIDDLE,1,1,nfont);

            insertcell(table,"Critical illness  Dep 1",Element.ALIGN_CENTER,Element.ALIGN_MIDDLE,1,1,nfont);
            insertcell(table,mcriticself.getText().toString(),Element.ALIGN_CENTER,Element.ALIGN_MIDDLE,1,1,nfont);




            insertcell(table,"Critical illness  Dep 2",Element.ALIGN_CENTER,Element.ALIGN_MIDDLE,1,1,nfont);
            insertcell(table,criticaldep2.getText().toString(),Element.ALIGN_CENTER,Element.ALIGN_MIDDLE,1,1,nfont);

            insertcell(table,"Critical illness  Dep 3",Element.ALIGN_CENTER,Element.ALIGN_MIDDLE,1,1,nfont);
            insertcell(table,criticaldep3.getText().toString(),Element.ALIGN_CENTER,Element.ALIGN_MIDDLE,1,1,nfont);

            insertcell(table,"Critical illness  Dep 4",Element.ALIGN_CENTER,Element.ALIGN_MIDDLE,1,1,nfont);
            insertcell(table,criticaldep4.getText().toString(),Element.ALIGN_CENTER,Element.ALIGN_MIDDLE,1,1,nfont);


            insertcell(table,"",Element.ALIGN_CENTER,Element.ALIGN_MIDDLE,4,1,nfont);

            insertcell(table,"Policy Premium",Element.ALIGN_CENTER,Element.ALIGN_MIDDLE,2,1,Bfont);
            insertcell(table,pp1.getText().toString(),Element.ALIGN_CENTER,Element.ALIGN_MIDDLE,2,1,Bfont);

            insertcell(table,"Gst 18%",Element.ALIGN_CENTER,Element.ALIGN_MIDDLE,2,1,Bfont);
            insertcell(table,pp2.getText().toString(),Element.ALIGN_CENTER,Element.ALIGN_MIDDLE,2,1,Bfont);

            insertcell(table,"Total Premiumm",Element.ALIGN_CENTER,Element.ALIGN_MIDDLE,2,1,Bfont);
            insertcell(table,pp3.getText().toString(),Element.ALIGN_CENTER,Element.ALIGN_MIDDLE,2,1,Bfont);


            doc.add(table);

            Paragraph lastpara =new Paragraph("\n\n -By- \nHealth Insurance Team",Bfont);
            lastpara.setAlignment(ALIGN_RIGHT);
            doc.add(lastpara);
        } catch (DocumentException de) {
            Toast.makeText(getApplicationContext(),"not create" ,Toast.LENGTH_LONG).show();
            Log.e("PDFCreator", "DocumentException:" + de);
        } catch (IOException e) {
            Toast.makeText(getApplicationContext(),"not availabe" ,Toast.LENGTH_LONG).show();
            Log.e("PDFCreator", "ioException:" + e);
        }
        finally {
            doc.close();
        }

//sendEmail();
    }



    private void insertcell(PdfPTable table, String text, int align, int align2, int colspan, int rowspan, Font font)
    {
        PdfPCell newcell=new PdfPCell(new Paragraph(text,font));
        newcell.setHorizontalAlignment(align);
        newcell.setPaddingLeft(5);
        newcell.setVerticalAlignment(align2);
        newcell.setColspan(colspan);
        newcell.setRowspan(rowspan);
        table.addCell(newcell);
        }

    public void send(View view)
    {
        final Context context = this;
        LayoutInflater Li= LayoutInflater.from(context);
        View promptview = Li.inflate(R.layout.prompt,null);
        AlertDialog.Builder alertbuilder = new AlertDialog.Builder(context);
        alertbuilder.setView(promptview);
        final EditText sendlist = (EditText) promptview.findViewById(R.id.editTextDialogUserInput);





        alertbuilder
                .setCancelable(false)
                .setPositiveButton("Send Mail", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i)
                    {

                        String regEx = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

                        String pattern=sendlist.getText().toString().trim();
                        Matcher matcherObj = Pattern.compile(regEx).matcher(pattern);

                        if (matcherObj.matches()) {

                            writepdffile();


                            // String email= "santospandey13@gmail.com";
                            String subject = "Health Insurance quote";
                            String message = "Dear Sir\n" + "Please find Health Insurance Quote\n\n\n\n\n\n\n" + "Health Insurance Team\n"+"new Delhi";
                            String toto=sendlist.getText().toString();


                            SendMail sm = new SendMail(Main2Activity.this,subject, message,toto);
                            // email,
                            //Executing sendmail to send email
                            sm.execute();

                        } else
                            {
                            Toast.makeText(getApplicationContext(),"Invalid Email Address", Toast.LENGTH_SHORT).show();
                        }






                    }
                })

                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
        AlertDialog alertDialog= alertbuilder.create();
        alertDialog.show();
        }



}
