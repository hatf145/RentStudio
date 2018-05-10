package iteso.com.rentstudio;

import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import iteso.com.rentstudio.beans.Property;
import iteso.com.rentstudio.beans.Rent;


public class Fragment_Main extends android.support.v4.app.Fragment {
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    ArrayList<Rent> myDataSet = new ArrayList<>();
    private DatabaseReference databaseReference;
    private FirebaseAuth mAuth;
    private GregorianCalendar calendar = new GregorianCalendar();

    public Fragment_Main(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference().child(mAuth.getCurrentUser().getUid());

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                myDataSet.clear();
                for(DataSnapshot snapshot : dataSnapshot.child("properties").getChildren()){
                    Property aux = snapshot.getValue(Property.class);
                    if(!aux.getLessor().equals("lessor_1")) {
                        Rent auxRent = new Rent(aux.getName(), aux.getLessor(), myDate(aux.getPayday()));
                        myDataSet.add(auxRent);
                        mAdapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        View view = inflater.inflate(R.layout.fragment_main_screen, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.fragment_main_recycler_view);

        recyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new Adapter_Rent_Card(getActivity(), myDataSet);
        recyclerView.setAdapter(mAdapter);

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    public String myDate(int day){
        switch(calendar.get(Calendar.MONTH) + 1){
            case 1:
                return day + " / Enero";
            case 2:
                return day + " / Febrero";
            case 3:
                return day + " / Marzo";
            case 4:
                return day + " / Abril";
            case 5:
                return day + " / Mayo";
            case 6:
                return day + " / Junio";
            case 7:
                return day + " / Julio";
            case 8:
                return day + " / Agosto";
            case 9:
                return day + " / Septiembre";
            case 10:
                return day + " / Octubre";
            case 11:
                return day + " / Noviembre";
            case 12:
                return day + " / Diciembe";
                default:
                    return day + " / Enero";
        }
    }
}
