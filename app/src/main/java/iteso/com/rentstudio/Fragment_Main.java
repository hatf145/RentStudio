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

import iteso.com.rentstudio.beans.Lessor;
import iteso.com.rentstudio.beans.Property;
import iteso.com.rentstudio.beans.Rent;
import iteso.com.rentstudio.beans.User;


public class Fragment_Main extends android.support.v4.app.Fragment {
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<Rent> myDataSet = new ArrayList<>();
    private DatabaseReference databaseReference;
    private FirebaseAuth mAuth;
    private GregorianCalendar calendar = new GregorianCalendar();
    private int userType, red;
    private String userName;

    public Fragment_Main(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        userType = getArguments().getInt("userType");
        System.out.println("onFragment: " + userType);

        mAuth = FirebaseAuth.getInstance();

        databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mAuth = FirebaseAuth.getInstance();
                userName = dataSnapshot.child(mAuth.getCurrentUser().getUid()).child("info").child("name").getValue(String.class);
                System.out.println("NAME " + userName);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        fillArrendador();

        View view = inflater.inflate(R.layout.fragment_main_screen, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.fragment_main_recycler_view);

        recyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new Adapter_Rent_Card(1,getActivity(), myDataSet);
        recyclerView.setAdapter(mAdapter);

        return view;
    }

    public void fillArrendador(){
        databaseReference = FirebaseDatabase.getInstance().getReference();
        if(userType == 1) {
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    myDataSet.clear();
                    for (DataSnapshot snapshot : dataSnapshot.child("properties").getChildren()) {
                        Property aux = snapshot.getValue(Property.class);
                        if (aux.getLessor().equals(userName)) {
                            red = 0;
                            for (DataSnapshot snapshot2 : dataSnapshot.child(mAuth.getCurrentUser().getUid()).getChildren()) {
                                if(red == 0) {
                                    User aux2 = snapshot2.getValue(User.class);
                                    if (aux2.getName().equals(aux.getLessor())) {
                                        Rent auxRent = new Rent(aux.getAddress(), aux.getLessor(), myDate(aux.getPayday()), aux2.getPhone(), aux.getCost(), aux2.getEmail());
                                        myDataSet.add(auxRent);
                                        mAdapter.notifyDataSetChanged();
                                        red = 1;
                                    }
                                }
                            }
                        }
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }

        fillArrendatario();
    }

    public void fillArrendatario(){
        databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(userType == 0){
                    myDataSet.clear();
                }
                for(DataSnapshot snapshot : dataSnapshot.child(mAuth.getCurrentUser().getUid()).child("properties").getChildren()){
                    Property aux = snapshot.getValue(Property.class);
                    if(aux.getLessor()!="lessor_1") {
                        for(DataSnapshot snapshot2 : dataSnapshot.child(mAuth.getCurrentUser().getUid()).child("lessors").getChildren()){
                            Lessor aux2 = snapshot2.getValue(Lessor.class);
                            if(aux2.getName().equals(aux.getLessor())) {
                                Rent auxRent = new Rent(aux.getAddress(), aux.getLessor(), myDate(aux.getPayday()),aux2.getPhone(),aux.getCost(),aux2.getEmail());
                                myDataSet.add(auxRent);
                                mAdapter.notifyDataSetChanged();
                            }
                        }
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
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