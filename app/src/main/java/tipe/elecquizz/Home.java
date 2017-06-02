package tipe.elecquizz;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Objects;

public class Home extends AppCompatActivity {

    //variable declaration
    private ImageView back;
    private ImageView title;
    private Button play;
    private TextView settings;
    private TextView credit;
    private RelativeLayout easy;
    private RelativeLayout medium;
    private RelativeLayout hard;
    private CheckBox sound;
    private TextView credit_text;
    public static MediaPlayer mp;
    public static MediaPlayer mpgood;
    public static MediaPlayer mpbad;

    //main method, start when the activity start
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //set window fullscreen and without actionbar
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //set in the windows the xml file
        setContentView(R.layout.activity_home);

        //set not to sleep screen
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        //initialize sound
        mp = MediaPlayer.create(this, R.raw.sound);
        mpbad = MediaPlayer.create(this, R.raw.soundbad);
        mpgood = MediaPlayer.create(this, R.raw.soundgood);

        //initialize variable
        back = (ImageView) findViewById(R.id.back);
        title = (ImageView) findViewById(R.id.title);
        play = (Button) findViewById(R.id.play);
        settings = (TextView) findViewById(R.id.settings);
        credit = (TextView) findViewById(R.id.credit);
        easy = (RelativeLayout) findViewById(R.id.easy);
        medium = (RelativeLayout) findViewById(R.id.medium);
        hard = (RelativeLayout) findViewById(R.id.hard);
        sound = (CheckBox) findViewById(R.id.sound);
        credit_text = (TextView) findViewById(R.id.credit_text);

        Button easy_button = (Button) findViewById(R.id.easy_button);
        TextView easy_score1 = (TextView) findViewById(R.id.easy_score1);
        TextView easy_score3 = (TextView) findViewById(R.id.easy_score3);

        Button medium_button = (Button) findViewById(R.id.medium_button);
        ImageView medium_score0 = (ImageView) findViewById(R.id.medium_score0);
        TextView medium_score1 = (TextView) findViewById(R.id.medium_score1);
        TextView medium_score3 = (TextView) findViewById(R.id.medium_score3);

        Button hard_button = (Button) findViewById(R.id.hard_button);
        ImageView hard_score0 = (ImageView) findViewById(R.id.hard_score0);
        TextView hard_score1 = (TextView) findViewById(R.id.hard_score1);
        TextView hard_score3 = (TextView) findViewById(R.id.hard_score3);

        //set fonts to view
        Typeface type = Typeface.createFromAsset(getAssets(), "fonts/Woolkarth-Bold Bold.ttf");
        play.setTypeface(type);
        settings.setTypeface(type);
        credit.setTypeface(type);
        easy_button.setTypeface(type);
        easy_score1.setTypeface(type);
        easy_score3.setTypeface(type);
        medium_button.setTypeface(type);
        medium_score1.setTypeface(type);
        medium_score3.setTypeface(type);
        hard_button.setTypeface(type);
        hard_score1.setTypeface(type);
        hard_score3.setTypeface(type);
        sound.setTypeface(type);
        credit_text.setTypeface(type);

