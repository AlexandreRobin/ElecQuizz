package tipe.elecquizz;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

public class Selection extends AppCompatActivity {

    //variable declaration
    private MediaPlayer mp;

    //main method, start when the activity start
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //set window fullscreen and without actionbar
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //set in the windows the xml file
        setContentView(R.layout.activity_selection);

        //set not to sleep screen
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        //initialize sound
        mp = Home.mp;

        //initialize variable
        ImageView back = (ImageView) findViewById(R.id.back);
        TextView selection = (TextView) findViewById(R.id.selection);

        //set fonts to view
        Typeface type = Typeface.createFromAsset(getAssets(), "fonts/Woolkarth-Bold Bold.ttf");
        selection.setTypeface(type);

        //set title with level type
        if (getIntent().getIntExtra("level_type", 0) == 0) {
            selection.setText(R.string.EASY);
        } else if (getIntent().getIntExtra("level_type", 0) == 1) {
            selection.setText(R.string.MEDIUM);
        } else if (getIntent().getIntExtra("level_type", 0) == 2) {
            selection.setText(R.string.HARD);
        }

        //get number of level in the actual difficulty
        int lenght = Level.array[getIntent().getIntExtra("level_type", 0)].length;

        //on back button press
        back.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        onBackPressed();
                                    }
                                }
        );

        //set grid layout that display level choice button
        GridView gridview = (GridView) findViewById(R.id.listlevel);
        gridview.setAdapter(new Adapter(this, lenght, getIntent().getIntExtra("level_type", 0)));
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                click(position, getIntent().getIntExtra("level_type", 0));
            }
        });
    }

    //click method start when you click on the level choice button
    private void click(int position, int level_type) {
        //start sound effect when the click method start if sound checkbox is check
        SharedPreferences sharedPreferences = getSharedPreferences("ElecQuizz", Context.MODE_PRIVATE);
        if(sharedPreferences.getBoolean("sound", true)) {
            try {
                if (mp.isPlaying()) {
                    mp.stop();
                    mp.release();
                    mp = MediaPlayer.create(Selection.this, R.raw.sound);
                } mp.start();
            } catch(Exception e) { e.printStackTrace(); }
        }

        //start level activity with animation and say to level activity on which button you clicked
        Intent intent = new Intent(getApplicationContext(), Level.class);
        intent.putExtra("level_type", level_type);
        intent.putExtra("level_id", position);
        startActivity(intent);
        this.overridePendingTransition(R.anim.slide_right_start, R.anim.slide_right_end);
    }

    //on back pressed method start when you click on android back
    @Override
    public void onBackPressed() {
        //start sound effect when the click method start if sound checkbox is check
        SharedPreferences sharedPreferences = getSharedPreferences("ElecQuizz", Context.MODE_PRIVATE);
        if(sharedPreferences.getBoolean("sound", true)) {
            try {
                if (mp.isPlaying()) {
                    mp.stop();
                    mp.release();
                    mp = MediaPlayer.create(Selection.this, R.raw.sound);
                } mp.start();
            } catch(Exception e) { e.printStackTrace(); }
        }

        //start home activity with animation
        Intent intent = new Intent(getApplicationContext(), Home.class);
        intent.putExtra("home", 1);
        startActivity(intent);
        this.overridePendingTransition(R.anim.slide_left_start, R.anim.slide_left_end);
    }
}
