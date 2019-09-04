package com.example.rocketman;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.BounceInterpolator;
import android.widget.EditText;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

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

        final Context context = this;

        rocket.animate()
                .translationY(-height)
                .setDuration(time)
                .setInterpolator(new AccelerateDecelerateInterpolator())
                .withEndAction(new Runnable() {
                    @Override
                    public void run() {
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
