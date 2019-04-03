package com.example.inclass09;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class ContactList extends AppCompatActivity {

    ArrayList<Contact> arrayList;
    ContactAdapter contactAdapter;
    public static final int REQ_CODE = 100;
    ListView listView;
    public static final String VALUE_KEY = "list";
    Button logout;
    DatabaseReference mDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_list);

        arrayList = new ArrayList<>();
        listView = findViewById(R.id.listView);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        mDatabase = database.getReference("contact").child(FirebaseAuth.getInstance().getCurrentUser().getUid());

        contactAdapter = new ContactAdapter(this, R.layout.fragment_contact_list, arrayList);
        listView.setAdapter(contactAdapter);

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                arrayList.clear();
                Contact a;
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    a = data.getValue(Contact.class);
                    arrayList.add(a);
                }
                //this checks if adapter is null
                if (contactAdapter != null) {
                    contactAdapter.notifyDataSetChanged();
                }

            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                mDatabase.child(arrayList.get(position).getKey()).removeValue();
                contactAdapter.notifyDataSetChanged();
                return true;
            }
        });


        findViewById(R.id.buttonCreateContact).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ContactList.this, CreateContactActivity.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.logoutbtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(ContactList.this, MainActivity.class);
                startActivity(intent);

            }
        });

    }

}
