package com.example.prueba;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

import androidx.appcompat.app.AppCompatActivity;

public class Informacion extends AppCompatActivity {

    private Button ej;
    private TextView text, text2;

    private Timer t = new Timer();
    private int count = 0;
    private MediaPlayer mp = new MediaPlayer();

    @Override
    public void onBackPressed () {
        if (mp != null)
            mp.stop();
        super.onBackPressed();
        finish();
    }

    @Override
    protected void onStop() {
        if (mp != null)
            mp.stop();
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        if (mp != null)
            mp.stop();
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        if (mp != null)
            mp.stop();
        super.onPause();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.informacion);

        ej = findViewById(R.id.boton);
        text = findViewById(R.id.segundos);
        text2 = findViewById(R.id.texto);

        mp = MediaPlayer.create(this, R.raw.rock60);

        ej.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mp.start();
                ej.setEnabled(false);

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        text2.setText("AHORA");
                        t.cancel();
                    }
                }, (mp.getDuration() * 2)+100);

                t.scheduleAtFixedRate(new TimerTask() {
                    @Override
                    public void run() {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (count < 8) {
                                    count = (count + 1);
                                    text.setText("- " + count + " -");
                                } else {
                                    count = 1;
                                    text.setText("- " + count + " -");
                                }
                            }
                        });
                    }
                 }, 220, 1000);
            }
        });

    }

}
