package com.reijn.easydrive.Fragments;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.TextInputEditText;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.luseen.simplepermission.permissions.Permission;
import com.luseen.simplepermission.permissions.PermissionFragment;
import com.luseen.simplepermission.permissions.PermissionUtils;
import com.luseen.simplepermission.permissions.SinglePermissionCallback;
import com.reijn.easydrive.R;
import com.reijn.easydrive.Transport;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static android.R.layout.simple_spinner_item;
import static android.app.Activity.RESULT_OK;

public class ExtraFragment extends PermissionFragment {
    @BindView(R.id.tvFuelCosts)
    TextInputEditText tvFuelCosts;
    @BindView(R.id.btFuelCosts)
    Button btFuelCosts;
    @BindView(R.id.tvCarwashCosts)
    TextInputEditText tvCarwashCosts;
    @BindView(R.id.btCarwashCosts)
    Button btCarwashCosts;
    @BindView(R.id.tvCleaningCosts)
    TextInputEditText tvCleaningCosts;
    @BindView(R.id.btCleaningCosts)
    Button btCleaningCosts;
    @BindView(R.id.tvOvCosts)
    TextInputEditText tvOvCosts;
    @BindView(R.id.btOvCosts)
    Button btOvCosts;
    @BindView(R.id.tvOtherCosts)
    TextInputEditText tvOtherCosts;
    @BindView(R.id.btOtherCosts)
    Button btOtherCosts;
    @BindView(R.id.tvNotes)
    TextInputEditText tvNotes;
    @BindView(R.id.spinner)
    Spinner spinner;
    @BindView(R.id.swVertraging)
    Switch swVertraging;
    @BindView(R.id.tvDelayLocation)
    TextInputEditText tvDelayLocation;
    @BindView(R.id.tvDelayTime)
    TextInputEditText tvDelayTime;
    @BindView(R.id.ivFuelCosts)
    ImageView ivFuelCosts;
    @BindView(R.id.ivCarwashCosts)
    ImageView ivCarwashCosts;
    @BindView(R.id.ivCleaningCosts)
    ImageView ivCleaningCosts;
    @BindView(R.id.ivOvCosts)
    ImageView ivOvCosts;
    @BindView(R.id.ivOtherCosts)
    ImageView ivOtherCosts;

    private static final String ARG_PARAM1 = "param1";
    static final int REQUEST_IMAGE_CAPTURE = 1;

    private Unbinder unbinder;
    Button btn = null;
    Transport transport;

    @OnClick({R.id.btFuelCosts, R.id.btCarwashCosts, R.id.btCleaningCosts, R.id.btOvCosts, R.id.btOtherCosts})
    void dispatchTakePictureIntent(final View v) {
        requestPermission(Permission.CAMERA, new SinglePermissionCallback() {
            @Override
            public void onPermissionResult(boolean permissionGranted, boolean isPermissionDeniedForever) {
                if (permissionGranted) {
                    Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    btn = (Button) v;
                    if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
                        startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                    }
                } else if (isPermissionDeniedForever) {
                    PermissionUtils.openApplicationSettings(getContext());
                } else {
                    Toast.makeText(getActivity(), "Please grant permissions", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    @OnClick(R.id.tvDelayTime)
    public void start(View v) {
        Calendar c = Calendar.getInstance();
        TimePickerDialog mTimePicker = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                tvDelayTime.setText(String.format("%02d", selectedHour) + ":" + String.format("%02d", selectedMinute));
            }
        }, c.get(Calendar.HOUR_OF_DAY), c.get(Calendar.MINUTE), true);
        mTimePicker.setTitle("Selecteer tijd");
        mTimePicker.show();
    }

    public ExtraFragment() {
    }

    public static ExtraFragment newInstance(Transport transport) {
        ExtraFragment fragment = new ExtraFragment();
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
        View view = inflater.inflate(R.layout.fragment_extra, container, false);
        unbinder = ButterKnife.bind(this, view);
        setupUI();
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        ImageView image = null;
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            switch (btn.getId()) {
                case R.id.btFuelCosts:
                    image = ivFuelCosts;
                    break;
                case R.id.btCarwashCosts:
                    image = ivCarwashCosts;
                    break;
                case R.id.btCleaningCosts:
                    image = ivCleaningCosts;
                    break;
                case R.id.btOvCosts:
                    image = ivOvCosts;
                    break;
                case R.id.btOtherCosts:
                    image = ivOtherCosts;
                    break;
            }
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            assert image != null;
            image.setImageBitmap(imageBitmap);
            image.setVisibility(View.VISIBLE);
        }
    }

    private void setupUI() {
        assert transport != null;
        tvFuelCosts.setText(transport.getFuelCosts());
        tvCarwashCosts.setText(transport.getCarwashCosts());
        tvCleaningCosts.setText(transport.getCleaningCosts());
        tvOvCosts.setText(transport.getOvCosts());
        tvOtherCosts.setText(transport.getOtherCosts());
        tvNotes.setText(transport.getNotes());
        tvDelayLocation.setText(transport.getDelayLocation());
        tvDelayTime.setText(transport.getDelayTime());

        if ((transport.getCardUsed()) != null) {
            spinner.setSelection(Integer.parseInt(transport.getCardUsed()), true);
        }

        if (Objects.equals(transport.getDelay(), "true")) {
            swVertraging.setChecked(true);
            tvDelayTime.setVisibility(View.VISIBLE);
            tvDelayLocation.setVisibility(View.VISIBLE);
        }

        swVertraging.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    tvDelayTime.setVisibility(View.VISIBLE);
                    tvDelayLocation.setVisibility(View.VISIBLE);
                } else {
                    tvDelayTime.setVisibility(View.GONE);
                    tvDelayLocation.setVisibility(View.GONE);
                    tvDelayLocation.setText("");
                    tvDelayTime.setText("");
                }

            }
        });

        spinner.setAdapter(new ArrayAdapter<String>(
                getContext(), simple_spinner_item, new ArrayList<String>() {{
            add("Nee");
            add("Essopas");
            add("Shellpas");
        }}));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
