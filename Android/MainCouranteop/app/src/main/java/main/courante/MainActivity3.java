package main.courante;

import androidx.appcompat.app.AppCompatActivity;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.Spanned;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;

public class MainActivity3 extends AppCompatActivity {

    Button btnDPSD, btnDPSF, btnLOCD, btnLOCR;
    private int mHour, mMinute;
    private static final String TAG = "";

    Enregistrements Manifestation = new Enregistrements();

//    Référence à la base de données
    DatabaseReference myRef;
    FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

//        Récupération des valeurs de l'Intent
        Bundle extras = getIntent().getExtras();
        final String num;
        final String nat;

        if (extras != null) {
            num = extras.getString("Numero");
            nat = extras.getString("Nature");
        }else {
            num ="0";
            nat = "0";
        }

//        Récupération des éléments visuels
        Button submit = findViewById(R.id.buttonValider2);
        final Intent intent2 = new Intent(this, MainActivity4.class);

        btnDPSD = (Button) findViewById(R.id.button);
        btnDPSF = (Button) findViewById(R.id.button1);

        btnLOCD = (Button) findViewById(R.id.button2);
        btnLOCR = (Button) findViewById(R.id.button3);

        final LinearLayout layoutDeb = findViewById(R.id.layoutHDPS);
        final LinearLayout layoutFin = findViewById(R.id.layoutHDPS1);
        final LinearLayout layoutDep = findViewById(R.id.layoutHDep);
        final LinearLayout layoutRet = findViewById(R.id.layoutHRet);

        final TextView points = findViewById(R.id.h);

        final EditText HeureDebDPS = findViewById(R.id.editHeureDDPS);
        final EditText MinuteDebDPS = findViewById(R.id.editMinuteDDPS);

        final EditText HeureFinDPS = findViewById(R.id.editHeureFDPS);
        final EditText MinuteFinDPS = findViewById(R.id.editMinuteFDPS);

        final EditText HeureDep = findViewById(R.id.editHeureDep);
        final EditText MinuteDep = findViewById(R.id.editMinuteDep);

        final EditText HeureRet = findViewById(R.id.editHeureRet);
        final EditText MinuteRet = findViewById(R.id.editMinuteRet);

//        Filtre pour le nombre de chiffre max à saisir
        HeureDebDPS.setFilters(new InputFilter[]{new InputFilter.LengthFilter(2)});
        MinuteDebDPS.setFilters(new InputFilter[]{new InputFilter.LengthFilter(2)});

        HeureFinDPS.setFilters(new InputFilter[]{new InputFilter.LengthFilter(2)});
        MinuteFinDPS.setFilters(new InputFilter[]{new InputFilter.LengthFilter(2)});

        HeureDep.setFilters(new InputFilter[]{new InputFilter.LengthFilter(2)});
        MinuteDep.setFilters(new InputFilter[]{new InputFilter.LengthFilter(2)});

        HeureRet.setFilters(new InputFilter[]{new InputFilter.LengthFilter(2)});
        MinuteRet.setFilters(new InputFilter[]{new InputFilter.LengthFilter(2)});


//        Filtre pour les heures et minutes (0<heures<24 et 0<minutes<59)
        HeureDebDPS.setFilters(new InputFilter[]{ new InputFilterMinMax("0", "23")});
        MinuteDebDPS.setFilters(new InputFilter[]{ new InputFilterMinMax("0", "59")});

        HeureFinDPS.setFilters(new InputFilter[]{ new InputFilterMinMax("0", "23")});
        MinuteFinDPS.setFilters(new InputFilter[]{ new InputFilterMinMax("0", "59")});

        HeureDep.setFilters(new InputFilter[]{ new InputFilterMinMax("0", "23")});
        MinuteDep.setFilters(new InputFilter[]{ new InputFilterMinMax("0", "59")});

