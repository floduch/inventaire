package main.courante;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity6 extends AppCompatActivity {

    private static final String TAG = "";
    Enregistrements Manifastation = new Enregistrements();

//    Référence à la base de données
    private FirebaseDatabase database;
    private DatabaseReference myRef, myRef1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main6);

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

//        Création des intnents pour accéder aux différentes activités
        final Intent intent5 = new Intent(this, MainActivity5.class);
        final Intent intent6 = new Intent(this, MainActivity6.class);
        final Intent intent7 = new Intent(this, MainActivity7.class);

//        Réferences à la base de données
        database = FirebaseDatabase.getInstance();
        myRef =  database.getReference("0").child(num).child(nat).child("Personnel");

//        Récupération et création des éléments visuels
        final LinearLayout layout2 = findViewById(R.id.layout2);
        final LinearLayout layoutCO = new LinearLayout(MainActivity6.this);
        final LinearLayout layoutPSE1 = new LinearLayout(MainActivity6.this);
        final LinearLayout layoutPSE2 = new LinearLayout(MainActivity6.this);
        final LinearLayout layoutLAT = new LinearLayout(MainActivity6.this);
        final LinearLayout layoutSTAG = new LinearLayout(MainActivity6.this);

        final TextView textCO = new TextView(MainActivity6.this);
        final TextView textPSE1 = new TextView(MainActivity6.this);
        final TextView textPSE2 = new TextView(MainActivity6.this);
        final TextView textLAT = new TextView(MainActivity6.this);
        final TextView textSTAG = new TextView(MainActivity6.this);

        final TextView intituleCO = new TextView(MainActivity6.this);
        final TextView intitulePSE1 = new TextView(MainActivity6.this);
        final TextView intitulePSE2 = new TextView(MainActivity6.this);
        final TextView intituleLAT = new TextView(MainActivity6.this);
        final TextView intituleSTAG = new TextView(MainActivity6.this);

        Button submit = findViewById(R.id.button_ajout2);
        Button valid = findViewById(R.id.buttonSuite);


//        Lecture de la base de données
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
//                Try catch en cas d'absence de personnel

                try {
//                    Récupérer les valeurs de chaque qualification
                    HashMap<String, String> value = (HashMap) dataSnapshot.getValue();
                    String valPSE2 = String.valueOf(value.get("PSE2"));
                    String valCO = String.valueOf(value.get("CO"));
                    String valPSE1 = String.valueOf(value.get("PSE1"));
                    String valLAT = String.valueOf(value.get("LAT"));
                    String valSTAG = String.valueOf(value.get("STAG"));

                    String pse2 = "";
                    String co = "";
                    String pse1 = "";
                    String lat = "";
                    String stag = "";

//                    Suppression des crochets dans les listes
                    for (int i = 0; i < valPSE2.length(); i++){
                        if (valPSE2.charAt(i) == '['){

                        }else if (valPSE2.charAt(i) == ']'){

                        }else {
                            pse2+=valPSE2.charAt(i);
                        }
                    }

                    for (int i = 0; i < valPSE1.length(); i++){
                        if (valPSE1.charAt(i) == '['){

                        }else if (valPSE1.charAt(i) == ']'){

                        }else {
                            pse1+=valPSE1.charAt(i);
                        }
                    }

                    for (int i = 0; i < valCO.length(); i++){
                        if (valCO.charAt(i) == '['){

                        }else if (valCO.charAt(i) == ']'){

                        }else {
                            co+=valCO.charAt(i);
                        }
                    }

                    for (int i = 0; i < valLAT.length(); i++){
                        if (valLAT.charAt(i) == '['){

                        }else if (valLAT.charAt(i) == ']'){

                        }else {
                            lat+=valLAT.charAt(i);
                        }
                    }

                    for (int i = 0; i < valSTAG.length(); i++){
                        if (valSTAG.charAt(i) == '['){

                        }else if (valSTAG.charAt(i) == ']'){

                        }else {
                            stag+=valSTAG.charAt(i);
                        }
                    }

//                    Ajout des éléments visuels remplis avec le personnel
                    for (String i : value.keySet()) {

                        if (i.equals("CO")){
                            layout2.removeView(layoutCO);
                            layoutCO.removeView(intituleCO);
                            layoutCO.removeView(textCO);

                            intituleCO.setText("CO : ");
                            intituleCO.setTextSize(20);
                            layoutCO.addView(intituleCO);
                            textCO.setText(co);
                            textCO.setTextSize(20);
                            layoutCO.addView(textCO);
                            layout2.addView(layoutCO);
                        }
                        if (i.equals("PSE1")){
                            layout2.removeView(layoutPSE1);
                            layoutPSE1.removeView(intitulePSE1);
                            layoutPSE1.removeView(textPSE1);

                            intitulePSE1.setText("PSE1 : ");
                            intitulePSE1.setTextSize(20);
                            layoutPSE1.addView(intitulePSE1);
                            textPSE1.setText(pse1);
                            textPSE1.setTextSize(20);
                            layoutPSE1.addView(textPSE1);
                            layout2.addView(layoutPSE1);
                        }
                        if (i.equals("PSE2")){
                            layout2.removeView(layoutPSE2);
                            layoutPSE2.removeView(intitulePSE2);
                            layoutPSE2.removeView(textPSE2);

                            intitulePSE2.setText("PSE2 : ");
                            intitulePSE2.setTextSize(20);
                            layoutPSE2.addView(intitulePSE2);
                            textPSE2.setText(pse2);
                            textPSE2.setTextSize(20);
                            layoutPSE2.addView(textPSE2);
                            layout2.addView(layoutPSE2);
                        }
                        if (i.equals("LAT")){
                            layout2.removeView(layoutLAT);
                            layoutLAT.removeView(intituleLAT);
                            layoutLAT.removeView(textLAT);

                            intituleLAT.setText("LAT : ");
                            intituleLAT.setTextSize(20);
                            layoutLAT.addView(intituleLAT);
                            textLAT.setText(lat);
                            textLAT.setTextSize(20);
                            layoutLAT.addView(textLAT);
                            layout2.addView(layoutLAT);
                        }
                        if (i.equals("STAG")){
                            layout2.removeView(layoutSTAG);
                            layoutSTAG.removeView(intituleSTAG);
                            layoutSTAG.removeView(textSTAG);

                            intituleSTAG.setText("Stagiaire : ");
                            intituleSTAG.setTextSize(20);
                            layoutSTAG.addView(intituleSTAG);
                            textSTAG.setText(stag);
                            textSTAG.setTextSize(20);
                            layoutSTAG.addView(textSTAG);
                            layout2.addView(layoutSTAG);
                        }
                    }

                }catch ( java.lang.NullPointerException j){
                    intent6.putExtra("Numero", num);
                    intent6.putExtra("Nature", nat);
                    startActivity(intent6);
                }
            }
            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                intent5.putExtra("Numero", num);
                intent5.putExtra("Nature", nat);
                startActivity(intent5);
            }
        });

        valid.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                intent7.putExtra("Numero", num);
                intent7.putExtra("Nature", nat);
                startActivity(intent7);
            }
        });

    }
}