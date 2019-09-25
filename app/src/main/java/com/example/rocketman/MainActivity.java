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

    Spinner soundEffect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void openSettings(View view) {

        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        LayoutInflater inflater = this.getLayoutInflater();
        final View v = inflater.inflate(R.layout.settings, null);

        soundEffect = v.findViewById(R.id.soundEffect);
        String[] items = new String[]{"Max", "Gustav", "Aron", "Lukas", "Jacob", "Caesar"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        soundEffect.setAdapter(adapter);

        builder.setView(v);
        builder.setTitle("edit parameters");
        builder.setPositiveButton("Launch", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

                double waterMass = 0;
                double rocketMass = 0;
                double pressure = 0;
                double fluidDensity = 0;


                EditText paramDensity = v.findViewById(R.id.paramDensity);
                if (!paramDensity.getText().toString().matches("")) {
                    fluidDensity = Double.parseDouble(paramDensity.getText().toString());
                }

                EditText paramPressure = v.findViewById(R.id.paramPressure);
                if (!paramPressure.getText().toString().matches("")) {
                    pressure = Double.parseDouble(paramPressure.getText().toString());
                }

                EditText paramRocket = v.findViewById(R.id.paramRocket);
                if (!paramRocket.getText().toString().matches("")) {
                    rocketMass = Double.parseDouble(paramRocket.getText().toString());
                }

                EditText paramWater = v.findViewById(R.id.paramWater);
                if (!paramWater.getText().toString().matches("")) {
                    waterMass = Double.parseDouble(paramWater.getText().toString());
                }

                double height = Math.pow((waterMass / (rocketMass + waterMass)), 2) * (pressure * 6895 / (fluidDensity * 9.81));

                double time = Math.pow((2 * height / 9.81), 1 / 2);
                launch((float) height, (long) time * 1000, soundEffect.getSelectedItem().toString());
            }
        });
        builder.setNegativeButton("cancel", null);
        builder.show();
    }


    public void launch(final float height, final long time, final String nameSound) {
        final ImageView rocket = findViewById(R.id.rocket);
        final ImageView splash = findViewById(R.id.splash);

        final float displayedHeight = height * 62;

        final Context context = this;

        final MediaPlayer mpCountdown = MediaPlayer.create(this, R.raw.sound);
        mpCountdown.start();

        mpCountdown.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            public void onCompletion(final MediaPlayer mp) {

                final MediaPlayer mpExplosion = MediaPlayer.create(context, R.raw.sound);
                mpExplosion.start();

                splash.setVisibility(View.VISIBLE);

                rocket.animate()
                        .translationY(-displayedHeight)
                        .setDuration(time)
                        .setInterpolator(new AccelerateDecelerateInterpolator())
                        .withEndAction(new Runnable() {
                            @Override
                            public void run() {
                                mpExplosion.stop();
                                splash.setVisibility(View.INVISIBLE);
                                rocket.animate()
                                        .translationY(0)
                                        .setDuration(time)
                                        .setInterpolator(new AccelerateDecelerateInterpolator())
                                        .withEndAction(new Runnable() {
                                            @Override
                                            public void run() {
                                                new AlertDialog.Builder(context)
                                                        .setTitle("Height: " + height)
                                                        .setNegativeButton("ok", null)
                                                        .show();
                                            }
                                        })
                                        .start();
                            }
                        })
                        .start();

                splash.animate()
                        .translationY(-displayedHeight)
                        .setDuration(time)
                        .setInterpolator(new AccelerateDecelerateInterpolator())
                        .withEndAction(new Runnable() {
                            @Override
                            public void run() {
                                splash.animate()
                                        .translationY(0)
                                        .setDuration(time)
                                        .setInterpolator(new AccelerateDecelerateInterpolator())
                                        .start();
                            }
                        })
                        .start();
            }
        });
    }
}
