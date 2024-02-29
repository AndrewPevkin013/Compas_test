package com.example.compas_test;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;

public class Main extends Activity implements SensorEventListener {

  private SensorManager sensorManager;
  private Sensor accelerometer;
  private Sensor magnetometer;
  private TextView orientationTextView;

  private float[] accelerometerReading = new float[3];
  private float[] magnetometerReading = new float[3];
  private float[] rotationMatrix = new float[9];
  private float[] orientationAngles = new float[3];

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_compass);

    orientationTextView = findViewById(R.id.orientationTextView);
    sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
    accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
    magnetometer = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
  }

  @Override
  protected void onResume() {
    super.onResume();
    sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_UI);
    sensorManager.registerListener(this, magnetometer, SensorManager.SENSOR_DELAY_UI);
  }

  @Override
  protected void onPause() {
    super.onPause();
    sensorManager.unregisterListener(this);
  }

  @Override
  public void onSensorChanged(SensorEvent event) {
    if (event.sensor == accelerometer) {
      System.arraycopy(event.values, 0, accelerometerReading, 0, accelerometerReading.length);
    } else if (event.sensor == magnetometer) {
      System.arraycopy(event.values, 0, magnetometerReading, 0, magnetometerReading.length);
    }

    if (accelerometerReading != null && magnetometerReading != null) {
      SensorManager.getRotationMatrix(rotationMatrix, null, accelerometerReading, magnetometerReading);
      SensorManager.getOrientation(rotationMatrix, orientationAngles);

      float azimuthInDegrees = (float) Math.toDegrees(orientationAngles[0]);

      if (azimuthInDegrees < 0) {
        azimuthInDegrees += 360;
      }

      orientationTextView.setText("Азимут: " + azimuthInDegrees + " градусов");
    }
  }

  @Override
  public void onAccuracyChanged(Sensor sensor, int accuracy) {
    // Метод вызывается при изменении точности датчика, но в данном примере не используется
  }
}
