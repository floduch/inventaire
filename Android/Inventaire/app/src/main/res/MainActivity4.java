package com.example.inventairemateriel;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.inventairemateriel.R;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity4 extends AppCompatActivity {

    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);

    }

    public void closeApplication(View view) {
        finish();
        moveTaskToBack(true);
    }
}