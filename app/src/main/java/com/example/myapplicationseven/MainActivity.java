package com.example.myapplicationseven;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends Activity {

    private static final String TAG = "SensorTest";

    private SensorManager mSensorManager;
    private Sensor mAccelerometer;
    private TestSensorListener mSensorListener;

    private ImageView imageView;
    private SensorManager manager;
    private TestSensorListener listener=new TestSensorListener();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // 初始化传感器
        mSensorListener = new TestSensorListener();
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);


        imageView=(ImageView)this.findViewById(R.id.imageView);
        manager=(SensorManager)getSystemService(SENSOR_SERVICE);
        imageView.setKeepScreenOn(true);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // 注册传感器监听函数
        mSensorManager.registerListener((SensorEventListener) mSensorListener, mAccelerometer, SensorManager.SENSOR_DELAY_UI);
        //为系统的方向传感器注册监听器
        Sensor sensor = manager.getDefaultSensor(Sensor.TYPE_ORIENTATION);
        manager.registerListener(listener, sensor,
                SensorManager.SENSOR_DELAY_GAME);

    }

    @Override
    protected void onPause() {
        super.onPause();
        // 注销监听函数
        mSensorManager.unregisterListener(mSensorListener);

        manager.unregisterListener(listener);
    }


    class TestSensorListener implements SensorEventListener {
        private float predegree = 0;
        @Override
        public void onSensorChanged(SensorEvent event) {
            // 读取加速度传感器数值，values数组0,1,2分别对应x,y,z轴的加速度
            Log.i(TAG, "onSensorChanged: " + event.values[0] + ", " + event.values[1] + ", " + event.values[2]);
            TextView a=(TextView)findViewById(R.id.txt1);
            TextView b=(TextView)findViewById(R.id.txt2);
            TextView c=(TextView)findViewById(R.id.txt3);
            a.setText("X方向上的加速度："+event.values[0]);
            b.setText("Y方向上的加速度："+event.values[1]);
            c.setText("Z方向上的加速度："+event.values[2]);

            float degree = event.values[0];// 存放了方向值 90
            RotateAnimation animation = new RotateAnimation(predegree, -degree,
                    Animation.RELATIVE_TO_SELF, 0.5f,
                    Animation.RELATIVE_TO_SELF, 0.5f);
            animation.setDuration(200);
            imageView.startAnimation(animation);
            predegree = -degree;
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
            Log.i(TAG, "onAccuracyChanged");
        }

    }




}
