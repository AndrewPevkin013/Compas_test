package com.example.compas_test;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;

import com.example.compas_test.R;

public class Main extends Activity implements SensorEventListener {

    private SensorManager sensorManager;
    private Sensor compassSensor;
    private TextView compassTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compass);

        compassTextView = findViewById(R.id.compassTextView);

        // Получение экземпляра SensorManager
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        // Получение компасного датчика
        compassSensor = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Регистрация слушателя для компасного датчика
        sensorManager.registerListener(this, compassSensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        // Отмена регистрации слушателя при приостановке активности
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        // Обработка изменения данных с компаса
        if (event.sensor == compassSensor) {
            float magneticNorth = event.values[0];
            // Обновление текстового поля с данными компаса
            compassTextView.setText("Направление: " + magneticNorth + " градусов");
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Метод вызывается при изменении точности датчика, но в данном примере не используется
    }
}
