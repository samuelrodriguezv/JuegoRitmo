package com.example.prueba;

import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import androidx.appcompat.app.AppCompatActivity;

public class tempo_3jugadores extends AppCompatActivity {

    private Button empezar, j1, j2, j3, resul;
    private TextView j1p, j2p, j3p, textoRondas;

    private boolean pulsado1 = false;
    private boolean pulsado2 = false;
    private boolean pulsado3 = false;

    private final Timer T = new Timer();
    private int count = 0;
    private int tiempo1 = 0;
    private int tiempo2 = 0;
    private int tiempo3 = 0;
    private int aux = 0;
    private int contador = 0;

    private int puntos1 = 0, puntos2 = 0, puntos3 = 0;
    private int puntosTotal1 = 0, puntosTotal2 = 0, puntosTotal3 = 0;

    private boolean repetir = false; //boolean para non repetir o timer

    //creo outro int para contar as rondas
    private int rondaActual = 1;

    MediaPlayer mp = new MediaPlayer();

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
        setContentView(R.layout.juego3j);

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

        empezar = findViewById(R.id.empezar);
        j1 = findViewById(R.id.j1);
        j2 = findViewById(R.id.j2);
        j3 = findViewById(R.id.j3);
        j1p = findViewById(R.id.j1p);
        j2p = findViewById(R.id.j2p);
        j3p = findViewById(R.id.j3p);
        resul = findViewById(R.id.resul);
        textoRondas = findViewById(R.id.rondas);

        empezar.setEnabled(true);
        j1.setEnabled(false);
        j2.setEnabled(false);
        j3.setEnabled(false);
        resul.setEnabled(false);

        //recollo o dato de rondas escrito na actividade "opciones"
        Intent i = getIntent();
        final int rondas = i.getIntExtra("rondas", 0);

        //transformo o dato en int
        final int nRondas = rondas;

        empezar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //establecemos a variable que leva o tempo a 0 para que se reinicie
                //cada vez que se pulsa o boton "iniciar"
                count = 0;

                //a variable "i" contará a ronda actual
                contador++;
                //creo un texto para indicar as rondas
                textoRondas.setText("Nº de ronda: " + contador + " / " + rondas);
                textoRondas.setTextColor(Color.BLACK);

                //borro os textos cada vez que comeza un audio
                j1p.setText("");
                j2p.setText("");
                j3p.setText("");

