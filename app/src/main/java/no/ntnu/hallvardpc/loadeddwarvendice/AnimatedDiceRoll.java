package no.ntnu.hallvardpc.loadeddwarvendice;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.widget.GridView;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by Alexander on 24.11.2017.
 */

public class AnimatedDiceRoll extends Activity {
    MediaPlayer mediaPlayer;
    private SensorManager sensorManager;
    private float accel; // acceleration apart from gravity
    private float accelCurrent; // current acceleration including gravity
    private float accelLast; // last acceleration including gravity

    int numberOfDice;
    int diceTypeValue;

    private GridView diceOnScreen;
    private List<Integer> diceIDs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animated_dice_roll);

        Bundle b = getIntent().getExtras();
        this.numberOfDice = b.getInt("DiceAmount");

        mediaPlayer = MediaPlayer.create(AnimatedDiceRoll.this, R.raw.dice_landing);

        //As not all dice types are in, sets diceTypeValue to 6
        if (b.getInt("DiceType") != 6) {
            this.diceTypeValue = 6;
        } else {
            this.diceTypeValue = b.getInt("DiceType");
        }

        //--- GridView(Dice on screen) ---
        this.diceIDs = new ArrayList<>();

        for (int i = 0; i < numberOfDice; i++) {
            addDiceID(1);
        }
        this.diceOnScreen = findViewById(R.id.diceOnScreenGridView);
        diceOnScreen.setAdapter(new ImageAdapter(this, numberOfDice, diceIDs));

        //--- Sensor ---
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sensorManager.registerListener(sensorListener, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);
        accel = 0.00f;
        accelCurrent = SensorManager.GRAVITY_EARTH;
        accelLast = SensorManager.GRAVITY_EARTH;
    }

    /**
     *
     */
    private void rollDice() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < numberOfDice; i++) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        roll();
                    }
                }
            }
        }).start();
        diceOnScreen.setAdapter(new ImageAdapter(this, numberOfDice, diceIDs));
    }

    /**
     * The image files are named in this format d(the total number of dice side)_s(side of the dice)
     * Ex. D6_S5, will is a six sided dice where the image is of the side showing 5
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void roll() {
        int rollResult = ThreadLocalRandom.current().nextInt(1, diceTypeValue + 1);
        addDiceID(rollResult);
    }

    /**
     * @param diceSide
     */
    private void addDiceID(int diceSide) {
        String diceResult = "d" + Integer.toString(diceTypeValue) + "_s" + diceSide;
        int resID = getResources().getIdentifier(diceResult, "mipmap", "no.ntnu.hallvardpc.loadeddwarvendice");
        diceIDs.add(resID);
    }


    private final SensorEventListener sensorListener = new SensorEventListener() {
        public void onSensorChanged(SensorEvent sensorEvent) {
            float x = sensorEvent.values[0];
            float y = sensorEvent.values[1];
            float z = sensorEvent.values[2];
            accelLast = accelCurrent;
            accelCurrent = (float) Math.sqrt((double) (x * x + y * y + z * z));
            float delta = accelCurrent - accelLast;
            accel = accel * 0.9f + delta; // perform low-cut filter

            if (accel > 12)
            {
                diceIDs.clear();
                mediaPlayer.start();
                rollDice();
            }
        }

        public void onAccuracyChanged(Sensor sensor, int accuracy) {
        }
    };

    public void onResume() {
        super.onResume();
        sensorManager.registerListener(sensorListener, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);
    }

    public void onPause() {
        mediaPlayer.pause();
        sensorManager.unregisterListener(sensorListener);
        super.onPause();
    }
}