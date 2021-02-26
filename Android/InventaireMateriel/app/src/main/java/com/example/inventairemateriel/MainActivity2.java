package com.example.inventairemateriel;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MainActivity2 extends AppCompatActivity {
    DatePickerDialog picker;
    EditText date;

    DatabaseReference myRef;
    FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        final Intent intent2 = new Intent(MainActivity2.this, MainActivity3.class);

        final EditText nomprenom = findViewById(R.id.editTextNom);
        final Button valider = findViewById(R.id.buttonVal);

        date=(EditText) findViewById(R.id.editDate);

        final Date[] currentDate = {new Date()};
        final EditText date = findViewById(R.id.editDate);
        final Button actuDate = findViewById(R.id.buttonDate);
        final DateFormat[] dateFormat = {new SimpleDateFormat("dd/MM/yyyy")};
        final String[] formdate = {dateFormat[0].format(currentDate[0])};

        date.setInputType(InputType.TYPE_NULL);
        date.setText(formdate[0]);

        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog
                picker = new DatePickerDialog(MainActivity2.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        date.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                    }
                }, year, month, day);
                picker.show();
            }
        });

        actuDate.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                currentDate[0] = new Date();
                dateFormat[0] = new SimpleDateFormat("dd/MM/yyyy");
                formdate[0] = dateFormat[0].format(currentDate[0]);
                date.setText(formdate[0]);
            }
        });

        valider.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String Nomprenom = nomprenom.getText().toString();
                String dateP = date.getText().toString();

                database = FirebaseDatabase.getInstance();
                myRef =  database.getReference("1").child("Personne").child(Nomprenom);
                myRef.child("Date").setValue(dateP);
                intent2.putExtra("personne",Nomprenom);
                startActivity(intent2);
            }
        });
    }
}