package main.courante;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.icu.text.SymbolTable;
import android.os.Bundle;
import android.util.LayoutDirection;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity5 extends AppCompatActivity {

    private static final String TAG = "";
    Enregistrements Manifestation = new Enregistrements();

//    Référence à la base de données
    private FirebaseDatabase database;
    private DatabaseReference myRef, myRef1, myRef2, myRef3;

    private HashMap<String, Object> pers = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main5);

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

//        Création de l'intent pour passer à l'activité suivante
        final Intent intent4 = new Intent(this, MainActivity6.class);

//        Récupération et création des éléments visuels
        final LinearLayout layout = findViewById(R.id.layoutAjout);

        final TextView txtPers = new TextView(MainActivity5.this);

        final AutoCompleteTextView nomPers = new AutoCompleteTextView(MainActivity5.this);

        final Button ajoutCO = findViewById(R.id.buttonCO);
        final Button ajoutPSE2 = findViewById(R.id.buttonPSE2);
        final Button ajoutPSE1 = findViewById(R.id.buttonPSE1);
        final Button ajoutLAT = findViewById(R.id.buttonLAT);
        final Button ajoutSTAG = findViewById(R.id.buttonSTAG);

        final Button submit = findViewById(R.id.button_valid);
        submit.setEnabled(false);

//        Références à la base de données
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("1");    //.child("CO");
        myRef1 = database.getReference("0").child(num).child(nat).child("Personnel");//.child("Personnel")
        myRef2 = database.getReference("0").child(num).child(nat).child("Personnel");
        myRef3 = database.getReference("0").child(num).child(nat).child("Infos").child("Type");


//        Recupérer la valeur du type de poste
        myRef3.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                final String type = dataSnapshot.getValue().toString();

