package com.samuelmaynard.mkbreathalyzer;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.media.MediaPlayer;
import android.media.Rating;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageButton;
import android.widget.PopupWindow;
import android.widget.RatingBar;
import android.widget.Toast;
import android.widget.ToggleButton;

public class Main extends AppCompatActivity {

    MediaPlayer daylight;
    boolean playing;

    String[] bacs = {
            "have you even had a drink?",
            "you're good for another couple shots",
            "you might want to start holding up there cowboy",
            "put the baileys down",
            "dude I've been patting your head for an hour go to bed"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        daylight = MediaPlayer.create(getApplicationContext(), R.raw.daylight);
        playing = false;

        RatingBar ratingBar = (RatingBar) findViewById(R.id.ratingBar);
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                Log.i("Main", "rating: " + rating);
                if ((int)rating != 0) {
                    String bac = bacs[(int) rating - 1];
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Main.this);
                    alertDialogBuilder.setMessage(bac)
                            .setCancelable(true)
                            .setPositiveButton("back", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            });

                    AlertDialog alert = alertDialogBuilder.create();
                    alert.show();
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        return true;
    }

    public void playpause(View view) {
        playing = !playing;
        if (playing) {
            daylight.start(); // no need to call prepare(); create() does that for you
            ((ImageButton)view).setImageResource(R.drawable.pause);
        } else {
            daylight.pause();
            ((ImageButton)view).setImageResource(R.drawable.play);
        }
    }

    public void goagain(View view) {
        RatingBar ratingBar =  (RatingBar) findViewById(R.id.ratingBar);
        ratingBar.setRating(0);
    }
}
