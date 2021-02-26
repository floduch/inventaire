package main.courante;

import android.Manifest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.content.Intent;
import android.widget.Spinner;
import android.widget.Toast;


import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Activité 2 : Page des informations sur le DPS
 * @author Florian Duchaine
 * @version 2.0
 */

public class MainActivity2 extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private static final String TAG = "";
    DatePickerDialog picker;
    EditText date;
    private final int REQUEST_LOCATION_PERMISSION = 1;
    private GpsTracker gpsTracker;

    Enregistrements Manifestation = new Enregistrements();

//    Référence à la base de données
    DatabaseReference myRef;
    FirebaseDatabase database;
    private Button loca;
    private  EditText AdrManifest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

//        String loc = getCompleteAddressString();


        if (ContextCompat.checkSelfPermission(MainActivity2.this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity2.this, Manifest.permission.ACCESS_FINE_LOCATION)){
                ActivityCompat.requestPermissions(MainActivity2.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
//                loc = String.valueOf(location);
//                System.out.println(loc);
            }else{
                ActivityCompat.requestPermissions(MainActivity2.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
//                loc = String.valueOf(location);
//                System.out.println(loc);
            }
        }
        double latitude, longitude;


        // Récupération des éléments visuels
        final EditText NatManifest = findViewById(R.id.editNature);
        final EditText LieuManifest = findViewById(R.id.editLieu);
        AdrManifest = findViewById(R.id.editAdresse);
        final EditText NumManifest = findViewById(R.id.editNumero);
        final Spinner spinnerType = findViewById(R.id.spinner);

        Button submit = findViewById(R.id.buttonValider);
        loca = findViewById(R.id.buttonLoc);

        date=(EditText) findViewById(R.id.editDate);

        // Transformation de la date en date dd/MM/yyy
        final Date[] currentDate = {new Date()};
        final EditText date = findViewById(R.id.editDate);
        final Button actuDate = findViewById(R.id.buttonDate);
        final DateFormat[] dateFormat = {new SimpleDateFormat("dd/MM/yyyy")};
        final String[] formdate = {dateFormat[0].format(currentDate[0])};

        // Création de l'Intent pour accéder à la page suivante
        final Intent intent = new Intent(this, MainActivity3.class);

        // Elément sélectionné sur le dérouleur type de poste
        spinnerType.setOnItemSelectedListener((AdapterView.OnItemSelectedListener) MainActivity2.this);

        // Ajout des types dans le dérouleur
        ArrayAdapter<CharSequence> selectType= ArrayAdapter.createFromResource(this, R.array.type, android.R.layout.simple_spinner_item);
        spinnerType.setPrompt("Type du DPS");
        selectType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerType.setAdapter(selectType);

        date.setInputType(InputType.TYPE_NULL);
        date.setText(formdate[0]);

        // Affichage de la date sous forme de calendrier
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog
                picker = new DatePickerDialog(MainActivity2.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        date.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                    }
                }, year, month, day);
                picker.show();
            }
        });

//        String finalLoc = loc;
        loca.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getLocation(AdrManifest);
//                AdrManifest.setText(finalLoc);
//                System.out.println("loc "+finalLoc);
            }
        });

        // Actualisation de la date actuelle
        actuDate.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                currentDate[0] = new Date();
                dateFormat[0] = new SimpleDateFormat("dd/MM/yyyy");
                formdate[0] = dateFormat[0].format(currentDate[0]);
                date.setText(formdate[0]);
            }
        });

        // Actions réalisées au clic sur le boutton
        submit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

//                Récupération des textes des différents champs
                String nature = NatManifest.getText().toString();
                String lieu = LieuManifest.getText().toString();
                String adresse = AdrManifest.getText().toString();
                String  numero = NumManifest.getText().toString();
                String dateb = date.getText().toString();
                String type = spinnerType.getSelectedItem().toString();

                Manifestation.setManifestation(nature, lieu, adresse, dateb, type, numero);

                String id = String.valueOf(Manifestation.getId());

                // Ecriture dans la base de données
                database = FirebaseDatabase.getInstance();
                myRef = database.getReference("ID");
                myRef.setValue(id);
                myRef = database.getReference(id).child(numero);

                myRef.child(nature);
                myRef.child(nature).child("Infos").child("Lieu").setValue(lieu);
                myRef.child(nature).child("Infos").child("Adresse").setValue(adresse);
                myRef.child(nature).child("Infos").child("Date").setValue(dateb);
                myRef.child(nature).child("Infos").child("Type").setValue(type);


                intent.putExtra("Nature", nature);
                intent.putExtra("Numero", numero);

                startActivity(intent);
            }
        });
    }
    // Retourne l'élément sélectionné dans le dérouleur
    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
        // An item was selected. You can retrieve the selected item using
        // parent.getItemAtPosition(pos)
        String item = parent.getItemAtPosition(pos).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults){
        String loc = "";
        switch (requestCode){
            case 1: {
                if (grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    if (ContextCompat.checkSelfPermission(MainActivity2.this,
                            Manifest.permission.ACCESS_FINE_LOCATION)==PackageManager.PERMISSION_GRANTED){
                        Toast.makeText(this, "Permission Granted : ", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    
                    Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
                }
                return;
            }
        }
    }

    private String getCompleteAddressString(double LATITUDE, double LONGITUDE) {
        String strAdd = "";
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(LATITUDE, LONGITUDE, 1);
            if (addresses != null) {
                Address returnedAddress = addresses.get(0);
                StringBuilder strReturnedAddress = new StringBuilder("");

                for (int i = 0; i <= returnedAddress.getMaxAddressLineIndex(); i++) {
                    strReturnedAddress.append(returnedAddress.getAddressLine(i)).append("\n");
                }
                strAdd = strReturnedAddress.toString();
                Log.w("adresse", strReturnedAddress.toString());
            } else {
                Log.w("adresse", "No Address returned!");
                Toast myToast = Toast.makeText(MainActivity2.this, "Aucune adresse", Toast.LENGTH_SHORT);
                myToast.show();
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.w("adresse", "Canont get Address!");
            Toast myToast = Toast.makeText(MainActivity2.this, "Adresse non accessible", Toast.LENGTH_SHORT);
            myToast.show();
        }
        return strAdd;
    }

    public void getLocation(View view){
        gpsTracker = new GpsTracker(MainActivity2.this);
        if(gpsTracker.canGetLocation()){
            double latitude = gpsTracker.getLatitude();
            double longitude = gpsTracker.getLongitude();
            String loc = getCompleteAddressString(latitude, longitude);
            AdrManifest.setText(String.valueOf(loc));
        }else{
            gpsTracker.showSettingsAlert();
        }
    }

}