package com.example.group02midterm;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Handler;
import android.view.MenuItem;
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

    private TextView tvGuessDisplay, tvName, tvInt, tvMax, tvDisplayDiff, tvInstruction, textView4, textView8, textView9;
    private EditText etInput;
    private Button btnGuess, btnNewGame, btnBack;
    private int numberToFind, numberTries, maxTries;
    private ImageView ivZhongli;
    MediaPlayer music, congrats, failed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        textView4 = (TextView) findViewById(R.id.textView4);
        textView8 = (TextView) findViewById(R.id.textView8);
        textView9 = (TextView) findViewById(R.id.textView9);
        tvInstruction = (TextView) findViewById(R.id.tvInstruction);
        tvGuessDisplay = (TextView) findViewById(R.id.tvGuessDisplay);
        tvDisplayDiff = (TextView) findViewById(R.id.tvDisplayDiff);
        tvName = (TextView) findViewById(R.id.tvName);
        tvMax = (TextView) findViewById(R.id.tvMax);
        tvInt = (TextView) findViewById(R.id.tvInt);
        etInput = (EditText) findViewById(R.id.etInput);
        btnGuess = (Button) findViewById(R.id.btnGuess);
        btnNewGame = (Button) findViewById(R.id.btnNewGame);
        btnBack = (Button) findViewById(R.id.btnBack);
        btnGuess.setOnClickListener(this);
        btnNewGame.setOnClickListener(this);
        btnBack.setOnClickListener(this);

        Intent intent = getIntent();
        String name, gender;
        name = intent.getStringExtra("NAME");
        gender = intent.getStringExtra("GENDER");

        //Display Honorific and Names
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

        if (MAX_DIFF == 50) {
            maxTries = 25;
            tvInstruction.setText("YOU ONLY HAVE "+maxTries+ " GUESSES!");
            tvDisplayDiff.setText("EASY");
            tvDisplayDiff.setTextColor(Color.parseColor("#2980B9"));
        } else if (MAX_DIFF == 250) {
            maxTries = 20;
            tvInstruction.setText("YOU ONLY HAVE "+maxTries+ " GUESSES!");
            tvDisplayDiff.setText("NORMAL");
            tvDisplayDiff.setTextColor(Color.parseColor("#229954"));
        } else if (MAX_DIFF == 500) {
            maxTries = 15;
            tvInstruction.setText("YOU ONLY HAVE "+maxTries+ " GUESSES!");
            tvDisplayDiff.setText("HARD");
            tvDisplayDiff.setTextColor(Color.parseColor("#D35400"));
        } else {
            maxTries = 10;
            tvInstruction.setText("YOU ONLY HAVE "+maxTries+ " GUESSES!");
            tvDisplayDiff.setText("EXTREME");
            tvDisplayDiff.setTextColor(Color.parseColor("#B22727"));
        }
        music = MediaPlayer.create(GameActivity.this, R.raw.game_audio);
        music.start();
    }

    //function for resetting activity using the back button in nav bar
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                music.stop();
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onClick(View view) {
        if (view == btnGuess) {
            if (etInput.getText().toString().trim().isEmpty()) {
                Toast.makeText(this, "Please Input a Number", Toast.LENGTH_SHORT).show();
            } else {
                tvInstruction.setVisibility(View.INVISIBLE);
                validate();
            }
        }
        Intent newGame = getIntent();
        if (view == btnNewGame) {
            startActivity(newGame);
        }
        Intent back = new Intent(GameActivity.this, MainActivity.class);
        if (view == btnBack) {
            startActivity(back);
            finish();
        }
    }

    private void validate() {
        int n = Integer.parseInt(etInput.getText().toString());
        ivZhongli = (ImageView) findViewById(R.id.ivZhongli);

        final Dialog dialog = new Dialog(GameActivity.this);

        //Function to Dismiss Pop up Window Automatically
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                dialog.dismiss();
            }
        }, 2500);

        if (n == numberToFind) {
            dialog.setContentView(R.layout.dialog_congrats);
            dialog.show();

            ivZhongli.setVisibility(View.INVISIBLE);
            btnNewGame.setVisibility(View.VISIBLE);
            btnBack.setVisibility(View.VISIBLE);
            textView4.setVisibility(View.INVISIBLE);
            tvGuessDisplay.setVisibility(View.INVISIBLE);
            btnGuess.setVisibility(View.INVISIBLE);
            etInput.setVisibility(View.INVISIBLE);

            textView8.setText("YAY! YOU'VE GUESSED THE NUMBER CORRECTLY!");
            textView9.setText("NUMBER OF TRIES: " + numberTries + "/" + maxTries);
            congrats = MediaPlayer.create(GameActivity.this, R.raw.congrats_audio);
            congrats.start();

            //background music stop if success
            music.stop();
            int MAX_DIFF = Integer.parseInt(tvMax.getText().toString());
            newGame(MAX_DIFF);

        } else if (n > numberToFind && numberTries != maxTries) {
            numberTries++;
            MediaPlayer music = MediaPlayer.create(GameActivity.this, R.raw.toohigh);
            music.start();
            dialog.setContentView(R.layout.dialog_toohigh);
            dialog.show();
        } else if (n < numberToFind && numberTries != maxTries) {
            numberTries++;
            MediaPlayer music = MediaPlayer.create(GameActivity.this, R.raw.toolow);
            music.start();
            dialog.setContentView(R.layout.dialog_toolow);
            dialog.show();

        }
        if (maxTries == numberTries) {
            dialog.setContentView(R.layout.dialog_failed);
            dialog.show();

            ivZhongli.setVisibility(View.INVISIBLE);
            btnNewGame.setVisibility(View.VISIBLE);
            btnBack.setVisibility(View.VISIBLE);
            textView4.setVisibility(View.INVISIBLE);
            tvGuessDisplay.setVisibility(View.INVISIBLE);
            btnGuess.setVisibility(View.INVISIBLE);
            etInput.setVisibility(View.INVISIBLE);

            textView8.setText("LOOKS LIKE YOU DID NOT GUESS THE NUMBER");
            textView9.setText("NUMBER OF TRIES: " + numberTries  + "/" + maxTries);
            //background music stop if failed
            music.stop();
            failed = MediaPlayer.create(GameActivity.this, R.raw.failed_audio);
            failed.start();

        }
        tvGuessDisplay.setText("GUESS REMAINING: " + (maxTries - numberTries));
    }

    private void newGame(int DIFF) {
        int MAX_NUMBER = random.nextInt(DIFF);
        numberToFind = random.nextInt(MAX_NUMBER);
        etInput.setText("");
        numberTries=0;
    }

}


