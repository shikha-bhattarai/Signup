package com.example.inclass09;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;

public class ContactList extends AppCompatActivity {

    ArrayList<Contact>arrayList;
    ContactAdapter contactAdapter;
    public static final int REQ_CODE = 100;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_list);
        arrayList = new ArrayList<>();
        contactAdapter = new ContactAdapter(this, R.layout.fragment_contact_list, arrayList);

        findViewById(R.id.buttonCreateContact).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ContactList.this, CreateContactActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQ_CODE){
            if(resultCode == RESULT_OK){

            }else if(resultCode == RESULT_CANCELED){
                Log.d("demo", "no value received");
            }
        }
    }

}
