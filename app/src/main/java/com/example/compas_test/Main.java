package com.example.compas_test;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.os.Handler;


public class Main extends Activity implements SensorEventListener {
  private ImageView image;
  private float currentDegree = 0f;
  private SensorManager mSensorManager;
  TextView tvHeading;
  TextView tvRotate;
  private float prev;
  private float curr;
  private Handler mHandler = new Handler();
  private boolean isRotating = false;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_compass);
    image = (ImageView) findViewById(R.id.imageViewCompass);
    tvHeading = (TextView) findViewById(R.id.tvHeading);
    tvRotate = (TextView) findViewById(R.id.tvRotate);

    mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
  }

  @Override
  protected void onResume() {
    super.onResume();

    // for the system's orientation sensor registered listeners
    mSensorManager.registerListener(this, mSensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION),
      SensorManager.SENSOR_DELAY_GAME);
  }

  @Override
  protected void onPause() {
    super.onPause();
    mSensorManager.unregisterListener(this);
  }

  @Override
  public void onSensorChanged(SensorEvent event) {

    float newDegree = Math.round(event.values[0]);


    mHandler.postDelayed(() -> {
      prev = currentDegree;
      curr = newDegree;
//      if (curr >= 225 && curr <= 359 && prev >= 0) {
//        if ((360 - prev + curr) >= 45) {
//          tvRotate.setText("Go Left!");
//          isRotating = true;
//        }
//      } else if (prev >= 225 && prev <= 359 && curr >= 0) {
//        if ((360 - prev - curr) >= 45) {
//          tvRotate.setText("Go Right!");
//          isRotating = true;
//        }
//      } else
        if (curr - prev >= 45) {
          tvRotate.setText("Go Left!");
          isRotating = true;
        }
        else if (prev - curr >= 45) {
          tvRotate.setText("Go Right!");
          isRotating = true;
        }
        else {
          tvRotate.setText("Go Forward!");
          isRotating = false;
      }

      if (!isRotating) {
        mHandler.postDelayed(() -> {
          if (!isRotating) {
            tvRotate.setText("Go Forward!");
          }
        }, 2000);
      }
    }, 1000);

    currentDegree = newDegree;
    tvHeading.setText("Heading: " + Float.toString(currentDegree) + " degrees");
  }


  @Override
  public void onAccuracyChanged(Sensor sensor, int accuracy) {
    // not in use
  }
}
