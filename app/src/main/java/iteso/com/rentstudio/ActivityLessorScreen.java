package iteso.com.rentstudio;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import iteso.com.rentstudio.beans.Lessor;
import iteso.com.rentstudio.beans.Property;

public class ActivityLessorScreen extends AppCompatActivity {
TextView email, name, phone;
Button editar, eliminar;
Lessor l;
    String name1;
    private DatabaseReference databaseReference;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lessor_screen2);
        email=findViewById(R.id.lemail);
        name=findViewById(R.id.llessor);
        phone=findViewById(R.id.lphone);
        editar=findViewById(R.id.lbutton);
        eliminar=findViewById(R.id.eliminarLessor);



        if(getIntent().getExtras()!=null){
            l = getIntent().getParcelableExtra("ITEM");
            if (l != null) {
                email.setText("Email:"+ l.getEmail());
                phone.setText("Teléfono: "+l.getPhone());
                name.setText(l.getName()+" "+ l.getLastname());
                name1=l.getName();

            }
        }
        editar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent3=new Intent(ActivityLessorScreen.this,ActivityEditLessor.class);
                intent3.putExtra("ITEM",l);
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
                for(DataSnapshot snapshot : dataSnapshot.child("lessors").getChildren()) {
                    Lessor aux = snapshot.getValue(Lessor.class);
                    if(aux.getName().equals(name1)){
                        databaseReference.child("lessors").child(snapshot.getKey()).removeValue();

                    }
                }

            }


            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        Intent backHome = new Intent(ActivityLessorScreen.this, Activity_Main_Screen.class);
        backHome.setFlags(backHome.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK |Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(backHome);
        finish();
    }
});

    }
}
