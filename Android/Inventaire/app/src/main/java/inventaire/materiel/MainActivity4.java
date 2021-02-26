package inventaire.materiel;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity4 extends AppCompatActivity {
    private static final String TAG = "";

    private FirebaseDatabase database;
    private DatabaseReference myRef, myRef1;
    private HashMap<Object, String> map = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);

        Bundle extras = getIntent().getExtras();
        String pers;
        if (extras != null) {
            pers = extras.getString("personne");
            // and get whatever type user account id is
        }else {
            pers ="0";
        }

        final Intent intent4 = new Intent(MainActivity4.this, MainActivity.class);
        final Intent intent5 = new Intent(MainActivity4.this, MainActivity5.class);

        final LinearLayout textResum = findViewById(R.id.textResum);
        final TextView text = new TextView(MainActivity4.this);
        text.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);

        final Button valider = findViewById(R.id.buttonVal);
        final Button actu = findViewById(R.id.buttonActu);
        final Button modif = findViewById(R.id.buttonModif);
        valider.setBackground(getDrawable(R.drawable.rounded_corner));
        actu.setBackground(getDrawable(R.drawable.rounded_corner));
        modif.setBackground(getDrawable(R.drawable.rounded_corner));

        database = FirebaseDatabase.getInstance();
        myRef =  database.getReference("1").child("Personne").child(pers).child("Matériel");
        ArrayList<String> liste = new ArrayList<>();


        actu.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        HashMap<String, String> value = (HashMap) dataSnapshot.getValue();
                        String resultat = "";
                        try {
                            for (String i : value.keySet()) {

                                resultat += i+ " : "+value.get(i) + "\n";
                                text.setText(resultat);
                                textResum.removeView(text);
                                textResum.addView(text);
                            }
                        }
                        catch ( java.lang.NullPointerException j) {
                            System.out.println("NON");
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError error) {
                        Log.w(TAG, "Failed to read value.", error.toException());
                    }
                });
            }
        });

        valider.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(intent4);
            }
        });

        modif.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                intent5.putExtra("personne",pers);
                startActivity(intent5);
            }
        });

    }
}