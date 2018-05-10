package iteso.com.rentstudio;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class Activity_Settings extends AppCompatActivity {
TextView notifications, eula, logout;
    TextView mPaymnet, mHelp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__settings);
        notifications=findViewById(R.id.activity_settings_not);
        eula=findViewById(R.id.activity_settings_eula);
        logout=findViewById(R.id.activity_settings_logout);
        mPaymnet = findViewById(R.id.activity_settings_payment);
        mHelp = findViewById(R.id.activity_settings_help);

        notifications.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Activity_Notifications.class);
                startActivity(intent);
            }
        });

        eula.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Activity_Eula.class);
                startActivity(intent);
            }
        });

        mPaymnet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Activity_Payment_Method.class);
                startActivity(intent);
            }
        });

        mHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Activity_help.class);
                startActivity(intent);
            }
        });
    }


    public void onClick(View v){
        switch (v.getId()){
            case R.id.activity_settings_not:
                Intent notIntent = new Intent(Activity_Settings.this,
                        Activity_Notifications.class);
                startActivity(notIntent);
                break;
            case R.id.activity_settings_help:
                Intent helpIntent = new Intent(Activity_Settings.this,
                        Activity_help.class);
                startActivity(helpIntent);
                break;
            case R.id.activity_settings_payment:
                Intent paymentIntent = new Intent(Activity_Settings.this,
                        Activity_Payment_Method.class);
                startActivity(paymentIntent);
                break;
            case R.id.activity_settings_eula:
                Intent useIntent = new Intent(Activity_Settings.this,
                        Activity_Eula.class);
                startActivity(useIntent);
                break;
            case R.id.activity_settings_logout:
                Intent mainIntent = new Intent(Activity_Settings.this,
                        Activity_LogIn.class);
                mainIntent.addFlags(mainIntent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(mainIntent);
                finish();
                break;
        }


    }
}
