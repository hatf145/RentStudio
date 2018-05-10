package iteso.com.rentstudio;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import iteso.com.rentstudio.beans.User;

public class Activity_Register_User extends AppCompatActivity implements View.OnClickListener {
    private EditText etName, etLastname, etEmail, etPhone, etPassword;
    private String sName, sLastname, sEmail, sPhone, sPassword;
    private Boolean type;
    private RadioGroup radioGroup;
    private RadioButton radioButtonA, radioButtonB;
    private DatabaseReference databaseReference;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__register__user);

        etName = findViewById(R.id.activity_registerUser_name);
        etLastname = findViewById(R.id.activity_registerUser_lastname);
        etEmail = findViewById(R.id.activity_registerUser_email);
        etPhone = findViewById(R.id.activity_registerUser_cellphone);
        etPassword = findViewById(R.id.activity_registerUser_password);
        radioGroup = findViewById(R.id.activity_registerUser_radioGroup);
        radioButtonA = findViewById(R.id.radio_lessor);
        radioButtonB = findViewById(R.id.radio_lesse);

        databaseReference = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();

    }

    @Override
    public void onClick(View v){
        if (v.getId() == R.id.activity_registerUser_register) {
            sName = etName.getText().toString();
            sLastname = etLastname.getText().toString();
            sEmail = etEmail.getText().toString();
            sPhone = etPhone.getText().toString();
            sPassword = etPassword.getText().toString();
            type = radioButtonA.isSelected();

            createAccount(sEmail, sPassword);
        }
    }

    private void createAccount(final String email, String password){
        if(!validateForm()){
            return;
        }

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            FirebaseUser user = mAuth.getCurrentUser();
                            databaseReference.child("users").child(user.getUid()).setValue(email);
                            updateUI(user);
                        } else {
                            Log.e("OHSHIT", "onComplete: Failed=" + task.getException().getMessage());
                            updateUI(null);
                        }
                    }
                });
    }

    public void updateUI(FirebaseUser user){
        if(user != null){
            if(sName != null && sLastname != null && sEmail != null && sPhone != null && sPassword != null && type != null){
                User aux = new User(sEmail, sLastname, sName, sPassword, sPhone, (type ? 1 : 0));
                databaseReference.child(mAuth.getCurrentUser().getUid()).child("info").setValue(aux);
            }
            Intent loginIntent = new Intent(Activity_Register_User.this,
                    Activity_Main_Screen.class);
            loginIntent.setFlags(loginIntent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK |Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(loginIntent);
        } else {

        }

    }

    private boolean validateForm() {
        boolean valid = true;

        String email = etEmail.getText().toString();
        if (TextUtils.isEmpty(email)) {
            etEmail.setError("Required.");
            valid = false;
        } else {
            etEmail.setError(null);
        }

        String password = etPassword.getText().toString();
        if (TextUtils.isEmpty(password)) {
            etPassword.setError("Required.");
            valid = false;
        } else {
            etPassword.setError(null);
        }

        String name = etName.getText().toString();
        if (TextUtils.isEmpty(name)) {
            etName.setError("Required.");
            valid = false;
        } else {
            etName.setError(null);
        }

        String lastname = etLastname.getText().toString();
        if (TextUtils.isEmpty(lastname)) {
            etLastname.setError("Required.");
            valid = false;
        } else {
            etLastname.setError(null);
        }

        String cellphone = etPhone.getText().toString();
        if (TextUtils.isEmpty(cellphone)) {
            etPhone.setError("Required.");
            valid = false;
        } else {
            etPhone.setError(null);
        }

        return valid;
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
