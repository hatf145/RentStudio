package iteso.com.rentstudio;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Activity_LogIn extends AppCompatActivity implements View.OnClickListener{
    private EditText etUserName, etPassword;
    private static final String TAG = "EmailPassword";
    private FirebaseAuth mAuth;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__log_in);

        etUserName = findViewById(R.id.activity_login_username);
        etPassword = findViewById(R.id.activity_login_password);

        mAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();

    }

    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.activity_login_registerButton:
                Intent intent = new Intent(Activity_LogIn.this, Activity_Register_User.class);
                startActivity(intent);
                break;
            case R.id.activity_login_loginButton:
                signIn(etUserName.getText().toString(), etPassword.getText().toString());
                break;

        }
    }

    private void signIn(String email, String password){
        Log.d(TAG, "signIn:" + email);

        if(!validateForm()){
            return;
        }

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            etUserName.setText("");
                            etUserName.setError("Wrong!");
                            etPassword.setText("");
                            etPassword.setError("Wrong!");
                            updateUI(null);
                        }
                    }
                });
    }



    private boolean validateForm() {
        boolean valid = true;

        String email = etUserName.getText().toString();
        if (TextUtils.isEmpty(email)) {
            etUserName.setError("Required.");
            valid = false;
        } else {
            etUserName.setError(null);
        }

        String password = etPassword.getText().toString();
        if (TextUtils.isEmpty(password)) {
            etPassword.setError("Required.");
            valid = false;
        } else {
            etPassword.setError(null);
        }

        return valid;
    }

    @Override
    public void onStart(){
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }

    public void updateUI(FirebaseUser user){
        if(user != null){
            Intent loginIntent = new Intent(Activity_LogIn.this,
                    Activity_Main_Screen.class);
            loginIntent.setFlags(loginIntent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK |Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(loginIntent);
        } else {

        }

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putString("username", etUserName.getText().toString());

        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        if(savedInstanceState.getString("username") != null)
            etUserName.setText(savedInstanceState.getString("username"));

    }
}
