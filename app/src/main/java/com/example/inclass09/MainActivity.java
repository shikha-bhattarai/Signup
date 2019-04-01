package com.example.inclass09;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    EditText emailTextview;
    EditText passTextview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        emailTextview = (EditText) findViewById(R.id.emailTextview);
        passTextview = (EditText) findViewById(R.id.passTextview);
    }
}
