package inventaire.materiel;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity2 extends AppCompatActivity {

    private static final String TAG = "";
    DatePickerDialog picker;
    EditText date;

    DatabaseReference myRef, myRef1;
    FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        final Intent intent2 = new Intent(inventaire.materiel.MainActivity2.this, inventaire.materiel.MainActivity3.class);

        final AutoCompleteTextView nomprenom = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextNom);
        //nomprenom.setFilters(new InputFilter[]{new InputFilter.AllCaps()});

        final Button valider = findViewById(R.id.buttonVal);
        valider.setBackground(getDrawable(R.drawable.rounded_corner));

        database = FirebaseDatabase.getInstance();
        myRef =  database.getReference("1").child("Personne");
        ArrayList<String> liste = new ArrayList<>();

        date=(EditText) findViewById(R.id.editDate);

        final Date[] currentDate = {new Date()};
        final EditText date = findViewById(R.id.editDate);
        final Button actuDate = findViewById(R.id.buttonDate);
        final DateFormat[] dateFormat = {new SimpleDateFormat("dd/MM/yyyy")};
        final String[] formdate = {dateFormat[0].format(currentDate[0])};

        actuDate.setBackground(getDrawable(R.drawable.rounded_corner));
        date.setInputType(InputType.TYPE_NULL);
        date.setText(formdate[0]);

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                HashMap<String, String> value = (HashMap) dataSnapshot.getValue();
                String resultat = "";
                ArrayList<String> resultatsVal = new ArrayList<String>();


                try {
                    for (String i : value.keySet()) {
                        resultatsVal.add(i);
                        final HRArrayAdapter<String> adapterVal = new HRArrayAdapter<String>(inventaire.materiel.MainActivity2.this, android.R.layout.simple_list_item_1, resultatsVal);
                        nomprenom.setAdapter(adapterVal);
                    }
//
                }catch ( java.lang.NullPointerException j) {
                    System.out.println("NON");
                }

            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog
                picker = new DatePickerDialog(inventaire.materiel.MainActivity2.this, new DatePickerDialog.OnDateSetListener() {
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

                if (TextUtils.isEmpty(Nomprenom)) {
                    Toast myToast = Toast.makeText(MainActivity2.this, "Veuillez entrer un nom", Toast.LENGTH_SHORT);
                    myToast.show();

                }else {

                    database = FirebaseDatabase.getInstance();
                    myRef =  database.getReference("1").child("Personne").child(Nomprenom);
                    myRef.child("Date").setValue(dateP);
                    intent2.putExtra("personne",Nomprenom);
                    startActivity(intent2);
                }
            }
        });
    }
}