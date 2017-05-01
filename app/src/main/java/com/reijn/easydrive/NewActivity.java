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

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
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
    @BindView(R.id.tvDate)
    @Pattern(message = "Voer een geldige datum in!", regex = "^(?:(?:31(\\/|-|\\.)(?:0?[13578]|1[02]))\\1|(?:(?:29|30)(\\/|-|\\.)(?:0?[1,3-9]|1[0-2])\\2))(?:(?:1[6-9]|[2-9]\\d)?\\d{2})$|^(?:29(\\/|-|\\.)0?2\\3(?:(?:(?:1[6-9]|[2-9]\\d)?(?:0[48]|[2468][048]|[13579][26])|(?:(?:16|[2468][048]|[3579][26])00))))$|^(?:0?[1-9]|1\\d|2[0-8])(\\/|-|\\.)(?:(?:0?[1-9])|(?:1[0-2]))\\4(?:(?:1[6-9]|[2-9]\\d)?\\d{2})$")
    EditText tvDate;
    @BindView(R.id.tvCompany)
    @Length(min = 2, message = "Dit veld is verplicht")
    EditText tvCompany;
    @BindView(R.id.tvTransportNumber)
    EditText tvTransportNumber;
    @BindView(R.id.tvPlate)
    @Pattern(message = "Voer een geldig kenteken in!", regex = "[A-Za-z0-9]{1,3}-[A-Za-z0-9]{1,3}-[A-Za-z0-9]{1,3}")
    EditText tvPlate;
    @BindView(R.id.tvOrigin)
    @Length(min = 2, message = "Dit veld is verplicht")
    EditText tvOrigin;
    @BindView(R.id.tvDestination)
    @Length(min = 2, message = "Dit veld is verplicht")
    EditText tvDestination;
    DatePickerDialog.OnDateSetListener listener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            tvDate.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
        }
    };
    private Calendar calendar = Calendar.getInstance();
    private DatabaseReference mTransportReference;
    private String id;
    private Transport transport;
    private Validator validator;

    @OnClick(R.id.tvDate)
    public void test(View view) {
        new DatePickerDialog(this, listener, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new);
        ButterKnife.bind(this);

        validator = new Validator(this);
        validator.setValidationListener(this);

        FirebaseDatabase mFirebaseDatabase = FirebaseDatabase.getInstance();
        mTransportReference = mFirebaseDatabase.getReference().child("transports")
                .child(MainActivity.firebaseUser.getUid());

        if (getIntent().getSerializableExtra("transport") != null){
            transport = (Transport) getIntent().getSerializableExtra("transport");
            id = transport.getId();
            tvDate.setText(transport.getDate());
            tvCompany.setText(transport.getCompany());
            tvTransportNumber.setText(transport.getTransportNumber());
            tvPlate.setText(transport.getPlate());
            tvOrigin.setText(transport.getOrigin());
            tvDestination.setText(transport.getDestination());
        } else {
            transport = new Transport();
        }
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
        transport.setDate(tvDate.getText().toString());
        transport.setCompany(tvCompany.getText().toString());
        transport.setTransportNumber(tvTransportNumber.getText().toString());
        transport.setPlate(tvPlate.getText().toString());
        transport.setOrigin(tvOrigin.getText().toString());
        transport.setDestination(tvDestination.getText().toString());

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
