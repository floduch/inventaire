package com.example.inventairemateriel;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity3 extends AppCompatActivity {

    private static final String TAG = "";

    private FirebaseDatabase database;
    private DatabaseReference myRef, myRef1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        Bundle extras = getIntent().getExtras();
        String pers;
        if (extras != null) {
            pers = extras.getString("personne");
            // and get whatever type user account id is
        }else {
            pers ="0";
        }

        System.out.println(pers);
        Intent intent3 = new Intent(MainActivity3.this, MainActivity3.class);
        Intent intent4 = new Intent(MainActivity3.this, MainActivity4.class);
        System.out.println(pers.getClass().getName());


        final AutoCompleteTextView nomMat = (AutoCompleteTextView) findViewById(R.id.autocomplete_mat);
        final EditText quantite = findViewById(R.id.editTextQuant);

        final Button nouveau = findViewById(R.id.buttonNewMat);
        final Button valider = findViewById(R.id.buttonVal);

        database = FirebaseDatabase.getInstance();
        myRef =  database.getReference("1").child("Personne").child(pers).child("Matériel");
        myRef1 = database.getReference("0").child("0");

        myRef1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<String> value = (ArrayList<String>) dataSnapshot.getValue();
                ArrayList<String> resultatsVal = new ArrayList<String>();

                for (int i = 0; i < value.size(); i++) {
                    resultatsVal.add(value.get(i));
                }
                final HRArrayAdapter<String> adapterVal = new HRArrayAdapter<String>(MainActivity3.this, android.R.layout.simple_list_item_1, resultatsVal);
                nomMat.setAdapter(adapterVal);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

        nouveau.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        String mat = nomMat.getText().toString();
                        String qua = quantite.getText().toString();

                        long taille = dataSnapshot.getChildrenCount();
                        myRef.child(mat).setValue(qua);
                    }
                    @Override
                    public void onCancelled(DatabaseError error) {
                        Log.w(TAG, "Failed to read value.", error.toException());
                    }
                });
                intent3.putExtra("personne",pers);
                startActivity(intent3);
            }
        });

        valider.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        String mat = nomMat.getText().toString();
                        String qua = quantite.getText().toString();

                        long taille = dataSnapshot.getChildrenCount();
                        myRef.child(mat).setValue(qua);
                    }
                    @Override
                    public void onCancelled(DatabaseError error) {
                        Log.w(TAG, "Failed to read value.", error.toException());
                    }
                });

                startActivity(intent4);
            }
        });

    }
}