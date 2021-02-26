package inventaire.materiel;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "";

    private FirebaseDatabase database;
    private DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Button nouveau = findViewById(R.id.buttonNew);
        final Button admin = findViewById(R.id.buttonAdmin);
        nouveau.setBackground(getDrawable(R.drawable.rounded_corner));
        admin.setBackground(getDrawable(R.drawable.rounded_corner));

        final Intent intent = new Intent(this, MainActivity2.class);
        final Intent intent1 = new Intent(MainActivity.this, Administration.class);

        database = FirebaseDatabase.getInstance();
        myRef =  database.getReference("1").child("Personne"); //.child(pers).child("Matériel")

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                HashMap<String, HashMap<String, String>> value = (HashMap) dataSnapshot.getValue();
                try{
                    System.out.println(value);
                    if (value.equals(null)) {
                        System.out.println("null");
                    }

                }catch ( java.lang.NullPointerException j){

                    admin.setEnabled(false);
                }

            }
            @Override
            public void onCancelled(DatabaseError error) {
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

        nouveau.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(intent);
            }
        });
        admin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(intent1);
            }
        });
    }
}