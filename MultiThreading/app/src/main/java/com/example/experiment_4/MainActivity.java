package com.example.experiment_4;

import android.graphics.Color;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private TextView progressText;
    private ProgressBar progressBar;
    private RelativeLayout relativeLayout;
    private Button colorChange;
    private Button colorChangeStop;
    private Handler mainUiHandler = new Handler();
    //Booleans to handle threads
    private volatile boolean progressThread = false;
    private volatile boolean changeColourThread = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progressText = (TextView) findViewById(R.id.progress_text);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        relativeLayout = (RelativeLayout) findViewById(R.id.main_page);
        colorChange = (Button) findViewById(R.id.color_change);
        colorChangeStop = (Button) findViewById(R.id.color_change_stop);
        colorChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeColourThread = false;
                TaskRunnable runnable = new TaskRunnable();
                new Thread(runnable).start();
            }
        });
        colorChangeStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeColourThread = true;
                relativeLayout.setBackgroundColor(Color.WHITE);
            }
        });
    }

    public void startThread(View view) {
        progressThread=false;
         SampleRunnable runnable = new SampleRunnable(10);
         new Thread(runnable).start();
    }

    public void stopThread(View view) {
        progressThread = true;
    }

    class SampleRunnable implements Runnable{

        int seconds;
        SampleRunnable(int seconds){
            this.seconds = seconds;
        }
        @Override
        public void run() {
            for(int i=0;i<=seconds;i++){
                if(progressThread) return;
                final int temp = (i*100)/seconds;
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                /*
                    Handler threadHandler = new Handler(Looper.getMainLooper());
                    threadHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            buttonStartThread.setText("50%");
                        }
                    });
                    OR
                    buttonStartThread.post(new Runnable() {
                        @Override
                        public void run() {
                            buttonStartThread.setText("50%");
                        }
                    });
                    OR
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        buttonStartThread.setText("50%");
                    }
                });
                 */
                  mainUiHandler.post(new Runnable() {
                      @Override
                      public void run() {
                          progressBar.setProgress(temp);
                          progressText.setText(temp+"%");
                      }
                  });

                Log.d(TAG,"startThread"+i);

            }
        }
    }

    class TaskRunnable implements Runnable{

        @Override
        public void run() {

                Random random = new Random();

                for(int i=0;i<10;i++) {
                    if(changeColourThread) return;
                    final int color = Color.argb(255, random.nextInt(256), random.nextInt(256), random.nextInt(256));

                    relativeLayout.post(new Runnable() {
                        @Override
                        public void run() {
                            relativeLayout.setBackgroundColor(color);
                        }
                    });
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }


        }
    }
}