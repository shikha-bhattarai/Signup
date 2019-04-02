package com.example.inclass09;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    EditText emailTextview;
    EditText passTextview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
      emailTextview = (EditText) findViewById(R.id.emailEditText);
       passTextview = (EditText) findViewById(R.id.passEditText);

        findViewById(R.id.signupbtn).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.signupbtn:
                startActivity(new Intent(this, SignUp.class));
                break;

        }
    }
}
