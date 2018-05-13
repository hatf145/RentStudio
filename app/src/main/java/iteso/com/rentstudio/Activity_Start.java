package iteso.com.rentstudio;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Timer;
import java.util.TimerTask;

public class Activity_Start extends AppCompatActivity {
    ImageView icon;
    TextView rent;
    FirebaseAuth mAuth;
    FirebaseUser user;
    DatabaseReference databaseReference;
    int userType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        icon = findViewById(R.id.activity_start_logo);
        rent = findViewById(R.id.activity_start_title);

        mAuth = FirebaseAuth.getInstance();

        if(mAuth.getCurrentUser() != null){
            getType();
        }
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                Intent intent;
                intent = new Intent(Activity_Start.this,
                            Activity_LogIn.class);
                System.out.println("onPutExtraSTART" + userType);
                intent.putExtra("userType", userType);
                startActivity(intent);
                finish();
            }
        };
        Timer timer = new Timer();
        timer.schedule(task,1000);
    }

    public void getType(){
        databaseReference = FirebaseDatabase.getInstance().getReference().child(mAuth.getCurrentUser().getUid()).child("info");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                userType = (int) dataSnapshot.child("type").getValue(Integer.class);
                System.out.println("onListenerSTART: " + userType);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}

