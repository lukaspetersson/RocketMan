package com.example.rocketman;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.animation.ObjectAnimator;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.BounceInterpolator;

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

        builder.setView(v);
        builder.setTitle("edit parameters");
        builder.setPositiveButton("Launch", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                launch(1000f, 2000);
            }
        });
        builder.setNegativeButton("cancel", null);
        builder.show();
    }

    public void launch(float height, long time){
        View rocket = findViewById(R.id.rocket);

        ObjectAnimator upAnimation = ObjectAnimator.ofFloat(rocket, "translationY", -1*height);
        upAnimation.setDuration(time);
        upAnimation.setInterpolator(new AccelerateDecelerateInterpolator());
        upAnimation.start();

        ObjectAnimator downAnimation = ObjectAnimator.ofFloat(rocket, "translationY", 0f);
        downAnimation.setDuration(time);
        downAnimation.setStartDelay(time);
        downAnimation.setInterpolator(new AccelerateDecelerateInterpolator());
        downAnimation.start();
    }
}
