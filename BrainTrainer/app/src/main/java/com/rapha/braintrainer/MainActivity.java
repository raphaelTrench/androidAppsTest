package com.rapha.braintrainer;

import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    TextView number;
    TextView block0,block1,block2,block3;
    int result,random,random2,score,totalTries;

    public void timerUpdate(final Button button)
    {
        final TextView timer = (TextView)findViewById(R.id.timer);

        new CountDownTimer(30000+100,1000){
            public void onTick(long millisecondsUntilDone){

                long seconds = millisecondsUntilDone/1000;
                if(seconds <=9)
                {
                    timer.setText("0"+String.valueOf(seconds));
                }
                else
                {
                    timer.setText(String.valueOf(seconds));
                }
            }
            public void onFinish(){
                timer.setText("00");

                button.setVisibility(View.VISIBLE);

                TextView message = (TextView)findViewById(R.id.message);
                message.setText("Score: " + score + "/" + totalTries);

                block0.setVisibility(View.INVISIBLE);
                block1.setVisibility(View.INVISIBLE);
                block2.setVisibility(View.INVISIBLE);
                block3.setVisibility(View.INVISIBLE);

                number.setVisibility(View.INVISIBLE);

                score=0;
                totalTries=0;
            }
        }.start();
    }

    public void play(View view)
    {
        block0.setVisibility(View.VISIBLE);
        block1.setVisibility(View.VISIBLE);
        block2.setVisibility(View.VISIBLE);
        block3.setVisibility(View.VISIBLE);

        TextView message = (TextView)findViewById(R.id.message);
        message.setVisibility(View.INVISIBLE);

        Button button = (Button)findViewById(R.id.button);
        button.setVisibility(View.INVISIBLE);
        button.setText("Play Again!");
        timerUpdate(button);

        click(view);
    }
    public void click(View view)
    {
        number.setVisibility(View.VISIBLE);
        Random randomGenerator = new Random();
        random = randomGenerator.nextInt(40) + 5;
        random2 = randomGenerator.nextInt(30) + 7;
        result = random + random2;
        number.setText(String.valueOf(random) + " + " + String.valueOf(random2));
        displayAnswer();
    }
    public void displayAnswer()
    {
        Random randomGenerator = new Random();
        int numberAnswer = randomGenerator.nextInt(4);
        int range = result + 5 - (random+random2/2) + 1;

        switch (numberAnswer) {
            case 0:
            {
                block0.setText(String.valueOf(result));
                block1.setText(String.valueOf(randomGenerator.nextInt(range) + random + random2/2));
                block2.setText(String.valueOf(randomGenerator.nextInt(range) + random + random2/2));
                block3.setText(String.valueOf(randomGenerator.nextInt(range) + random + random2/2));
            }
            break;
            case 1:
            {
                block1.setText(String.valueOf(result));
                block0.setText(String.valueOf(randomGenerator.nextInt(range) + random + random2/2));
                block2.setText(String.valueOf(randomGenerator.nextInt(range) + random + random2/2));
                block3.setText(String.valueOf(randomGenerator.nextInt(range) + random + random2/2));
            }
            break;
            case 2:
            {
                block0.setText(String.valueOf(result));
                block1.setText(String.valueOf(randomGenerator.nextInt(range) + random + random2/2));
                block2.setText(String.valueOf(randomGenerator.nextInt(range) + random + random2/2));
                block3.setText(String.valueOf(randomGenerator.nextInt(range) + random + random2/2));
            }
            break;
            case 3:
            {
                block3.setText(String.valueOf(result));
                block1.setText(String.valueOf(randomGenerator.nextInt(range) + random + random2/2));
                block2.setText(String.valueOf(randomGenerator.nextInt(range) + random + random2/2));
                block0.setText(String.valueOf(randomGenerator.nextInt(range) + random + random2/2));
            }
            break;
        }
        TextView Score = (TextView)findViewById(R.id.score);
        Score.setText(score + "/"+ totalTries);
    }

    public void answer(View v) {

        String messageV = ("ERRRROU!");
        totalTries++;
        switch (v.getId()) {
            case R.id.block0:
            {
                int answer = Integer.parseInt(String.valueOf(block0.getText()));
                if(answer == result)
                {
                    score++;
                    messageV = ("boa");
                }
            }
            break;
            case R.id.block1:
            {
                int answer = Integer.parseInt(String.valueOf(block1.getText()));
                if(answer == result)
                {
                    score++;
                    messageV = ("boa");
                }
            }
            break;
            case R.id.block2:
            {
                int answer = Integer.parseInt(String.valueOf(block2.getText()));
                if(answer == result)
                {
                    score++;
                    messageV = ("boa");
                }
            }
            break;
            case R.id.block3:
            {
                int answer = Integer.parseInt(String.valueOf(block3.getText()));
                if(answer == result)
                {
                    score++;
                    messageV = ("boa");
                }
            }
            break;
        }
        TextView message  = (TextView)findViewById(R.id.message);
        message.setVisibility(View.VISIBLE);
        message.setText(messageV);
        click(v);
        displayAnswer();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        number = (TextView)findViewById(R.id.math);

        number.setVisibility(View.INVISIBLE);

        block0 = (TextView)findViewById(R.id.block0);
        block0.setVisibility(View.INVISIBLE);
        block1 = (TextView)findViewById(R.id.block1);
        block1.setVisibility(View.INVISIBLE);
        block2 = (TextView)findViewById(R.id.block2);
        block2.setVisibility(View.INVISIBLE);
        block3 = (TextView)findViewById(R.id.block3);
        block3.setVisibility(View.INVISIBLE);;

        score=0;
        totalTries=0;
    }
}
