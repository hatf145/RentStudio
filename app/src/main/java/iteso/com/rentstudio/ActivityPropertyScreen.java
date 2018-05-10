package iteso.com.rentstudio;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import iteso.com.rentstudio.beans.Property;

public class ActivityPropertyScreen extends AppCompatActivity {
    public TextView address;
    public TextView cost;
    public TextView availability;
    public TextView name;
    public TextView payday;
    public TextView state;
    public TextView town;
    Property property;
    Button editar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_property_screen);

        address = findViewById(R.id.property_address);
        cost = findViewById(R.id.property_money);
        availability =findViewById(R.id.property_availability);
        name = findViewById(R.id.property_house);
        payday = findViewById(R.id.property_payday);
        state = findViewById(R.id.property_state);
        town = findViewById(R.id.property_town);
        editar=findViewById(R.id.activity_property_screen_button);


        if(getIntent().getExtras()!=null){
            property = getIntent().getParcelableExtra("ITEM");
            if (property != null) {
                address.setText("Dirección: "+property.getAddress());
                cost.setText("Renta: "+Integer.toString(property.getCost()));
                if(property.getLessor().equals("lessor_1")){
                    availability.setText("Disponible");
                }else{
                    availability.setText("Arrendador: "+property.getLessor());
                    payday.setText("Día de pago: "+Integer.toString(property.getPayday()));
                }
                name.setText(property.getName());
                state.setText("Estado: "+property.getState());
                town.setText("Ciudad: "+property.getTown());

            }
        }
        editar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent3=new Intent(ActivityPropertyScreen.this,ActivityEditProperty.class);
                intent3.putExtra("ITEM",property);
                startActivityForResult(intent3,9999);
            }
        });

    }


}
