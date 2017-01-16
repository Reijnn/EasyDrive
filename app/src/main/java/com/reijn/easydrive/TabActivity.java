package com.reijn.easydrive;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.reijn.easydrive.Fragments.ExtraFragment;
import com.reijn.easydrive.Fragments.SendFragment;
import com.reijn.easydrive.Fragments.TimeFragment;

public class TabActivity extends AppCompatActivity {
    DatabaseReference mTransportReference;
    Transport transport;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        SectionsPagerAdapter mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        ViewPager mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setOffscreenPageLimit(2);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        transport = (Transport) getIntent().getSerializableExtra("transport");
        FirebaseDatabase mFirebaseDatabase = FirebaseDatabase.getInstance();
        mTransportReference = mFirebaseDatabase.getReference().child("transports")
                .child(MainActivity.firebaseUser.getUid());
    }

    private void save() {
        EditText starttime = (EditText) findViewById(R.id.startTime);
        EditText startaddress = (EditText) findViewById(R.id.startAddress);
        EditText arrivalpickup = (EditText) findViewById(R.id.arrivalOrigin);
        EditText departurepickup = (EditText) findViewById(R.id.departureOrigin);
        EditText arrivaldestination = (EditText) findViewById(R.id.arrivalDestination);
        EditText keys = (EditText) findViewById(R.id.keysTime);
        EditText keystoo = (EditText) findViewById(R.id.keysToo);
        Switch sSecondTransport = (Switch) findViewById(R.id.sSecondTransport);
        EditText tvFinalStation =(EditText) findViewById(R.id.tvFinalStation);
        EditText tvFinalAdress =(EditText) findViewById(R.id.tvFinalAdress);
        EditText tvFinalTime=(EditText) findViewById(R.id.tvFinalTime);

        transport.setStartTime(starttime.getText().toString());
        transport.setStartAddress(startaddress.getText().toString());
        transport.setArrivalOrigin(arrivalpickup.getText().toString());
        transport.setDepartureOrigin(departurepickup.getText().toString());
        transport.setArrivalDestination(arrivaldestination.getText().toString());
        transport.setKeysTime(keys.getText().toString());
        transport.setKeysToo(keystoo.getText().toString());
        transport.setFinalAddress(tvFinalAdress.getText().toString());
        transport.setFinalTime(tvFinalTime.getText().toString());
        transport.setFinalStation(tvFinalStation.getText().toString());
        transport.setSecondTransport(String.valueOf(sSecondTransport.isChecked()));

        EditText tvfuel = (EditText) findViewById(R.id.tvFuelCosts);
        EditText tvcarwash = (EditText) findViewById(R.id.tvCarwashCosts);
        EditText tvvacuum = (EditText) findViewById(R.id.tvCleaningCosts);
        EditText tvov = (EditText) findViewById(R.id.tvOvCosts);
        EditText tvother = (EditText) findViewById(R.id.tvOtherCosts);
        EditText tvnotes = (EditText) findViewById(R.id.tvNotes);
        EditText tvwhere = (EditText) findViewById(R.id.tvDelayLocation);
        EditText tvtime = (EditText) findViewById(R.id.tvDelayTime);
        Switch sDelay = (Switch) findViewById(R.id.swVertraging);
        Spinner spinner = (Spinner) findViewById(R.id.spinner);

        transport.setFuelCosts(tvfuel.getText().toString());
        transport.setCarwashCosts(tvcarwash.getText().toString());
        transport.setCleaningCosts(tvvacuum.getText().toString());
        transport.setOvCosts(tvov.getText().toString());
        transport.setOtherCosts(tvother.getText().toString());
        transport.setNotes(tvnotes.getText().toString());
        transport.setDelayLocation(tvwhere.getText().toString());
        transport.setDelayTime(tvtime.getText().toString());
        transport.setDelay(String.valueOf(sDelay.isChecked()));
        transport.setCardUsed(String.valueOf(spinner.getSelectedItemPosition()));

        mTransportReference.child(transport.getId()).setValue(transport);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_new, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_submit:
                save();
                finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPause() {
        save();
        super.onPause();
    }

    @Override
    public void onBackPressed() {
        save();
        super.onBackPressed();
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return TimeFragment.newInstance(transport);
                case 1:
                    return ExtraFragment.newInstance(transport);
                case 2:
                    return SendFragment.newInstance(transport);
            }
            return TimeFragment.newInstance(transport);
        }

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "1. Tijden";
                case 1:
                    return "2. Extra";
                case 2:
                    return "3. Formulier";
            }
            return null;
        }
    }
}
