package iteso.com.rentstudio;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import iteso.com.rentstudio.beans.Lessor;

public class Adapter_Lessor_Card extends RecyclerView.Adapter<Adapter_Lessor_Card.ViewHolder> {
    ArrayList<Lessor> mDataSet;
    private Context context;
    int fragment;

    public Adapter_Lessor_Card(int fragment,Context context, ArrayList<Lessor> myDataSet) {
        mDataSet = myDataSet;
        this.context = context;
        this.fragment=fragment;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_lessor, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        //public RelativeLayout mEventLayout;

        public TextView mLessorName;
        public TextView mLessorPhone;
        public TextView mLessorEmail;
        public LinearLayout mEventLayout;


        public ViewHolder(View v) {
            super(v);
            //mEventLayout = (RelativeLayout) v.findViewById(R.id.item_product_layout);

            mLessorName = v.findViewById(R.id.lessor_card_name_text);
            mLessorPhone = v.findViewById(R.id.lessor_card_phone_text);
            mLessorEmail = v.findViewById(R.id.lessor_card_email_text);
            mEventLayout = (LinearLayout) v.findViewById(R.id.lessor_card_layout);//////////////////////////

        }
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.mLessorName.setText(mDataSet.get(position).getName());
        holder.mLessorPhone.setText(mDataSet.get(position).getPhone());
        holder.mLessorEmail.setText(mDataSet.get(position).getEmail());
        holder.mLessorPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(Intent.ACTION_DIAL,
                        Uri.parse("tel:" + mDataSet.get(position).getPhone()));
                context.startActivity(intent2);
            }
        });
        holder.mLessorEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Intent.ACTION_SENDTO);
                intent.setData(Uri.parse("mailto:" + mDataSet.get(position).getEmail()));
                context.startActivity(intent);

            }
        });

        holder.mEventLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Lessor iit=new Lessor();
                iit.setEmail(mDataSet.get(position).getEmail());
                iit.setLastname(mDataSet.get(position).getLastname());
                iit.setName(mDataSet.get(position).getName());
                iit.setPhone(mDataSet.get(position).getPhone());

                Intent intent3=new Intent(context,ActivityLessorScreen.class);
                intent3.putExtra("ITEM",iit);
                intent3.putExtra("FRAGMENT", fragment);
                ((Activity_Main_Screen) context).startActivityForResult(intent3,9999);

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
