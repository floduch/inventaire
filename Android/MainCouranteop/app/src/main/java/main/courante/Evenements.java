package main.courante;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.InputFilter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Calendar;

public class Evenements extends AppCompatActivity {

    private int mHour, mMinute, mSecond;
    private Boolean samuContacte = false;
    private String numero = "0";

    DatabaseReference myRef, myRef1, myRef2;
    FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evenements);

//                Récupération des valeurs de l'Intent
        Bundle extras = getIntent().getExtras();
        final String num;
        final String nat;
        if (extras != null) {
            num = extras.getString("Numero");
            nat = extras.getString("Nature");
            // and get whatever type user account id is
        }else {
            num ="0";
            nat = "0";
        }

        //
//       String num = "2021012601";
//       String nat = "Test 1";

       final Intent intentV = new Intent(Evenements.this, Victimes.class);

        final TableLayout layoutTab = findViewById(R.id.layoutTab);
        final TableRow tabArrivee = findViewById(R.id.tabArrivee);
        final TableRow tabOpe = findViewById(R.id.tabOpe);
        final TableRow tabOrga = findViewById(R.id.tabOrga);

        final Button buttonArrivee = findViewById(R.id.buttonArrivee);
        final Button buttonOpe = findViewById(R.id.buttonOpe);
        final Button buttonOrga = findViewById(R.id.buttonOrga);
        final Button buttonSAMUO = findViewById(R.id.buttonSamu);

        final Button buttonDBinom = findViewById(R.id.buttonDBinom);
        final Button buttonRBinom = findViewById(R.id.buttonRBinom);
        final Button buttonVDBinom = findViewById(R.id.buttonValDBinom);
        final Button buttonVRBinom = findViewById(R.id.buttonValRBinom);

        final Button buttonVictime = findViewById(R.id.buttonVictime);

        buttonOpe.setEnabled(false);
        buttonOrga.setEnabled(false);

        buttonDBinom.setEnabled(false);
        buttonRBinom.setEnabled(false);
        buttonVDBinom.setEnabled(false);
        buttonVRBinom.setEnabled(false);

        final EditText horaireArrivee = findViewById(R.id.textHoraireArriv);
        final EditText horaireOpe = findViewById(R.id.textHoraireOpe);
        final EditText horaireOrga = findViewById(R.id.textHoraireOrga);

        final EditText horaireDBinom = findViewById(R.id.textHoraireDBinom);
        final EditText horaireRBinom = findViewById(R.id.textHoraireRBinom);

        database = FirebaseDatabase.getInstance();
//        myRef1 = database.getReference("1");    //.child("CO");
        myRef = database.getReference("0").child(num).child(nat).child("Evénements");


