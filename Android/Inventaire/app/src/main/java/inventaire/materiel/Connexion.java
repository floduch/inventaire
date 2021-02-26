package inventaire.materiel;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Connexion extends AppCompatActivity {

    private static final String TAG = "";

    private FirebaseDatabase database;
    private DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connexion);

        final Intent intent = new Intent(Connexion.this, Administration.class);

        database = FirebaseDatabase.getInstance();
        myRef =  database.getReference("2").child("Connexion");

        EditText id = findViewById(R.id.editTextIdentifiant);
        EditText pass = findViewById(R.id.editTextPassword);

        Button valide = findViewById(R.id.buttonVal);
        valide.setBackground(getDrawable(R.drawable.rounded_corner));

        ArrayList<String > identifiants = new ArrayList<>();
        ArrayList<String > passwords = new ArrayList<>();

        identifiants.add("admin");
        passwords.add("admin");

        for (int i = 0; i < identifiants.size(); i++){
            myRef.child(identifiants.get(i)).child("Pass").setValue(code(passwords.get(i)));
            myRef.child("Connected").setValue("false");
        }

        valide.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                String textId = id.getText().toString();
                String textPass = pass.getText().toString();

                String test = code(textPass);

                myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        String value = (String) dataSnapshot.child(textId).child("Pass").getValue();

                        try{
                            if (code(textPass).equals(value)){

                                Toast myToast = Toast.makeText(Connexion.this, "Bienvenue " + textId, Toast.LENGTH_SHORT);
                                myToast.show();

                                myRef.child("Connected").setValue("true");
                                Boolean connect = true;
                                intent.putExtra("connect", connect);
                                startActivity(intent);

                            }else {
                                Toast myToast = Toast.makeText(Connexion.this, "Mot de passe ou identifiant incorrect", Toast.LENGTH_SHORT);
                                myToast.show();
                            }


                        }catch ( java.lang.NullPointerException j) {
                            Toast myToast = Toast.makeText(Connexion.this, "Identifiant incorrect", Toast.LENGTH_SHORT);
                            myToast.show();
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
    public String code(String mot){
        String coder = "";
        ArrayList<Character> lettresC = new ArrayList<>();
        ArrayList<Character> lettres = new ArrayList<>();

        lettresC.add('@'); lettresC.add('8'); lettresC.add('ç'); lettresC.add('}'); lettresC.add('3'); lettresC.add('£'); lettresC.add('6'); lettresC.add('#'); lettresC.add('!'); lettresC.add(';');
        lettresC.add('&'); lettresC.add('['); lettresC.add(']'); lettresC.add('%'); lettresC.add('0'); lettresC.add('9'); lettresC.add('¤'); lettresC.add('§'); lettresC.add('$'); lettresC.add('=');
        lettresC.add('µ'); lettresC.add('/'); lettresC.add('~'); lettresC.add('>'); lettresC.add('|'); lettresC.add('7');

        lettres.add('a'); lettres.add('b'); lettres.add('c'); lettres.add('d'); lettres.add('e'); lettres.add('f'); lettres.add('g'); lettres.add('h'); lettres.add('i'); lettres.add('j');
        lettres.add('k'); lettres.add('l'); lettres.add('m'); lettres.add('n'); lettres.add('o'); lettres.add('p'); lettres.add('q'); lettres.add('r'); lettres.add('s'); lettres.add('t');
        lettres.add('u'); lettres.add('v'); lettres.add('w'); lettres.add('x'); lettres.add('y'); lettres.add('z');

        for (int i = 0; i < mot.length(); i++){
            for (int j = 0; j < lettres.size(); j++){
                if (mot.charAt(i) == lettres.get(j)){
                    coder+=lettresC.get(j);
                }
            }
        }

        return coder;
    }
}