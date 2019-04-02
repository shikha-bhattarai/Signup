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
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

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
        //FirebaseApp.initializeApp(this);
        mAuth = FirebaseAuth.getInstance();

        Button button = findViewById(R.id.signUpbtn);
        button.setOnClickListener(this);
        findViewById(R.id.cancelButton).setOnClickListener(this);

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
        final String firstNameString = firstName.getText().toString();
        final String lastNameString = lastName.getText().toString();
        final String emailString = email.getText().toString();
        final String passwordString = password.getText().toString();
        String passwordString02 = password02.getText().toString();

        if(firstNameString.equals("")){
            firstName.setError("Please enter a first name");
            return;
        }

        if(lastNameString.equals("")){
            lastName.setError("Please enter a last name");
            return;
        }
        if(emailString.equals("")){
            email.setError("Please enter an email");
            return;
        }
        if(passwordString.length()<6){
            password.setError("Minimun password length is 6");
            return;
        }
        if(!passwordString.equals(passwordString02)){
            Toast.makeText(this, "Password does not match", Toast.LENGTH_SHORT).show();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(emailString).matches()){
            email.setError("Please enter a valid email");
            return;
        }
        mAuth.createUserWithEmailAndPassword(emailString, passwordString).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    //Toast.makeText(SignUp.this, "Sign Up Successful", Toast.LENGTH_SHORT).show();
                    User user = new User(firstNameString, lastNameString, emailString, passwordString);
                    FirebaseDatabase.getInstance().getReference("Users")
                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(user)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(SignUp.this, "Registration Complete", Toast.LENGTH_LONG).show();
                            }else{
                                Toast.makeText(SignUp.this, "Registration Incomplete", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                    startActivity(new Intent(SignUp.this, ContactList.class));
                }else{
                    Toast.makeText(SignUp.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
