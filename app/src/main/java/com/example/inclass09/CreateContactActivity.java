package com.example.inclass09;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CreateContactActivity extends AppCompatActivity {

    EditText fullName, email, phone;
    Button submit;
    ImageView imageView;
    public static final int REQUEST_CAMERA = 2;
    DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
    DatabaseReference mContactRef;
    Contact contact;
     static String CONTACT_KEY = "CONTACT";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_contact);

        fullName = findViewById(R.id.nameEditText);
        email = findViewById(R.id.emailEditText);
        phone = findViewById(R.id.phoneEditText);
        imageView = findViewById(R.id.imageView2);
        submit = findViewById(R.id.submitbtn);
        mContactRef = mRootRef.child("contact").child(FirebaseAuth.getInstance().getCurrentUser().getUid());

        //Camera
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadImage();
            }
        });



        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String fullNameString = fullName.getText().toString();
                String emailString = email.getText().toString();
                String phoneString = phone.getText().toString();
               if(fullNameString.equals("")){
                    fullName.setError("Please enter a name");
                    return;
                }

                if(emailString.equals("") || !Patterns.EMAIL_ADDRESS.matcher(emailString).matches()){
                    email.setError("Please enter a valid email");
                    return;
                }

                if(phoneString.length()!=10){
                    phone.setError("Please enter a phone without any symbol");
                    return;
                }

                    Contact contact = new Contact(fullNameString, emailString, phoneString);
                    String key = mContactRef.push().getKey();
                    mContactRef.child(key).setValue(contact);

                Intent intent = new Intent();
                intent.putExtra(CONTACT_KEY, contact);
                setResult(RESULT_OK, intent);
                finish();
            }
        });

    }

    private void uploadImage() {
        Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent,REQUEST_CAMERA);
    }
}
