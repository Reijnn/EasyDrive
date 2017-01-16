package com.reijn.easydrive.Fragments;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.creativityapps.gmailbackgroundlibrary.BackgroundMail;
import com.luseen.simplepermission.permissions.Permission;
import com.luseen.simplepermission.permissions.PermissionFragment;
import com.luseen.simplepermission.permissions.PermissionUtils;
import com.luseen.simplepermission.permissions.SinglePermissionCallback;
import com.reijn.easydrive.MainActivity;
import com.reijn.easydrive.R;
import com.reijn.easydrive.Transport;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static android.app.Activity.RESULT_OK;


public class SendFragment extends PermissionFragment {
    private static final String ARG_PARAM1 = "param1";
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private boolean photoSet = false;
    Transport transport;
    Unbinder unbinder;
    @BindView(R.id.button2)
    Button button2;
    @BindView(R.id.imageView2)
    ImageView imageView2;

    @OnClick(R.id.button2)
    public void click() {
        if (photoSet) {
            BackgroundMail.newBuilder(getContext())
                    .withUsername("reijnn@gmail.com")
                    .withPassword("cxbziqvczfwfhpri")
                    .withMailto("reijnn@gmail.com")
                    .withSubject("Test")
                    .withBody("Ritbonformulier \n\n" +
                            "De volgende gegevens zijn ontvangen: \n\n" +
                            "Ritdatum: " + transport.getDate() + "\n" +
                            "Naam chauffeur: " + MainActivity.firebaseUser.getDisplayName() + "\n" +
                            "Emailadres chauffeur: " + MainActivity.firebaseUser.getEmail() + "\n" +
                            "Woonplaats chauffeur: " + transport.getDate() + "\n" +
                            "Naam leasemaatschappij: " + transport.getCompany() + "\n" +
                            "Kenteken: " + transport.getPlate() + "\n" +
                            "Starttijd chauffeur: " + transport.getStartTime() + "\n" +
                            "Gestart vanaf station: " + transport.getStartAddress() + "\n" +
                            "Aankomsttijd afhaaladres: " + transport.getArrivalOrigin() + "\n" +
                            "Vertrektijd afhaaladres: " + transport.getDepartureOrigin() + "\n" +
                            "Aankomsttijd afleveradres: " + transport.getArrivalDestination() + "\n" +
                            "Sleutels afgegeven om: " + transport.getKeysTime() + "\n" +
                            "Sleutels afgegeven aan: " + transport.getKeysToo() + "\n" +
                            "Vervolgtransport: " + transport.getSecondTransport() + "\n" +
                            "Geeindigd op station: " + transport.getFinalStation() + "\n" +
                            "Eindtijd chauffeur: " + transport.getFinalTime() + "\n" +
                            "Vertraging: " + transport.getDelay())
                    .withProcessVisibility(true)
                    .withSendingMessage("Rit versturen naar easyway...")
                    .withSendingMessageSuccess("Succesvol verstuurd!")
                    .withSendingMessageError("Oops.. er is iets fout gegaan!")
                    .withOnSuccessCallback(new BackgroundMail.OnSuccessCallback() {
                        @Override
                        public void onSuccess() {
                            getActivity().finish();
                        }
                    })
                    .withOnFailCallback(new BackgroundMail.OnFailCallback() {
                        @Override
                        public void onFail() {

                        }
                    })
                    .send();
        } else {
            requestPermission(Permission.CAMERA, new SinglePermissionCallback() {
                @Override
                public void onPermissionResult(boolean permissionGranted, boolean isPermissionDeniedForever) {
                    if (permissionGranted) {
                        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
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
    }

    public SendFragment() {
    }

    public static SendFragment newInstance(Transport transport) {
        SendFragment fragment = new SendFragment();
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
        View view = inflater.inflate(R.layout.fragment_send, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            imageView2.setImageBitmap(imageBitmap);
            photoSet = true;
            button2.setText("Versturen");
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
