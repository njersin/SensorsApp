package com.bignerdranch.android.sensorsapp;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.pm.ConfigurationInfo;
import android.content.res.Configuration;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Surface;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private SensorManager mSensorManager;
    private Sensor mAccelerometer;
    private TextView mAccelerometerTextView;
    private Sensor mTemperature;
    private TextView mTemperatureTextView;
    private Sensor mLightLevel;
    private TextView mLightLevelTextView;
    private Sensor mScreenOrientation;
    private TextView mScreenOrientationTextView;

    private Display mScreenDisplay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAccelerometerTextView = (TextView) findViewById(R.id.acceleration_text);
        mAccelerometerTextView.setText("Acceleration: ");

        mTemperatureTextView = (TextView) findViewById(R.id.ambient_temperature_text);
        mTemperatureTextView.setText("Ambient Temperature: ");

        mLightLevelTextView = (TextView) findViewById(R.id.light_level_text);
        mLightLevelTextView.setText("Light Level: ");

        mScreenOrientationTextView = (TextView) findViewById(R.id.current_screen_orientation_text);
        mScreenOrientationTextView.setText("Screen Orientation: ");

        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        if (mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) != null) {
            mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        } else {
            mAccelerometerTextView.setText("Acceleration: Your device has no accelerometers.");
        }

        if (mSensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE) != null) {
            mTemperature = mSensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE);
        } else {
            mTemperatureTextView.setText("Ambient Temperature: Your device has no thermometer.");
        }

        if (mSensorManager.getDefaultSensor(Sensor.TYPE_LIGHT) != null) {
            mLightLevel = mSensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        } else {
            mLightLevelTextView.setText("Light Level: Your device has no photometer.");
        }

       //Get screen orientation
        getScreenOrientation();

    }

    private void getScreenOrientation() {
        mScreenDisplay = ((WindowManager) getSystemService(WINDOW_SERVICE)).getDefaultDisplay();
        switch (mScreenDisplay.getRotation())
        {
            case Surface.ROTATION_0:
                mScreenOrientationTextView.setText("Screen Orientation: Portrait");
                break;
            case Surface.ROTATION_90:
                mScreenOrientationTextView.setText("Screen Orientation: Landscape");
                break;
            case Surface.ROTATION_180:
                mScreenOrientationTextView.setText("Screen Orientation: Upside down");
                break;
            case Surface.ROTATION_270:
                mScreenOrientationTextView.setText("Screen Orientation: Landscape");
                break;
        }
    }

    @Override
    public final void onAccuracyChanged(Sensor sensor, int accuracy) {

    }


    @Override
    public final void onSensorChanged(SensorEvent event) {

        getScreenOrientation();

        switch (event.sensor.getType())
        {
            case Sensor.TYPE_ACCELEROMETER:
                String acceleration = "X = " + event.values[0] + ", Y = "+ event.values[1] + ", Z = " + event.values[2];
                mAccelerometerTextView.setText("Acceleration: " + acceleration);
                break;
            case Sensor.TYPE_AMBIENT_TEMPERATURE:
                mTemperatureTextView.setText("Ambient Temperature: " + String.valueOf(event.values[0]));
                break;
            case Sensor.TYPE_LIGHT:
                mLightLevelTextView.setText("Light Level: " + String.valueOf(event.values[0]));
                break;
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        mSensorManager.registerListener(this, mTemperature, SensorManager.SENSOR_DELAY_NORMAL);
        mSensorManager.registerListener(this, mLightLevel, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
