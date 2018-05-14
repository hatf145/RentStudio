package iteso.com.rentstudio;

import android.app.DatePickerDialog;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.icu.util.TimeZone;
import android.net.Uri;
import android.provider.CalendarContract;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;

import iteso.com.rentstudio.beans.Lessor;
import iteso.com.rentstudio.beans.Property;
import  android.Manifest.permission;

import static android.Manifest.permission.READ_CALENDAR;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_CALENDAR;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static android.content.pm.PackageManager.PERMISSION_GRANTED;
import android.Manifest;

public class Activity_Register_Rent extends AppCompatActivity {
    private Spinner lessor, property;
    private Calendar calendar;
    private TextView date;
    private int year, month, day, i, userType;
    private Button btnRent;
    private ArrayList<String> lNames, pNames;
    private DatabaseReference databaseReference;
    private FirebaseAuth mAuth;
    final int callbackId = 42;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__register_rent);


        userType = getIntent().getIntExtra("userType", 0);
        checkPermission(callbackId, permission.WRITE_CALENDAR, permission.READ_CALENDAR);

        mAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();

        date = findViewById(R.id.activity_rent_date);
        btnRent = findViewById(R.id.activity_brent_register);
        lessor = findViewById(R.id.activity_rent_lessor);
        property = findViewById(R.id.activity_rent_property);
        lNames = new ArrayList<>();
        pNames = new ArrayList<>();

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                lNames.clear();
                pNames.clear();
                for(DataSnapshot snapshot : dataSnapshot.child(mAuth.getCurrentUser().getUid()).child("lessors").getChildren()){

                    Lessor auxLessor = snapshot.getValue(Lessor.class);
                    lNames.add(auxLessor.getName());
                }

                for(DataSnapshot snapshot : dataSnapshot.child(mAuth.getCurrentUser().getUid()).child("properties").getChildren()){

                    Property auxProperty = snapshot.getValue(Property.class);
                    pNames.add(auxProperty.getName());
                }

                ArrayAdapter<String> lessorAdapter = new ArrayAdapter<>(Activity_Register_Rent.this, android.R.layout.simple_spinner_item, lNames);
                lessorAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                lessor.setAdapter(lessorAdapter);

                ArrayAdapter<String> propertyAdapter = new ArrayAdapter<>(Activity_Register_Rent.this, android.R.layout.simple_spinner_item, pNames);
                propertyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                property.setAdapter(propertyAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        calendar = Calendar.getInstance();
        day = (int)calendar.get(Calendar.DAY_OF_MONTH);
        month = (int)calendar.get(Calendar.MONTH);
        year = (int)calendar.get(Calendar.YEAR);
        date.setText(day + "/" + (month + 1) + "/" + year);

        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(Activity_Register_Rent.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int iyear, int iday, int imonth) {
                        date.setText((iday + 1) + "/" + imonth + "/" + iyear);
                        day = imonth;
                        month = iday+1;
                        year = iyear;
                    }
                }, year, month, day);
                datePickerDialog.show();
            }
        });

        btnRent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String auxLessor = lessor.getSelectedItem().toString();
                String auxProperty = property.getSelectedItem().toString();
                createRent(auxLessor, auxProperty, day);
                Intent backHome = new Intent(Activity_Register_Rent.this, Activity_Main_Screen.class);
                backHome.setFlags(backHome.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(backHome);
            }
        });
    }


    private void checkPermission(int callbackId, String... permissionsId) {
        boolean permissions = true;
        for (String p : permissionsId) {
            permissions = permissions && ContextCompat.checkSelfPermission(this, p) == PERMISSION_GRANTED;
        }

        if (!permissions)
            ActivityCompat.requestPermissions(this, permissionsId, callbackId);
    }

    public void createRent(final String lessor, final String property, final int day) {
        i = 1;
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot : dataSnapshot.child(mAuth.getCurrentUser().getUid()).child("properties").getChildren()){

                    Property aux = snapshot.getValue(Property.class);
                    if (aux.getName().equals(property) && i == 1) {
                        System.out.println("GOT HERE");
                        databaseReference.child(mAuth.getCurrentUser().getUid()).child("properties").child(snapshot.getKey()).child("lessor").setValue(lessor);
                        databaseReference.child(mAuth.getCurrentUser().getUid()).child("properties").child(snapshot.getKey()).child("payday").setValue(Integer.valueOf(day));
                    }
                    if(userType == 0){
                        for(DataSnapshot snapshot2 : dataSnapshot.child("properties").getChildren()){
                            Property auxB = snapshot2.getValue(Property.class);
                            if(auxB.getName().equals(property) && i == 1){
                                System.out.println("GOT HERE");
                                databaseReference.child("properties").child(snapshot2.getKey()).child("lessor").setValue(lessor);
                                databaseReference.child("properties").child(snapshot2.getKey()).child("payday").setValue(Integer.valueOf(day));
                                Calendar g=Calendar.getInstance();
                                g.set(year,month,day);
                                Intent intent = new Intent(Intent.ACTION_INSERT)
                                        .setData(CalendarContract.Events.CONTENT_URI)
                                        .putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, g)
                                        .putExtra(CalendarContract.EXTRA_EVENT_END_TIME, g)
                                        .putExtra(CalendarContract.Events.TITLE, "Pago de renta")
                                        .putExtra(CalendarContract.Events.DESCRIPTION, "Pago de renta de la propiedad "+aux.getName())
                                        .putExtra(CalendarContract.Events.RRULE,"FREQ=MONTHLY;COUNT=12;");
                                startActivity(intent);
                                finish();
                            }
                        }


                    }
                }
                i = 0;
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });




    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putString("date", date.getText().toString());
        outState.putString("lessor", lessor.getSelectedItem().toString());
        outState.putString("property", property.getSelectedItem().toString());
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        if(savedInstanceState.getString("date") != null)
            date.setText(savedInstanceState.getString("date"));
        if(savedInstanceState.getString("property").equals(getString(R.string.available)))
            property.setSelection(0);
        else
            property.setSelection(1);
    }
}
