
package iteso.com.rentstudio;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import iteso.com.rentstudio.beans.Property;


public class Adapter_Property_Card extends RecyclerView.Adapter<Adapter_Property_Card.ViewHolder>{
    ArrayList<Property> mDataSet;
    private Context context;
    int fragment;

    public Adapter_Property_Card(int fragment,Context context, ArrayList<Property> myDataSet){
        mDataSet = myDataSet;
        this.context = context;
        this.fragment=fragment;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_property, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public TextView cost;
        public TextView name;
        public LinearLayout mEventLayout;

        public ViewHolder(View v){
            super(v);
            name = v.findViewById(R.id.property_card_house_text);
           cost = v.findViewById(R.id.property_card_rent_text);
            mEventLayout = (LinearLayout) v.findViewById(R.id.property_card_layout);
        }
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
       holder.cost.setText(String.valueOf(mDataSet.get(position).getCost()));
        holder.name.setText(mDataSet.get(position).getName());

        holder.mEventLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Property iit=new Property();
                iit.setAddress(mDataSet.get(position).getAddress());
                iit.setCost(mDataSet.get(position).getCost());
                iit.setLessor(mDataSet.get(position).getLessor());
                iit.setName(mDataSet.get(position).getName());
                iit.setPayday(mDataSet.get(position).getPayday());
                iit.setState(mDataSet.get(position).getState());
                iit.setTown(mDataSet.get(position).getTown());

                Intent intent=new Intent(context,ActivityPropertyScreen.class);
                intent.putExtra("ITEM",iit);
                intent.putExtra("FRAGMENT", fragment);
                ((Activity_Main_Screen) context).startActivityForResult(intent,9999);

            }
        });
    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }
}
