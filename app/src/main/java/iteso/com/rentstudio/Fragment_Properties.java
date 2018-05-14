package iteso.com.rentstudio;

import android.content.Intent;
import android.os.Bundle;
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

import iteso.com.rentstudio.beans.Property;

public class Fragment_Properties extends android.support.v4.app.Fragment {
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<Property> myDataSet = new ArrayList<>();
    private DatabaseReference databaseReference;
    private FirebaseAuth mAuth;
    private int userType;
    private String userName;

    public Fragment_Properties(){
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

        mAdapter = new Adapter_Property_Card(0,getActivity(), myDataSet, userType);
        recyclerView.setAdapter(mAdapter);

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void fillArrendador(){
        databaseReference = FirebaseDatabase.getInstance().getReference().child("properties");

        if(userType == 1) {
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    myDataSet.clear();
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        Property aux = snapshot.getValue(Property.class);
                        if(aux.getLessor().equals("lessor_1") || aux.getLessor().equals(userName)){
                            myDataSet.add(aux);
                            mAdapter.notifyDataSetChanged();
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
        databaseReference = FirebaseDatabase.getInstance().getReference().child(mAuth.getCurrentUser().getUid()).child("properties");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(userType == 0){
                    myDataSet.clear();
                }
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    Property aux = snapshot.getValue(Property.class);
                    myDataSet.add(aux);
                    mAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

}

