package iteso.com.rentstudio;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import iteso.com.rentstudio.ActivityRentScreen;
import iteso.com.rentstudio.Activity_Main_Screen;
import iteso.com.rentstudio.beans.Lessor;
import iteso.com.rentstudio.beans.Property;
import iteso.com.rentstudio.beans.Rent;

/**
 * Created by hecto on 26/02/2018.
 */

public class Adapter_Rent_Card extends RecyclerView.Adapter<Adapter_Rent_Card.ViewHolder>{
    ArrayList<Rent> mDataSet;
    private Context context;
    private int fragment;

    public Adapter_Rent_Card(int fragment,Context context, ArrayList<Rent> myDataSet){
        mDataSet = myDataSet;
        this.context = context;
        this.fragment=fragment;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_rent, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        //public RelativeLayout mEventLayout;

        public TextView mHouseName;
        public TextView mLessorName;
        public TextView mDate;
        public LinearLayout mEventLayout1;



        public ViewHolder(View v){
            super(v);
            //mEventLayout = (RelativeLayout) v.findViewById(R.id.item_product_layout);
            mEventLayout1 = (LinearLayout) v.findViewById(R.id.rent_card_layout);//////////////////////////
            mHouseName = v.findViewById(R.id.rent_card_house_name);
            mLessorName = v.findViewById(R.id.rent_card_lessor_name);
            mDate = v.findViewById(R.id.rent_card_date_text);

        }
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.mHouseName.setText(mDataSet.get(position).getAddress());
        holder.mLessorName.setText(mDataSet.get(position).getLessor());
        holder.mDate.setText(mDataSet.get(position).getDate());
        holder.mEventLayout1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Rent rent=new Rent();
                rent.setAddress(mDataSet.get(position).getAddress());
                rent.setLessor(mDataSet.get(position).getLessor());
                rent.setDate(mDataSet.get(position).getDate());
                rent.setPhone(mDataSet.get(position).getPhone());
                rent.setCost(mDataSet.get(position).getCost());
                rent.setMail(mDataSet.get(position).getMail());

                Intent intent=new Intent(context,ActivityRentScreen.class);
                intent.putExtra("ITEM",rent);
                intent.putExtra("FRAGMENT", fragment);
                ((Activity_Main_Screen) context).startActivityForResult(intent,9999);

            }
        });



    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }
}