        //get the score from cache
        final SharedPreferences sharedPreferences = getSharedPreferences("ElecQuizz", Context.MODE_PRIVATE);
        int score[] = new int[3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < Level.array[i].length; j++) {
                score[i] = score[i] + sharedPreferences.getInt(i + ":" + j, 0);
            }
        }

        //launch click methode with id of witch one just click, on click
        play.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        click("play");
                                    }
                                }
        );
        settings.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            click("settings");
                                        }
                                    }
        );
        credit.setOnClickListener(new View.OnClickListener() {
                                      @Override
                                      public void onClick(View view) {
                                          click("credit");
                                      }
                                  }
        );
        back.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        click("back");
                                    }
                                }
        );
        easy_button.setOnClickListener(new View.OnClickListener() {
                                           @Override
                                           public void onClick(View view) {
                                               click("easy");
                                           }
                                       }
        );
        medium_button.setOnClickListener(new View.OnClickListener() {
                                             @Override
                                             public void onClick(View view) {
                                                 click("medium");
                                             }
                                         }
        );
        hard_button.setOnClickListener(new View.OnClickListener() {
                                           @Override
                                           public void onClick(View view) {
                                               click("hard");
                                           }
                                       }
        );

        //set check the sound checkbox
        sound.setChecked(sharedPreferences.getBoolean("sound", true));

        //save preference about sound
        sound.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
                if (isChecked) {
                    SharedPreferences sharedPreferences = getSharedPreferences("ElecQuizz", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean("sound", true);
                    editor.apply();
                } else {
                    SharedPreferences sharedPreferences = getSharedPreferences("ElecQuizz", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean("sound", false);
                    editor.apply();
                }
            }
        });

        //display or hide view when you're on home screen
        if (getIntent().getIntExtra("home", 0) == 1) {
            play.setVisibility(View.GONE);
            settings.setVisibility(View.GONE);
            credit.setVisibility(View.GONE);
            title.setVisibility(View.GONE);
            back.setVisibility(View.VISIBLE);
            easy.setVisibility(View.VISIBLE);
            medium.setVisibility(View.VISIBLE);
            hard.setVisibility(View.VISIBLE);
        }

        //show score on levels buttons
        easy_score1.setText(String.valueOf(score[0]));
        easy_score3.setText(String.valueOf("/" + Level.array[0].length * 3));
        if (score[0] >= Math.round((Level.array[0].length * 3) * 0.5)) {
            medium_score1.setText(String.valueOf(score[1]));
            medium_score3.setText(String.valueOf("/" + Level.array[1].length * 3));
        } else {
            medium_score0.setVisibility(View.VISIBLE);
            medium_button.setOnClickListener(null);
            medium_score1.setText(String.valueOf((int) (Math.round(Level.array[0].length * 3) * 0.5)));
        }
        if (score[0] + score[1] >= Math.round((Level.array[0].length * 3 + Level.array[1].length * 3) * 0.5)) {
            hard_score1.setText(String.valueOf(score[2]));
            hard_score3.setText(String.valueOf("/" + Level.array[2].length * 3));
        } else {
            hard_score0.setVisibility(View.VISIBLE);
            hard_button.setOnClickListener(null);
            hard_score1.setText(String.valueOf((int) (Math.round((Level.array[0].length * 3 + Level.array[1].length * 3) * 0.5))));

        }
    }

    //click method start when you click on a view
    private void click(String whichOne) {
        //start sound effect when the click method start if sound checkbox is check
        SharedPreferences sharedPreferences = getSharedPreferences("ElecQuizz", Context.MODE_PRIVATE);
        if (sharedPreferences.getBoolean("sound", true)) {
            try {
                if (mp.isPlaying()) {
                    mp.stop();
                    mp.release();
                    mp = MediaPlayer.create(Home.this, R.raw.sound);
                }
                mp.start();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        //get windows width and height
        Point size = new Point();
        Home.this.getWindowManager().getDefaultDisplay().getSize(size);
        int width = size.x;
        int height = size.y;

        //if you clicked on play or settings or credit
        if (Objects.equals(whichOne, "play") || Objects.equals(whichOne, "settings") || Objects.equals(whichOne, "credit")) {
            //hide some views
            play.setVisibility(View.GONE);
            settings.setVisibility(View.GONE);
            credit.setVisibility(View.GONE);
            back.setVisibility(View.VISIBLE);

            //if you clicked on play
            if (Objects.equals(whichOne, "play")) {
                title.setVisibility(View.GONE);

                //display easy button with animation
                easy.setVisibility(View.VISIBLE);
                ObjectAnimator animeasy = ObjectAnimator.ofFloat(easy, View.TRANSLATION_X, width, 0);
                animeasy.setDuration(200);
                animeasy.start();

                //display medium button with animation
                medium.setVisibility(View.VISIBLE);
                ObjectAnimator animmedium = ObjectAnimator.ofFloat(medium, View.TRANSLATION_X, width, 0);
                animmedium.setDuration(300);
                animmedium.start();

                //display hard button with animation
                hard.setVisibility(View.VISIBLE);
                ObjectAnimator animhard = ObjectAnimator.ofFloat(hard, View.TRANSLATION_X, width, 0);
                animhard.setDuration(400);
                animhard.start();

            }//else if you clicked on settings
            else if (Objects.equals(whichOne, "settings")) {
                title.setVisibility(View.GONE);

                //display sound checkbox with animation
                sound.setVisibility(View.VISIBLE);
                ObjectAnimator animsound = ObjectAnimator.ofFloat(sound, View.TRANSLATION_X, -1 * width, 0);
                animsound.setDuration(200);
                animsound.start();

            }//else if you clicked on credit
            else if (Objects.equals(whichOne, "credit")) {
                //display sound checkbox with animation
                credit_text.setVisibility(View.VISIBLE);
                ObjectAnimator animcredit = ObjectAnimator.ofFloat(credit_text, View.TRANSLATION_Y, height, 0);
                animcredit.setDuration(200);
                animcredit.start();
            }
        }//else if you clicked on easy or medium or hard
        else if (Objects.equals(whichOne, "easy") || Objects.equals(whichOne, "medium") || Objects.equals(whichOne, "hard")) {
            //start selection activity
            Intent intent = new Intent(getApplicationContext(), Selection.class);
            //if you clicked on easy
            if (Objects.equals(whichOne, "easy")) {
                //say to selection activity that you clicked on easy
                intent.putExtra("level_type", 0);
            }//else if you clicked on medium
            else if (Objects.equals(whichOne, "medium")) {
                //say to selection activity that you clicked on medium
                intent.putExtra("level_type", 1);
            }//else if you clicked on hard
            else {
                //say to selection activity that you clicked on hard
                intent.putExtra("level_type", 2);
            }
            //start activity with animation
            startActivity(intent);
            this.overridePendingTransition(R.anim.slide_right_start, R.anim.slide_right_end);

        }//else if you clicked on back
        else if (Objects.equals(whichOne, "pressedback") || Objects.equals(whichOne, "back")) {
            //if back button is visible
            if (back.getVisibility() == View.VISIBLE) {
                //hide back button
                back.setVisibility(View.INVISIBLE);

                //set animation for easy button
                ObjectAnimator animeasy = ObjectAnimator.ofFloat(easy, View.TRANSLATION_X, 0, width);
                animeasy.setDuration(400);
                //hide or show view when animation easy is finish
                animeasy.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        easy.setVisibility(View.GONE);
                        medium.setVisibility(View.GONE);
                        hard.setVisibility(View.GONE);
                        play.setVisibility(View.VISIBLE);
                        settings.setVisibility(View.VISIBLE);
                        credit.setVisibility(View.VISIBLE);
                        title.setVisibility(View.VISIBLE);
                        sound.setVisibility(View.GONE);
                        credit_text.setVisibility(View.GONE);
                    }
                });
                animeasy.start();

                //set animation for medium button
                ObjectAnimator animmedium = ObjectAnimator.ofFloat(medium, View.TRANSLATION_X, 0, width);
                animmedium.setDuration(300);
                animmedium.start();

                //set animation for hard button
                ObjectAnimator animhard = ObjectAnimator.ofFloat(hard, View.TRANSLATION_X, 0, width);
                animhard.setDuration(200);
                animhard.start();

                //set animation for sound button
                ObjectAnimator animsound = ObjectAnimator.ofFloat(sound, View.TRANSLATION_X, 0, -1 * width);
                animsound.setDuration(200);
                animsound.start();

                //set animation for credit button
                ObjectAnimator animcredit = ObjectAnimator.ofFloat(credit_text, View.TRANSLATION_Y, 0, height);
                animcredit.setDuration(200);
                animcredit.start();
            } else {
                this.finishAffinity();
            }
        }
    }

    //on back pressed method start when you click on android back
    @Override
    public void onBackPressed() {
        click("pressedback");
    }
}