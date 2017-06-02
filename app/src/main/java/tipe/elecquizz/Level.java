package tipe.elecquizz;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Level extends AppCompatActivity {

    //variable declaration
    private Button answer1;
    private Button answer2;
    private Button answer3;
    private Button answer4;
    private int level_type;
    private int level_id;
    private int badAnswer = 0;
    private int reverselist[] = new int[4];
    private MediaPlayer mp;
    private MediaPlayer mpgood;
    private MediaPlayer mpbad;
    public static String[][][] array =
            {
                    {//EASY
                            {"4V", "5V", "6V", "2V", "2"},//answer1, answer2, answer3, answer4, id of correct answer
                            {"3V", "5V", "7V", "1V", "1"},
                            {"4A", "5A", "2A", "1,2A", "1"},
                            {"0,75", "0,5V", "1,5V", "0,25V", "2"},
                            {"100", "75", "50", "40", "3"},
                            {"0,01A", "0,05A", "0,1V", "0.1A", "4"},
                            {"2*Z1+Z2", "Z1/(Z1+Z2)", "Z2/(Z1+Z2)", "2*Z2+Z1", "2"},
                            {"300", "500", "250", "400", "1"},
                            {"5V", "20V", "10V", "15V", "3"},
                            {"3V", "5V", "8V", "2V", "4"},
                            {"4,2V", "2V", "4V", "1V", "3"},
                            {"0.01A", "100mA", "20mA", "0.2A", "2"},
                            {"0.02A", "400mA", "250mA", "0.2A", "1"},
                            {"(E1+E2-E3)/(Rg1+Rg2+Rg3+R1+R2+R3)", "(E1-E2-E3)/(Rg1+Rg2+Rg3+R1+R2+R3)", "(E1+E2+E3)/(Rg1+Rg2+Rg3+R1+R2+R3)", "(E1+E2-E3)/(Rg1+Rg2+Rg3-R1-R2-R3)", "1"},
                            {"6A", "10A", "4A", "2A", "3"}
                            },
                            {//MEDIUM
                                    {"16.4", "14.6", "95", "380", "2"},//answer1, answer2, answer3, answer4, id of correct answer
                                    {"3V", "5V", "6.5V", "7.5V", "3"},
                                    {"A Completer", "A Completer", "A Completer", "A Completer", "2"},
                                    {"100", "68", "32", "14", "3"},
                                    {"2/13", "4/15", "5/12", "0,5", "1"},
                                    {"A Completer", "A Completer", "A Completer", "A Completer", "2"},
                                    {"2/350", "4/225", "2/450", "2/225", "3"},
                                    {"A Completer", "A Completer", "A Completer", "A Completer", "2"}
                            },
                            {//HARD
                                    {"du/dt+(1/R)*U=0", "du/dt+(1/RL)*U=0", "RL*(du/dt)+(1/L)*U=0", "du/dt+(R/L)*U=0", "4"},
                                    {"di/dt+(1/LC)*i=0", "di/dt+(L/C)*i=0", "di/dt+(1/C)*i=0", "di/dt+(1/LC²)*i=0", "1"},
                                    {"(du²/d²t)+(RC)*dU/dt+(L/C)*U=0", "(du²/d²t)+(R/C)*dU/dt+(L/C)*U=0", "(du²/d²t)+(1/RC)*dU/dt+(1/LC)*U=0", "(du²/d²t)+(1/RC)*dU/dt+(C/L)*U=0", "3"},
                                    {"(di²/d²t)+(RL)*di/dt+(C/L)*i=0", "(di²/d²t)+(RL)*di/dt+(1/LC)*i=0", "(di²/d²t)+(1/L)*di/dt+(1/LC)*i=0", "(di²/d²t)+(R/L)*di/dt+(1/LC)*i=0", "4"},
                            }
                    };



    //main method, start when the activity start
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //set window fullscreen and without actionbar
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //set in the windows the xml file
        setContentView(R.layout.activity_level);

        //set not to sleep screen
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        //initialize sound
        mp = Home.mp;
        mpgood = Home.mpgood;
        mpbad = Home.mpbad;

        //initialize variable
        ImageView back = (ImageView) findViewById(R.id.back);
        TextView level = (TextView) findViewById(R.id.level);
        ImageView image = (ImageView) findViewById(R.id.image);
        answer1 = (Button) findViewById(R.id.answer1);
        answer2 = (Button) findViewById(R.id.answer2);
        answer3 = (Button) findViewById(R.id.answer3);
        answer4 = (Button) findViewById(R.id.answer4);

        //get level type and id
        level_type = getIntent().getIntExtra("level_type", 0);
        level_id = getIntent().getIntExtra("level_id", 0);

        //set fonts to view
        Typeface type = Typeface.createFromAsset(getAssets(), "fonts/Woolkarth-Bold Bold.ttf");
        level.setTypeface(type);
        answer1.setTypeface(type);
        answer2.setTypeface(type);
        answer3.setTypeface(type);
        answer4.setTypeface(type);

        //set title with level type and id
        String nameImage = "";
        if (level_type == 0) {
            level.setText(String.valueOf("EASY_" + String.valueOf(level_id + 1)));
            nameImage = "easy_" + String.valueOf(level_id + 1);
        } else if (level_type == 1) {
            level.setText(String.valueOf("MEDIUM_" + String.valueOf(level_id + 1)));
            nameImage = "medium_" + String.valueOf(level_id + 1);
        } else if (level_type == 2) {
            level.setText(String.valueOf("HARD_" + String.valueOf(level_id + 1)));
            nameImage = "hard_" + String.valueOf(level_id + 1);
        }

        //set image with level type and id
        int resImage = getResources().getIdentifier(nameImage, "drawable", getPackageName());
        image.setImageResource(resImage);

        //shuffle answer
        List<Integer> list = new ArrayList<>();
        list.add(0);
        list.add(1);
        list.add(2);
        list.add(3);
        Collections.shuffle(list);

        //set answer button text
        answer1.setText(array[level_type][level_id][list.get(0)]);
        answer2.setText(array[level_type][level_id][list.get(1)]);
        answer3.setText(array[level_type][level_id][list.get(2)]);
        answer4.setText(array[level_type][level_id][list.get(3)]);

        //find reverse shuffle list
        for (int i = 0; i < reverselist.length; i++) {
            reverselist[list.get(i)] = i;
        }

        //action when you clicked on view
        back.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        onBackPressed();
                                    }
                                }
        );
        answer1.setOnClickListener(new View.OnClickListener() {
                                       @Override
                                       public void onClick(View view) {
                                           click(1);
                                       }
                                   }
        );
        answer2.setOnClickListener(new View.OnClickListener() {
                                       @Override
                                       public void onClick(View view) {
                                           click(2);
                                       }
                                   }
        );
        answer3.setOnClickListener(new View.OnClickListener() {
                                       @Override
                                       public void onClick(View view) {
                                           click(3);
                                       }
                                   }
        );
        answer4.setOnClickListener(new View.OnClickListener() {
                                       @Override
                                       public void onClick(View view) {
                                           click(4);
                                       }
                                   }
        );
    }

    //click method start when you click on a view
    private void click(int answer) {
        SharedPreferences sharedPreferences = getSharedPreferences("ElecQuizz", Context.MODE_PRIVATE);

        //if the answer is good
        if (answer - 1 == reverselist[Integer.parseInt(array[level_type][level_id][4]) - 1]) {
            //start sound effect when the click method start if sound checkbox is check
            if (sharedPreferences.getBoolean("sound", true)) {
                try {
                    if (mpgood.isPlaying()) {
                        mpgood.stop();
                        mpgood.release();
                        mpgood = MediaPlayer.create(Level.this, R.raw.soundgood);
                    }
                    mpgood.start();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            //collect score
            int score;
            if (badAnswer == 0) {
                score = 3;
            } else if (badAnswer == 1) {
                score = 2;
            } else if (badAnswer == 2) {
                score = 1;
            } else {
                score = 0;
            }

            //save score
            if (score > sharedPreferences.getInt(level_type + ":" + level_id, 0)) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putInt(level_type + ":" + level_id, score);
                editor.apply();
            }

            //set answer button background and text color
            if (answer == 1) {
                answer1.setBackgroundColor(ContextCompat.getColor(Level.this, R.color.colorGreen));
                answer1.setTextColor(ContextCompat.getColor(Level.this, R.color.colorAccent));
            } else if (answer == 2) {
                answer2.setBackgroundColor(ContextCompat.getColor(Level.this, R.color.colorGreen));
                answer2.setTextColor(ContextCompat.getColor(Level.this, R.color.colorAccent));
            } else if (answer == 3) {
                answer3.setBackgroundColor(ContextCompat.getColor(Level.this, R.color.colorGreen));
                answer3.setTextColor(ContextCompat.getColor(Level.this, R.color.colorAccent));
            } else {
                answer4.setBackgroundColor(ContextCompat.getColor(Level.this, R.color.colorGreen));
                answer4.setTextColor(ContextCompat.getColor(Level.this, R.color.colorAccent));
            }


            //if there is a next level
            if (level_id != array[level_type].length - 1) {
                //start level activity with animation
                Intent intent = new Intent(getApplicationContext(), Level.class);
                intent.putExtra("level_type", level_type);
                intent.putExtra("level_id", level_id + 1);
                startActivity(intent);
                this.overridePendingTransition(R.anim.slide_right_start, R.anim.slide_right_end);
            }//if there isn't a next level
            else {
                //start home activity with animation
                Intent intent2 = new Intent(getApplicationContext(), Home.class);
                intent2.putExtra("home", 1);
                startActivity(intent2);
                this.overridePendingTransition(R.anim.slide_left_start, R.anim.slide_left_end);
            }
        }//if the answer is bad
        else {
            //start sound effect when the click method start if sound checkbox is check
            if (sharedPreferences.getBoolean("sound", true)) {
                try {
                    if (mpbad.isPlaying()) {
                        mpbad.stop();
                        mpbad.release();
                        mpbad = MediaPlayer.create(Level.this, R.raw.soundbad);
                    }
                    mpbad.start();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            //set answer button background and text color
            badAnswer++;
            if (answer == 1) {
                answer1.setBackgroundColor(ContextCompat.getColor(Level.this, R.color.colorRed));
                answer1.setTextColor(ContextCompat.getColor(Level.this, R.color.colorAccent));
            } else if (answer == 2) {
                answer2.setBackgroundColor(ContextCompat.getColor(Level.this, R.color.colorRed));
                answer2.setTextColor(ContextCompat.getColor(Level.this, R.color.colorAccent));
            } else if (answer == 3) {
                answer3.setBackgroundColor(ContextCompat.getColor(Level.this, R.color.colorRed));
                answer3.setTextColor(ContextCompat.getColor(Level.this, R.color.colorAccent));
            } else {
                answer4.setBackgroundColor(ContextCompat.getColor(Level.this, R.color.colorRed));
                answer4.setTextColor(ContextCompat.getColor(Level.this, R.color.colorAccent));
            }
        }
    }

    //on back pressed method start when you click on android back
    @Override
    public void onBackPressed() {
        //start sound effect when the click method start if sound checkbox is check
        SharedPreferences sharedPreferences = getSharedPreferences("ElecQuizz", Context.MODE_PRIVATE);
        if (sharedPreferences.getBoolean("sound", true)) {
            try {
                if (mp.isPlaying()) {
                    mp.stop();
                    mp.release();
                    mp = MediaPlayer.create(Level.this, R.raw.sound);
                }
                mp.start();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        //start selection activity with animation
        Intent intent = new Intent(getApplicationContext(), Selection.class);
        intent.putExtra("level_type", level_type);
        startActivity(intent);
        this.overridePendingTransition(R.anim.slide_left_start, R.anim.slide_left_end);
    }
}