        HeureRet.setFilters(new InputFilter[]{ new InputFilterMinMax("0", "23")});
        MinuteRet.setFilters(new InputFilter[]{ new InputFilterMinMax("0", "59")});

//        fonctions pour faire apparaitre les heure sous forme d'horloge
        btnDPSD.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Get Current Time
                final Calendar c = Calendar.getInstance();
                mHour = c.get(Calendar.HOUR_OF_DAY);
                mMinute = c.get(Calendar.MINUTE);

                // Launch Time Picker Dialog
                TimePickerDialog timePickerDialog = new TimePickerDialog(MainActivity3.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                        HeureDebDPS.setText(String.valueOf(hourOfDay), TextView.BufferType.EDITABLE);
                        MinuteDebDPS.setText(String.valueOf(minute), TextView.BufferType.EDITABLE);
                    }
                }, mHour, mMinute, true);
                timePickerDialog.show();
            }
        });
//
        btnDPSF.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Get Current Time
                final Calendar c = Calendar.getInstance();
                mHour = c.get(Calendar.HOUR_OF_DAY);
                mMinute = c.get(Calendar.MINUTE);

                // Launch Time Picker Dialog
                TimePickerDialog timePickerDialog = new TimePickerDialog(MainActivity3.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                        HeureFinDPS.setText(String.valueOf(hourOfDay), TextView.BufferType.EDITABLE);
                        MinuteFinDPS.setText(String.valueOf(minute), TextView.BufferType.EDITABLE);
                    }
                }, mHour, mMinute, true);
                timePickerDialog.show();
            }
        });
//
        btnLOCD.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Get Current Time
                final Calendar c = Calendar.getInstance();
                mHour = c.get(Calendar.HOUR_OF_DAY);
                mMinute = c.get(Calendar.MINUTE);

                // Launch Time Picker Dialog
                TimePickerDialog timePickerDialog = new TimePickerDialog(MainActivity3.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                        HeureDep.setText(String.valueOf(hourOfDay), TextView.BufferType.EDITABLE);
                        MinuteDep.setText(String.valueOf(minute), TextView.BufferType.EDITABLE);
                    }
                }, mHour, mMinute, true);
                timePickerDialog.show();
            }
        });
//
        btnLOCR.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Get Current Time
                final Calendar c = Calendar.getInstance();
                mHour = c.get(Calendar.HOUR_OF_DAY);
                mMinute = c.get(Calendar.MINUTE);

                // Launch Time Picker Dialog
                TimePickerDialog timePickerDialog = new TimePickerDialog(MainActivity3.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                        HeureRet.setText(String.valueOf(hourOfDay), TextView.BufferType.EDITABLE);
                        MinuteRet.setText(String.valueOf(minute), TextView.BufferType.EDITABLE);
                    }
                }, mHour, mMinute, true);
                timePickerDialog.show();
            }
        });

//      Référence dans la base de données
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("0").child(num).child(nat);

        submit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

//                Récupération des textes dans les champs
                String time1 = HeureDebDPS.getText().toString()+points.getText().toString()+MinuteDebDPS.getText().toString();
                String time2 = HeureFinDPS.getText().toString()+points.getText().toString()+MinuteFinDPS.getText().toString();
                String time3 = HeureDep.getText().toString()+points.getText().toString()+MinuteDep.getText().toString();
                String time4 = HeureRet.getText().toString()+points.getText().toString()+MinuteRet.getText().toString();

                Manifestation.setHeures(time1, time2, time3, time4);

//                Ecriture dans la base de données
                myRef.child("Heures").child("HeureDebDPS").setValue(time1);
                myRef.child("Heures").child("HeureFinDPS").setValue(time2);
                myRef.child("Heures").child("HeureDep").setValue(time3);
                myRef.child("Heures").child("HeureRet").setValue(time4);

                //myRef.setValue(Manifestation.getHeures());

                Log.d("Manifestation ", String.valueOf(Manifestation.getHeures()));

                intent2.putExtra("Numero", num);
                intent2.putExtra("Nature", nat);
                startActivity(intent2);
            }
        });

    }
}