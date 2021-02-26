package main.courante;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.platforminfo.DefaultUserAgentPublisher;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity7 extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private static final String TAG = "";

//    Référence à la base de données
    DatabaseReference myRef, myRef2, myRef3, myRef4, myRef5;
    FirebaseDatabase database;

    Enregistrements Manifestation = new Enregistrements();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main7);

//        Récupération des valeurs de l'Intent
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

        final Intent intent = new Intent(this, Evenements.class);
//        Récupération des éléments visuels
        final TextView vehicules = findViewById(R.id.textVEH);
        final Spinner spinnerSection = findViewById(R.id.spinnerSection);
        final Spinner spinnerType = findViewById(R.id.spinnerType);
        final Spinner spinnerImmat = findViewById(R.id.spinnerImmat);

        final TextView lots = findViewById(R.id.textLots);
        final TextView lotA = findViewById(R.id.textViewLotA);
        final TextView lotB= findViewById(R.id.textViewLotB);
        final TextView lotC = findViewById(R.id.textViewLotC);

        final EditText editA = findViewById(R.id.editLotA);
        final EditText editB = findViewById(R.id.editLotB);
        final EditText editC = findViewById(R.id.editLotC);

        final Button submit = findViewById(R.id.buttonVal);

//        Références à la base données
        database = FirebaseDatabase.getInstance();
        myRef =  database.getReference("2").child("MAT").child("VEH");
        myRef2 =  database.getReference("2").child("MAT").child("VEH");
        myRef3 =  database.getReference("2").child("MAT").child("VEH");
        myRef4 = database.getReference("0").child(num).child(nat).child("Matériel").child("Lots");
        myRef5 = database.getReference("0").child(num).child(nat).child("Matériel").child("Immat");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                HashMap<String, String> Depart = (HashMap) dataSnapshot.getValue();


                ArrayList section = new ArrayList();

                for (String i : Depart.keySet()) {
                    section.add(i);
                }


                ArrayList type = new ArrayList();
                Depart = (HashMap) dataSnapshot.child("Noisy-Gournay").getValue();
                for (String i : Depart.keySet()){
                    type.add(i);
                }

                ArrayAdapter<String> adp = new ArrayAdapter<String> (MainActivity7.this,android.R.layout.simple_spinner_dropdown_item,section);
                spinnerSection.setAdapter(adp);
                spinnerSection.setPrompt("Section");

                spinnerSection.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {

                        String loc = spinnerSection.getSelectedItem().toString();

                        myRef2.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                HashMap<String, String> Depart = (HashMap) dataSnapshot.getValue();


                                ArrayList section = new ArrayList();

                                for (String i : Depart.keySet()) {
                                    section.add(i);
                                }

                                ArrayAdapter<String> adp = new ArrayAdapter<String> (MainActivity7.this,android.R.layout.simple_spinner_dropdown_item,section);
                                spinnerSection.setAdapter(adp);
                                spinnerSection.setPrompt("Section");

                                spinnerSection.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {

                                        myRef2.addValueEventListener(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(DataSnapshot dataSnapshot) {
                                                HashMap<String, String> Depart = (HashMap) dataSnapshot.getValue();
                                                String loc = spinnerSection.getSelectedItem().toString();

                                                ArrayList type = new ArrayList();
                                                Depart = (HashMap) dataSnapshot.child(loc).getValue();
                                                for (String i : Depart.keySet()){
                                                    type.add(i);
                                                }
                                                ArrayAdapter<String> adp = new ArrayAdapter<String> (MainActivity7.this,android.R.layout.simple_spinner_dropdown_item,type);
                                                spinnerType.setAdapter(adp);
                                                spinnerType.setPrompt("Type");

                                                spinnerType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                                    @Override
                                                    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {

                                                        myRef3.addValueEventListener(new ValueEventListener() {
                                                            @Override
                                                            public void onDataChange(DataSnapshot dataSnapshot) {
                                                                String loc = spinnerSection.getSelectedItem().toString();
                                                                String tp = spinnerType.getSelectedItem().toString();
                                                                HashMap<String, String> Depart = (HashMap) dataSnapshot.child(loc).child(tp).getValue();

                                                                ArrayList immat = new ArrayList();
                                                                for (String i : Depart.keySet()){
                                                                    immat.add(i+" : "+Depart.get(i));
                                                                }
                                                                ArrayAdapter<String> adp = new ArrayAdapter<String> (MainActivity7.this,android.R.layout.simple_spinner_dropdown_item,immat);
                                                                spinnerImmat.setAdapter(adp);
                                                                spinnerImmat.setPrompt("Immatriculation");
                                                            }
                                                            @Override
                                                            public void onCancelled(DatabaseError error) {
                                                                // Failed to read value
                                                                Log.w(TAG, "Failed to read value.", error.toException());
                                                            }
                                                        });
                                                    }
                                                    @Override
                                                    public void onNothingSelected(AdapterView<?> parent) {
                                                    }
                                                });
                                            }
                                            @Override
                                            public void onCancelled(DatabaseError error) {
                                                // Failed to read value
                                                Log.w(TAG, "Failed to read value.", error.toException());
                                            }
                                        });
                                    }
                                    @Override
                                    public void onNothingSelected(AdapterView<?> parent) {
                                    }
                                });
                            }
                            @Override
                            public void onCancelled(DatabaseError error) {
                                // Failed to read value
                                Log.w(TAG, "Failed to read value.", error.toException());
                            }
                        });
                    }
                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String immat = spinnerImmat.getSelectedItem().toString();

                String nbLotA = editA.getText().toString();
                String nbLotB = editB.getText().toString();
                String nbLotC = editC.getText().toString();

                Manifestation.setImmat(immat);
                Manifestation.setLots(nbLotA, nbLotB, nbLotC);

                List<String> list = Manifestation.getLots();

                myRef4.child("Lot A").setValue(list.get(0));
                myRef4.child("Lot B").setValue(list.get(1));
                myRef4.child("Lot C").setValue(list.get(2));
                myRef5.setValue(Manifestation.getImmat());

                Toast myToast = Toast.makeText(MainActivity7.this, "Ajouté à la base de données", Toast.LENGTH_SHORT);
                myToast.show();
//                intent.putExtra("Numero", num);
//                intent.putExtra("Nature", nat);
//                startActivity(intent);
            }
        });

    }
    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
        // An item was selected. You can retrieve the selected item using
        // parent.getItemAtPosition(pos)
        String item = parent.getItemAtPosition(pos).toString();
    }
    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}