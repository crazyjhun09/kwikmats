package com.crzyjn09.kwikmats;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import static java.lang.Math.abs;

public class MainActivity extends AppCompatActivity {

    ProgressBar pb;
    int score1 = 0;
    int counter = 0;
    int highscore = 0;
    int speed = 50;
    int solved = 0;
    TimerTask tt = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void toStart(final View view){
        setContentView(R.layout.activity_main);
        TextView high = findViewById(R.id.highscore);
        high.setText("Highscore: "+highscore);
    }

    public void toPlay(final View view) {
        counter=0;
        final int scoreMultiplier = solved/10;
        int min = 0;
        int max = 5;

        if (scoreMultiplier == 1){
            max = (scoreMultiplier+1)*5;
        }
        else if(scoreMultiplier == 0){
        }
        else {
            max = (scoreMultiplier+1)*5;
            min = (scoreMultiplier-1)*5;
        }

        int random1 = new Random().nextInt((max - min) + 1) + min;
        int random2 = new Random().nextInt((max - min) + 1) + min;
        int sum = random1 + random2;

        int average = (sum)/2;
        int randRes = 2*max;
        int minRand = (randRes+average)/2;
        int randomRes = new Random().nextInt((randRes - minRand) + 1) + minRand;

        final int corrector = new Random().nextInt(2);
        if (randomRes == sum && scoreMultiplier == 0){
            randomRes++;
        }
        else if (randomRes == sum && scoreMultiplier >= 1){
            randomRes = randomRes+10;
        }

        setContentView(R.layout.play);
        final Button yesBtn = findViewById(R.id.yes);
        final Button noBtn = findViewById(R.id.no);
        final TextView score = findViewById(R.id.score);
        score.setText("Score: "+score1);
        TextView numbers = findViewById(R.id.numbers);
        pb = (ProgressBar)findViewById(R.id.progressBar);
        prog(view);
        if (corrector == 0) {

            numbers.setText(random1 + "  +  " + random2 + "  =  " + randomRes);
            noBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    score1 = score1 + 50 + ((int)(((float)abs(pb.getProgress()-100)/100)*50*(scoreMultiplier+1)));
                    solved++;
                    toPlay(view);
                }
            });
            yesBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setHighscore(view);
                }
            });
        } else if (corrector == 1) {
            numbers.setText(random1 + " + " + random2 + "=" + sum);
            noBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setHighscore(view);
                }
            });
            yesBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    score1=score1 + 50 + ((int)(((float)abs(pb.getProgress()-100)/100)*50*(scoreMultiplier+1)));
                    solved++;
                    toPlay(view);
                }
            });
        }
    }
    public void prog (final View view) {
        final Timer t = new Timer();
            pb = (ProgressBar)findViewById(R.id.progressBar);
                if(tt != null){
                    return;
                }
                tt = new TimerTask() {
                @Override
                public void run() {
                    counter++;
                    pb.setProgress(counter);
                    if (counter == 100) {
                        runOnUiThread(new Runnable() {
                               @Override
                            public void run() {
                                setHighscore(view);
                            }
                        });
                    }
                }
            };
            t.schedule(tt, 0,speed);
    }
    public void setHighscore(View view){
        if (score1 > highscore){
            highscore = score1;
        }
        score1=0;
        solved=0;
        toStart(view);
    }
}