package com.example.prueba;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Resultados4 extends AppCompatActivity {

    private TextView tvGanador, j1, j2, j3, j4, rondas, puntMax;
    private Button bVolver, bRepetir;

    @SuppressLint("ResourceAsColor")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.resultados4);

        tvGanador = findViewById(R.id.text_ganador);
        j1 = findViewById(R.id.jugador);
        j2 = findViewById(R.id.jugador2);
        j3 = findViewById(R.id.jugador3);
        j4 = findViewById(R.id.jugador4);
        bVolver = findViewById(R.id.volver);
        bRepetir = findViewById(R.id.repetir);
        rondas = findViewById(R.id.textView14);
        puntMax = findViewById(R.id.textView15);

        Intent i = getIntent();
        int puntos1 = i.getIntExtra("puntos1", 0);
        int puntos2 = i.getIntExtra("puntos2", 0);
        int puntos3 = i.getIntExtra("puntos3", 0);
        int puntos4 = i.getIntExtra("puntos4", 0);
        final int nRondas = i.getIntExtra("rondas", 0);

        rondas.setText("Numero de rondas: " + nRondas);
        puntMax.setText("Maxima puntuación posible: " + nRondas*10);

        //volve a pantalla de opcions
        bVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                finish();
            }
        });

        //repite a partida cos mesmos datos
        bRepetir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Resultados4.this, tempo_4jugadores.class);
                i.putExtra("rondas", nRondas);
                startActivity(i);
                finish();
            }
        });

        //mostro o gañador con máis puntos
        if(puntos1 > puntos2 && puntos1 > puntos3 && puntos1 > puntos4){
            j1.setText("J1 - " + puntos1 + " punto/s");
            j1.setBackgroundResource(R.color.player1);
            j2.setText("J2 - " + puntos2 + " punto/s");
            j2.setBackgroundResource(R.color.player2);
            j3.setText("J3 - " + puntos3 + " punto/s");
            j3.setBackgroundResource(R.color.player3);
            j4.setText("J4 - " + puntos4 + " punto/s");
            j4.setBackgroundResource(R.color.player4);
        } else if(puntos2 > puntos1 && puntos2 > puntos3 && puntos2 > puntos4){
            j1.setText("J2 - " + puntos2 + " punto/s");
            j1.setBackgroundResource(R.color.player2);
            j2.setText("J1 - " + puntos1 + " punto/s");
            j2.setBackgroundResource(R.color.player1);
            j3.setText("J3 - " + puntos3 + " punto/s");
            j3.setBackgroundResource(R.color.player3);
            j4.setText("J4 - " + puntos4 + " punto/s");
            j4.setBackgroundResource(R.color.player4);
        } else if(puntos3 > puntos1 && puntos3 > puntos2 && puntos3 > puntos4) {
            j1.setText("J3 - " + puntos3 + " punto/s");
            j1.setBackgroundResource(R.color.player3);
            j2.setText("J1 - " + puntos1 + " punto/s");
            j2.setBackgroundResource(R.color.player1);
            j3.setText("J2 - " + puntos2 + " punto/s");
            j3.setBackgroundResource(R.color.player2);
            j4.setText("J4 - " + puntos4 + " punto/s");
            j4.setBackgroundResource(R.color.player4);
        } else if(puntos4 > puntos1 && puntos4 > puntos2 && puntos4 > puntos3) {
            j1.setText("J4 - " + puntos4 + " punto/s");
            j1.setBackgroundResource(R.color.player4);
            j2.setText("J1 - " + puntos1 + " punto/s");
            j2.setBackgroundResource(R.color.player1);
            j3.setText("J2 - " + puntos2 + " punto/s");
            j3.setBackgroundResource(R.color.player2);
            j4.setText("J3 - " + puntos3 + " punto/s");
            j4.setBackgroundResource(R.color.player3);
        } else {
            tvGanador.setText("Empate: ");
            j1.setBackgroundResource(R.color.empate);
            j1.setTextColor(Color.BLACK);
            if (puntos1 == puntos2 && puntos2 == puntos3 && puntos3 == puntos4) {
                j1.setText(puntos1 + " punto/s");
            }
            else if (puntos1 == puntos2 && puntos1 > puntos3 && puntos1 > puntos4) {
                j1.setText("J1 / J2 - " + puntos1 + " punto/s");
                j2.setText("J3 - " + puntos3 + " punto/s");
                j2.setBackgroundResource(R.color.player3);
                j3.setText("J4 - " + puntos4 + " punto/s");
                j3.setBackgroundResource(R.color.player4);
            } else if (puntos1 == puntos3 && puntos1 > puntos2 && puntos1 > puntos4) {
                j1.setText("J1 / J3 - " + puntos1 + " punto/s");
                j2.setText("J2 - " + puntos2 + " punto/s");
                j2.setBackgroundResource(R.color.player2);
                j3.setText("J4 - " + puntos4 + " punto/s");
                j3.setBackgroundResource(R.color.player4);
            } else if (puntos1 == puntos4 && puntos1 > puntos2 && puntos1 > puntos3) {
                j1.setText("J1 / J4 - " + puntos1 + " punto/s");
                j2.setText("J2 - " + puntos2 + " punto/s");
                j2.setBackgroundResource(R.color.player2);
                j3.setText("J3 - " + puntos3 + " punto/s");
                j3.setBackgroundResource(R.color.player3);
            }
            else if (puntos1 == puntos2 && puntos1 == puntos3 && puntos1 > puntos4) {
                j1.setText("J1 / J2 / J3 - " + puntos1 + " punto/s");
                j2.setText("J4 - " + puntos4 + " punto/s");
                j2.setBackgroundResource(R.color.player4);
            } else if (puntos1 == puntos3 && puntos1 > puntos2 && puntos1 == puntos4) {
                j1.setText("J1 / J3 / J4 - " + puntos1 + " punto/s");
                j2.setText("J2 - " + puntos2 + " punto/s");
                j2.setBackgroundResource(R.color.player2);
            } else if (puntos1 == puntos4 && puntos1 == puntos2 && puntos1 > puntos3) {
                j1.setText("J1 / J2 / J4 - " + puntos1 + " punto/s");
                j2.setText("J3 - " + puntos3 + " punto/s");
                j2.setBackgroundResource(R.color.player3);
            }
            else if (puntos2 > puntos1 && puntos2 == puntos3 && puntos2 > puntos4) {
                j1.setText("J2 / J3 - " + puntos2 + " punto/s");
                j2.setText("J1 - " + puntos1 + " punto/s");
                j2.setBackgroundResource(R.color.player1);
                j3.setText("J4 - " + puntos4 + " punto/s");
                j3.setBackgroundResource(R.color.player4);
            } else if (puntos2 > puntos1 && puntos2 > puntos3 && puntos2 == puntos4) {
                j1.setText("J2 / J4 - " + puntos2 + " punto/s");
                j2.setText("J1 - " + puntos1 + " punto/s");
                j2.setBackgroundResource(R.color.player1);
                j3.setText("J3 - " + puntos3 + " punto/s");
                j3.setBackgroundResource(R.color.player3);
            } else if (puntos3 > puntos1 && puntos3 > puntos2 && puntos3 == puntos4) {
                j1.setText("J3 / J4 - " + puntos3 + " punto/s");
                j2.setText("J1 - " + puntos1 + " punto/s");
                j2.setBackgroundResource(R.color.player1);
                j3.setText("J2 - " + puntos2 + " punto/s");
                j3.setBackgroundResource(R.color.player2);
            }
            else if (puntos2 > puntos1 && puntos2 == puntos3 && puntos2 == puntos4) {
                j1.setText("J2 / J3 / J4 - " + puntos2 + " punto/s");
                j2.setText("J1 - " + puntos1 + " punto/s");
                j2.setBackgroundResource(R.color.player1);
            }
        }
    }
}
