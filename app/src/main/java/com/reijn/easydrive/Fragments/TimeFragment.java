package com.reijn.easydrive.Fragments;

import android.app.Activity;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TimePicker;

import com.reijn.easydrive.R;
import com.reijn.easydrive.Transport;

import java.util.Calendar;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class TimeFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    Transport transport;
    @BindView(R.id.sSecondTransport)
    Switch sSecondTransport;
    @BindView(R.id.tvFinalStation)
    EditText tvFinalStation;
    @BindView(R.id.tvFinalAdress)
    EditText tvFinalAdress;
    @BindView(R.id.tvFinalTime)
    EditText tvFinalTime;
    private Unbinder unbinder;

    @BindView(R.id.startTime)
    TextInputEditText startTime;
    @BindView(R.id.startAddress)
    TextInputEditText startAddress;
    @BindView(R.id.arrivalOrigin)
    TextInputEditText arrivalOrigin;
    @BindView(R.id.departureOrigin)
    TextInputEditText departureOrigin;
    @BindView(R.id.arrivalDestination)
    TextInputEditText arrivalDestination;
    @BindView(R.id.keysTime)
    TextInputEditText keysTime;
    @BindView(R.id.keysToo)
    TextInputEditText keysToo;

    @OnClick({R.id.startTime, R.id.arrivalOrigin, R.id.departureOrigin, R.id.arrivalDestination, R.id.keysTime, R.id.tvFinalTime})
    public void submit(View v) {
        final EditText tv = (EditText) v;
        InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);

        Calendar c = Calendar.getInstance();
        TimePickerDialog mTimePicker = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                tv.setText(String.format("%02d", selectedHour) + ":" + String.format("%02d", selectedMinute));
            }
        }, c.get(Calendar.HOUR_OF_DAY), c.get(Calendar.MINUTE), true);
        mTimePicker.setTitle("Selecteer tijd");
        mTimePicker.show();
    }

    public TimeFragment() {
    }

    public static TimeFragment newInstance(Transport transport) {
        TimeFragment fragment = new TimeFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM1, transport);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            transport = (Transport) getArguments().getSerializable(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_times, container, false);
        unbinder = ButterKnife.bind(this, view);

        assert transport != null;
        startTime.setText(transport.getStartTime());
        startAddress.setText(transport.getStartAddress());
        arrivalOrigin.setText(transport.getArrivalOrigin());
        departureOrigin.setText(transport.getDepartureOrigin());
        arrivalDestination.setText(transport.getArrivalDestination());
        keysTime.setText(transport.getKeysTime());
        keysToo.setText(transport.getKeysToo());
        tvFinalStation.setText(transport.getFinalStation());
        tvFinalTime.setText(transport.getFinalTime());
        tvFinalAdress.setText(transport.getFinalAddress());
        tvFinalAdress.setVisibility(View.GONE);

        if (Objects.equals(transport.getSecondTransport(), "true")){
            sSecondTransport.setChecked(true);
            tvFinalAdress.setVisibility(View.VISIBLE);
            tvFinalStation.setVisibility(View.GONE);
            tvFinalTime.setVisibility(View.GONE);
        }

        sSecondTransport.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    tvFinalStation.setVisibility(View.GONE);
                    tvFinalTime.setVisibility(View.GONE);
                    tvFinalStation.setText("");
                    tvFinalTime.setText("");
                    tvFinalAdress.setVisibility(View.VISIBLE);
                } else {
                    tvFinalStation.setVisibility(View.VISIBLE);
                    tvFinalTime.setVisibility(View.VISIBLE);
                    tvFinalAdress.setVisibility(View.GONE);
                    tvFinalAdress.setText("");
                }

            }
        });

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
