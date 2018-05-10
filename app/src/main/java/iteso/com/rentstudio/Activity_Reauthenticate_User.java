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

public class Activity_Reauthenticate_User extends AppCompatActivity implements View.OnClickListener{
    private EditText etUserName, etPassword;
    private static final String TAG = "EmailPassword";
    private FirebaseAuth mAuth;
    private DatabaseReference databaseReference;
    FirebaseUser user = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__reauthenticate__user);

        etUserName = findViewById(R.id.activity_reAuth_username);
        etPassword = findViewById(R.id.activity_reAuth_password);

        mAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();
    }

    @Override
    public void onClick(View v){
        switch (v.getId()) {
            case R.id.activity_reAuth_loginButton:
                mAuth.signOut();
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
                            user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
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

    public void updateUI(FirebaseUser user){
        if(user != null){
            Intent loginIntent = new Intent(Activity_Reauthenticate_User.this,
                    Activity_Edit_User.class);
            loginIntent.setFlags(loginIntent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK |Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(loginIntent);
        } else {
            etUserName.setText("");
            etUserName.setError("Wrong!");
            etPassword.setText("");
            etPassword.setError("Wrong!");
        }

    }
}
