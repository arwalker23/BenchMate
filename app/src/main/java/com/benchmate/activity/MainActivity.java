package com.benchmate.activity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.benchmate.R;

public class MainActivity extends AppCompatActivity {

    Button buttonNew, buttonLoad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        buttonNew = findViewById(R.id.buttonNew);
        buttonLoad = findViewById(R.id.buttonLoad);

        buttonNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSetup();
            }
        });
        buttonLoad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openLoad();
            }
        });
    }

    public void openSetup() {
        Intent intent = new Intent(this, Setup.class);
        startActivity(intent);
    }

    public void openLoad() {
        Intent intent = new Intent(this, Load.class);
        startActivity(intent);
    }
}
