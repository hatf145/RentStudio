package iteso.com.rentstudio;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import iteso.com.rentstudio.beans.Rent;

/**
 * Created by hecto on 26/02/2018.
 */

public class Adapter_Rent_Card extends RecyclerView.Adapter<Adapter_Rent_Card.ViewHolder>{
    ArrayList<Rent> mDataSet;
    private Context context;

    public Adapter_Rent_Card(Context context, ArrayList<Rent> myDataSet){
        mDataSet = myDataSet;
        this.context = context;
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

        public ViewHolder(View v){
            super(v);
            //mEventLayout = (RelativeLayout) v.findViewById(R.id.item_product_layout);

            mHouseName = v.findViewById(R.id.rent_card_house_name);
            mLessorName = v.findViewById(R.id.rent_card_lessor_name);
            mDate = v.findViewById(R.id.rent_card_date_text);
        }
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.mHouseName.setText(mDataSet.get(position).getProperty());
        holder.mLessorName.setText(mDataSet.get(position).getLessor());
        holder.mDate.setText(mDataSet.get(position).getDate());
    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }
}