                pulsado1 = false;
                pulsado2 = false;
                pulsado3 = false;

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
                        j2.setEnabled(true);
                        j3.setEnabled(true);
                    }
                }, mp.getDuration());

                // Cóntase o tempo transcurrido desde que comeza o audio para máis tarde calcular a puntuación.
                // Se "repetir" é falso significa que ainda non se iniciou, asi que comeza o timer
                // no caso contrario, sáltase o timer para que non se acumule.
                // Esto evita que o timer se sume cada vez máis rapido, polo que só se vai usar unha vez.
                if(repetir == false) {

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
                pulsado1 = true; //pasamos o boolean a "true"

                //se o boolean do boton 2 tamen é "true", o boton "iniciar" volve a estar activo
                // nese caso cando se pulsen os dous botons, o boton "iniciar" activase
                if(pulsado2 == true && pulsado3 == true){

                    //se o numero de ronda actual é inferior ao numero total de rondas, o boton "iniciar" actívase
                    if(rondaActual < nRondas) {
                        empezar.setEnabled(true);
                        rondaActual++;

                    } else {//se o numero de ronda actual é igual ao total, o boton de "resultados" actívase
                        resul.setEnabled(true);
                    }
                }
            }
        });

        /*
        boton xogador 2
         */
        j2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //creamos a seguinte variable para que garde o tempo cando se pulse
                tiempo2 = count;

                puntos2 = puntuacion2(mp, tiempo2, j2p);

                j2.setEnabled(false); //cando faga a sua funcion, o boton queda inutilizado
                pulsado2 = true;

                //repito o mesmo sistema do boton "j1"
                if(pulsado1 == true && pulsado3 == true){
                    if(rondaActual < nRondas) {
                        empezar.setEnabled(true);
                        rondaActual++;
                    } else {
                        resul.setEnabled(true);
                    }
                }
            }
        });

        j3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //creamos a seguinte variable para que garde o tempo cando se pulse
                tiempo3 = count;

                puntos3 = puntuacion3(mp, tiempo3, j3p);

                j3.setEnabled(false); //cando faga a sua funcion, o boton queda inutilizado
                pulsado3 = true; //pasamos o boolean a "true"

                //repito o mesmo sistema do boton "j1"
                if(pulsado2 == true && pulsado1 == true){
                    if(rondaActual < nRondas) {
                        empezar.setEnabled(true);
                        rondaActual++;
                    } else {
                        resul.setEnabled(true);
                    }
                }
            }
        });

        resul.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(tempo_3jugadores.this, Resultados3.class);
                i.putExtra("puntos1", puntos1);
                i.putExtra("puntos2", puntos2);
                i.putExtra("puntos3", puntos3);
                i.putExtra("rondas", nRondas);
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
            puntosTotal1 = puntosTotal1 + 10;
        } else if(i >= ((song.getDuration()*2)-400) && i <= ((song.getDuration()*2)-200) ||
                i >= ((song.getDuration()*2)+200) && i <= ((song.getDuration()*2)+400)){
            jp.setText("Puntuacion: " + 9);
            puntosTotal1 = puntosTotal1 + 9;
        } else if(i >= ((song.getDuration()*2)-600) && i <= ((song.getDuration()*2)-400) ||
                i >= ((song.getDuration()*2)+400) && i <= ((song.getDuration()*2)+600)){
            jp.setText("Puntuacion: " + 8);
            puntosTotal1 = puntosTotal1 + 8;
        } else if(i >= ((song.getDuration()*2)-800) && i <= ((song.getDuration()*2)-600) ||
                i >= ((song.getDuration()*2)+600) && i <= ((song.getDuration()*2)+800)){
            jp.setText("Puntuacion: " + 7);
            puntosTotal1 = puntosTotal1 + 7;
        } else if(i >= ((song.getDuration()*2)-1000) && i <= ((song.getDuration()*2)-800) ||
                i >= ((song.getDuration()*2)+800) && i <= ((song.getDuration()*2)+1000)){
            jp.setText("Puntuacion: " + 6);
            puntosTotal1 = puntosTotal1 + 6;
        } else if(i >= ((song.getDuration()*2)-1200) && i <= ((song.getDuration()*2)-1000) ||
                i >= ((song.getDuration()*2)+1000) && i <= ((song.getDuration()*2)+1200)){
            jp.setText("Puntuacion: " + 5);
            puntosTotal1 = puntosTotal1 + 5;
        } else if(i >= ((song.getDuration()*2)-1400) && i <= ((song.getDuration()*2)-1200) ||
                i >= ((song.getDuration()*2)+1200) && i <= ((song.getDuration()*2)+1400)){
            jp.setText("Puntuacion: " + 4);
            puntosTotal1 = puntosTotal1 + 4;
        } else if(i >= ((song.getDuration()*2)-1600) && i <= ((song.getDuration()*2)-1400) ||
                i >= ((song.getDuration()*2)+1400) && i <= ((song.getDuration()*2)+1600)){
            jp.setText("Puntuacion: " + 3);
            puntosTotal1 = puntosTotal1 + 3;
        } else if(i >= ((song.getDuration()*2)-1800) && i <= ((song.getDuration()*2)-1600) ||
                i >= ((song.getDuration()*2)+1600) && i <= ((song.getDuration()*2)+1800)){
            jp.setText("Puntuacion: " + 2);
            puntosTotal1 = puntosTotal1 + 2;
        } else if(i >= ((song.getDuration()*2)-2000) && i <= ((song.getDuration()*2)-1800) ||
                i >= ((song.getDuration()*2)+1800) && i <= ((song.getDuration()*2)+2000)){
            jp.setText("Puntuacion: " + 1);
            puntosTotal1 = puntosTotal1 + 1;
        } else if(i <= ((song.getDuration()*2)-2000) || i >= ((song.getDuration()*2)+2000)){
            jp.setText("Puntuacion: " + 0);
        }

        return puntosTotal1;

    }

    /*
    metodo para seleccionar a puntuacion do xogador 2
     */
    private int puntuacion2(MediaPlayer song, int i, TextView jp){

        if(i >= ((song.getDuration()*2)-200) && i <= ((song.getDuration()*2)+200)){
            jp.setText("Puntuacion: " + 10);
            puntosTotal2 = puntosTotal2 + 10;
        } else if(i >= ((song.getDuration()*2)-400) && i <= ((song.getDuration()*2)-200) ||
                i >= ((song.getDuration()*2)+200) && i <= ((song.getDuration()*2)+400)){
            jp.setText("Puntuacion: " + 9);
            puntosTotal2 = puntosTotal2 + 9;
        } else if(i >= ((song.getDuration()*2)-600) && i <= ((song.getDuration()*2)-400) ||
                i >= ((song.getDuration()*2)+400) && i <= ((song.getDuration()*2)+600)){
            jp.setText("Puntuacion: " + 8);
            puntosTotal2 = puntosTotal2 + 8;
        } else if(i >= ((song.getDuration()*2)-800) && i <= ((song.getDuration()*2)-600) ||
                i >= ((song.getDuration()*2)+600) && i <= ((song.getDuration()*2)+800)){
            jp.setText("Puntuacion: " + 7);
            puntosTotal2 = puntosTotal2 + 7;
        } else if(i >= ((song.getDuration()*2)-1000) && i <= ((song.getDuration()*2)-800) ||
                i >= ((song.getDuration()*2)+800) && i <= ((song.getDuration()*2)+1000)){
            jp.setText("Puntuacion: " + 6);
            puntosTotal2 = puntosTotal2 + 6;
        } else if(i >= ((song.getDuration()*2)-1200) && i <= ((song.getDuration()*2)-1000) ||
                i >= ((song.getDuration()*2)+1000) && i <= ((song.getDuration()*2)+1200)){
            jp.setText("Puntuacion: " + 5);
            puntosTotal2 = puntosTotal2 + 5;
        } else if(i >= ((song.getDuration()*2)-1400) && i <= ((song.getDuration()*2)-1200) ||
                i >= ((song.getDuration()*2)+1200) && i <= ((song.getDuration()*2)+1400)){
            jp.setText("Puntuacion: " + 4);
            puntosTotal2 = puntosTotal2 + 4;
        } else if(i >= ((song.getDuration()*2)-1600) && i <= ((song.getDuration()*2)-1400) ||
                i >= ((song.getDuration()*2)+1400) && i <= ((song.getDuration()*2)+1600)){
            jp.setText("Puntuacion: " + 3);
            puntosTotal2 = puntosTotal2 + 3;
        } else if(i >= ((song.getDuration()*2)-1800) && i <= ((song.getDuration()*2)-1600) ||
                i >= ((song.getDuration()*2)+1600) && i <= ((song.getDuration()*2)+1800)){
            jp.setText("Puntuacion: " + 2);
            puntosTotal2 = puntosTotal2 + 2;
        } else if(i >= ((song.getDuration()*2)-2000) && i <= ((song.getDuration()*2)-1800) ||
                i >= ((song.getDuration()*2)+1800) && i <= ((song.getDuration()*2)+2000)){
            jp.setText("Puntuacion: " + 1);
            puntosTotal2 = puntosTotal2 + 1;
        } else if(i <= ((song.getDuration()*2)-2000) || i >= ((song.getDuration()*2)+2000)){
            jp.setText("Puntuacion: " + 0);
        }

        return puntosTotal2;

    }

    /*
    metodo para seleccionar a puntuacion do xogador 3
     */
    private int puntuacion3(MediaPlayer song, int i, TextView jp){

        if(i >= ((song.getDuration()*2)-200) && i <= ((song.getDuration()*2)+200)){
            jp.setText("Puntuacion: " + 10);
            puntosTotal3 = puntosTotal3 + 10;
        } else if(i >= ((song.getDuration()*2)-400) && i <= ((song.getDuration()*2)-200) ||
                i >= ((song.getDuration()*2)+200) && i <= ((song.getDuration()*2)+400)){
            jp.setText("Puntuacion: " + 9);
            puntosTotal3 = puntosTotal3 + 9;
        } else if(i >= ((song.getDuration()*2)-600) && i <= ((song.getDuration()*2)-400) ||
                i >= ((song.getDuration()*2)+400) && i <= ((song.getDuration()*2)+600)){
            jp.setText("Puntuacion: " + 8);
            puntosTotal3 = puntosTotal3 + 8;
        } else if(i >= ((song.getDuration()*2)-800) && i <= ((song.getDuration()*2)-600) ||
                i >= ((song.getDuration()*2)+600) && i <= ((song.getDuration()*2)+800)){
            jp.setText("Puntuacion: " + 7);
            puntosTotal3 = puntosTotal3 + 7;
        } else if(i >= ((song.getDuration()*2)-1000) && i <= ((song.getDuration()*2)-800) ||
                i >= ((song.getDuration()*2)+800) && i <= ((song.getDuration()*2)+1000)){
            jp.setText("Puntuacion: " + 6);
            puntosTotal3 = puntosTotal3 + 6;
        } else if(i >= ((song.getDuration()*2)-1200) && i <= ((song.getDuration()*2)-1000) ||
                i >= ((song.getDuration()*2)+1000) && i <= ((song.getDuration()*2)+1200)){
            jp.setText("Puntuacion: " + 5);
            puntosTotal3 = puntosTotal3 + 5;
        } else if(i >= ((song.getDuration()*2)-1400) && i <= ((song.getDuration()*2)-1200) ||
                i >= ((song.getDuration()*2)+1200) && i <= ((song.getDuration()*2)+1400)){
            jp.setText("Puntuacion: " + 4);
            puntosTotal3 = puntosTotal3 + 4;
        } else if(i >= ((song.getDuration()*2)-1600) && i <= ((song.getDuration()*2)-1400) ||
                i >= ((song.getDuration()*2)+1400) && i <= ((song.getDuration()*2)+1600)){
            jp.setText("Puntuacion: " + 3);
            puntosTotal3 = puntosTotal2 + 3;
        } else if(i >= ((song.getDuration()*2)-1800) && i <= ((song.getDuration()*2)-1600) ||
                i >= ((song.getDuration()*2)+1600) && i <= ((song.getDuration()*2)+1800)){
            jp.setText("Puntuacion: " + 2);
            puntosTotal3 = puntosTotal3 + 2;
        } else if(i >= ((song.getDuration()*2)-2000) && i <= ((song.getDuration()*2)-1800) ||
                i >= ((song.getDuration()*2)+1800) && i <= ((song.getDuration()*2)+2000)){
            jp.setText("Puntuacion: " + 1);
            puntosTotal3 = puntosTotal3 + 1;
        } else if(i <= ((song.getDuration()*2)-2000) || i >= ((song.getDuration()*2)+2000)){
            jp.setText("Puntuacion: " + 0);
        }

        return puntosTotal3;

    }

}
