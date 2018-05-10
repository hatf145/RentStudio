package iteso.com.rentstudio;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import iteso.com.rentstudio.beans.Property;

public class Activity_Register_Property extends AppCompatActivity {
    EditText etName, etDirection, etTown, etState, etRent;
    String sName, sDirection, sTown, sState, sRent;
    int iRent;
    Button btnProperty;
    DatabaseReference databaseReference;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__register__property);
        etName =findViewById(R.id.activity_registerProperty_name);
        etDirection =findViewById(R.id.activity_registerProperty_direction);
        etTown =findViewById(R.id.activity_registerProperty_town);
        etState =findViewById(R.id.activity_registerProperty_state);
        etRent =findViewById(R.id.activity_registerProperty_rent);
        btnProperty =findViewById(R.id.activity_bregister_property);

        databaseReference = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();

        btnProperty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sName = etName.getText().toString();
                sDirection = etDirection.getText().toString();
                sTown = etTown.getText().toString();
                sState = etState.getText().toString();
                sRent = etRent.getText().toString();

                if(sName != null && sDirection != null && sTown != null && sState != null && sRent != null) {
                    iRent = Integer.parseInt(sRent);
                    Property aux = new Property(sDirection, iRent, "lessor_1", sName, 0, sState, sTown);
                    String key = databaseReference.push().getKey();
                    databaseReference.child(mAuth.getCurrentUser().getUid()).child("properties").child(key).setValue(aux);

                    Intent loginIntent = new Intent(Activity_Register_Property.this,
                            Activity_Main_Screen.class);
                    loginIntent.setFlags(loginIntent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK |Intent.FLAG_ACTIVITY_CLEAR_TOP);

                    startActivity(loginIntent);
                }
            }
        });
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putString("etName", etName.getText().toString());
        outState.putString("etDirection", etDirection.getText().toString());
        outState.putString("etTown", etTown.getText().toString());
        outState.putString("etState", etState.getText().toString());
        outState.putString("etRent", etRent.getText().toString());
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        if(savedInstanceState.getString("etName") != null)
            etName.setText(savedInstanceState.getString("etName"));
        if(savedInstanceState.getString("etTown") != null)
            etTown.setText(savedInstanceState.getString("etTown"));
        if(savedInstanceState.getString("etState") != null)
            etState.setText(savedInstanceState.getString("etState"));
        if(savedInstanceState.getString("etRent") != null)
            etRent.setText(savedInstanceState.getString("etRent"));
    }
}
