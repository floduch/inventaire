package inventaire.materiel;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class Administration extends AppCompatActivity {

    private static final String TAG = "";

    private FirebaseDatabase database;
    private DatabaseReference myRef, myRef1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_administration);

        Bundle extras = getIntent().getExtras();
        Boolean connect;
        if (extras != null) {
            connect = extras.getBoolean("connect");
            // and get whatever type user account id is
        }else {
           connect = false;
        }

        LinearLayout layout = findViewById(R.id.textResum);
        LinearLayout layout1 = findViewById(R.id.textResum1);
       LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        layout.setGravity(Gravity.CENTER);
        Button terminer = findViewById(R.id.buttonVal);
        Button supprimer = new Button(Administration.this);
        Button ajouter = new Button(Administration.this);
        Button modifier = new Button(Administration.this);
        Button oui = new Button(Administration.this);
        Button non = new Button(Administration.this);

        terminer.setBackground(getDrawable(R.drawable.rounded_corner));

        supprimer.setBackground(getDrawable(R.drawable.rounded_corner));
        supprimer.setBackground(getDrawable(R.drawable.ic_baseline_remove_24));

        ajouter.setBackground(getDrawable(R.drawable.rounded_corner));
        ajouter.setBackground(getDrawable(R.drawable.ic_round_add_241));

        modifier.setBackground(getDrawable(R.drawable.rounded_corner));
        modifier.setBackground(getDrawable(R.drawable.ic_round_mode_edit_24));

        oui.setBackground(getDrawable(R.drawable.rounded_corner));
        non.setBackground(getDrawable(R.drawable.rounded_corner));

        Button connexion = findViewById(R.id.buttonConnexion);
        connexion.setBackground(getDrawable(R.drawable.rounded_corner));

        TextView confirm = new TextView(Administration.this);

        confirm.setText("Confirmer ?");
        confirm.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);

        supprimer.setText("Supprimer la base");
        supprimer.setBackgroundColor(Color.rgb(134,187,252));
        supprimer.setTextColor(Color.BLACK);

        modifier.setText("Modifier les quantités");
        modifier.setBackgroundColor(Color.rgb(134,187,252));
        modifier.setTextColor(Color.BLACK);

        ajouter.setText("Ajouter du matériel");
        ajouter.setBackgroundColor(Color.rgb(134,187,252));
        ajouter.setTextColor(Color.BLACK);

        oui.setText("OUI");
        oui.setBackgroundColor(Color.rgb(134,187,252));
        oui.setTextColor(Color.BLACK);

        non.setText("NON");
        non.setBackgroundColor(Color.rgb(134,187,252));
        non.setTextColor(Color.BLACK);

        final Intent intent = new Intent(Administration.this, MainActivity.class);
        final Intent intent2 = new Intent(Administration.this, Administration.class);
        final Intent intent3 = new Intent(Administration.this, Connexion.class);
        final Intent intent4 = new Intent(Administration.this, MainActivity5.class);
        final Intent intent5 = new Intent(Administration.this, MainActivity3.class);

        database = FirebaseDatabase.getInstance();
        myRef =  database.getReference("1").child("Personne"); //.child(pers).child("Matériel")
        myRef1 =  database.getReference("2").child("Connexion").child("Connected");

        myRef1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String value = (String) dataSnapshot.getValue();
                if (value.equals("true")){
//                    connexion.setEnabled(false);
                    connexion.setText("Déconnexion");

                    connexion.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            myRef1.setValue("false");
                            Toast myToast = Toast.makeText(Administration.this, "Déconnecté ", Toast.LENGTH_SHORT);
                            myToast.show();
                            connexion.setText("Connexion");
                            startActivity(intent2);

                        }
                    });

                }
            }
            @Override
            public void onCancelled(DatabaseError error) {
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                HashMap<String, HashMap<String, String>> value = (HashMap) dataSnapshot.getValue();

                String resultat = "";

                try{
                    System.out.println(value + " "+String.valueOf(value));
                    Button[] bouttons = new Button[value.size()];

                    for (int i = 0; i < bouttons.length; i++){
                        bouttons[i] = new Button(Administration.this);
                    }

                    int id = 0;
                    for (String i : value.keySet()) {

                        bouttons[id].setText(i);
                        bouttons[id].setBackgroundColor(Color.rgb(134,187,252));
                        bouttons[id].setTextColor(Color.BLACK);
                        layout.removeView(bouttons[id]);
                        layout.addView(bouttons[id]);
                        id++;
                        System.out.println(i+ " "+String.valueOf(value.get(i)));

                    }

                    for (int i = 0; i < bouttons.length; i++){
                        int k = i;
                        bouttons[i].setOnClickListener(new View.OnClickListener() {
                            public void onClick(View v) {
                                TextView texte = new TextView(Administration.this);
                                layout1.removeAllViews();

                                String mots = "";

                                System.out.println("Texte bouton " +bouttons[k].getText());
                                System.out.println("value of texte bouton "+String.valueOf(value.get(bouttons[k].getText())));
                                HashMap<String, String> test = (HashMap) dataSnapshot.child((String) bouttons[k].getText()).child("Matériel").getValue();
                                ;//value.get(bouttons[k].getText());

                                for (String s : test.keySet()) {

                                    mots += s + " : " + String.valueOf(test.get(s)) + "\n";
                                    texte.setText(mots);
                                    texte.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
                                    layout1.removeView(texte);
                                    layout1.addView(texte);
                                }

                                myRef1.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        String value = (String) dataSnapshot.getValue();

                                        layout1.removeView(modifier);
                                        layout1.addView(modifier);

                                        layout1.removeView(ajouter);
                                        layout1.addView(ajouter);

                                        modifier.setOnClickListener(new View.OnClickListener() {
                                            public void onClick(View v) {
                                                intent4.putExtra("personne", bouttons[k].getText());
                                                startActivity(intent4);
                                            }
                                        });

                                        ajouter.setOnClickListener(new View.OnClickListener() {
                                            public void onClick(View v) {
                                                intent5.putExtra("personne", bouttons[k].getText());
                                                startActivity(intent5);
                                            }
                                        });

                                        if (value.equals("true")){

                                            layout1.removeView(supprimer);
                                            layout1.addView(supprimer);

                                            supprimer.setOnClickListener(new View.OnClickListener() {
                                                public void onClick(View v) {

                                                    layout1.removeView(confirm);
                                                    layout1.addView(confirm);

                                                    layout1.removeView(oui);
                                                    layout1.addView(oui);

                                                    layout1.removeView(non);
                                                    layout1.addView(non);

                                                    oui.setOnClickListener(new View.OnClickListener() {
                                                        public void onClick(View v) {
                                                            Toast myToast = Toast.makeText(Administration.this, "Base " + bouttons[k].getText() + " suprimée", Toast.LENGTH_SHORT);
                                                            myToast.show();

                                                            myRef.child((String) bouttons[k].getText()).removeValue();
                                                            startActivity(intent2);
                                                        }
                                                    });

                                                    non.setOnClickListener(new View.OnClickListener() {
                                                        public void onClick(View v) {
                                                            startActivity(intent2);
                                                        }
                                                    });
                                                }
                                            });
                                        }
                                    }
                                    @Override
                                    public void onCancelled(DatabaseError error) {
                                        Log.w(TAG, "Failed to read value.", error.toException());
                                    }
                                });
                            }
                        });
                    }
                }catch ( java.lang.NullPointerException j){
                    Toast myToast = Toast.makeText(Administration.this, "Aucune base existante", Toast.LENGTH_SHORT);
                    myToast.show();

                    startActivity(intent);
                }
            }
            @Override
            public void onCancelled(DatabaseError error) {
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

        terminer.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(intent);
            }
        });

        connexion.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(intent3);
            }
        });
    }
}