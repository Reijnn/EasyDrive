package com.reijn.easydrive;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.database.FirebaseListAdapter;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MAIN";
    private static final int RC_SIGN_IN = 1;
    private static final int RC_NEW_RIT = 2;
    private FirebaseAnalytics mFirebaseAnalytics;
    private AdView mAdView;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mTransportReference;

    private FirebaseListAdapter adapter;
    private ListView listView;

    public static FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        listView = (ListView) findViewById(R.id.listView);

        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                firebaseUser = firebaseAuth.getCurrentUser();
                if (firebaseUser != null) {
                    setup();
                } else {
                    startActivityForResult(
                            AuthUI.getInstance()
                                    .createSignInIntentBuilder()
                                    .setIsSmartLockEnabled(!BuildConfig.DEBUG)
                                    .setProviders(Arrays.asList(new AuthUI.IdpConfig.Builder(AuthUI.GOOGLE_PROVIDER).build()))
                                    .build(),
                            RC_SIGN_IN);
                }
            }
        };

//        mAdView = (AdView) findViewById(R.id.adView);
//        AdRequest adRequest = new AdRequest.Builder().build();
//        mAdView.loadAd(adRequest);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(MainActivity.this, NewActivity.class), RC_NEW_RIT);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        switch (item.getItemId()){
            case R.id.action_settings:
                return true;
            case R.id.action_logout:
                AuthUI.getInstance().signOut(this);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN && resultCode == RESULT_CANCELED){
            finish();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        adapter.cleanup();
    }

    private  void setup(){
        mTransportReference = mFirebaseDatabase.getReference().child("transports")
                .child(MainActivity.firebaseUser.getUid());
        adapter = new FirebaseListAdapter<Transport>(this, Transport.class,
                android.R.layout.two_line_list_item, mTransportReference)
        {
            @Override
            protected void populateView(View v, Transport model, int position) {
                ((TextView)v.findViewById(android.R.id.text1)).setText(model.getTransDate());
                ((TextView)v.findViewById(android.R.id.text2)).setText(model.getTransPlate());
            }
        };
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            public boolean onItemLongClick(AdapterView parent, final View view, final int position, final long id) {
                new AlertDialog.Builder(MainActivity.this)
                        .setMessage("Wat wilt u doen?")
                        .setPositiveButton("Verwijderen", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                Transport t = (Transport) adapter.getItem(position);
                                mTransportReference.child(t.getId()).removeValue();
                            }
                        })
                        .setNegativeButton("Aanpassen", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(MainActivity.this, NewActivity.class);
                                Transport t = (Transport) adapter.getItem(position);
                                intent.putExtra("id", t.getId());
                                startActivity(intent);
                            }
                        })
                        .show();
                return true;
            }
        });
    }
}
