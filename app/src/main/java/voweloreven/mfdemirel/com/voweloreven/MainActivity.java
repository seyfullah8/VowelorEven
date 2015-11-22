package voweloreven.mfdemirel.com.voweloreven;

import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

//// ilisu push yaptim
public class MainActivity extends AppCompatActivity {
    boolean ustOpen, altOpen, vowel, even, gameOver;
    int puan = 0;
    int count = 45;
    int combo = 0;
    TextView puanGoster, timerGoster;
    TextView ustTextView, altTextView;
    LinearLayout mainLayout;

    int category = 2; // oyun modu(harf-sayı=1 <> kelime-renk=2)
    String[] colorsString = {"RED", "GREEN", "BLUE", "YELLOW", "BLACK"};
    ArrayList<String> colorsString2 = new ArrayList<String>();
    int[] colorsColor = {Color.RED, Color.GREEN, Color.BLUE, Color.YELLOW, Color.BLACK};
    ArrayList<Integer> colorsColor2 = new ArrayList<Integer>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        for(int i = 0; i < 5; i++) {
            colorsColor2.add(colorsColor[i]);
            colorsString2.add(colorsString[i]);
        }

        ustTextView = (TextView) findViewById(R.id.ust);
        altTextView = (TextView) findViewById(R.id.alt);

        mainLayout = (LinearLayout) findViewById(R.id.main_layout);

        final Button yesButton = (Button) findViewById(R.id.yesButton);
        final Button noButton = (Button) findViewById(R.id.noButton);

        Typeface face = Typeface.createFromAsset(getAssets(), "fonts/goodtimes.ttf");
        ustTextView.setTypeface(face);
        altTextView.setTypeface(face);

        puanGoster = (TextView) findViewById(R.id.puanGoster);
        timerGoster = (TextView) findViewById(R.id.timer);

        puanGoster.setText(String.valueOf(puan));

        final Thread timer = setupTimer();
        timer.start();

        editTextViews();

        yesButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                if (!gameOver) {
                    if(category == 1) {
                        if (ustOpen && !altOpen) {
                            if (vowel) {
                                puan += 10;
                                combo++;

                            } else {
                                puan -= 10;
                                combo = 0;
                                count--;
                            }
                        } else if (altOpen && !ustOpen) {
                            if (even) {
                                puan += 10;
                                combo++;

                            } else {
                                puan -= 10;
                                count--;
                                combo = 0;
                            }
                        }
                        if (combo == 5) {
                            count += 5;
                            combo = 0;
                        }
                    } else if (category == 2){
                        ColorDrawable clrDrwble = (ColorDrawable) mainLayout.getBackground();
                        int bgColorIndex = colorsColor2.indexOf(clrDrwble.getColor());
                        if(ustOpen && !altOpen){
                            if(bgColorIndex == colorsString2.indexOf(ustTextView.getText())){
                                puan += 10;
                                combo++;
                            } else{
                                puan -= 10;
                                combo = 0;
                                count--;
                            }
                        } else if (altOpen && !ustOpen) {
                            if(bgColorIndex == colorsColor2.indexOf(altTextView.getCurrentTextColor())){
                                puan += 10;
                                combo++;
                            } else{
                                puan -= 10;
                                combo = 0;
                                count--;
                            }
                        }
                        if (combo == 5) {
                            count += 5;
                            combo = 0;
                        }
                    }
                    puanGoster.setText(String.valueOf(puan));
                    editTextViews();

                }
            }
        });

        noButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                if (!gameOver) {
                    if(category == 1) {
                        if (ustOpen && !altOpen) {
                            if (!vowel) {
                                puan += 10;
                                combo++;

                            } else {
                                puan -= 10;
                                combo = 0;
                                count--;
                            }
                        } else if (altOpen && !ustOpen) {
                            if (!even) {
                                puan += 10;
                                combo++;

                            } else {
                                puan -= 10;
                                combo = 0;
                                count--;
                            }
                        }

                        if (combo == 5) {
                            count += 5;
                        }
                    } else if (category == 2){
                        ColorDrawable clrDrwble = (ColorDrawable) mainLayout.getBackground();
                        int bgColorIndex = colorsColor2.indexOf(clrDrwble.getColor());
                        if(ustOpen && !altOpen){
                            if(colorsString2.indexOf(ustTextView.getText()) != bgColorIndex){
                                puan += 10;
                                combo++;
                            } else{
                                puan -= 10;
                                combo = 0;
                                count--;
                            }
                        } else if (altOpen && !ustOpen) {
                            if(colorsColor2.indexOf(altTextView.getCurrentTextColor()) != bgColorIndex){
                                puan += 10;
                                combo++;
                            } else{
                                puan -= 10;
                                combo = 0;
                                count--;
                            }
                        }
                        if (combo == 5) {
                            count += 5;
                            combo = 0;
                        }
                    }
                    puanGoster.setText(String.valueOf(puan));
                    editTextViews();
                }
                else{
                    ustTextView.setText("GAME");
                    altTextView.setText("OVER");
                }
            }
        });
    }

    private Thread setupTimer() {
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
        return t;
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

    public void editTextViews() {

        String ustButtonText = "";
        String altButtonText = "";

        Random rand = new Random();
        boolean topOrBottom = rand.nextBoolean();

        if (category == 1) {

            boolean randomPriority = rand.nextBoolean();

            if (topOrBottom) {

                ustOpen = true;
                altOpen = false;

                if (randomPriority) {
                    ustButtonText = RandomLetter() + String.valueOf(RandomNumber());
                } else {
                    ustButtonText = String.valueOf(RandomNumber()) + RandomLetter();
                }
                altButtonText = "";

            } else {

                ustOpen = false;
                altOpen = true;

                if (randomPriority) {
                    altButtonText = RandomLetter() + String.valueOf(RandomNumber());
                } else {
                    altButtonText = String.valueOf(RandomNumber()) + RandomLetter();
                }
                ustButtonText = "";

            }

        } else if (category == 2) {

            int clrStr = rand.nextInt(5);
            int clrClr = rand.nextInt(5);
            int bckgrnd = rand.nextInt(5);

            ustTextView.setBackgroundColor(Color.WHITE);
            altTextView.setBackgroundColor(Color.WHITE);

            if (topOrBottom) {

                ustOpen = true;
                altOpen = false;

                ustButtonText = colorsString[clrStr];
                ustTextView.setTextColor(colorsColor[clrClr]);
                altButtonText = "";

            } else {

                ustOpen = false;
                altOpen = true;

                ustButtonText = "";
                altButtonText = colorsString[clrStr];
                altTextView.setTextColor(colorsColor[clrClr]);

            }

            mainLayout.setBackgroundColor(colorsColor[bckgrnd]);
        }

        ustTextView.setText(ustButtonText);
        altTextView.setText(altButtonText);
    }
}

