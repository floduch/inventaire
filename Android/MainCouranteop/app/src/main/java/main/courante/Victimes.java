package main.courante;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TableRow;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import static android.util.TypedValue.COMPLEX_UNIT_DIP;

public class Victimes extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_victimes);

        final EditText numVictime = findViewById(R.id.editNumVictime);

        final Button idVictimeO = findViewById(R.id.buttonConOVictime);
        final Button idVictimeN = findViewById(R.id.buttonConNVictime);

        final Button sexeM = findViewById(R.id.tabButtonSexeM);
        final Button sexeF = findViewById(R.id.tabButtonSexeF);
        final Button sexeI = findViewById(R.id.tabButtonSexeI);

        final TableRow TnomVictime = findViewById(R.id.tabNomVictime);
        final TableRow TpNomVictime = findViewById(R.id.tabPNomVictime);
        final TableRow nomVictime = findViewById(R.id.tabEditNomVictime);
        final TableRow pNomVictime = findViewById(R.id.tabEditPNomVictime);
        final TableRow TedateNVictime = findViewById(R.id.tabDNVictime);
        final TableRow TadateNVictime = findViewById(R.id.tabEditDNVictime);

        final TextView TnomV = new TextView(Victimes.this);
        final TextView TPnomV = new TextView(Victimes.this);
        final TextView TDateNV = new TextView(Victimes.this);
        final TextView TAgeV = findViewById(R.id.textAgeVictime);

        final EditText nomV = new EditText(Victimes.this);
        final EditText pNomV = new EditText(Victimes.this);
        final EditText ageV = findViewById(R.id.editAgeVictime);

        final ImageButton calendar = new ImageButton(Victimes.this);
        calendar.setImageResource(R.drawable.ic_twotone_calendar_today_24);
        //calendar.setBackgroundColor(getResources().getColor(R.color.def));

        final ImageButton delete = new ImageButton(Victimes.this);
        delete.setImageResource(R.drawable.ic_baseline_delete_24);
        //delete.setBackgroundColor(getResources().getColor(R.color.def));


        nomV.setSingleLine();
        pNomV.setSingleLine();

        final Date[] currentDate = {new Date()};
        final EditText dateNV = new EditText(Victimes.this);
        final Button actuDate = findViewById(R.id.buttonDate);
        final DateFormat[] dateFormat = {new SimpleDateFormat("dd/MM/yyyy")};
        final String[] formdate = {dateFormat[0].format(currentDate[0])};

        dateNV.setInputType(InputType.TYPE_NULL);
        dateNV.setText(formdate[0]);

        idVictimeO.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                idVictimeO.setBackgroundColor(getResources().getColor(R.color.green));
                idVictimeN.setBackgroundColor(getResources().getColor(R.color.def));

                TnomV.setText("Nom");
                TnomV.setTextSize(TypedValue.COMPLEX_UNIT_DIP,  20);

                TPnomV.setText("Prénom");
                TPnomV.setTextSize(TypedValue.COMPLEX_UNIT_DIP,  20);

                TDateNV.setText("Date de naissance");
                TDateNV.setTextSize(TypedValue.COMPLEX_UNIT_DIP,  20);

               TnomVictime.removeAllViews();
               TpNomVictime.removeAllViews();
               nomVictime.removeAllViews();
               pNomVictime.removeAllViews();
               TedateNVictime.removeAllViews();
               TadateNVictime.removeAllViews();

                TnomVictime.addView(TnomV);
                TpNomVictime.addView(TPnomV);
                TedateNVictime.addView(TDateNV);


                nomVictime.addView(nomV);
                pNomVictime.addView(pNomV);
                TadateNVictime.addView(dateNV);
                TadateNVictime.addView(calendar);
                TadateNVictime.addView(delete);
            }
        });

        idVictimeN.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                idVictimeO.setBackgroundColor(getResources().getColor(R.color.def));
                idVictimeN.setBackgroundColor(getResources().getColor(R.color.green));

                TnomVictime.removeAllViews();
                TpNomVictime.removeAllViews();
                nomVictime.removeAllViews();
                pNomVictime.removeAllViews();
                TedateNVictime.removeAllViews();
                TadateNVictime.removeAllViews();
            }
        });

        dateNV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog
                DatePickerDialog picker = new DatePickerDialog(Victimes.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        if (monthOfYear < 10){
                            dateNV.setText(dayOfMonth + "/0" + (monthOfYear + 1) + "/" + year);
                        }else {
                            dateNV.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                        }
                        try {
                            String age = calculAge(dateNV.getText().toString());
                            ageV.setText(age);
                            ageV.setTextSize(TypedValue.COMPLEX_UNIT_DIP,  20);

                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                }, year, month, day);
                picker.getDatePicker().setMaxDate(new Date().getTime());
                picker.show();
            }
        });

        calendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);

                if (month < 10){
                    dateNV.setText(day + "/0" + (month + 1) + "/" + year);
                }else {
                    dateNV.setText(day + "/" + (month + 1) + "/" + year);
                }
                try {
                    String age = calculAge(dateNV.getText().toString());
                    ageV.setText(age);
                    ageV.setTextSize(TypedValue.COMPLEX_UNIT_DIP,  20);

                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dateNV.setText("");
            }
        });
    }

    public String calculAge (String date) throws ParseException {
        String resultat = "0";

        Date date1 = new SimpleDateFormat("dd/MM/yyyy").parse(date);
        Date actuelle = new Date();
        actuelle.getTime();

        long diffInMillies = Math.abs(actuelle.getTime() - date1.getTime());
        long diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);

        resultat = String.valueOf(diff);
        System.out.println(resultat);
        return resultat;
    }
}