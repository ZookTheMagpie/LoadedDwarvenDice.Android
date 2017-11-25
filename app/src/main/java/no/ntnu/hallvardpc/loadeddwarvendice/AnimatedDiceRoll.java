package no.ntnu.hallvardpc.loadeddwarvendice;

import android.app.ActionBar;
import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.Image;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by Alexander on 24.11.2017.
 */

public class AnimatedDiceRoll extends Activity implements SensorEventListener {
    private static final int UPDATE_DELAY = 50;
    private static final int SHAKE_THRESHOLD = 800;

    int numberOfDice;
    int diceTypeValue;

    ArrayList<ImageView> diceOnScreen;
    private RelativeLayout animatedDiceRollerLayout;
    private long lastUpdate = -1;
    private float x, y, z;
    private float last_x, last_y, last_z;
    private boolean paused;
    private SensorManager sensorManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.paused = false;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animated_dice_roll);

        this.diceOnScreen = new ArrayList<>();

        this.animatedDiceRollerLayout = (RelativeLayout) findViewById(R.id.animatedDiceRollerLayout);

        Bundle b = getIntent().getExtras();
        this.numberOfDice = b.getInt("DiceAmount");
        this.diceTypeValue = b.getInt("DiceType");

        //The image files are named in this format d(the total number of dice side)_s(side of the dice)
        //Ex. D6_S5, will is a six sided dice where the image is of the side showing 5
        String diceType = "d" + Integer.toString(diceTypeValue) + "_s" + Integer.toString(numberOfDice);

        for (int i = 1; i <= numberOfDice; i++) {
            int resID = getResources().getIdentifier(diceType, "mipmap", "no.ntnu.hallvardpc.loadeddwarvendice");

            ImageView diceImage = new ImageView(getApplicationContext());
            diceImage.setImageResource(resID);

            ViewGroup.LayoutParams diceImageLayoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

            diceImage.setLayoutParams(diceImageLayoutParams);

            animatedDiceRollerLayout.addView(diceImage);

            diceOnScreen.add(diceImage);
        }

        animatedDiceRollerLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    rollDice();
                } catch (Exception e) {
                }
            }
        });
        this.sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        boolean accelSupported = sensorManager.registerListener(this,
                sensorManager.getDefaultSensor(SensorManager.SENSOR_ACCELEROMETER), SensorManager.SENSOR_DELAY_GAME);
        if (!accelSupported)
            sensorManager.unregisterListener(this); //no accelerometer on the device
        rollDice();
    }


    private void rollDice() {
        if (paused) return;
        new Thread(new Runnable() {
            @Override
            public void run() {
                Iterator<ImageView> it = diceOnScreen.iterator();
                while (it.hasNext()) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        roll(it.next());
                    }
                }
            }
        }).start();
        MediaPlayer mediaPlayer = MediaPlayer.create(this, R.raw.dice_rolling);
        try {
            mediaPlayer.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
        mediaPlayer.start();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void roll(ImageView next) {
        int rollResult = ThreadLocalRandom.current().nextInt(1, diceTypeValue + 1);

        String diceResult = "d" + Integer.toString(diceTypeValue) + "_s" + rollResult;
        int resID = getResources().getIdentifier(diceResult, "mipmap", "no.ntnu.hallvardpc.loadeddwarvendice");
        next.setImageResource(resID);
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        Sensor aSensor = sensorEvent.sensor;
        if (aSensor.getType() == SensorManager.SENSOR_ACCELEROMETER) {
            long curTime = System.currentTimeMillis();
            if ((curTime - lastUpdate) > UPDATE_DELAY) {
                long diffTime = (curTime - lastUpdate);
                lastUpdate = curTime;
                x = sensorEvent.values[SensorManager.DATA_X];
                y = sensorEvent.values[SensorManager.DATA_Y];
                z = sensorEvent.values[SensorManager.DATA_Z];
                float speed = Math.abs(x + y + z - last_x - last_y - last_z) / diffTime * 10000;
                if (speed > SHAKE_THRESHOLD) {
                    rollDice();
                }
                last_x = x;
                last_y = y;
                last_z = z;
            }
        }
    }


    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {
    }

    public void onResume() {
        super.onResume();
        paused = false;
    }

    public void onPause() {
        super.onPause();
        paused = true;
    }
}