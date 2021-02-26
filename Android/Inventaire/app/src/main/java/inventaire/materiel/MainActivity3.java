package inventaire.materiel;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;

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
        Intent intent3 = new Intent(inventaire.materiel.MainActivity3.this, inventaire.materiel.MainActivity3.class);
        Intent intent4 = new Intent(inventaire.materiel.MainActivity3.this, MainActivity4.class);

        final AutoCompleteTextView nomMat = (AutoCompleteTextView) findViewById(R.id.autocomplete_mat);
        nomMat.setFilters(new InputFilter[]{new InputFilter.AllCaps()});
        final EditText quantite = findViewById(R.id.editTextQuant);

        final Button nouveau = findViewById(R.id.buttonNewMat);
        final Button valider = findViewById(R.id.buttonVal);
        nouveau.setBackground(getDrawable(R.drawable.rounded_corner));
        valider.setBackground(getDrawable(R.drawable.rounded_corner));

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
                final HRArrayAdapter<String> adapterVal = new HRArrayAdapter<String>(inventaire.materiel.MainActivity3.this, android.R.layout.simple_list_item_1, resultatsVal);
                nomMat.setAdapter(adapterVal);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

        nouveau.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                String mat = nomMat.getText().toString();
                String qua = quantite.getText().toString();

                if (TextUtils.isEmpty(mat) || TextUtils.isEmpty(qua)) {
                    Toast myToast = Toast.makeText(MainActivity3.this, "Veuillez entrer un matériel et/ou une quantité", Toast.LENGTH_SHORT);
                    myToast.show();

                } else {

                    myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {

                            String mat = nomMat.getText().toString();
                            String qua = quantite.getText().toString();

                            myRef.child(mat).setValue(qua);
                        }

                        @Override
                        public void onCancelled(DatabaseError error) {
                            Log.w(TAG, "Failed to read value.", error.toException());
                        }
                    });

                    intent3.putExtra("personne", pers);
                    startActivity(intent3);
                }
            }
        });

        valider.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                String mat = nomMat.getText().toString();
                String qua = quantite.getText().toString();

                if (TextUtils.isEmpty(mat) || TextUtils.isEmpty(qua)) {
                    Toast myToast = Toast.makeText(MainActivity3.this, "Veuillez entrer un matériel et/ou une quantité", Toast.LENGTH_SHORT);
                    myToast.show();

                } else {

                    myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {

                            String mat = nomMat.getText().toString();
                            String qua = quantite.getText().toString();

                            myRef.child(mat).setValue(qua);

                        }

                        @Override
                        public void onCancelled(DatabaseError error) {
                            Log.w(TAG, "Failed to read value.", error.toException());
                        }
                    });

                    intent4.putExtra("personne", pers);
                    startActivity(intent4);
                }
            }
        });

    }
}