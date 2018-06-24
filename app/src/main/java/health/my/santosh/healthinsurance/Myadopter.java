package health.my.santosh.healthinsurance;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class Myadopter extends RecyclerView.Adapter<Myadopter.ViewHolder> {

   //private List<Hospital> ;
   List<Hospital> mhospital;


   private Context context;
   private RecyclerView recyclerview;

    public Myadopter(List<Hospital> mhospital, Context context,RecyclerView recyclerView) {
        this.mhospital = mhospital;

        this.context = context;
       this.recyclerview = recyclerview;
    }

    @Override
    public Myadopter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater= LayoutInflater.from(parent.getContext());
        View v=inflater.inflate(R.layout.rowlaypot,parent,false);
        ViewHolder viewHolder=new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final Myadopter.ViewHolder holder, final int position) {

        final Hospital hospital=mhospital.get(position);

        holder.textView1.setText(hospital.getName());
        holder.textView2.setText(hospital.getAddress());
        holder.textView3.setText(hospital.getArea());
        holder.textView4.setText(hospital.getCity());

        Animation animation = AnimationUtils.loadAnimation(context,R.anim.entry_from_right);
        holder.itemView.startAnimation(animation);





        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            String Mname =     hospital.getName();
                String Maddress= hospital.getAddress();
                String Marea= hospital.getArea();
                String Mcity= hospital.getCity();
                Intent intent=new Intent(context,HospitalDetail.class);
                intent.putExtra("Name",Mname);
                intent.putExtra("Address",Maddress);
                intent.putExtra("Area",Marea);
                intent.putExtra("City",Mcity);
                context.startActivity(intent);
                ((Activity) context).overridePendingTransition(R.anim.entry_from_right,R.anim.entry_from_right);


                }
        });

    }


    @Override
    public int getItemCount() {
        return mhospital.size();
    }






    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textView1;
        public TextView textView2;
        public TextView textView3;
        public TextView textView4;

        public View layout;

        public ViewHolder(View itemView) {
            super(itemView);
            layout=itemView;
            textView1=(TextView)itemView.findViewById(R.id.hname);
            textView2=(TextView)itemView.findViewById(R.id.haddress);
            textView3=(TextView)itemView.findViewById(R.id.harea);
            textView4=(TextView)itemView.findViewById(R.id.hcity);

        }
    }
}
