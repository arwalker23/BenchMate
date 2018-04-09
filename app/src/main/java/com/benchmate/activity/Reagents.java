package com.benchmate.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.benchmate.R;
import com.benchmate.domain.Experiment;
import com.benchmate.domain.Reagent;

import java.util.ArrayList;
import java.util.List;

public class Reagents extends Activity implements CompoundButton.OnCheckedChangeListener {

    int flag = 0;
    Button buttonBack, buttonSave;
//    ArrayList<String> selectedReagents = new ArrayList<>();
//    ArrayList<String> retrievedSelectedReagents;
    ArrayList<Boolean> checkedReagents;
    ArrayList<CheckBox> checkboxArray = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reagents);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        // Retrieve experiment object from intent
        Intent intent = getIntent();
        final Experiment experiment = (Experiment) intent.getSerializableExtra("experiment");
        // Retrieve name of well so Reagents knows which Well object to modify
        final String wellName = intent.getStringExtra("wellName");
        // Debug toast
        Toast.makeText(this, "Now viewing well " + experiment.getWells().get(wellName).getName(), Toast.LENGTH_SHORT).show();
        // Retrieve checked boxes from experiment class TreeMap "wells"
        checkedReagents = new ArrayList<>(experiment.getWells().get(wellName).getSelectedReagents());

        // Handle if instanceState is saved
        if (savedInstanceState != null) {
            checkedReagents = (ArrayList<Boolean>) savedInstanceState.getSerializable("checkedReagents");
            flag = savedInstanceState.getInt("savedflag");
        }

        buttonBack = findViewById(R.id.buttonBack);
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Reagents.this, Plate.class);
                intent.putExtra("experiment", experiment);
                startActivity(intent);
                finish();
            }
        });

        buttonSave = findViewById(R.id.buttonSave);
        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Reagents.this, Plate.class);
                // Handling for selectedFields array, can update the Experiment object TreeMap "wells" accordingly
                experiment.getWells().get(wellName).setSelectedReagents(checkedReagents);
                intent.putExtra("experiment", experiment);
                startActivity(intent);
                finish();
            }
        });

//        String[] reagents_array = getResources().getStringArray(R.array.units_array); // TODO: remove
        List<Reagent> reagents_array = experiment.getReagents();
//        CheckBox[] checkboxArray = new CheckBox[reagents_array.size()]; // TODO: remove
        LinearLayout checkboxLayout = findViewById(R.id.chkboxlyt);

        // Generate checkboxes for each reagent
        for (int i = 0; i < reagents_array.size(); i++) {
            CheckBox checkbox = new CheckBox(getApplicationContext());
            checkbox.setText(reagents_array.get(i).prettyPrint());
            checkbox.setTextSize(28);
            checkboxArray.add(checkbox);
            checkbox.setId(i);
            checkboxLayout.addView(checkbox);
            checkbox.setOnCheckedChangeListener(this);
            checkbox.setChecked(checkedReagents.get(i));
        }

        // If there were previously saved checkboxes that the user ticked before, check them again
        if (flag != 0) {
            for (int i = 0; i < checkboxArray.size(); i++) {
                checkboxArray.get(i).setChecked(checkedReagents.get(i));
            }
//            for (CheckBox checkbox : checkboxArray) { TODO: remove
////                for (int i = 0; i < retrievedSelectedReagents.size(); i++) {
////                    if ((checkbox.getText() + "").equals(retrievedSelectedReagents.get(i))) {
////                        checkbox.toggle();
////                    }
////                }
//            }
        }
    }

    public void onCheckedChanged(CompoundButton checkbox, boolean isChecked) {
//        String checkedText = checkbox.getText() + "";

        if (isChecked) {
//            selectedReagents.add(checkedText);
            checkedReagents.set(checkbox.getId(), true);
//            Toast.makeText(this, cb.getText() + " was selected!", Toast.LENGTH_SHORT).show();
        } else {
//            selectedReagents.remove(checkedText);
            checkedReagents.set(checkbox.getId(), false);
//            Toast.makeText(this, cb.getText() + " was not selected!", Toast.LENGTH_SHORT).show();
        }
    }

    public void onSaveInstanceState(Bundle savedState) {
        super.onSaveInstanceState(savedState);
        flag = 1;
        savedState.putSerializable("checkedReagents", checkedReagents);
//        savedState.putStringArrayList("selectedReagents", selectedReagents);
        savedState.putInt("savedflag", flag);
    }
}