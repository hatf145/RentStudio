package iteso.com.rentstudio;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class ActivityPayments extends AppCompatActivity {

    private int userType;
    TextView tv1, tv2, tv3;
    View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payments);

        userType = getIntent().getIntExtra("userType", 0);
        Log.e("OHSHIT", Integer.toString(userType));

        tv1 = (TextView) findViewById(R.id.payment_textview_0);
        tv2 = (TextView) findViewById(R.id.payment_textview_1);
        tv3 = (TextView) findViewById(R.id.payment_textview_2);
        view = findViewById(R.id.view_line);

        if (userType == 1) {
            tv1.setText(getString(R.string.payment_card));
            tv2.setText(getString(R.string.payment_history));
            tv3.setText("");
            view.setVisibility(View.GONE);

        } else {
            tv1.setText(getString(R.string.payment_card));
            tv2.setText(getString(R.string.payment_paypal));
            tv3.setText(getString(R.string.recurring_payment));
        }
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.payment_textview_0:
                Intent cardPayment = new Intent(ActivityPayments.this,
                        ActivityCardPayment.class);
                cardPayment.putExtra("userType", userType);
                startActivity(cardPayment);
                break;
            case R.id.payment_textview_1:
                if (userType == 0) {
                    Intent secondIntent = new Intent(ActivityPayments.this,
                            ActivityPaymentHistory.class);
                    startActivity(secondIntent);
                } else {
                    Intent secondIntent = new Intent(ActivityPayments.this,
                            ActivityPaypalPayment.class);
                    startActivity(secondIntent);

                }
                break;
            case R.id.payment_textview_2:
                if (userType == 0) {
                    Intent secondIntent = new Intent(ActivityPayments.this,
                            ActivityRecurringPayment.class);
                    startActivity(secondIntent);
                    break;

                }
        }
    }
}
