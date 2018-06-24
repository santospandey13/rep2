package health.my.santosh.healthinsurance;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Databasehandler extends SQLiteAssetHelper
{
    public static final String DATABASE_NAME = "santosh11.db";
    public static final String TABLE_NAME = "pandey11";
    public static final String COL_1 = "AGE_BAND";
    public static final String COL_2 = "SUM_ASSURED";
    public static final String COL_3 = "TYPE";
    public static final String COL_4 = "PREMIUM";


    public static final String TABLE_NAME2 = "hospital1";
    public static final String HCOL_1 = "AGE_BAND";
    public static final String HCOL_2 = "TYPE";
    public static final String HCOL_3 = "AMOUNT";
    public static final String HCOL_4 = "PREMIUM";

    public static final String TABLE_NAME3 = "Critical11";
    public static final String CCOL_1 = "AGE_BAND";
    public static final String CCOL_2 = "AMOUNT";
    public static final String CCOL_3 = "PREMIUM";

    public static final String TABLE_NAME4 = "maternity11";
    public static final String MCOL_1 = "AGE_BAND";
    public static final String MCOL_2 = "TYPE";
    public static final String MCOL_3 = "PREMIUM";

    public static final String TABLE_NAME55 = "networkhospital";
    public static final String NCOL_1 = "H_NAME";
    public static final String NCOL_2 = "H_ADDRESS";
    public static final String NCOL_3 = "H_AREA";
    public static final String NCOL_4 = "H_CITY";
    public static final String NCOL_5 = "H_STATE";
    public static final String NCOL_6 = "H_CONTPERSON";
    public static final String NCOL_7 = "H_CONTNUMBER";
    public static final String NCOL_8 = "H_PINCODE";

public Context context;

    public Databasehandler(Context context) {

        super(context,DATABASE_NAME,null,1);
        this.context=context;
    }


    public List<Hospital> gethospital( String searchTerm)
    {
        List<Hospital> hdata= new ArrayList<>();
        hdata.clear();
        SQLiteDatabase db = getReadableDatabase();

        Cursor nh=db.rawQuery("SELECT H_NAME,H_ADDRESS,H_AREA,H_CITY FROM networkhospital "+" WHERE H_ADDRESS "+" LIKE '%"+searchTerm+"%'"+";",null);
        Hospital hospital;
        if(nh.moveToFirst())
        {
            do {
                hospital= new Hospital();

                hospital.setName(nh.getString(0));
                hospital.setAddress(nh.getString(1));
                hospital.setArea(nh.getString(2));
                hospital.setCity(nh.getString(3));
                hdata.add(hospital);
            }while (nh.moveToNext());
        }
        return hdata;

    }

    public List<Hospital> gethospitalAll()
    {
        List<Hospital> hdata= new ArrayList<>();
        hdata.clear();



        SQLiteDatabase db = getReadableDatabase();

        Cursor nh=db.rawQuery("SELECT H_NAME,H_ADDRESS,H_AREA,H_CITY FROM networkhospital ORDER BY H_NAME ASC",null);
        Hospital hospital;
        if(nh.moveToFirst())
        {
            do {
                hospital= new Hospital();

                hospital.setName(nh.getString(0));
                hospital.setAddress(nh.getString(1));
                hospital.setArea(nh.getString(2));
                hospital.setCity(nh.getString(3));
                hdata.add(hospital);
            }while (nh.moveToNext());
        }
        return hdata;

    }

    public Cursor retrieve(String searchTerm)
    {
        String[] columns={NCOL_2,NCOL_3,NCOL_4,NCOL_5};
        Cursor c = null;
        SQLiteDatabase db = getReadableDatabase();

        if(searchTerm != null && searchTerm.length()>0)
        {
            String sql="SELECT H_NAME,H_ADDRESS,H_AREA,H_CITY FROM networkhospital"+" WHERE H_ADDRESS "+" LIKE '%"+searchTerm+"%'  ORDER BY H_NAME ASC";
         c= db.rawQuery(sql,null);
            return c;
        }

        c=db.query(TABLE_NAME55,columns,null,null,null,null,null);
        return c;

    }


    public Cursor getNethospitaldata(String data1,String data2,String data3)
    {
        SQLiteDatabase db = getReadableDatabase();
        Cursor c=db.rawQuery("SELECT H_STATE,H_CONTPERSON,H_CONTNUMBER,H_PINCODE FROM "+TABLE_NAME55+" where "+NCOL_2+"='"+data1+"' AND "+NCOL_3+"= '"+data2+"' AND "+NCOL_4+"='"+data3+"'      " ,null);
        return c;
    }



    public Cursor getdata(String data1,String data2,String data3)
    {
        SQLiteDatabase db = getReadableDatabase();
        Cursor c=db.rawQuery("SELECT PREMIUM FROM pandey11"+" where AGE_BAND"+"='"+data1+"' AND SUM_ASSURED "+"= '"+data2+"' AND TYPE"+"='"+data3+"'      " ,null);
        return c;
    }
    public Cursor getHospitaldata(String data1,String data2,String data3)
    {
        SQLiteDatabase db = getReadableDatabase();
        Cursor c1=db.rawQuery("SELECT PREMIUM FROM "+TABLE_NAME2+" where "+HCOL_1+"='"+data1+"' AND "+HCOL_2+"= '"+data2+"' AND "+HCOL_3+"='"+data3+"'      " ,null);
        return c1;
    }
    public Cursor getCriticalldata(String data1,String data2)
    {
        SQLiteDatabase db = getReadableDatabase();
        Cursor c=db.rawQuery("SELECT PREMIUM FROM "+TABLE_NAME3+" where "+CCOL_1+"='"+data1+"' AND "+CCOL_2+"= '"+data2+"'" ,null);
        return c;
    }
    public Cursor getMaternitydata(String data1,String data2)
    {
        SQLiteDatabase db = getReadableDatabase();
        Cursor c=db.rawQuery("SELECT PREMIUM FROM "+TABLE_NAME4+" where "+MCOL_1+"='"+data1+"' AND "+MCOL_2+"= '"+data2+"'" ,null);
        return c;
    }

}