//                Récupérer les personnels des qualifications existantes
                myRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        ArrayList<String> value = (ArrayList<String>) dataSnapshot.child("CO").getValue();
                        ArrayList<String> value1 = (ArrayList<String>) dataSnapshot.child("PSE2").getValue();
                        ArrayList<String> value2 = (ArrayList<String>) dataSnapshot.child("PSE1").getValue();
                        ArrayList<String> value3 = (ArrayList<String>) dataSnapshot.child("LAT").getValue();
                        ArrayList<String> value4 = (ArrayList<String>) dataSnapshot.child("STAG").getValue();

                        ArrayList<String> resultatsCO = new ArrayList<String>();
                        ArrayList<String> resultatsPSE2 = new ArrayList<String>();
                        ArrayList<String> resultatsPSE1 = new ArrayList<String>();
                        ArrayList<String> resultatsLAT = new ArrayList<String>();
                        ArrayList<String> resultatsSTAG = new ArrayList<String>();

                        for (int i = 0; i < value.size(); i++) {
                            resultatsCO.add(value.get(i));
                        }

                        for (int i = 0; i < value1.size(); i++) {
                            resultatsPSE2.add(value1.get(i));
                        }

                        for (int i = 0; i < value2.size(); i++) {
                            resultatsPSE1.add(value2.get(i));
                        }

                        for (int i = 0; i < value3.size(); i++) {
                            resultatsLAT.add(value3.get(i));
                        }

                        for (int i = 0; i < value4.size(); i++) {
                            resultatsSTAG.add(value4.get(i));
                        }

                        if (type.equals("PAPS")) {
                            ajoutCO.setEnabled(false);
                            ajoutCO.setVisibility(View.GONE);

                            for (int i = 0; i < value.size(); i++) {
                                resultatsPSE2.add(value.get(i));
                            }
                        }

                        final HRArrayAdapter<String> adapterCO = new HRArrayAdapter<String>(MainActivity5.this, android.R.layout.simple_list_item_1, resultatsCO);
                        final HRArrayAdapter<String> adapterPSE2 = new HRArrayAdapter<String>(MainActivity5.this, android.R.layout.simple_list_item_1, resultatsPSE2);
                        final HRArrayAdapter<String> adapterPSE1 = new HRArrayAdapter<String>(MainActivity5.this, android.R.layout.simple_list_item_1, resultatsPSE1);
                        final HRArrayAdapter<String> adapterLAT = new HRArrayAdapter<String>(MainActivity5.this, android.R.layout.simple_list_item_1, resultatsLAT);
                        final HRArrayAdapter<String> adapterSTAG = new HRArrayAdapter<String>(MainActivity5.this, android.R.layout.simple_list_item_1, resultatsSTAG);

                        ajoutCO.setOnClickListener(new View.OnClickListener() {
                            public void onClick(View v) {

                                layout.removeView(txtPers);
                                layout.removeView(nomPers);
                                nomPers.setAdapter(adapterCO);
                                txtPers.setText("CO");
                                layout.addView(txtPers);
                                layout.addView(nomPers);

                                submit.setEnabled(true);
                            }
                        });

                        ajoutPSE2.setOnClickListener(new View.OnClickListener() {
                            public void onClick(View v) {

                                layout.removeView(txtPers);
                                layout.removeView(nomPers);
                                nomPers.setAdapter(adapterPSE2);
                                txtPers.setText("PSE2");
                                layout.addView(txtPers);
                                layout.addView(nomPers);

                                submit.setEnabled(true);
                            }
                        });

                        ajoutPSE1.setOnClickListener(new View.OnClickListener() {
                            public void onClick(View v) {

                                layout.removeView(txtPers);
                                layout.removeView(nomPers);
                                nomPers.setAdapter(adapterPSE1);
                                txtPers.setText("PSE1");
                                layout.addView(txtPers);
                                layout.addView(nomPers);

                                submit.setEnabled(true);
                            }
                        });

                        ajoutLAT.setOnClickListener(new View.OnClickListener() {
                            public void onClick(View v) {

                                layout.removeView(txtPers);
                                layout.removeView(nomPers);
                                nomPers.setAdapter(adapterLAT);
                                txtPers.setText("LAT");
                                layout.addView(txtPers);
                                layout.addView(nomPers);

                                submit.setEnabled(true);
                            }
                        });

                        ajoutSTAG.setOnClickListener(new View.OnClickListener() {
                            public void onClick(View v) {

                                layout.removeView(txtPers);
                                layout.removeView(nomPers);
                                nomPers.setAdapter(adapterSTAG);
                                txtPers.setText("Stagiaire");
                                layout.addView(txtPers);
                                layout.addView(nomPers);

                                submit.setEnabled(true);
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
            public void onCancelled (DatabaseError error){
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

//                Récupération des valeurs des champs
                final String nom1 = nomPers.getText().toString();
                Manifestation.setPersonnel(nom1);

//                Récupérer le nombre de personnel ajouté de chaque qualification
                myRef1.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        long test = dataSnapshot.getChildrenCount();
                        long CO = dataSnapshot.child("CO").getChildrenCount();
                        long PSE1 = dataSnapshot.child("PSE1").getChildrenCount();
                        long PSE2 = dataSnapshot.child("PSE2").getChildrenCount();
                        long LAT = dataSnapshot.child("LAT").getChildrenCount();
                        long STAG = dataSnapshot.child("STAG").getChildrenCount();

                        if (txtPers.getText().equals("CO")){
                            myRef1.child("CO").child(String.valueOf(CO)).setValue(Manifestation.getPersonnel());
                        }
                        if (txtPers.getText().equals("PSE1")){
                            myRef1.child("PSE1").child(String.valueOf(PSE1)).setValue(Manifestation.getPersonnel());
                        }
                        if (txtPers.getText().equals("PSE2")){
                            myRef1.child("PSE2").child(String.valueOf(PSE2)).setValue(Manifestation.getPersonnel());
                        }
                        if (txtPers.getText().equals("LAT")){
                            myRef1.child("LAT").child(String.valueOf(LAT)).setValue(Manifestation.getPersonnel());
                        }
                        if (txtPers.getText().equals("Stagiaire")){
                            myRef1.child("STAG").child(String.valueOf(STAG)).setValue(Manifestation.getPersonnel());
                        }
                        //myRef1.child(String.valueOf(test)).setValue(Manifestation.getPersonnel());
                    }

                    @Override
                    public void onCancelled(DatabaseError error) {
                        Log.w(TAG, "Failed to read value.", error.toException());
                    }
                });
                intent4.putExtra("Numero", num);
                intent4.putExtra("Nature", nat);
                startActivity(intent4);
            }
        });

    }
}