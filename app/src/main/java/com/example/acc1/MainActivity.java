package com.example.acc1;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import java.io.IOException;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity implements SensorEventListener, View.OnClickListener {
    private SensorManager sensorManager;
    private Sensor accSensor;
    private int i;
    private TextView rateTextView, tvX, tvY, tvZ, tvAccContent;
    private float count_x, count_y, count_z;

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 1){
                //do something
                rateTextView.setText("rate= "+ i);
                i = 0;
            }
            super.handleMessage(msg);
        }
    };
    Timer timer = new Timer();
    TimerTask timerTask = new TimerTask() {
        @Override
        public void run() {
            Message message = new Message();
            message.what = 1;
            handler.sendMessage(message);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON, WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);


        setContentView(R.layout.activity_main);

        Button b1 = (Button)findViewById(R.id.button1);
        Button b5 = (Button)findViewById(R.id.button5);
        Button b10 = (Button)findViewById(R.id.button10);
        Button b15 = (Button)findViewById(R.id.button15);
        Button b25 = (Button)findViewById(R.id.button25);
        Button b50 = (Button)findViewById(R.id.button50);
        Button b100 = (Button)findViewById(R.id.button100);
        Button b200 = (Button)findViewById(R.id.button200);
        Button bFastest = (Button)findViewById(R.id.buttonFastest);
        b1.setOnClickListener(this);
        b5.setOnClickListener(this);
        b10.setOnClickListener(this);
        b15.setOnClickListener(this);
        b25.setOnClickListener(this);
        b50.setOnClickListener(this);
        b100.setOnClickListener(this);
        b200.setOnClickListener(this);
        bFastest.setOnClickListener(this);

        tvX = (TextView)findViewById(R.id.tvX);
        tvY = (TextView)findViewById(R.id.tvY);
        tvZ = (TextView)findViewById(R.id.tvZ);
        tvAccContent = (TextView)findViewById(R.id.textView4);


        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        accSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        i = 0;
        rateTextView = this.findViewById(R.id.rate);


    }


        @Override
        protected void onResume () {
            super.onResume();
            sensorManager.registerListener(this, accSensor, SensorManager.SENSOR_DELAY_NORMAL);
            //sensorManager.registerListener(this, accSensor,50000,1000000);
            //timer.schedule(task,0,10000);
            //mHandler.postDelayed(r, 1000);
            rateTextView.setText("start");
            timer.schedule(timerTask, 1000, 1000);

            tvAccContent.setText(
                    "4.5~5.4" + "\n"
                    + "6.4~7.4" + "\n"
                    + "name: "+accSensor.getName()+"\n"
                    + "MaxDelay " +accSensor.getMaxDelay() + "\n"
                    + "MinDelay " + accSensor.getMinDelay() + "\n"
                    + "MaxEventCount " + accSensor.getFifoMaxEventCount() + "\n"
                    + "MaximumRange " + accSensor.getMaximumRange()
            );

            count_x = 0;
            count_y = 0;
            count_z = 0;

        }

        @Override
        protected void onPause () {
            super.onPause();
            sensorManager.unregisterListener(this);
            timer.cancel();
//mHandler.removeCallbacks(r);
        }

        @Override
        public void onSensorChanged (SensorEvent sensorEvent){
            float x = sensorEvent.values[0];
            float y = sensorEvent.values[1];
            float z = sensorEvent.values[2];

            Log.d("zzz", "x=%f"+x);

            if(x >= 4.5 && x <= 5.4){
                tvX.setTextColor(getResources().getColor(android.R.color.holo_red_light));
            }
            else
                tvX.setTextColor(getResources().getColor(android.R.color.black));

            if(y >= -5.4 && y <= -4.5)
                tvY.setTextColor(getResources().getColor(android.R.color.holo_red_light));
            else
                tvY.setTextColor(getResources().getColor(android.R.color.black));

            if(z >= 6.4 && z <=7.4 )
                tvZ.setTextColor(getResources().getColor(android.R.color.holo_red_light));
            else
                tvZ.setTextColor(getResources().getColor(android.R.color.black));

            i++;
            tvX.setText("x= " + x);
            tvY.setText("y= " + y);
            tvZ.setText("z= " + z);
        }

        @Override
        public void onAccuracyChanged (Sensor sensor,int i){

        }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button1:
                sensorManager.unregisterListener(this);
                sensorManager.registerListener(this, accSensor, 1000*1000);
                break;
            case R.id.button5:
                sensorManager.unregisterListener(this);
                sensorManager.registerListener(this, accSensor, 200*1000);
                break;
            case R.id.button10:
                sensorManager.unregisterListener(this);
                sensorManager.registerListener(this, accSensor, 100*1000);
                break;
            case R.id.button15:
                sensorManager.unregisterListener(this);
                sensorManager.registerListener(this, accSensor, 66*1000);
                break;
            case R.id.button25:
                sensorManager.unregisterListener(this);
                sensorManager.registerListener(this, accSensor, 40*1000);
                break;
            case R.id.button50:
                sensorManager.unregisterListener(this);
                sensorManager.registerListener(this, accSensor, 20*1000);
                break;
            case R.id.button100:
                sensorManager.unregisterListener(this);
                sensorManager.registerListener(this, accSensor, 10*1000);
                break;
            case R.id.button200:
                sensorManager.unregisterListener(this);
                sensorManager.registerListener(this, accSensor, 5*1000);
                break;
            case R.id.buttonFastest:
                sensorManager.unregisterListener(this);
                sensorManager.registerListener(this, accSensor, 2500);
                break;
            default:
                break;
        }
    }
}