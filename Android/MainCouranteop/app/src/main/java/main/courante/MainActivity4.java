package main.courante;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatAutoCompleteTextView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity4 extends AppCompatActivity {

    private static final String TAG = "";
    Enregistrements Manifestation = new Enregistrements();

//    Références à la base de données
    DatabaseReference myRef, myRef1, myRef2;
    FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);

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



        final Intent intent2 = getIntent();
        final String personne = intent2.getStringExtra("Nom");

//        Création de l'intent pour accéder à la page suivante
        final Intent intent3 = new Intent(this, MainActivity5.class);
        intent3.setAction("activite4");

//        Récupération des éléments visuels
        final LinearLayout llMain = findViewById(R.id.layoutPers);

        final TextView Personnel = (TextView) findViewById(R.id.textPersonnel);

        final TextView chef = (TextView) findViewById(R.id.textChef);
        final TextView chef2 = new TextView(this);
        final TextView chef3 = new TextView(this);

        final AutoCompleteTextView nomChef = (AutoCompleteTextView) findViewById(R.id.autocomplete_chef);
        final AutoCompleteTextView nomChef2 = new AutoCompleteTextView(MainActivity4.this);
        final AutoCompleteTextView nomChef3 = new AutoCompleteTextView(MainActivity4.this);

        Button submit = findViewById(R.id.button_ajout);

//        Références à la base de données
        database = FirebaseDatabase.getInstance();
        myRef =  database.getReference("0").child(num).child(nat).child("Chefs");
        myRef1 =  database.getReference("0").child(num).child(nat).child("Infos").child("Type");
        myRef2 = database.getReference("1");    //.child("CO");

        myRef1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

//                Récupération de la valeur du Type de DPS
                String value = dataSnapshot.getValue(String .class);
//                Toast myToast = Toast.makeText(MainActivity4.this, value, Toast.LENGTH_SHORT);


                if (value.equals("PAPS")){
                    chef.setText("Responsable du poste");
                    String nom = nomChef.getText().toString();

                    myRef.child("Responsable du Poste").setValue(nom);

                }else if (value.equals("DPS-PE")){
                    chef.setText("Chef de poste");
                    String nom = nomChef.getText().toString();

                    myRef.child("Chef du Poste").setValue(nom);

                }else if (value.equals("DPS-ME")){
                    chef.setText("Chef de section");
                    String nom = nomChef.getText().toString();

                    chef2.setText("Chef d'équipe");
                    llMain.addView(chef2);
                    llMain.addView(nomChef2);

                    String nom2 = nomChef.getText().toString();

                    myRef.child("Chef de section").setValue(nom);
                    myRef.child("Chef d'équipe").setValue(nom2);


                }else{
                    chef.setText("Chef de dispositif");
                    chef2.setText("Chef de section");
                    chef3.setText("Chef d'équipe");

                    llMain.addView(chef2);
                    llMain.addView(nomChef2);
                    llMain.addView(chef3);
                    llMain.addView(nomChef3);

                    String nom = nomChef.getText().toString();
                    String nom2 = nomChef.getText().toString();
                    String nom3 = nomChef.getText().toString();

                    myRef.child("Chef de dispositif").setValue(nom);
                    myRef.child("Chef de section").setValue(nom2);
                    myRef.child("Chef d'équipe").setValue(nom3);

                }
            }
            @Override
            public void onCancelled(DatabaseError error) {
// Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

        myRef2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

//                Récupération des valeurs des CO et PSE2
                ArrayList<String> value = (ArrayList<String>) dataSnapshot.child("CO").getValue();
                ArrayList<String> value1 = (ArrayList<String>) dataSnapshot.child("PSE2").getValue();
                ArrayList<String> resultats = new ArrayList<String >();

                for (int i = 0; i < value.size(); i++){
                    resultats.add(value.get(i));
                }
                for (int i = 0; i < value1.size(); i++){
                    resultats.add(value1.get(i));
                }

//                Permet l'affichage autocomplété
                HRArrayAdapter<String> adapter = new HRArrayAdapter<String>(MainActivity4.this, android.R.layout.simple_list_item_1, resultats);
                nomChef.setAdapter(adapter);
                nomChef2.setAdapter(adapter);
                nomChef3.setAdapter(adapter);
            }
            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

//                Récupération des valeurs des champs
                final String nom1 = nomChef.getText().toString();
                final String nom2 = nomChef2.getText().toString();
                final String nom3 = nomChef3.getText().toString();
                Manifestation.setChefPoste(nom1, nom2, nom3, nom1);

//                Ecriture dans la base de données
                myRef.child("Responsable du poste").setValue(nom1);
                myRef.child("Chef de dispositif").setValue(nom1);
                myRef.child("Chef de section").setValue(nom2);
                myRef.child("Chef d'équipe").setValue(nom3);

                intent3.putExtra("pers", personne);


//                Lecture de la base de données
                myRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

//                        Récupération des valeurs des chefs
                        String value = dataSnapshot.child("0").getValue(String .class);
                        Toast myToast = Toast.makeText(MainActivity4.this, (CharSequence) value, Toast.LENGTH_SHORT);
                        String displayedText = ((TextView)((LinearLayout)myToast.getView()).getChildAt(0)).getText().toString();

                    }
                    @Override
                    public void onCancelled(DatabaseError error) {
// Failed to read value
                        Log.w(TAG, "Failed to read value.", error.toException());
                    }
                });
                intent3.putExtra("Numero", num);
                intent3.putExtra("Nature", nat);
                startActivity(intent3);
            }
        });
    }
}