package iteso.com.rentstudio;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import iteso.com.rentstudio.beans.Lessor;
import iteso.com.rentstudio.beans.Property;

public class ActivityEditLessor extends AppCompatActivity {
    EditText etName, etLastname, etEmail, etPhone;
    Button save;
    Lessor l;
    String name;
    int i;
    private DatabaseReference databaseReference;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_lessor);

        etName = findViewById(R.id.activity_edit_name);
        etLastname = findViewById(R.id.activity_edit_lastname);
        etEmail = findViewById(R.id.activity_edit_email);
        etPhone = findViewById(R.id.activity_edit_cellphone);
        save = findViewById(R.id.save_lessor);

        if(getIntent().getExtras()!=null){
            l = getIntent().getParcelableExtra("ITEM");
            if (l != null) {
                name =l.getName();
                etName.setText(l.getName());
                etLastname.setText(l.getLastname());
                etEmail.setText(l.getEmail());
                etPhone.setText(l.getPhone());

            }
        }
        mAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference().child(mAuth.getCurrentUser().getUid());
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               i=1;
                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for(DataSnapshot snapshot : dataSnapshot.child("lessors").getChildren()) {
                            Lessor aux = snapshot.getValue(Lessor.class);
                            if(aux.getName().equals(name) && i == 1){
                                databaseReference.child("lessors").child(snapshot.getKey()).child("name").setValue(etName.getText().toString());
                                databaseReference.child("lessors").child(snapshot.getKey()).child("lastname").setValue(etLastname.getText().toString());
                                databaseReference.child("lessors").child(snapshot.getKey()).child("email").setValue(etEmail.getText().toString());
                                databaseReference.child("lessors").child(snapshot.getKey()).child("phone").setValue(etPhone.getText().toString());

                            }
                        }
                        for(DataSnapshot snapshot : dataSnapshot.child("properties").getChildren()) {
                            Property aux = snapshot.getValue(Property.class);
                            if(aux.getLessor().equals(name) && i == 1){
                                databaseReference.child("properties").child(snapshot.getKey()).child("lessor").setValue(etName.getText().toString());

                            }
                        }
                        i=0;
                    }


                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

                Intent backHome = new Intent(ActivityEditLessor.this, Activity_Main_Screen.class);
                startActivity(backHome);
                finish();
            }
        });
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString("etName", etName.getText().toString());
        outState.putString("lastname", etLastname.getText().toString());
        outState.putString("email", etEmail.getText().toString());
        outState.putString("phone", etPhone.getText().toString());

        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        if(savedInstanceState.getString("etName") != null)
            etName.setText(savedInstanceState.getString("etName"));

        if(savedInstanceState.getString("lastname") != null)
            etLastname.setText(savedInstanceState.getString("lastname"));

        if(savedInstanceState.getString("email") != null)
            etEmail.setText(savedInstanceState.getString("email"));

        if(savedInstanceState.getString("phone") != null)
            etPhone.setText(savedInstanceState.getString("phone"));
    }
}
