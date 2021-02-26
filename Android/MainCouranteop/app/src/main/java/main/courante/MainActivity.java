package main.courante;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Activité 1 : Page d'accueil
 * @author Florian Duchaine
 * @version 2.0
 */
public class MainActivity extends AppCompatActivity {

    // Référence à la base de données
    DatabaseReference myRef;
    FirebaseDatabase database;

    String TAG = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Récupération du boutton
        ImageView nouveau = findViewById(R.id.logoCB);

        // Création de l'Intent pour accéder à la page suivante
        final Intent intent = new Intent(this, MainActivity2.class);

        // Accès à la base de données Firebase
        database = FirebaseDatabase.getInstance();
        myRef =  database.getReference("0");

        //Suppression des donées existantes dans la bd
        //myRef.removeValue();

        // Action à réaliser au clic sur le boutton
        nouveau.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                // Passage à l'activité suivante
                startActivity(intent);
            }
        });

    }
}