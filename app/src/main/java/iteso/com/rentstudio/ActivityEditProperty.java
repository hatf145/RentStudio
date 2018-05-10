package iteso.com.rentstudio;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import iteso.com.rentstudio.beans.Property;

public class ActivityEditProperty extends AppCompatActivity {
    EditText name, address, town, state, rent;
    Button bproperty;
    Property propert;
    int i;
    private DatabaseReference databaseReference;
    String name1;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_property);

        name=findViewById(R.id.activity_editProperty_name);
        address=findViewById(R.id.activity_editProperty_direction);
        town=findViewById(R.id.activity_editProperty_town);
        state=findViewById(R.id.activity_editProperty_state);
        rent=findViewById(R.id.activity_editProperty_rent);
        bproperty=findViewById(R.id.activity_edit_property);

        if(getIntent().getExtras()!=null){
            propert = getIntent().getParcelableExtra("ITEM");
            if (propert != null) {
                name1=propert.getName();
                address.setText(propert.getAddress());
                rent.setText(propert.getCost().toString());
                name.setText(propert.getName());
                state.setText(propert.getState());
                town.setText(propert.getTown());

            }
        }
        mAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference().child(mAuth.getCurrentUser().getUid());
        bproperty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i=1;
                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for(DataSnapshot snapshot : dataSnapshot.child("properties").getChildren()) {
                            Property aux = snapshot.getValue(Property.class);
                            if(aux.getName().equals(name1) && i == 1){
                                databaseReference.child("properties").child(snapshot.getKey()).child("name").setValue(name.getText().toString());
                                databaseReference.child("properties").child(snapshot.getKey()).child("address").setValue(address.getText().toString());
                                databaseReference.child("properties").child(snapshot.getKey()).child("town").setValue(town.getText().toString());
                                databaseReference.child("properties").child(snapshot.getKey()).child("state").setValue(state.getText().toString());
                             ////////////////////////
                                // databaseReference.child("properties").child(snapshot.getKey()).child("cost").setValue(rent.getText().toString());

                            }
                        }

                        i=0;
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

                Intent backHome = new Intent(ActivityEditProperty.this, Activity_Main_Screen.class);
                startActivity(backHome);
                finish();
            }

        });
    }



    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putString("name", name.getText().toString());
        outState.putString("direction", address.getText().toString());
        outState.putString("town", town.getText().toString());
        outState.putString("state", state.getText().toString());
        outState.putString("rent", rent.getText().toString());
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        if(savedInstanceState.getString("name") != null)
            name.setText(savedInstanceState.getString("name"));
        if(savedInstanceState.getString("town") != null)
            town.setText(savedInstanceState.getString("town"));
        if(savedInstanceState.getString("state") != null)
            state.setText(savedInstanceState.getString("state"));
        if(savedInstanceState.getString("rent") != null)
            rent.setText(savedInstanceState.getString("rent"));

    }
}

