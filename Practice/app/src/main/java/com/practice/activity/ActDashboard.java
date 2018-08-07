package com.practice.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.nileshp.multiphotopicker.photopicker.activity.PickImageActivity;
import com.practice.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ActDashboard extends AppCompatActivity {

    @BindView(R.id.tvGalleryPicker) TextView tvGalleryPicker;
    @BindView(R.id.tvWaterTracker) TextView tvWaterTracker;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_dashboard);

        ButterKnife.bind(this);

        clickEvent();



    }


    public void clickEvent(){
        try{
            tvGalleryPicker.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    startActivity(new Intent(ActDashboard.this, ActGalleryPicker.class));

                }
            });

            tvWaterTracker.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    startActivity(new Intent(ActDashboard.this, ActGalleryPicker.class));

                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}