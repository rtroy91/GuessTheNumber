package com.example.guessthenumber;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Spinner spDifficulty;
    EditText etName, etGender;
    TextView tvDiffVal, tvAlert;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etName = (EditText) findViewById(R.id.etName);
        etGender = (EditText) findViewById(R.id.etGender);
        spDifficulty = (Spinner) findViewById(R.id.spDifficulty);

        ArrayList<String> data = new ArrayList<String>();
        data.add("Choose Difficulty");
        data.add("Easy (MAX: 50)");
        data.add("Normal (MAX: 250)");
        data.add("Hard (MAX: 500)");
        data.add("Extreme (MAX: 1000)");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, data);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spDifficulty.setAdapter(adapter);

        tvDiffVal = (TextView) findViewById(R.id.tvDiffVal);
        final Button btnStart = findViewById(R.id.btnStart);

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent start = new Intent(MainActivity.this, GameActivity.class);

                String name, gender, mode, val, i;
                name = etName.getText().toString();
                gender = etGender.getText().toString();

                start.putExtra("NAME", name);
                start.putExtra("GENDER", gender);

                val = spDifficulty.getSelectedItem().toString();
                i = val.replaceAll("[^0-9]", "");
                tvDiffVal.setText(i);

                mode = tvDiffVal.getText().toString();
                start.putExtra("DIFFICULTY", mode);



                if(name.equals("")){
                    Toast toast = Toast.makeText(MainActivity.this, "Please Type Your Name", Toast.LENGTH_SHORT);
                    toast.show();
                }else if(gender.compareToIgnoreCase("Male") > 0 && gender.compareToIgnoreCase("Female") > 0) {
                    Toast toast = Toast.makeText(MainActivity.this, "Wrong Gender Input", Toast.LENGTH_SHORT);
                    toast.show();
                }else if(gender.equals("")) {
                    Toast toast = Toast.makeText(MainActivity.this, "Please Type Your Gender", Toast.LENGTH_SHORT);
                    toast.show();
                }else if (mode.equals("")) {
                    Toast toast = Toast.makeText(MainActivity.this, "Please Choose Difficulty", Toast.LENGTH_SHORT);
                    toast.show();
                } else {
                    startActivity(start);
                }
            }
        });

        tvAlert = (TextView) findViewById(R.id.tvAlert);
        final Button btnExit = findViewById(R.id.btnExit);

        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

                builder.setCancelable(true);
                builder.setMessage("Are you sure you want to exit?");

                builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finishAffinity();
                    }
                });
                builder.show();
            }
        });
    }
}