//        -------------------------------------------------------------------------
//        final Button buttonPop = findViewById(R.id.button5);
//        final EditText editPop = findViewById(R.id.textView3);


        ArrayList<String> horaires = new ArrayList<>();

        buttonArrivee.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                buttonArrivee.setEnabled(false);
                buttonOpe.setEnabled(false);
                buttonOrga.setEnabled(true);

                final Calendar c = Calendar.getInstance();
                mHour = c.get(Calendar.HOUR_OF_DAY);
                mMinute = c.get(Calendar.MINUTE);

                horaireArrivee.setText(mHour+" : "+mMinute);
                myRef.child("ArriveeSecouristes").setValue(horaireArrivee.getText().toString());
            }
        });

        buttonOpe.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                buttonOpe.setEnabled(false);
                buttonDBinom.setEnabled(true);
                buttonRBinom.setEnabled(true);
                buttonVDBinom.setEnabled(true);
                buttonVRBinom.setEnabled(true);

                final Calendar c = Calendar.getInstance();
                mHour = c.get(Calendar.HOUR_OF_DAY);
                mMinute = c.get(Calendar.MINUTE);

                horaireOpe.setText(mHour+" : "+mMinute);
                myRef.child("posteOperationnel").setValue(horaireOpe.getText().toString());
            }
        });

        buttonSAMUO.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                buttonSAMUO.setEnabled(false);
                samuContacte = true;
                myRef.child("SAMUContacte").setValue("Oui");

                buttonOpe.setEnabled(true);

            }
        });

        buttonOrga.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                buttonOrga.setEnabled(false);

                final Calendar c = Calendar.getInstance();
                mHour = c.get(Calendar.HOUR_OF_DAY);
                mMinute = c.get(Calendar.MINUTE);

                horaireOrga.setText(mHour+" : "+mMinute);
                myRef.child("visiteOrganisateur").setValue(horaireOrga.getText().toString());
            }
        });

        buttonDBinom.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                final Calendar c = Calendar.getInstance();
                mHour = c.get(Calendar.HOUR_OF_DAY);
                mMinute = c.get(Calendar.MINUTE);
                mSecond = c.get(Calendar.SECOND);

                horaireDBinom.setText(mHour+" : "+mMinute+" : "+mSecond);
                startPopUp();

                myRef.child("departBinome").child(horaireDBinom.getText().toString()).setValue(getNumero());
            }
        });

        buttonRBinom.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                final Calendar c = Calendar.getInstance();
                mHour = c.get(Calendar.HOUR_OF_DAY);
                mMinute = c.get(Calendar.MINUTE);
                mSecond = c.get(Calendar.SECOND);

                horaireRBinom.setText(mHour+" : "+mMinute+" : "+mSecond);
                startPopUp();

                myRef.child("retourBinome").child(horaireRBinom.getText().toString()).setValue(getNumero());
            }
        });

        buttonVDBinom.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                buttonDBinom.setEnabled(true);
                myRef.child("departBinome").child(horaireDBinom.getText().toString()).setValue(getNumero());
                horaireDBinom.setText("");
            }
        });

        buttonVRBinom.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                buttonRBinom.setEnabled(true);
                myRef.child("retourBinome").child(horaireRBinom.getText().toString()).setValue(getNumero());
                horaireRBinom.setText("");
            }
        });

        buttonVictime.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

               startActivity(intentV);

            }
        });





        horaireArrivee.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Get Current Time
                final Calendar c = Calendar.getInstance();
                mHour = c.get(Calendar.HOUR_OF_DAY);
                mMinute = c.get(Calendar.MINUTE);

                // Launch Time Picker Dialog
                TimePickerDialog timePickerDialog = new TimePickerDialog(Evenements.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                        horaireArrivee.setText(String.valueOf(hourOfDay)+" : "+String.valueOf(minute), TextView.BufferType.EDITABLE);
                    }
                }, mHour, mMinute, true);
                timePickerDialog.show();
            }
        });

        horaireOpe.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Get Current Time
                final Calendar c = Calendar.getInstance();
                mHour = c.get(Calendar.HOUR_OF_DAY);
                mMinute = c.get(Calendar.MINUTE);

                // Launch Time Picker Dialog
                TimePickerDialog timePickerDialog = new TimePickerDialog(Evenements.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                        horaireOpe.setText(String.valueOf(hourOfDay)+" : "+String.valueOf(minute), TextView.BufferType.EDITABLE);
                    }
                }, mHour, mMinute, true);
                timePickerDialog.show();
            }
        });

        horaireOrga.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Get Current Time
                final Calendar c = Calendar.getInstance();
                mHour = c.get(Calendar.HOUR_OF_DAY);
                mMinute = c.get(Calendar.MINUTE);

                // Launch Time Picker Dialog
                TimePickerDialog timePickerDialog = new TimePickerDialog(Evenements.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                        horaireOrga.setText(String.valueOf(hourOfDay)+" : "+String.valueOf(minute), TextView.BufferType.EDITABLE);
                    }
                }, mHour, mMinute, true);
                timePickerDialog.show();
            }
        });


//        buttonArrivee.isEnabled()
    }

    public void startPopUp() {
        LayoutInflater inflater = LayoutInflater.from(Evenements.this);
        View view = inflater.inflate(R.layout.layout_pop_up, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Evenements.this);
        alertDialogBuilder.setView(view);

//        final Button buttonPop = findViewById(R.id.button5);
        final EditText editPop = view.findViewById(R.id.textView3);

        alertDialogBuilder.setCancelable(false).setPositiveButton("Valider", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        numero = editPop.getText().toString();
                        setNumero(numero);
                        //myRef.child("TESTPOPUP").setValue(editPop.getText().toString());
                    }
                }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
        System.out.println(numero);
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }
}

