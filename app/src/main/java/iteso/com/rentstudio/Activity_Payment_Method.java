package iteso.com.rentstudio;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;


public class Activity_Payment_Method extends AppCompatActivity {

    private TextView cardPaymentTextview;
    private TextView paypalPaymentTextview;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__payment__method);

        cardPaymentTextview = findViewById(R.id.card_payment_textview);
        cardPaymentTextview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Activity_Payment_Method.this, Activity_card_payment.class);
                startActivity(intent);

            }
        });


        paypalPaymentTextview = findViewById(R.id.paypal_payment_textview);
        paypalPaymentTextview.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Activity_Payment_Method.this, Activity_paypal.class);
                startActivity(intent);
            }
        });

    }
}
