package com.example.inclass09;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class SignUp extends AppCompatActivity implements View.OnClickListener{

    EditText firstName, lastName, email, password, password02;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up);
        firstName = findViewById(R.id.nameEditText);
        lastName = findViewById(R.id.lastNameEditText);
        email = findViewById(R.id.emailEditText);
        password = findViewById(R.id.passEditText);
        password02 = findViewById(R.id.confirmPassEditText);
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
    }
}
