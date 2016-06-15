package com.jappyapps.listapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class TestActivity extends AppCompatActivity {

    ImageView testImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        testImage = (ImageView) findViewById(R.id.test_image_picasso);
        Picasso.with(this).load("https://hilleletv.files.wordpress.com/2015/11/shahrukhkhan-jan30.jpg").into(testImage);

    }

}
