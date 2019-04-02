package com.example.inclass09;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class ContactList extends AppCompatActivity {

    ArrayList<Contact>arrayList;
    ContactAdapter contactAdapter;
    public static final int REQ_CODE = 100;
    ListView listView;
    public static final String VALUE_KEY = "list";
    Button logout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_list);

        arrayList = new ArrayList<>();
        listView = findViewById(R.id.listView);
        arrayList.add(new Contact("John", "em", "828565896"));

        contactAdapter = new ContactAdapter(this, R.layout.fragment_contact_list, arrayList);
        listView.setAdapter(contactAdapter);
        contactAdapter.notifyDataSetChanged();

        findViewById(R.id.buttonCreateContact).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ContactList.this, CreateContactActivity.class);
                startActivityForResult(intent, REQ_CODE);
            }
        });
        findViewById(R.id.logoutbtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(ContactList.this, MainActivity.class);
                startActivityForResult(intent, REQ_CODE);
            }
        });



    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQ_CODE){
            Log.d("demo", "dddd");
            if(resultCode == RESULT_OK){
                Log.d("demo", "wwwwwwwwwwwwwww");
                    Contact contact = (Contact) getIntent().getExtras().getSerializable(CreateContactActivity.CONTACT_KEY);

                    arrayList.add(contact);
                    contactAdapter.notifyDataSetChanged();
                    Log.d("demo", "sdfadfasfffffffffffffffffffffffffffffffffffffffffeceived");

            }else if(resultCode == RESULT_CANCELED){
                Log.d("demo", "no value received");
            }
        }
    }

}
