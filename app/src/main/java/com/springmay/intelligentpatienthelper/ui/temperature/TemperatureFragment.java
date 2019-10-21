package com.springmay.intelligentpatienthelper.ui.temperature;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import android.view.LayoutInflater;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.springmay.intelligentpatienthelper.R;

import java.util.Objects;

public class TemperatureFragment extends Fragment {

    private TextView textViewTemperature;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        //
        View root = inflater.inflate(R.layout.fragment_temperature, container, false);
        //
        textViewTemperature = root.findViewById(R.id.text_home);
        //
        DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference();
        //
        ValueEventListener temp =  usersRef.child("inDegreeC")
                .addValueEventListener(new ValueEventListener()  {

            @SuppressLint({"ResourceAsColor", "SetTextI18n"})
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String value = Objects.requireNonNull(dataSnapshot.getValue()).toString();

                textViewTemperature.setText(value + "C");

                float x;
                x=  Float.parseFloat(value);

                if(x>38.0)
                {
                    textViewTemperature.setTextColor(getResources().getColor(R.color.high));
                }
                else if (x<37.0)
                {
                    textViewTemperature.setTextColor(getResources().getColor(R.color.low));
                }
                else
                {
                    textViewTemperature.setTextColor(getResources().getColor(R.color.normal));
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return root;
    }





              }
