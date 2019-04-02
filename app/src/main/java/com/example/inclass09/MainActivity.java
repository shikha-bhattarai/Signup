package com.example.inclass09;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    EditText emailTextview;
    EditText passTextview;
    Button login;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
      emailTextview = (EditText) findViewById(R.id.emailEditText);
       passTextview = (EditText) findViewById(R.id.passEditText);

        findViewById(R.id.signupbtnMain).setOnClickListener(this);
        login = findViewById(R.id.loginbtn);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = emailTextview.getText().toString().trim();
                final String password = passTextview.getText().toString().trim();


                auth = FirebaseAuth.getInstance();
                //authenticate user
                auth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (!task.isSuccessful()) {

                                    if (password.length() < 6) {
                                        passTextview.setError("Minimun password length is 6");
                                    } else {
                                        Toast.makeText(MainActivity.this, "Auth failed", Toast.LENGTH_LONG).show();
                                    }
                                } else {
                                    Intent intent = new Intent(MainActivity.this, ContactList.class);
                                    startActivity(intent);
                                    finish();

                                }
                            }
                        });
            }
        });
    }


    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.signupbtnMain:

                startActivity(new Intent(this, SignUp.class));
                break;

        }
    }
}
