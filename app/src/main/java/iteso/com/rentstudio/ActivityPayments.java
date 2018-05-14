package iteso.com.rentstudio;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.braintreepayments.api.dropin.DropInActivity;
import com.braintreepayments.api.dropin.DropInRequest;
import com.braintreepayments.api.dropin.DropInResult;
import com.braintreepayments.api.interfaces.HttpResponseCallback;
import com.braintreepayments.api.internal.HttpClient;
import com.braintreepayments.api.models.PaymentMethodNonce;

import java.util.HashMap;
import java.util.Map;


public class ActivityPayments extends AppCompatActivity {


    private int userType;
    TextView tv1, tv2;

    final int REQUEST_CODE = 1;
    //  final String get_token = "http://192.168.1.12/braintreepayment/main.php";
    final String get_token = "http://cortezroberto.com/BraintreePayments/main.php";
    final String send_payment_details = "http://cortezroberto.com/BraintreePayments/checkout.php";
    String token, amount;
    HashMap<String, String> paramHash;

    Button btnPay;
    EditText etAmount;
    LinearLayout llHolder;
    TextView payment_textview_0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payments);

        userType = getIntent().getIntExtra("userType", 0);
        Log.e("OHSHIT", Integer.toString(userType));
        llHolder = (LinearLayout) findViewById(R.id.llHolder);
        tv1 = (TextView) findViewById(R.id.payment_textview_0);
        tv2 = (TextView) findViewById(R.id.payment_textview_1);


        if (userType == 1) {
            tv1.setText(getString(R.string.payment_card));
            tv2.setText(getString(R.string.payment_history));

        } else {
            tv1.setText(getString(R.string.payment_card));
            tv2.setText(getString(R.string.recurring_payment));
        }



        llHolder = (LinearLayout) findViewById(R.id.llHolder);
     //   etAmount = (EditText) findViewById(R.id.etPrice);
        payment_textview_0 = findViewById(R.id.payment_textview_0);
       // btnPay = (Button) findViewById(R.id.btnPay);
        payment_textview_0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBraintreeSubmit();
            }
        });
        new HttpRequest().execute();
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
                            ActivityPaypalPayment.class);
                    startActivity(secondIntent);
                } else {
                    Intent secondIntent = new Intent(ActivityPayments.this,
                            ActivityPaymentHistory.class);
                    startActivity(secondIntent);

                }
                break;

                }
        }





    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                DropInResult result = data.getParcelableExtra(DropInResult.EXTRA_DROP_IN_RESULT);
                PaymentMethodNonce nonce = result.getPaymentMethodNonce();
                String stringNonce = nonce.getNonce();
                Log.d("mylog", "Result: " + stringNonce);
                // Send payment price with the nonce
                // use the result to update your UI and send the payment method nonce to your server
                //  if (!etAmount.getText().toString().isEmpty()) {
                //    amount = etAmount.getText().toString();
                paramHash = new HashMap<>();
                paramHash.put("amount", amount);
                paramHash.put("nonce", stringNonce);
                sendPaymentDetails();
                //   } else
                //      Toast.makeText(ActivityPayments1.this, "Please enter a valid amount.", Toast.LENGTH_SHORT).show();

            } else if (resultCode == Activity.RESULT_CANCELED) {
                // the user canceled
                Log.d("mylog", "user canceled");
            } else {
                // handle errors here, an exception may be available in
                Exception error = (Exception) data.getSerializableExtra(DropInActivity.EXTRA_ERROR);
                Log.d("mylog", "Error : " + error.toString());
            }
        }
    }

    public void onBraintreeSubmit() {
        DropInRequest dropInRequest = new DropInRequest()
                .clientToken(token);
        startActivityForResult(dropInRequest.getIntent(this), REQUEST_CODE);
    }

    private void sendPaymentDetails() {
        RequestQueue queue = Volley.newRequestQueue(ActivityPayments.this);
        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.POST, send_payment_details,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(response.contains("Successful"))
                        {
                            Toast.makeText(ActivityPayments.this, "Transaction successful", Toast.LENGTH_LONG).show();
                        }
                        else Toast.makeText(ActivityPayments.this, "Transaction failed", Toast.LENGTH_LONG).show();
                        Log.d("mylog", "Final Response: " + response.toString());
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("mylog", "Volley error : " + error.toString());
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                if (paramHash == null)
                    return null;
                Map<String, String> params = new HashMap<>();
                for (String key : paramHash.keySet()) {
                    params.put(key, paramHash.get(key));
                    Log.d("mylog", "Key : " + key + " Value : " + paramHash.get(key));
                }

                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("Content-Type", "application/x-www-form-urlencoded");
                return params;
            }
        };
        queue.add(stringRequest);
    }

    private class HttpRequest extends AsyncTask {
        ProgressDialog progress;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progress = new ProgressDialog(ActivityPayments.this, android.R.style.Theme_DeviceDefault_Dialog);
            progress.setCancelable(false);
            progress.setMessage("We are contacting our servers for token, Please wait");
            progress.setTitle("Getting token");
            progress.show();
        }

        @Override
        protected Object doInBackground(Object[] objects) {
            HttpClient client = new HttpClient();
            client.get(get_token, new HttpResponseCallback() {
                @Override
                public void success(String responseBody) {
                    Log.d("mylog", responseBody);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(ActivityPayments.this, "Successfully got token", Toast.LENGTH_SHORT).show();
                            llHolder.setVisibility(View.VISIBLE);
                        }
                    });
                    token = responseBody;
                }

                @Override
                public void failure(Exception exception) {
                    final Exception ex = exception;
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(ActivityPayments.this, "Failed to get token: " + ex.toString(), Toast.LENGTH_LONG).show();
                        }
                    });
                }
            });
            return null;
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            progress.dismiss();
        }
    }
}