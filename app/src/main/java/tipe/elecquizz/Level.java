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
    private SharedPreferences sharedPreferences;
    private int goodanswerid;
    private MediaPlayer mp;
    private MediaPlayer mpgood;
    private MediaPlayer mpbad;
    public final static String[][][] array = {
            {//EASY
                    {"I1=I2+I3-I4", "I1=I2+I3+I4", "I1=I2-I3+I4", "I1=-I2-I3+I4"},//good answer, bad answer, bad answer, bad answer
                    {"E=U1-U2+U3", "E=-U1-U2+U3", "E=U1-U2-U3", "E=-U1+U2-U3"},
                    {"4A", "5A", "3A", "6A"},
                    {"U=R1*I", "U=R1+I", "U=I/R1", "U=R1/I"},
                    {"I=E/R", "I=E*R", "I=R/E", "I=E-R"},
                    {"5V", "4V", "10V", "-5V"},
                    {"R1+R2", "R1*R2", "R1/R2", "R1-R2"},
                    {"R1*R2/(R1+R2)", "(R1-R2)/(R1+R2)", "(R1+R2)/(R1*R2)", "R1*R2/(R1-R2)"},
                    {"300Ω", "100Ω", "150Ω", "200Ω"},
                    {"50Ω", "200Ω", "25Ω", "100Ω"},
                    {"I=E/(R1+R2+R3)", "I=(R1+R2+R3)/E", "I=E/(R1-R2-R3)", "I=E*(R1+R2+R3)"},
                    {"I2=I*(1/R2)/((1/R1)+(1/R2))", "I2=I*(1/R2)/((1/R1)*(1/R2))", "I2=I*(1/R2)*((1/R1)+(1/R2))", "I2=I*(R2)/((R1)+(R2))"},
                    {"U2=E*R2/(R1+R2)", "U2=(R1+R2)/(E*R2)", "U2=E*R2*(R1+R2)", "U2=E*R2/(R1-R2)"},
                    {"1V", "3V", "2V", "6V"},
                    {"0.5A", "0.25A", "0.75A", "0.05A"},
                    {"I=E/R1", "I=E*R1", "I=R1/E", "I=(E+R1)/R1"},
                    {"E=R1*I", "E=R1/I", "E=I/R1", "E=R1+I"},
                    {"2.3V", "1.75V", "1.6V", "1.9V"},
                    {"10V", "15V", "5V", "-10V"},
                    {"(E1+E2-E3)/(Rg1+Rg2+Rg3+R1+R2+R3)", "(-E1-E2+E3)/(Rg1+Rg2+Rg3+R1+R2+R3)", "(E1+E2+E3)/(Rg1+Rg2+Rg3+R1+R2+R3)", "(E1+E2-E3)*(Rg1+Rg2+Rg3-R1-R2-R3)"},},
            {//MEDIUM
                    {"0.5A", "0.01A", "0.05A", "0.1A"},
                    {"Z=R+jωL+(1/jωC)", "Z=R+jωL-(1/jωC)", "Z=R+jωC+(1/jωL)", "Z=R+(1/jωL)+(1/jωC)"},
                    {"Z1/(Z1+Z2)", "Z1*(Z1+Z2)", "Z1/(Z1-Z2)", "(Z1+Z2)/Z1"},
                    {"R/(jωC+1)", "(jωC+1)/R", "R/(JωC-1)", "R*(JωC+1)"},
                    {"14.6Ω", "16.4Ω", "95Ω", "380Ω"},
                    {"6.5V", "3V", "5V", "7.5V"},
                    {"2.2V", "1.5V", "3V", "2.6V"},
                    {"10Ω", "12Ω", "8Ω", "6Ω"},
                    {"0.015A", "0.025A", "0.05A", "0.15A"},
                    {"P= U*I or R*I²", "P= U*I or R²*I", "P= U/I or R/I²", "P= U/I or R*I²"},
                    {"0.01W", "1W", "0.1W", "0.5W"},
                    {"jωRC/(1+jωRC)", "jωRC/(1-jωRC)", "(1+jωRC)/jωRC", "jωRC*(1+jωRC)"},
                    {"jω(L/R)/(R+jωL)", "jω(L/R)/(1+jωL)", "jωLR/(R+jWL)", "(R+jωL)/jω(L/R)"},
                    {"1/RC", "R/C", "C/R", "RC"},
                    {"R/L", "L/R", "RL", "1/RL"},},
            {//HARD
                    {"2/450A", "2/350A", "4/225A", "2/225A"},
                    {"7.1V", "8.2V", "5V", "6.4V"},
                    {"136Ω", "137Ω", "120Ω", "140Ω"},
                    {"I=U/(1-ω²LC)", "I=U/(1+ω²LC)", "I=U/(1-ωLC)", "I=U/(1-ωL²C²)"},
                    {"H=1/(1+jQ((ω/ω0)-(ω0/ω)))", "H=1/(1-jQ((ω/ω0)-(ω0/ω)))", "H=1/(1+jQ((ω/ω0)+(ω0/ω)))", "H=1/(1+jQ((ω0/ω)-(ω0/ω)))"},
                    {"(dU/dt)+U/RC=E/RC", "(dU/dt)+UR/C=E/RC", "(dU/dt)+U*RC=E/RC", "(dU/dt)+UC/R=E"},
                    {"RL*(dI/dt)+(1/L)*I=E/L", "dI/dt+(1/R)*I=E/L", "dI/dt+(1/RL)*I=E/L", "dI/dt+(R/L)*I=E/L"},
                    {"dU/dt+(1/RC)*U=I/C", "dU/dt+(1/C)*U=I/C", "dI/dt+(1/RC)*I=I", "dI/dt+(R/C²)*I=I/C"},
                    {"(dU²/d²t)+(1/RC)*dU/dt+(1/LC)*U=I/C", "(dU²/d²t)+(RC)*dU/dt+(L/C)*U=I/C", "(dU²/d²t)+(R/C)*dU/dt+(L/C)*U=I/C", "(dU²/d²t)+(1/RC)*dU/dt+(C/L)*U=I/C"},
                    {"(dI²/d²t)+(R/L)*dI/dt+(1/LC)*I=0", "(dI²/d²t)+(RL)*dI/dt+(C/L)*I=0", "(dI²/d²t)+(RL)*dI/dt+(1/LC)*I=0", "(dI²/d²t)+(1/L)*dI/dt+(1/LC)*I=0"},}
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
        sharedPreferences = getSharedPreferences("ElecQuizz", Context.MODE_PRIVATE);

        //get level type and id
        level_type = getIntent().getIntExtra("level_type", 0);
        level_id = getIntent().getIntExtra("level_id", 0);

        //set fonts to view
        Typeface type = Typeface.createFromAsset(getAssets(), "fonts/Woolkarth-Bold Bold.ttf");
        level.setTypeface(type);/*
        answer1.setTypeface(type);
        answer2.setTypeface(type);
        answer3.setTypeface(type);
        answer4.setTypeface(type);
*/
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

        //shuffle answers
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

        //find good answer
        for (int i = 0; i < 4; i++) {
            if (list.get(i) == 0) {
                goodanswerid = i;
            }
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
                                           click(0);
                                       }
                                   }
        );
        answer2.setOnClickListener(new View.OnClickListener() {
                                       @Override
                                       public void onClick(View view) {
                                           click(1);
                                       }
                                   }
        );
        answer3.setOnClickListener(new View.OnClickListener() {
                                       @Override
                                       public void onClick(View view) {
                                           click(2);
                                       }
                                   }
        );
        answer4.setOnClickListener(new View.OnClickListener() {
                                       @Override
                                       public void onClick(View view) {
                                           click(3);
                                       }
                                   }
        );
    }

    //click method start when you click on a view
    private void click(int answer) {
        //if the answer is good
        if (answer == goodanswerid) {
            //start sound effect when the click method start if sound checkbox is check
            if (sharedPreferences.getBoolean("sound", true)) {
                mpgood.start();
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
            if (answer == 0) {
                answer1.setBackgroundColor(ContextCompat.getColor(Level.this, R.color.colorGreen));
                answer1.setTextColor(ContextCompat.getColor(Level.this, R.color.colorAccent));
            } else if (answer == 1) {
                answer2.setBackgroundColor(ContextCompat.getColor(Level.this, R.color.colorGreen));
                answer2.setTextColor(ContextCompat.getColor(Level.this, R.color.colorAccent));
            } else if (answer == 2) {
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
                mpbad.start();
            }

            //set answer button background and text color
            badAnswer++;
            if (answer == 0) {
                answer1.setBackgroundColor(ContextCompat.getColor(Level.this, R.color.colorRed));
                answer1.setTextColor(ContextCompat.getColor(Level.this, R.color.colorAccent));
            } else if (answer == 1) {
                answer2.setBackgroundColor(ContextCompat.getColor(Level.this, R.color.colorRed));
                answer2.setTextColor(ContextCompat.getColor(Level.this, R.color.colorAccent));
            } else if (answer == 2) {
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
        if (sharedPreferences.getBoolean("sound", true)) {
            mp.start();
        }

        //start selection activity with animation
        Intent intent = new Intent(getApplicationContext(), Selection.class);
        intent.putExtra("level_type", level_type);
        startActivity(intent);
        this.overridePendingTransition(R.anim.slide_left_start, R.anim.slide_left_end);
    }
}
