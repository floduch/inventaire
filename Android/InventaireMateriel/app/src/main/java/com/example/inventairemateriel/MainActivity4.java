package com.example.inventairemateriel;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.inventairemateriel.R;

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