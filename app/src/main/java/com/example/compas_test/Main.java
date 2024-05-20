package com.example.compas_test;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.widget.ImageView;
import android.widget.TextView;

public class Main extends Activity {
  private ImageView image;
  private TextView tvMagnetometer;
  private TextView tvAccelerometer;
  private SensorManager sensorManager;
  private Sensor magnetometer;
  private Sensor accelerometer;

  private SensorHandlerThread sensorHandlerThread;
  private Handler mainHandler;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_compass);

    image = (ImageView) findViewById(R.id.imageViewCompass);
    tvMagnetometer = (TextView) findViewById(R.id.tvMagnetometer);
    tvAccelerometer = (TextView) findViewById(R.id.tvAccelerometer);

    sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
    accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
    magnetometer = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);

    sensorHandlerThread = new SensorHandlerThread();
    sensorHandlerThread.start();

    mainHandler = new Handler(Looper.getMainLooper());

    sensorHandlerThread.setHandler(mainHandler, tvMagnetometer, tvAccelerometer);
  }

  @Override
  protected void onResume() {
    super.onResume();
    sensorManager.registerListener(sensorHandlerThread, accelerometer, SensorManager.SENSOR_DELAY_GAME);
    sensorManager.registerListener(sensorHandlerThread, magnetometer, SensorManager.SENSOR_DELAY_GAME);
  }

  @Override
  protected void onPause() {
    super.onPause();
    sensorManager.unregisterListener(sensorHandlerThread);
    sensorHandlerThread.quit();
  }

  private static class SensorHandlerThread extends HandlerThread implements SensorEventListener {
    private Handler handler;
    private TextView tvMagnetometer;
    private TextView tvAccelerometer;

    public SensorHandlerThread() {
      super("SensorHandlerThread");
    }

    public void setHandler(Handler handler, TextView tvMagnetometer, TextView tvAccelerometer) {
      this.handler = handler;
      this.tvMagnetometer = tvMagnetometer;
      this.tvAccelerometer = tvAccelerometer;
    }

    @Override
    public void onSensorChanged(final SensorEvent event) {
      if (event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) {
        float azimuth = Math.round(event.values[0]);
        float pitch = Math.round(event.values[1]);
        float roll = Math.round(event.values[2]);

        double tesla = Math.sqrt((azimuth * azimuth) + (pitch * pitch) + (roll * roll));
        final String text = String.format("\n \n %.0f Тл", tesla);

        handler.post(new Runnable() {
          @Override
          public void run() {
            tvMagnetometer.setText(text);
          }
        });
      } else if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
        final String text = String.format("X: %.2f,\nY: %.2f,\nZ: %.2f", event.values[0], event.values[1], event.values[2]);

        handler.post(new Runnable() {
          @Override
          public void run() {
            tvAccelerometer.setText(text);
          }
        });
      }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
      // not in use
    }
  }
}
