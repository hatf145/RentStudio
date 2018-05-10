package iteso.com.rentstudio;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import iteso.com.rentstudio.beans.Lessor;

public class ActivityLessorScreen extends AppCompatActivity {
TextView email, name, phone;
Button editar;
Lessor l;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lessor_screen2);
        email=findViewById(R.id.lemail);
        name=findViewById(R.id.llessor);
        phone=findViewById(R.id.lphone);
        editar=findViewById(R.id.lbutton);


        if(getIntent().getExtras()!=null){
            l = getIntent().getParcelableExtra("ITEM");
            if (l != null) {
                email.setText("Email:"+ l.getEmail());
                phone.setText("Teléfono: "+l.getPhone());
                name.setText(l.getName()+" "+ l.getLastname());

            }
        }
        editar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent3=new Intent(ActivityLessorScreen.this,ActivityEditLessor.class);
                intent3.putExtra("ITEM",l);
                startActivityForResult(intent3,9999);
            }
        });

    }
}
