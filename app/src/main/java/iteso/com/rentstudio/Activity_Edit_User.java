package iteso.com.rentstudio;

import android.content.Intent;
import android.os.Parcel;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Activity_Edit_User extends AppCompatActivity implements View.OnClickListener{
    private EditText etPassword, etCellphone, etEmail, etLastName, etName;
    private DatabaseReference databaseReference;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__edit__user);
        etName =findViewById(R.id.activity_editUser_name);
        etPassword =findViewById(R.id.activity_editUser_password);
        etCellphone =findViewById(R.id.activity_editUser_cellphone);
        etEmail =findViewById(R.id.activity_editUser_email);
        etLastName =findViewById(R.id.activity_editUser_lastname);

        databaseReference = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.activity_editUser_Button:
                updateUser();
                Intent backHome = new Intent(Activity_Edit_User.this, Activity_Main_Screen.class);
                startActivity(backHome);
                finish();
        }

    }

    private void updateUser(){
        if(!validateForm())
            return;

        databaseReference.child(mAuth.getCurrentUser().getUid()).child("info")
                .child("name").setValue(etName.getText().toString());
        databaseReference.child(mAuth.getCurrentUser().getUid()).child("info")
                .child("lastname").setValue(etLastName.getText().toString());
        databaseReference.child(mAuth.getCurrentUser().getUid()).child("info")
                .child("phone").setValue(etCellphone.getText().toString());
        databaseReference.child(mAuth.getCurrentUser().getUid()).child("info")
                .child("email").setValue(etEmail.getText().toString());
        databaseReference.child(mAuth.getCurrentUser().getUid()).child("info")
                .child("password").setValue(etPassword.getText().toString());

        FirebaseUser user = mAuth.getCurrentUser();
        
        user.updateEmail(etEmail.getText().toString());
        user.updatePassword(etPassword.getText().toString());
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

        String lastname = etLastName.getText().toString();
        if (TextUtils.isEmpty(lastname)) {
            etLastName.setError("Required.");
            valid = false;
        } else {
            etLastName.setError(null);
        }

        String cellphone = etCellphone.getText().toString();
        if (TextUtils.isEmpty(cellphone)) {
            etCellphone.setError("Required.");
            valid = false;
        } else {
            etCellphone.setError(null);
        }

        return valid;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putString("etName", etName.getText().toString());
        outState.putString("etLastName", etLastName.getText().toString());
        outState.putString("etCellphone", etCellphone.getText().toString());
        outState.putString("etPassword", etPassword.getText().toString());
        outState.putString("etEmail", etEmail.getText().toString());

        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        if(savedInstanceState.getString("etName") != null)
            etName.setText(savedInstanceState.getString("etName"));
        if(savedInstanceState.getString("etLastName") != null)
            etLastName.setText(savedInstanceState.getString("etLastName"));
        if(savedInstanceState.getString("etEmail") != null)
            etEmail.setText(savedInstanceState.getString("etEmail"));
        if(savedInstanceState.getString("etPassword") != null)
            etPassword.setText(savedInstanceState.getString("etPassword"));
        if(savedInstanceState.getString("etCellphone") != null)
            etCellphone.setText(savedInstanceState.getString("etCellphone"));
    }
}
