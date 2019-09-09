package com.example.rocketman;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.DialogInterface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.BounceInterpolator;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

public class MainActivity extends AppCompatActivity {

    String nameSound = "Max";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void openSettings(View view) {

        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        LayoutInflater inflater = this.getLayoutInflater();
        final View v = inflater.inflate(R.layout.settings, null);

        //TODO: water
        builder.setView(v);
        builder.setTitle("edit parameters");
        builder.setPositiveButton("Launch", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

                EditText parameter1 = v.findViewById(R.id.parameter1);
                if (!parameter1.getText().toString().matches("")) {
                    float parameterOne = Integer.parseInt(parameter1.getText().toString());
                    launch(parameterOne, 2000);
                }else{
                    launch(1000, 2000);
                }
            }
        });
        builder.setNegativeButton("cancel", null);
        builder.show();
    }


        public void launch(final float height, final long time){
        final ImageView rocket = findViewById(R.id.rocket);
        final ImageView splash = findViewById(R.id.splash);
        final Spinner soundEffect = findViewById(R.id.soundEffect);

        final MediaPlayer mp = MediaPlayer.create(this, R.raw.sound);
        mp.start();

        String[] items = new String[]{"Max", "Gustav", "Aron", "Lukas", "Jacob"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        soundEffect.setAdapter(adapter);

        soundEffect.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {

                nameSound = position + "";
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        splash.setVisibility(View.VISIBLE);

        final Context context = this;

        rocket.animate()
                .translationY(-height)
                .setDuration(time)
                .setInterpolator(new AccelerateDecelerateInterpolator())
                .withEndAction(new Runnable() {
                    @Override
                    public void run() {
                        splash.setVisibility(View.INVISIBLE);
                        mp.stop();
                        rocket.animate()
                                .translationY(0)
                                .setDuration(time)
                                .setInterpolator(new AccelerateDecelerateInterpolator())
                                .withEndAction(new Runnable() {
                                    @Override
                                    public void run() {
                                        new AlertDialog.Builder(context)
                                                .setTitle("Height: "+height)
                                                .setNegativeButton("ok", null)
                                                .show();
                                    }
                                })
                                .start();
                    }
                })
                .start();

       /* ObjectAnimator upAnimation = ObjectAnimator.ofFloat(rocket, "translationY", -1*height);
        upAnimation.setDuration(time);
        upAnimation.setInterpolator(new AccelerateDecelerateInterpolator());
        upAnimation.start();

        ObjectAnimator downAnimation = ObjectAnimator.ofFloat(rocket, "translationY", 0f);
        downAnimation.setDuration(time);
        downAnimation.setStartDelay(time);
        downAnimation.setInterpolator(new AccelerateDecelerateInterpolator());
        downAnimation.start();*/
    }
}
