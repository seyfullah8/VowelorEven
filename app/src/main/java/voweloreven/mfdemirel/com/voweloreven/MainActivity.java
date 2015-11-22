package voweloreven.mfdemirel.com.voweloreven;

import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.*;

import java.util.Random;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

public class MainActivity extends AppCompatActivity {
    boolean ustOpen, altOpen, vowel, even, gameOver;
    int puan = 0;
    int count = 45;
    int combo = 0;
    TextView puanGoster;
    TextView timerGoster;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        final Button ustButton = (Button) findViewById(R.id.ust);
        final Button altButton = (Button) findViewById(R.id.alt);
        final Button yesButton = (Button) findViewById(R.id.yesButton);
        final Button noButton = (Button) findViewById(R.id.noButton);
        puanGoster = (TextView) findViewById(R.id.puanGoster);
        timerGoster = (TextView) findViewById(R.id.timer);
        puanGoster.setText(String.valueOf(puan));

        Thread t = new Thread() {

            @Override
            public void run() {
                try {
                    while (!isInterrupted()) {
                        Thread.sleep(1000);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (count > 0) {
                                    count--;
                                    timerGoster.setText(String.valueOf(count));
                                } else
                                    gameOver = true;
                            }
                        });
                    }
                } catch (InterruptedException e) {
                }
            }
        };

        t.start();


        editButtons(ustButton, altButton);

        yesButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                if (!gameOver) {
                    if (ustOpen) {
                        if (vowel) {
                            puan += 10;
                            combo++;

                        } else {
                            puan -= 10;
                            combo = 0;
                        }
                    }
                    if (altOpen) {
                        if (even) {
                            puan += 10;
                            combo++;

                        } else {
                            puan -= 10;
                            combo = 0;
                        }
                    }
                    if (combo == 5)
                        count += 5;
                    puanGoster.setText(String.valueOf(puan));
                    editButtons(ustButton, altButton);
                }
            }
        });

        noButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                if (!gameOver) {
                    if (ustOpen) {
                        if (!vowel) {
                            puan += 10;
                            combo++;

                        } else {
                            puan -= 10;
                            combo = 0;
                        }
                    }
                    if (altOpen) {
                        if (!even) {
                            puan += 10;
                            combo++;

                        } else {
                            puan -= 10;
                            combo = 0;
                        }
                    }
                    if (combo == 5)
                        count += 5;
                    puanGoster.setText(String.valueOf(puan));
                    editButtons(ustButton, altButton);
                }
            }
        });


    }

    public String RandomLetter() {
        String[] letters = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};
        Random r = new Random();
        String returnvalue = letters[r.nextInt(26)];
        if (returnvalue == "A" || returnvalue == "E" || returnvalue == "I" || returnvalue == "O" || returnvalue == "U") {
            vowel = true;
        } else {
            vowel = false;
        }
        return returnvalue;
    }


    public int RandomNumber() {
        int[] numbers = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
        Random r = new Random();
        int returnvalue = numbers[r.nextInt(5)];
        if (returnvalue == 2 || returnvalue == 4 || returnvalue == 6 || returnvalue == 8 || returnvalue == 0) {
            even = true;

        } else {
            even = false;

        }
        return returnvalue;
    }

    public void editButtons(Button ustButton, Button altButton) {
        String ustButtonText = "";
        String altButtonText = "";
        Random r = new Random();
        int randomgame = r.nextInt(2);
        if (randomgame == 0) {
            ustOpen = true;
            altOpen = false;

            Random t = new Random();
            int randomButton = r.nextInt(2);
            if (randomButton == 0) {
                ustButtonText = RandomLetter() + String.valueOf(RandomNumber());
            } else
                ustButtonText = String.valueOf(RandomNumber()) + RandomLetter();
            altButtonText = "";

        } else {
            ustOpen = false;
            altOpen = true;

            Random t = new Random();
            int randomButton = r.nextInt(2);
            if (randomButton == 0) {
                altButtonText = RandomLetter() + String.valueOf(RandomNumber());
            } else
                altButtonText = String.valueOf(RandomNumber()) + RandomLetter();
            ustButtonText = "";

        }

        ustButton.setText(ustButtonText);
        altButton.setText(altButtonText);
    }
}

