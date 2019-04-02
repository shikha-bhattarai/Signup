package com.example.inclass09;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignUp extends AppCompatActivity implements View.OnClickListener{

    EditText firstName, lastName, email, password, password02;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up);
        firstName = findViewById(R.id.FirstNameEditText);
        lastName = findViewById(R.id.lastNameEditText);
        email = findViewById(R.id.emailEditText);
        password = findViewById(R.id.passEditText);
        password02 = findViewById(R.id.confirmPassEditText);
        FirebaseApp.initializeApp(this);
        mAuth = FirebaseAuth.getInstance();

        Button button = findViewById(R.id.signUpbtn);
        button.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.signUpbtn:
                registerUser();
                break;
            case R.id.cancelButton:
                startActivity(new Intent(this, MainActivity.class));
                break;

        }
    }

    public void registerUser(){
        String firstNameString = firstName.getText().toString();
        String lastNameString = lastName.getText().toString();
        String emailString = email.getText().toString();
        String passwordString = password.getText().toString();
        String passwordString02 = password02.getText().toString();

        if(firstNameString.equals("")){
            firstName.setError("Please enter a first name");
        }else if(lastNameString.equals("")){
            lastName.setError("Please enter a last name");
        }else if(emailString.equals("")){
            email.setError("Please enter an email");
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(emailString).matches()){
            email.setError("Please enter a valid email");
        }
        if(passwordString.length()<6){
            password.setError("Minimun password length is 6");
        }

        mAuth.createUserWithEmailAndPassword(emailString, passwordString).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Log.d("sign up", "successful");
                }
            }
        });
    }
}
