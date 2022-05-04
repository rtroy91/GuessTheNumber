package com.example.guessthenumber;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import android.os.Bundle;

import java.util.Random;

public class GameActivity extends AppCompatActivity implements View.OnClickListener {

    final Random random = new Random();

    private TextView tvHint, tvName, tvInt, tvMax, tvDisplayDiff;
    private EditText etInput;
    private Button btnGuess;
    private int numberToFind, numberTries;
    private ImageView ivZhongli;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        tvHint = (TextView) findViewById(R.id.tvHint);
        tvDisplayDiff = (TextView) findViewById(R.id.tvDisplayDiff);
        tvName = (TextView) findViewById(R.id.tvName);
        tvMax = (TextView) findViewById(R.id.tvMax);
        tvInt = (TextView) findViewById(R.id.tvInt);
        etInput = (EditText) findViewById(R.id.etInput);
        btnGuess = (Button) findViewById(R.id.btnGuess);
        btnGuess.setOnClickListener(this);

        Intent intent = getIntent();
        String name, gender;
        name = intent.getStringExtra("NAME");
        gender = intent.getStringExtra("GENDER");

        if (gender.equalsIgnoreCase("Male")) {
            tvName.setText("WELCOME! Mr. " + name);
        } else if (gender.equalsIgnoreCase("Female")) {
            tvName.setText("WELCOME! Ms. " + name);
        }

        String str = intent.getStringExtra("DIFFICULTY");
        tvMax.setText(str);
        int MAX_DIFF = Integer.parseInt(tvMax.getText().toString());
        newGame(MAX_DIFF);
        tvInt.setText(Integer.toString(numberToFind));

        if(MAX_DIFF == 50){
            tvDisplayDiff.setText("EASY");
            tvDisplayDiff.setTextColor(Color.parseColor("#2980B9"));
        }else if(MAX_DIFF == 250){
            tvDisplayDiff.setText("NORMAL");
            tvDisplayDiff.setTextColor(Color.parseColor("#229954"));
        }else if(MAX_DIFF == 500){
            tvDisplayDiff.setText("HARD");
            tvDisplayDiff.setTextColor(Color.parseColor("#D35400"));
        }else{
            tvDisplayDiff.setText("EXTREME");
            tvDisplayDiff.setTextColor(Color.parseColor("#B22727"));
        }
    }
    @Override
    public void onClick(View view) {
        if (view == btnGuess) {
            validate();
        }
    }

    private void validate() {
        int n = Integer.parseInt(etInput.getText().toString());
        numberTries++;
        ivZhongli = (ImageView) findViewById(R.id.ivZhongli);
        final Dialog dialog = new Dialog(GameActivity.this);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                dialog.dismiss();
            }
        }, 2000);

        if (n == numberToFind) {
            Toast.makeText(this, "Congratulations ! You found the number " + numberToFind +
                    " in " + numberTries + " tries", Toast.LENGTH_SHORT).show();
            ivZhongli.setVisibility(View.INVISIBLE);
            int MAX_DIFF = Integer.parseInt(tvMax.getText().toString());
            newGame(MAX_DIFF);


        } else if (n > numberToFind) {
            tvHint.setText("TOO HIGH");
            dialog.setContentView(R.layout.dialog_toohigh);
            dialog.show();

        } else if (n < numberToFind) {
            tvHint.setText("TOO LOW");
            dialog.setContentView(R.layout.dialog_toolow);
            dialog.show();
        }
    }

    private void newGame(int DIFF) {
        int MAX_NUMBER = random.nextInt(DIFF);
        numberToFind = random.nextInt(MAX_NUMBER);
        tvHint.setText("?");
        etInput.setText("");
        numberTries = 0;
    }
}

