package iteso.com.rentstudio;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import iteso.com.rentstudio.beans.Property;
import iteso.com.rentstudio.beans.Rent;

public class ActivityRentScreen extends AppCompatActivity {
    public TextView address;
    public TextView lessor;
    public TextView date;
    public TextView phone;
    public TextView cost;
    public TextView mail;
    Rent rent;
    int i;
    Button delete;
    private DatabaseReference databaseReference;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rent_screen);
        address=findViewById(R.id.rent_screen_address);
        lessor=findViewById(R.id.rent_screen_lessor);
        date=findViewById(R.id.rent_screen_payday);
        phone=findViewById(R.id.rent_screen_phone);
        cost=findViewById(R.id.rent_screen_cost);
        mail=findViewById(R.id.rent_screen_mail);
        delete=findViewById(R.id.delete_button);
        if(getIntent().getExtras()!=null){
            rent = getIntent().getParcelableExtra("ITEM");
            if (rent != null) {
                address.setText("Dirección: "+rent.getAddress());
                lessor.setText("Arrendador: "+rent.getLessor());
                date.setText("Fecha: "+rent.getDate());
                phone.setText("Teléfono: "+rent.getPhone());
                cost.setText("Costo: "+Integer.toString(rent.getCost()));
                mail.setText("Correo: "+rent.getMail());

            }}
        mAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference().child(mAuth.getCurrentUser().getUid());

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String lessor="lessor_1";
                i=1;
                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for(DataSnapshot snapshot : dataSnapshot.child("properties").getChildren()) {
                            Property aux = snapshot.getValue(Property.class);
                            if(aux.getAddress().equals(rent.getAddress()) && i == 1){
                                databaseReference.child("properties").child(snapshot.getKey()).child("lessor").setValue(lessor);
                                // databaseReference.child("properties").child(snapshot.getKey()).child("payday").setValue(Integer.toString(payday));

                            }
                        }

                        i=0;
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

                Intent backHome = new Intent(ActivityRentScreen.this, Activity_Main_Screen.class);
                startActivity(backHome);
                finish();

            }
        });

    }
}