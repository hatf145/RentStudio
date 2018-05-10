package iteso.com.rentstudio;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import iteso.com.rentstudio.beans.Lessor;

public class Activity_Register_Lessor extends AppCompatActivity {
    EditText etName, etLastname, etEmail, etPhone;
    String sName, sLastName, sEmail, sPhone;
    Button btnRegisterLessor;
    DatabaseReference databaseReference;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__register__lessor);

        databaseReference = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();

        etName = findViewById(R.id.activity_registerLessor_name);
        etLastname = findViewById(R.id.activity_registerLessor_lastname);
        etEmail = findViewById(R.id.activity_registerLessor_email);
        etPhone = findViewById(R.id.activity_registerLessor_cellphone);
        btnRegisterLessor = findViewById(R.id.activity_registerLessor_register);

        btnRegisterLessor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sName = etName.getText().toString();
                sLastName = etLastname.getText().toString();
                sEmail = etEmail.getText().toString();
                sPhone = etPhone.getText().toString();

                if(sName != null && sLastName != null && sEmail != null && sPhone != null){
                    Lessor aux = new Lessor(sEmail, sLastName, sName, sPhone);
                    String key = databaseReference.push().getKey();
                    databaseReference.child(mAuth.getCurrentUser().getUid()).child("lessors").child(key).setValue(aux);

                    Intent loginIntent = new Intent(Activity_Register_Lessor.this,
                            Activity_Main_Screen.class);
                    loginIntent.setFlags(loginIntent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK |Intent.FLAG_ACTIVITY_CLEAR_TOP);

                    startActivity(loginIntent);
                }
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
