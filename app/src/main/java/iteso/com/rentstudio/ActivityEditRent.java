package iteso.com.rentstudio;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class ActivityEditRent extends AppCompatActivity {
 TextView l,p,date;
 Button save;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_rent);
        l=findViewById(R.id.activity_edit_rent_l);
        p=findViewById(R.id.activity_edit_rent_p);
        date=findViewById(R.id.activity_edit_rent_date);
        save=findViewById(R.id.activity_edit_rent_save);
    }



    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putString("date", date.getText().toString());
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        if(savedInstanceState.getString("date") != null)
            date.setText(savedInstanceState.getString("date"));
    }


}
