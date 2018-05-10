package iteso.com.rentstudio;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Switch;

public class Activity_Notifications extends AppCompatActivity {
    Switch all, payment,renew,sound;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__notifications);
        all=findViewById(R.id.activity_notificationsAll);
        payment=findViewById(R.id.activity_notificationsPayment);
        renew=findViewById(R.id.activity_notificationsRenew);
        sound=findViewById(R.id.activity_notificationsSound);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putBoolean("all", all.isChecked());
        outState.putBoolean("payment", payment.isChecked());
        outState.putBoolean("renew",renew.isChecked());
        outState.putBoolean("sound", sound.isChecked());
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        all.setChecked(savedInstanceState.getBoolean("all"));
        payment.setChecked(savedInstanceState.getBoolean("payment"));
        renew.setChecked(savedInstanceState.getBoolean("renew"));
        sound.setChecked(savedInstanceState.getBoolean("sound"));
    }
}
