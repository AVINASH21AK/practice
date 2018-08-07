package com.practice.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.nileshp.multiphotopicker.photopicker.activity.PickImageActivity;
import com.practice.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ActGalleryPicker extends AppCompatActivity {

    @BindView(R.id.tvSelected) TextView tvSelected;
    @BindView(R.id.viewDummy) View viewDummy;

    private ArrayList<String> pathList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_gallery_picker);

        ButterKnife.bind(this);

        clickEvent();

    }

    public void clickEvent(){
        try{
            viewDummy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent mIntent = new Intent(ActGalleryPicker.this, PickImageActivity.class);
                    mIntent.putExtra(PickImageActivity.KEY_LIMIT_MAX_IMAGE, 10);
                    mIntent.putExtra(PickImageActivity.KEY_LIMIT_MIN_IMAGE, 1);
                    startActivityForResult(mIntent, PickImageActivity.PICKER_REQUEST_CODE);
                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (resultCode != RESULT_OK) {
            return;
        }
        if (resultCode == -1 && requestCode == PickImageActivity.PICKER_REQUEST_CODE) {
            ActGalleryPicker.this.pathList = intent.getExtras().getStringArrayList(PickImageActivity.KEY_DATA_RESULT);
            if (this.pathList != null && !this.pathList.isEmpty()) {
                StringBuilder sb=new StringBuilder("");
                for(int i=0;i<pathList.size();i++) {
                    sb.append("Photo"+(i+1)+":"+pathList.get(i));
                    sb.append("\n");
                }
                tvSelected.setText(sb.toString()); // here this is textview for sample use...
            }
        }
    }
}