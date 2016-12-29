package com.reijn.easydrive;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Length;
import com.mobsandgeeks.saripaar.annotation.Pattern;

import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class NewActivity extends AppCompatActivity implements Validator.ValidationListener {
    Calendar calendar = Calendar.getInstance();

    @BindView(R.id.tvDate)
    @Pattern(message = "Voer een geldige datum in!", regex = "^(?:(?:31(\\/|-|\\.)(?:0?[13578]|1[02]))\\1|(?:(?:29|30)(\\/|-|\\.)(?:0?[1,3-9]|1[0-2])\\2))(?:(?:1[6-9]|[2-9]\\d)?\\d{2})$|^(?:29(\\/|-|\\.)0?2\\3(?:(?:(?:1[6-9]|[2-9]\\d)?(?:0[48]|[2468][048]|[13579][26])|(?:(?:16|[2468][048]|[3579][26])00))))$|^(?:0?[1-9]|1\\d|2[0-8])(\\/|-|\\.)(?:(?:0?[1-9])|(?:1[0-2]))\\4(?:(?:1[6-9]|[2-9]\\d)?\\d{2})$")
    EditText tvTransDate;
    @BindView(R.id.tvPlate)
    @Pattern(message = "Voer een geldig kenteken in!", regex = "[A-Za-z0-9]{1,3}-[A-Za-z0-9]{1,3}-[A-Za-z0-9]{1,3}")
    EditText tvTransPlate;
    @BindView(R.id.tvNr)
    EditText tvTransNr;
    @BindView(R.id.tvNaar)
    @Length(min = 2, message = "Dit veld is verplicht")
    EditText tvTransToo;
    @BindView(R.id.tvVan)
    @Length(min = 2, message = "Dit veld is verplicht")
    EditText tvTransFrom;
    @BindView(R.id.tvLease)
    @Length(min = 2, message = "Dit veld is verplicht")
    EditText tvTransCompany;

    @OnClick(R.id.tvDate)
    public void test(View view) {
        new DatePickerDialog(this, listener, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();

    }

    DatePickerDialog.OnDateSetListener listener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            tvTransDate.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
        }
    };

    private DatabaseReference mTransportReference;

    String id;
    Transport transport;
    Validator validator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new);
        ButterKnife.bind(this);

        AdView mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        validator = new Validator(this);
        validator.setValidationListener(this);

        FirebaseDatabase mFirebaseDatabase = FirebaseDatabase.getInstance();
        mTransportReference = mFirebaseDatabase.getReference().child("transports")
                .child(MainActivity.firebaseUser.getUid());

        id = getIntent().getStringExtra("id");
        if (id != null) {
            setTitle("Rit aanpassen");
            mTransportReference.child(id).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    transport = dataSnapshot.getValue(Transport.class);
                    tvTransDate.setText(transport.getTransDate());
                    tvTransCompany.setText(transport.getTransCompany());
                    tvTransNr.setText(transport.getTransNr());
                    tvTransPlate.setText(transport.getTransPlate());
                    tvTransFrom.setText(transport.getTransFrom());
                    tvTransToo.setText(transport.getTransToo());
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Toast.makeText(NewActivity.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            transport = new Transport();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_new, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case R.id.action_submit:
                validator.validate();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onValidationSucceeded() {
        String key;
        if (id == null) {
            key = mTransportReference.push().getKey();
        } else {
            key = id;
        }
        transport.setId(key);
        transport.setTransDate(tvTransDate.getText().toString());
        transport.setTransCompany(tvTransCompany.getText().toString());
        transport.setTransNr(tvTransNr.getText().toString());
        transport.setTransPlate(tvTransPlate.getText().toString());
        transport.setTransFrom(tvTransFrom.getText().toString());
        transport.setTransToo(tvTransToo.getText().toString());
        mTransportReference.child(key).setValue(transport);
        finish();
    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        for (ValidationError error : errors) {
            TextInputLayout view = (TextInputLayout) error.getView().getParent().getParent();
            String message = error.getCollatedErrorMessage(this);
            view.setError(message);
        }
    }
}
