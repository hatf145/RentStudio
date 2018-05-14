package iteso.com.rentstudio;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Timer;
import java.util.TimerTask;

import iteso.com.rentstudio.beans.Property;

public class ActivityPropertyScreen extends AppCompatActivity {
    public TextView address;
    public TextView cost;
    public TextView availability;
    public TextView name;
    public TextView payday;
    public TextView state;
    public TextView town;
    Property property;
    Button editar, eliminar;
    int userType;

    private DatabaseReference databaseReference;
    private FirebaseAuth mAuth;
    String name1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_property_screen);

        userType = getIntent().getIntExtra("userType", 0);

        address = findViewById(R.id.property_address);
        cost = findViewById(R.id.property_money);
        availability = findViewById(R.id.property_availability);
        name = findViewById(R.id.property_house);
        payday = findViewById(R.id.property_payday);
        state = findViewById(R.id.property_state);
        town = findViewById(R.id.property_town);
        editar = findViewById(R.id.activity_property_screen_button);
        eliminar = findViewById(R.id.eliminarPropiedad);

        if(getIntent().getExtras()!=null){
            property = getIntent().getParcelableExtra("ITEM");
            name1=property.getName();
            if (property != null) {
                address.setText("Dirección: "+property.getAddress());
                cost.setText("Renta: "+Integer.toString(property.getCost()));
                if(property.getLessor().equals("lessor_1")){
                    availability.setText("Disponible");
                }else{
                    availability.setText("Arrendador: "+property.getLessor());
                    payday.setText("Día de pago: "+Integer.toString(property.getPayday()));
                }
                name.setText(property.getName());
                state.setText("Estado: "+property.getState());
                town.setText("Ciudad: "+property.getTown());

            }
        }

        editar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent3=new Intent(ActivityPropertyScreen.this,ActivityEditProperty.class);
                intent3.putExtra("ITEM",property);
                intent3.putExtra("userType", userType);
                startActivityForResult(intent3,9999);
            }
        });

        mAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference().child(mAuth.getCurrentUser().getUid());
        
        eliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for(DataSnapshot snapshot : dataSnapshot.child("properties").getChildren()) {
                            Property aux = snapshot.getValue(Property.class);
                            if(aux.getName().equals(name1) ) {
                                databaseReference.child("properties").child(snapshot.getKey()).removeValue();
                            }
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

                TimerTask task = new TimerTask() {
                    @Override
                    public void run() {
                        if(userType == 0){
                            databaseReference = FirebaseDatabase.getInstance().getReference().child("properties");
                            databaseReference.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    for(DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                        Property aux = snapshot.getValue(Property.class);
                                        if(aux.getName().equals(name1) ) {
                                            databaseReference.child(snapshot.getKey()).removeValue();
                                        }
                                    }
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });
                        }
                    }
                };
                Timer timer = new Timer();
                timer.schedule(task,1000);

                Intent backHome = new Intent(ActivityPropertyScreen.this, Activity_Main_Screen.class);
                backHome.setFlags(backHome.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK |Intent.FLAG_ACTIVITY_CLEAR_TOP);
                backHome.putExtra("userType", userType);
                startActivity(backHome);
                finish();
            }
        });

    }


}
