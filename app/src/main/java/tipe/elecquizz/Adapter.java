package tipe.elecquizz;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;

class Adapter extends BaseAdapter {
    //variable declaration
    private final Context mContext;
    private final int Lenght;
    private final int Level_type;

    //initialize variable
    Adapter(Context c, int lenght, int level_type) {
        mContext = c;
        Lenght = lenght;
        Level_type = level_type;
    }

    public int getCount() {
        return Lenght;
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    //set item
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //set layout
        RelativeLayout layout = new RelativeLayout(mContext);
        layout.setLayoutParams(new GridView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, android.view.ViewGroup.LayoutParams.MATCH_PARENT));
        layout.setGravity(RelativeLayout.CENTER_IN_PARENT);

        //set design of item from xml file
        LayoutInflater linf = LayoutInflater.from(mContext);
        @SuppressLint({"ViewHolder", "InflateParams"}) View view = linf.inflate(R.layout.button_view, null);

        //set button
        Button item = (Button) view.findViewById(R.id.item);
        item.setLayoutParams(new RelativeLayout.LayoutParams((int) mContext.getResources().getDimension(R.dimen.tailleitem), (int) mContext.getResources().getDimension(R.dimen.tailleitem)));
        item.setText(String.valueOf(position + 1));

        //get score of level of item
        SharedPreferences sharedPreferences = mContext.getSharedPreferences("ElecQuizz", Context.MODE_PRIVATE);
        int score = sharedPreferences.getInt(Level_type + ":" + position, 0);

        //get image view
        ImageView score1 = (ImageView) view.findViewById(R.id.score1);
        ImageView score2 = (ImageView) view.findViewById(R.id.score2);
        ImageView score3 = (ImageView) view.findViewById(R.id.score3);

        //set core
        if(score == 3){
            score3.setVisibility(View.VISIBLE);
        }
        if(score >= 2){
            score2.setVisibility(View.VISIBLE);
        }
        if(score >= 1){
            score1.setVisibility(View.VISIBLE);
        }

        //set fonts to view
        Typeface type = Typeface.createFromAsset(mContext.getAssets(), "fonts/Woolkarth-Bold Bold.ttf");
        item.setTypeface(type);

        //add view in the layout
        layout.addView(view);

        return layout;
    }
}
