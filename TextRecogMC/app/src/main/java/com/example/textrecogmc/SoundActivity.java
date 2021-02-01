package com.example.textrecogmc;

import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

public class SoundActivity extends AppCompatActivity {
    private TextToSpeech mTTS;
    private TextView textView;
    private SeekBar seekBarPitch;
    private SeekBar seekBarSpeed;
    private Button speak;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listen);
        speak = findViewById(R.id.button_speak);
        mTTS = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int i) {
                if(i==TextToSpeech.SUCCESS){
                    int result = mTTS.setLanguage(Locale.GERMAN);
                    if(result==TextToSpeech.LANG_MISSING_DATA ||
                    result==TextToSpeech.LANG_NOT_SUPPORTED){
                        Log.e("TTS","Language not Supported");
                    }else{
                        speak.setEnabled(true);
                    }
                }else{
                    Log.e("TTS","Initialization failed");
                }
            }
        });
        textView = findViewById(R.id.sample_text);
        textView.setText(getIntent().getStringExtra("detectedText"));
        seekBarPitch = findViewById(R.id.seek_bar_pitch);
        seekBarSpeed = findViewById(R.id.seek_bar_speed);
        speak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                speak();
            }
        });

    }

    private void speak() {
        String text = textView.getText().toString();
        float pitch = (float)seekBarPitch.getProgress()/50;
        if(pitch<0.1) pitch = 0.1f;

        float speed = (float)seekBarSpeed.getProgress()/50;
        if(speed<0.1) speed = 0.1f;

        mTTS.setPitch(pitch);
        mTTS.setSpeechRate(speed);
        mTTS.speak(text,TextToSpeech.QUEUE_FLUSH,null);
    }

    @Override
    protected void onDestroy() {
        if(mTTS!=null){
            mTTS.stop();
            mTTS.shutdown();
        }
        super.onDestroy();
    }
}
