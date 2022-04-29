package com.example.guessthenumber;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import android.os.Bundle;

import java.util.Random;

public class GameActivity extends AppCompatActivity implements View.OnClickListener {

    public static final int MAX_NUMBER = 1000;
    public static final Random RANDOM = new Random();
    private TextView tvHint;
    private EditText etInput;
    private Button btnGuess;
    private int numberToFind, numberTries;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        tvHint = (TextView) findViewById(R.id.tvHint);
        etInput = (EditText) findViewById(R.id.etInput);
        btnGuess = (Button) findViewById(R.id.btnGuess);
        btnGuess.setOnClickListener(this);

        newGame();
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

        if (n == numberToFind) {
            Toast.makeText(this, "Congratulations ! You found the number " + numberToFind +
                    " in " + numberTries + " tries", Toast.LENGTH_SHORT).show();
            newGame();
        } else if (n > numberToFind) {
            tvHint.setText("TOO HIGH");
        } else if (n < numberToFind) {
            tvHint.setText("TOO LOW");
        }
    }

    private void newGame() {
        numberToFind = RANDOM.nextInt(MAX_NUMBER) + 1;
        tvHint.setText("START");
        etInput.setText("");
        numberTries = 0;
    }
}

