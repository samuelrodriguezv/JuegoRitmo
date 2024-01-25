package com.example.prueba;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

public class tempo_HighScore extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    private Button empezar, j1, resul;
    private TextView j1p, textoRondas;

    private final Timer T = new Timer();
    private int count = 0;
    private int tiempo1 = 0;
    private int aux = 0;
    private int rondas = 1;
    private int puntosTotal = 0;
    private int puntos1 = 0;
    private int contador = 0;
    private boolean repetir = false; //boolean para non repetir o timer

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

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.highscore);

        mAuth = FirebaseAuth.getInstance();
        String id = Objects.requireNonNull(mAuth.getCurrentUser()).getUid();

        //me posiciono con database y storage
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Usuarios").child(id);

        //inicializamos un array de audios
        final int[] audios = new int[]{R.raw.blues100, R.raw.blues100_2, R.raw.blues105, R.raw.blues110,
                R.raw.blues120, R.raw.blues120_2, R.raw.blues60, R.raw.blues65, R.raw.blues65_2,
                R.raw.blues70, R.raw.blues70_2, R.raw.blues75, R.raw.blues80, R.raw.blues85, R.raw.blues90,
                R.raw.funk105, R.raw.funk105_2, R.raw.funk115, R.raw.funk85, R.raw.funk90, R.raw.funk90_2,
                R.raw.funk95, R.raw.funk_rock65, R.raw.hard_rock, R.raw.hiphop110, R.raw.hiphop90,
                R.raw.jazz120, R.raw.jazz120_2, R.raw.jazz120_3, R.raw.jazz80, R.raw.jazz95, R.raw.jazz95_2,
                R.raw.metal120, R.raw.metal70, R.raw.metal80, R.raw.metal95, R.raw.reggae100, R.raw.reggae110,
                R.raw.reggae115, R.raw.reggae120, R.raw.reggae70, R.raw.reggae80, R.raw.rock100,
                R.raw.rock100_2, R.raw.rock105, R.raw.rock110, R.raw.rock115, R.raw.rock120, R.raw.rock120_2,
                R.raw.rock60, R.raw.rock60_2, R.raw.rock65, R.raw.rock75, R.raw.rock75_2, R.raw.rock80,
                R.raw.rock85, R.raw.rock85_2, R.raw.rock90, R.raw.rock90_2, R.raw.rock95, R.raw.rock95_2,
                R.raw.rock_funk80};

        empezar = findViewById(R.id.iniciar);
        j1 = findViewById(R.id.j1);
        j1p = findViewById(R.id.j1p);
        resul = findViewById(R.id.resul);
        textoRondas = findViewById(R.id.rondas);

        empezar.setEnabled(true);
        j1.setEnabled(false);
        resul.setEnabled(false);

        //boton para iniciar o audio
        empezar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //establecemos a variable que leva o tempo a 0 para que se reinicie
                //cada vez que se pulsa o boton "iniciar"
                count = 0;

                //a variable "i" contará a ronda actual
                contador++;
                //creo un texto para indicar as rondas
                textoRondas.setText("Nº de ronda: " + contador + " / 10");

                //borro o texto cada vez que comeza un audio
                j1p.setText("");

                //creo un obxecto random para seleccionar un audio ao azar
                int r = new Random().nextInt(audios.length);

                //coa variable "aux" impido que se repita o mesmo audio 2 veces seguidas
                if (r == aux) {
                    r = r + 1;
                }
                aux = r;

                //creo un "MediaPlayer" e asigno co obxecto random un valor aleatorio do array "audios"
                mp = MediaPlayer.create(getApplicationContext(), audios[r]);

                //deshabilito o boton empezar para que non se poida volver a pulsar ata que
                //todos os xogadores pulsen o seu boton de puntuacion
                empezar.setEnabled(false);

                //comeza o audio
                mp.start();

                //unha vez comeza o audio, o boton do xogador non se activa ata que o audio remate
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        j1.setEnabled(true);
                    }
                }, mp.getDuration());

                // Cóntase o tempo transcurrido desde que comeza o audio para máis tarde calcular a puntuación.
                // Se "repetir" é falso significa que ainda non se iniciou, asi que comeza o timer
                // no caso contrario, sáltase o timer para que non se acumule.
                // Esto evita que o timer se sume cada vez máis rapido, polo que só se vai usar unha vez.
                if (repetir == false) {

                    T.scheduleAtFixedRate(new TimerTask() {
                        @Override
                        public void run() {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    count = (count + 1);
                                }
                            });
                        }
                    }, 1, 1);
                }

                repetir = true; //convertimos o boolean a true porque xa iniciou o timer unha vez
            }
        });

        /*
        boton xogador 1
         */
        j1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //creamos a seguinte variable para que garde o tempo cando se pulse
                tiempo1 = count;

                puntos1 = puntuacion(mp, tiempo1, j1p);

                j1.setEnabled(false); //cando faga a sua funcion, o boton queda inutilizado

                //o boton empezar resetéase se non alcanza o numero introducido polo usuario
                //vai sumando 1 de cada vez que se pulse para limitar as rondas
                if(rondas < 10) {
                    empezar.setEnabled(true);
                    rondas++;
                } else {
                    resul.setEnabled(true);
                }
            }
        });

        resul.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mDatabase.child("Puntos").push().setValue(puntos1);

                Intent i = new Intent(tempo_HighScore.this, Resultados.class);
                startActivity(i);
                finish();
            }
        });

    }

    /*
    metodo para seleccionar a puntuacion
     */
    private int puntuacion(MediaPlayer song, int i, TextView jp){

        if(i >= ((song.getDuration()*2)-200) && i <= ((song.getDuration()*2)+200)){
            jp.setText("Puntuacion: " + 10);
            puntosTotal = puntosTotal + 10;
        } else if(i >= ((song.getDuration()*2)-400) && i <= ((song.getDuration()*2)-200) ||
                i >= ((song.getDuration()*2)+200) && i <= ((song.getDuration()*2)+400)){
            jp.setText("Puntuacion: " + 9);
            puntosTotal = puntosTotal + 9;
        } else if(i >= ((song.getDuration()*2)-600) && i <= ((song.getDuration()*2)-400) ||
                i >= ((song.getDuration()*2)+400) && i <= ((song.getDuration()*2)+600)){
            jp.setText("Puntuacion: " + 8);
            puntosTotal = puntosTotal + 8;
        } else if(i >= ((song.getDuration()*2)-800) && i <= ((song.getDuration()*2)-600) ||
                i >= ((song.getDuration()*2)+600) && i <= ((song.getDuration()*2)+800)){
            jp.setText("Puntuacion: " + 7);
            puntosTotal = puntosTotal + 7;
        } else if(i >= ((song.getDuration()*2)-1000) && i <= ((song.getDuration()*2)-800) ||
                i >= ((song.getDuration()*2)+800) && i <= ((song.getDuration()*2)+1000)){
            jp.setText("Puntuacion: " + 6);
            puntosTotal = puntosTotal + 6;
        } else if(i >= ((song.getDuration()*2)-1200) && i <= ((song.getDuration()*2)-1000) ||
                i >= ((song.getDuration()*2)+1000) && i <= ((song.getDuration()*2)+1200)){
            jp.setText("Puntuacion: " + 5);
            puntosTotal = puntosTotal + 5;
        } else if(i >= ((song.getDuration()*2)-1400) && i <= ((song.getDuration()*2)-1200) ||
                i >= ((song.getDuration()*2)+1200) && i <= ((song.getDuration()*2)+1400)){
            jp.setText("Puntuacion: " + 4);
            puntosTotal = puntosTotal + 4;
        } else if(i >= ((song.getDuration()*2)-1600) && i <= ((song.getDuration()*2)-1400) ||
                i >= ((song.getDuration()*2)+1400) && i <= ((song.getDuration()*2)+1600)){
            jp.setText("Puntuacion: " + 3);
            puntosTotal = puntosTotal + 3;
        } else if(i >= ((song.getDuration()*2)-1800) && i <= ((song.getDuration()*2)-1600) ||
                i >= ((song.getDuration()*2)+1600) && i <= ((song.getDuration()*2)+1800)){
            jp.setText("Puntuacion: " + 2);
            puntosTotal = puntosTotal + 2;
        } else if(i >= ((song.getDuration()*2)-2000) && i <= ((song.getDuration()*2)-1800) ||
                i >= ((song.getDuration()*2)+1800) && i <= ((song.getDuration()*2)+2000)){
            jp.setText("Puntuacion: " + 1);
            puntosTotal = puntosTotal + 1;
        } else if(i <= ((song.getDuration()*2)-2000) || i >= ((song.getDuration()*2)+2000)){
            jp.setText("Puntuacion: " + 0);
        }

        return puntosTotal;

    }
}
