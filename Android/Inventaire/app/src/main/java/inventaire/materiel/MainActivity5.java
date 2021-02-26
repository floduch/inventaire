package inventaire.materiel;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextClock;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Exclude;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

public class MainActivity5 extends AppCompatActivity {

    private static final String TAG = "";

    private FirebaseDatabase database;
    private DatabaseReference myRef, myRef1;
    private HashMap<Object, String> map = new HashMap<>();
    private boolean ajoutSoust;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main5);

        Bundle extras = getIntent().getExtras();
        String pers;
        if (extras != null) {
            pers = extras.getString("personne");
            // and get whatever type user account id is
        }else {
            pers ="0";
        }

        final Intent intent = new Intent(MainActivity5.this, MainActivity.class);
        final  Intent intent1 = new Intent(MainActivity5.this, MainActivity5.class);

        final LinearLayout textResum = findViewById(R.id.textResum);
        final ScrollView scrol = findViewById(R.id.scrol);

        final Button validerQuant = new Button(MainActivity5.this);
        final Button terminer = findViewById(R.id.buttonVal);
        final Button annuler = findViewById(R.id.buttonAnn);
        final Button ajouter = findViewById(R.id.buttonAjout);
        final Button soustra = findViewById(R.id.buttonSoust);


        final EditText quantite = new EditText(MainActivity5.this);
        final TextView ajout = new TextView(MainActivity5.this);

        validerQuant.setBackground(getDrawable(R.drawable.rounded_corner));
        terminer.setBackground(getDrawable(R.drawable.rounded_corner));
        annuler.setBackground(getDrawable(R.drawable.rounded_corner));
//        ajouter.setBackground(getDrawable(R.drawable.rounded_corner));
//        soustra.setBackground(getDrawable(R.drawable.rounded_corner));

        terminer.setEnabled(false);
        validerQuant.setEnabled(false);

//        ajouter.setBackground(getDrawable(R.drawable.rounded_corner_green));

        quantite.setInputType(InputType.TYPE_CLASS_NUMBER);
        validerQuant.setText("Valider");
        validerQuant.setBackgroundColor(getResources().getColor(R.color.red));
        validerQuant.setTextColor(Color.BLACK);
        
        database = FirebaseDatabase.getInstance();
        myRef =  database.getReference("1").child("Personne").child(pers).child("Matériel");

        ArrayList<TextView> liste = new ArrayList<>();

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                HashMap<String, String> value = (HashMap) dataSnapshot.getValue();

                String resultat = "";
                TextView[] texte = new TextView[value.size()];
                for (int i = 0; i< texte.length; i++){
                    texte[i] = new TextView(MainActivity5.this);
                    texte[i].setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
                }

                int id = 0;
                for (String a : value.keySet()) {

                    resultat = a;

                    texte[id].setText(resultat);
                    liste.add(texte[id]);
                    textResum.removeView(liste.get(id));
                    textResum.addView(liste.get(id));
                    id++;
                }
                ajouter.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        ajoutSoust = true;
                        ajouter.setBackgroundColor(getResources().getColor(R.color.green));
                        soustra.setBackgroundColor(getResources().getColor(R.color.purple_200));
                        validerQuant.setEnabled(true);
                        //validerQuant.setBackgroundColor(Color.rgb(134,187,252));
                        validerQuant.setBackgroundColor(getResources().getColor(R.color.purple_200));
                    }
                });

                soustra.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        ajoutSoust = false;
                        soustra.setBackgroundColor(getResources().getColor(R.color.green));
                        ajouter.setBackgroundColor(getResources().getColor(R.color.purple_200));
                        validerQuant.setEnabled(true);
                        //validerQuant.setBackgroundColor(Color.rgb(134,187,252));
                        validerQuant.setBackgroundColor(getResources().getColor(R.color.purple_200));
                    }
                });

                terminer.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        startActivity(intent);
                    }
                });

                annuler.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        startActivity(intent);
                    }
                });

                for (int i = 0; i < texte.length; i++){
                    int k = i;
                    String tex = (String) texte[k].getText();
                    texte[k].setText(tex + " : " + value.get(tex));

                    String finalResultat = resultat;
                    int finalI = i;
                    texte[i].setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {

                            quantite.setText("");
                            textResum.removeView(quantite);
                            textResum.removeView(validerQuant);

                            textResum.addView(quantite);
                            textResum.addView(validerQuant);

                            validerQuant.setOnClickListener(new View.OnClickListener() {
                                public void onClick(View v) {

                                    if (quantite.getText().toString().equals("0")) {
                                        myRef.child(tex).removeValue();
                                        Toast myToast = Toast.makeText(MainActivity5.this, "Supprimé de la base de données", Toast.LENGTH_SHORT);
                                        myToast.show();
                                        intent1.putExtra("personne",pers);
                                        startActivity(intent1);

                                    } else {

                                        String val = value.get(tex);
                                        System.out.println("texte i "+tex);
                                        String te = quantite.getText().toString() ;
                                        System.out.println("val = "+ val + " te = "+ te);
                                        String tot;
                                        texte[k].setText(tex + " : " + value.get(tex));

                                        if (ajoutSoust){
                                           tot  = String.valueOf(Math.abs(Integer.parseInt(te) + Integer.parseInt(val)));
                                        }else if (ajoutSoust == false){
                                            tot = String.valueOf(Math.abs(Integer.parseInt(te) - Integer.parseInt(val)));
                                        }else {
                                            tot  = String.valueOf(Math.abs(Integer.parseInt(te) + Integer.parseInt(val)));
                                        }

                                        myRef.child(tex).setValue(String.valueOf(tot));
                                        Toast myToast = Toast.makeText(MainActivity5.this, "Ajouté à la base de données", Toast.LENGTH_SHORT);
                                        myToast.show();
                                        terminer.setEnabled(true);

                                        intent1.putExtra("personne",pers);
                                        startActivity(intent1);
                                    }
                                }
                                });


                            terminer.setOnClickListener(new View.OnClickListener() {
                                public void onClick(View v) {
                                        startActivity(intent);
                                }
                            });

                            annuler.setOnClickListener(new View.OnClickListener() {
                                public void onClick(View v) {
                                    startActivity(intent);
                                }
                            });

                            ajouter.setOnClickListener(new View.OnClickListener() {
                                public void onClick(View v) {
                                    ajoutSoust = true;
                                    ajouter.setBackgroundColor(getResources().getColor(R.color.green));
                                    soustra.setBackgroundColor(getResources().getColor(R.color.purple_200));
                                    validerQuant.setEnabled(true);
                                    //validerQuant.setBackgroundColor(Color.rgb(134,187,252));
                                    validerQuant.setBackgroundColor(getResources().getColor(R.color.purple_200));
                                }
                            });

                            soustra.setOnClickListener(new View.OnClickListener() {
                                public void onClick(View v) {
                                    ajoutSoust = false;
                                    soustra.setBackgroundColor(getResources().getColor(R.color.green));
                                    ajouter.setBackgroundColor(getResources().getColor(R.color.purple_200));
                                    validerQuant.setEnabled(true);
                                    //validerQuant.setBackgroundColor(Color.rgb(134,187,252));
                                    validerQuant.setBackgroundColor(getResources().getColor(R.color.purple_200));
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
}