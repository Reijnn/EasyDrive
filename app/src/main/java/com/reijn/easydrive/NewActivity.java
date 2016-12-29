package com.reijn.easydrive;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class NewActivity extends AppCompatActivity {

    private EditText tvTransDate, tvTransCompany, tvTransPlate, tvTransFrom, tvTransToo;
    private Button bSave;

    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mTransportReference;

    private boolean action = true;
    String id;
    Transport transport;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new);

        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mTransportReference = mFirebaseDatabase.getReference().child("transports")
                .child(MainActivity.firebaseUser.getUid());

        tvTransDate =(EditText) findViewById(R.id.tvTransDate);
        tvTransCompany =(EditText) findViewById(R.id.tvTransCompany);
        tvTransPlate =(EditText) findViewById(R.id.tvTransPlate);
        tvTransFrom =(EditText) findViewById(R.id.tvTransFrom);
        tvTransToo =(EditText) findViewById(R.id.tvTransToo);
        bSave = (Button) findViewById(R.id.bSave);


        id = getIntent().getStringExtra("id");
        if (id != null){
            action = false;
            mTransportReference.child(id).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    transport = dataSnapshot.getValue(Transport.class);
                    tvTransDate.setText(transport.getTransDate());
                    tvTransCompany.setText(transport.getTransCompany());
                    tvTransPlate.setText(transport.getTransPlate());
                    tvTransFrom.setText(transport.getTransFrom());
                    tvTransToo.setText(transport.getTransToo());
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        } else {
            transport = new Transport();
        }



        bSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String key;
                if (action){
                    key = mTransportReference.push().getKey();
                } else {
                    key = id;
                }
                transport.setId(key);
                transport.setTransDate(tvTransDate.getText().toString());
                transport.setTransCompany(tvTransCompany.getText().toString());
                transport.setTransPlate(tvTransPlate.getText().toString());
                transport.setTransFrom(tvTransFrom.getText().toString());
                transport.setTransToo(tvTransToo.getText().toString());
                mTransportReference.child(key).setValue(transport);
                finish();
            }
        });


    }
}
